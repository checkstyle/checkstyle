///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package com.puppycrawl.tools.checkstyle.internal.utils;

import com.puppycrawl.tools.checkstyle.AuditEventFormatter;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;

/**
 * Represents the formatter for log message which is used in UTs.
 * Message format is: filePath:lineNo:columnNo: message.
 */
public class AuditEventUtFormatter implements AuditEventFormatter {

    /** Length of all separators. */
    private static final int LENGTH_OF_ALL_SEPARATORS = 4;

    @Override
    public String format(AuditEvent event) {
        final String fileName = event.getFileName();
        final String message = event.getMessage();

        // avoid StringBuffer.expandCapacity
        final int bufLen = event.getFileName().length() + event.getMessage().length()
            + LENGTH_OF_ALL_SEPARATORS;
        final StringBuilder sb = new StringBuilder(bufLen);

        sb.append(fileName).append(':').append(event.getLine());
        if (event.getColumn() > 0) {
            sb.append(':').append(event.getColumn());
        }
        sb.append(": ").append(message);

        return sb.toString();
    }

}
