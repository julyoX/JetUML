/*******************************************************************************
 * JetUML - A desktop application for fast UML diagramming.
 *
 * Copyright (C) 2025 by McGill University.
 *     
 * See: https://github.com/prmr/JetUML
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses.
 *******************************************************************************/
package org.jetuml.gui;

import static org.jetuml.application.ApplicationResources.RESOURCES;
import java.io.IOException;
import org.jetuml.persistence.DeserializationException;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class DeserializationErrorAlert extends Alert {

    private enum ErrorCategory {
        IO("error.open_file_io"),
        DESERIALIZATION("error.open_file_deserialization");

        private final String baseKey;

        ErrorCategory(String baseKey) {
            this.baseKey = baseKey;
        }

        public String getHeaderKey() {
            return baseKey;
        }

        public String getDetailsKey() {
            return baseKey + "_details";
        }
    }

    public DeserializationErrorAlert(Exception pException) {
        super(AlertType.ERROR, "", ButtonType.OK);
        assert pException instanceof IOException || pException instanceof DeserializationException;

        ErrorCategory category = determineCategory(pException);

        setTitle(RESOURCES.getString("alert.error.title"));
        setHeaderText(RESOURCES.getString(category.getHeaderKey()));

        StringBuilder contentText = new StringBuilder(RESOURCES.getString(category.getDetailsKey()));
        contentText.append(" ")
                   .append(RESOURCES.getString("alert.error.details"))
                   .append(" ")
                   .append(pException.getMessage());

        setContentText(contentText.toString());
    }


    private static ErrorCategory determineCategory(Exception pException) {
        if (pException instanceof IOException) {
            return ErrorCategory.IO;
        } else {
            return ErrorCategory.DESERIALIZATION;
        }
    }
}
