/*
 *
 * Enginuity Open-Source Tuning, Logging and Reflashing
 * Copyright (C) 2006 Enginuity.org
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 */

package enginuity.logger.ecu.comms.controller;

import enginuity.logger.ecu.comms.query.LoggerCallback;
import enginuity.logger.ecu.definition.EcuSwitch;
import enginuity.logger.ecu.definition.LoggerData;
import enginuity.logger.ecu.ui.StatusChangeListener;

public interface LoggerController {

    void setFileLoggerSwitch(EcuSwitch ecuSwitch, LoggerCallback callback);

    void addLogger(String callerId, LoggerData loggerData, LoggerCallback callback);

    void removeLogger(String callerId, LoggerData loggerData);

    boolean isStarted();

    void start();

    void stop();

    void addListener(StatusChangeListener listener);
}