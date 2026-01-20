///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal.utils;

import java.io.OutputStream;

import com.puppycrawl.tools.checkstyle.AbstractAutomaticBean.OutputStreamOptions;
import com.puppycrawl.tools.checkstyle.DefaultLogger;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

/**
 * A brief logger that only display info about errors.
 */
public class BriefUtLogger implements AuditListener {

    /** The wrapped DefaultLogger instance. */
    private final DefaultLogger delegate;

    /**
     * Creates BriefLogger object.
     *
     * @param out output stream for info messages and errors.
     */
    public BriefUtLogger(OutputStream out) {
        delegate = new DefaultLogger(out, OutputStreamOptions.CLOSE, out,
                OutputStreamOptions.NONE, new AuditEventUtFormatter());
    }

    @Override
    public void auditStarted(AuditEvent event) {
        // has to NOT log audit started event
    }

    @Override
    public void auditFinished(AuditEvent event) {
        delegate.auditFinished(event);
    }

    @Override
    public void fileStarted(AuditEvent event) {
        delegate.fileStarted(event);
    }

    @Override
    public void fileFinished(AuditEvent event) {
        delegate.fileFinished(event);
    }

    @Override
    public void addError(AuditEvent event) {
        delegate.addError(event);
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        delegate.addException(event, throwable);
    }

}
