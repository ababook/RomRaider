package enginuity.logger.ui;

import enginuity.Settings;
import enginuity.logger.LoggerController;
import enginuity.logger.LoggerControllerImpl;
import enginuity.logger.definition.EcuData;
import enginuity.logger.query.LoggerCallback;
import enginuity.logger.ui.handler.DataUpdateHandlerManager;
import static enginuity.util.ParamChecker.checkNotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class ParameterRegistrationBrokerImpl implements ParameterRegistrationBroker {
    private final LoggerController controller;
    private final DataUpdateHandlerManager handlerManager;
    private final List<EcuData> registeredEcuParameters = Collections.synchronizedList(new ArrayList<EcuData>());
    private long loggerStartTime = 0;

    public ParameterRegistrationBrokerImpl(DataUpdateHandlerManager handlerManager, Settings settings, MessageListener messageListener) {
        checkNotNull(handlerManager, settings, messageListener);
        this.handlerManager = handlerManager;
        this.controller = new LoggerControllerImpl(settings, messageListener);
    }

    public synchronized void registerEcuParameterForLogging(final EcuData ecuData) {
        if (!registeredEcuParameters.contains(ecuData)) {
            // register param with handlers
            handlerManager.registerData(ecuData);

            // add logger and setup callback
            controller.addLogger(ecuData, new LoggerCallback() {
                public void callback(byte[] value) {
                    // update handlers
                    handlerManager.handleDataUpdate(ecuData, value, System.currentTimeMillis() - loggerStartTime);
                }
            });

            // add to registered parameters list
            registeredEcuParameters.add(ecuData);
        }
    }

    public synchronized void deregisterEcuParameterFromLogging(EcuData ecuData) {
        if (registeredEcuParameters.contains(ecuData)) {
            // remove logger
            controller.removeLogger(ecuData);

            // deregister param from handlers
            handlerManager.deregisterData(ecuData);

            // remove from registered list
            registeredEcuParameters.remove(ecuData);
        }

    }

    public List<String> listSerialPorts() {
        return controller.listSerialPorts();
    }


    public synchronized void start() {
        loggerStartTime = System.currentTimeMillis();
        controller.start();
    }

    public synchronized void stop() {
        controller.stop();
    }

}