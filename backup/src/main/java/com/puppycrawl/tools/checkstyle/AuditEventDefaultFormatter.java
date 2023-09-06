///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.util.Locale;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Represents the default formatter for log message.
 * Default log message format is:
 * [SEVERITY LEVEL] filePath:lineNo:columnNo: message. [CheckName]
 * When the module id of the message has been set, the format is:
 * [SEVERITY LEVEL] filePath:lineNo:columnNo: message. [ModuleId]
 */
public class AuditEventDefaultFormatter implements AuditEventFormatter {

    /** Length of all separators. */
    private static final int LENGTH_OF_ALL_SEPARATORS = 10;

    /** Suffix of module names like XXXXCheck. */
    private static final String SUFFIX = "Check";

    @Override
    public String format(AuditEvent event) {
        final String fileName = event.getFileName();
        final String message = event.getMessage();

        final SeverityLevel severityLevel = event.getSeverityLevel();
        final String severityLevelName;
        if (severityLevel == SeverityLevel.WARNING) {
            // We change the name of severity level intentionally
            // to shorten the length of the log message.
            severityLevelName = "WARN";
        }
        else {
            severityLevelName = severityLevel.getName().toUpperCase(Locale.US);
        }

        // Avoid StringBuffer.expandCapacity
        final int bufLen = calculateBufferLength(event, severityLevelName.length());
        final StringBuilder sb = new StringBuilder(bufLen);

        sb.append('[').append(severityLevelName).append("] ")
            .append(fileName).append(':').append(event.getLine());
        if (event.getColumn() > 0) {
            sb.append(':').append(event.getColumn());
        }
        sb.append(": ").append(message).append(" [");
        if (event.getModuleId() == null) {
            final String checkShortName = getCheckShortName(event);
            sb.append(checkShortName);
        }
        else {
            sb.append(event.getModuleId());
        }
        sb.append(']');

        return sb.toString();
    }

    /**
     * Returns the length of the buffer for StringBuilder.
     * bufferLength = fileNameLength + messageLength + lengthOfAllSeparators +
     * + severityNameLength + checkNameLength.
     *
     * @param event audit event.
     * @param severityLevelNameLength length of severity level name.
     * @return the length of the buffer for StringBuilder.
     */
    private static int calculateBufferLength(AuditEvent event, int severityLevelNameLength) {
        return LENGTH_OF_ALL_SEPARATORS + event.getFileName().length()
            + event.getMessage().length() + severityLevelNameLength
            + getCheckShortName(event).length();
    }

    /**
     * Returns check name without 'Check' suffix.
     *
     * @param event audit event.
     * @return check name without 'Check' suffix.
     */
    private static String getCheckShortName(AuditEvent event) {
        final String checkFullName = event.getSourceName();
        String checkShortName = checkFullName.substring(checkFullName.lastIndexOf('.') + 1);
        if (checkShortName.endsWith(SUFFIX)) {
            final int endIndex = checkShortName.length() - SUFFIX.length();
            checkShortName = checkShortName.substring(0, endIndex);
        }
        return checkShortName;
    }

}
