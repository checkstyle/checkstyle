////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.io.OutputStream;
import java.util.function.Function;

import com.puppycrawl.tools.checkstyle.AuditEventFormatter;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;

/**
 * A brief logger that only display info about errors.
 */
public class BriefUtLogger extends DefaultLogger {

    /**
     * A factory to create BriefUtLogger.
     */
    public enum Factory implements Function<OutputStream, BriefUtLogger> {
        DEFAULT(new AuditEventUtFormatter()),
        DETAIL(new DetailAuditEventUtFormatter());

        private final AuditEventFormatter formatter;

        Factory(final AuditEventFormatter formatter) {
            this.formatter = formatter;
        }

        @Override
        public BriefUtLogger apply(OutputStream out) {
            return new BriefUtLogger(out, formatter);
        }
    }

    /**
     * Creates BriefLogger object.
     * @param out output stream for info messages and errors.
     */
    public BriefUtLogger(OutputStream out) {
        this(out, new AuditEventUtFormatter());
    }

    protected BriefUtLogger(OutputStream out, AuditEventFormatter eventFormatter) {
        super(out, OutputStreamOptions.CLOSE, out,
                OutputStreamOptions.NONE, eventFormatter);
    }

    @Override
    public void auditStarted(AuditEvent event) {
        // has to NOT log audit started event
    }

    /**
     * This {link AuditEventFormatter} implementation enriches {@code AuditEvent} formatting
     * of the {@link AuditEventUtFormatter} by appending the {@link LocalizedMessage} attributes.
     */
    private static final class DetailAuditEventUtFormatter implements AuditEventFormatter {
        private final AuditEventFormatter core = new AuditEventUtFormatter();

        @Override
        public String format(AuditEvent event) {
            final LocalizedMessage locMsg = event.getLocalizedMessage();
            final StringBuilder msgBuilder = new StringBuilder(core.format(event));
            if (locMsg.getTokenType() > 0) {
                msgBuilder.append(": [TokenType : ").append(locMsg.getTokenType()).append(']');
            }
            return msgBuilder.toString();
        }
    }
}
