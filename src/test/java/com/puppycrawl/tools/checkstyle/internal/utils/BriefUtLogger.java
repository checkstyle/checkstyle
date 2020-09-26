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

import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;

/**
 * A brief logger that only display info about errors.
 */
public class BriefUtLogger extends DefaultLogger {

    /**
     * Creates BriefLogger object.
     *
     * @param out output stream for info messages and errors.
     */
    public BriefUtLogger(OutputStream out) {
        this(out, OutputStreamOptions.CLOSE, out, OutputStreamOptions.NONE);
    }

    /**
     * Creates BriefLogger object.
     *
     * @param infoStream
     *            the {@code OutputStream} for info messages
     * @param infoStreamOptions
     *            if {@code CLOSE} info should be closed in auditFinished()
     * @param errorStream
     *            the {@code OutputStream} for error messages
     * @param errorStreamOptions
     *            if {@code CLOSE} error should be closed in auditFinished()
     */
    public BriefUtLogger(OutputStream infoStream,
            OutputStreamOptions infoStreamOptions, OutputStream errorStream,
            OutputStreamOptions errorStreamOptions) {
        super(infoStream, infoStreamOptions, errorStream, errorStreamOptions,
                new AuditEventUtFormatter());
    }

    @Override
    public void auditStarted(AuditEvent event) {
        // has to NOT log audit started event
    }

}
