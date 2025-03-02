///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Simple logger for metadata generator util.
 */
public class MetadataGeneratorLogger extends AbstractAutomaticBean implements AuditListener {

    /**
     * Where to write error messages.
     */
    private final PrintWriter errorWriter;

    /**
     * Formatter for the log message.
     */
    private final AuditEventFormatter formatter;

    /**
     * Close output stream in audit finished.
     */
    private final boolean closeErrorWriter;

    /**
     * Creates a new MetadataGeneratorLogger instance.
     *
     * @param outputStream where to log audit events
     * @param outputStreamOptions if {@code CLOSE} error should be closed in auditFinished()
     */
    public MetadataGeneratorLogger(OutputStream outputStream,
            OutputStreamOptions outputStreamOptions) {
        final Writer errorStreamWriter = new OutputStreamWriter(outputStream,
                StandardCharsets.UTF_8);
        errorWriter = new PrintWriter(errorStreamWriter);
        formatter = new AuditEventDefaultFormatter();
        closeErrorWriter = outputStreamOptions == OutputStreamOptions.CLOSE;
    }

    @Override
    public void auditStarted(AuditEvent event) {
        errorWriter.flush();
    }

    @Override
    public void auditFinished(AuditEvent event) {
        errorWriter.flush();
        if (closeErrorWriter) {
            errorWriter.close();
        }
    }

    @Override
    public void fileStarted(AuditEvent event) {
        // No code by default.
    }

    @Override
    public void fileFinished(AuditEvent event) {
        errorWriter.flush();
    }

    @Override
    public void addError(AuditEvent event) {
        final SeverityLevel severityLevel = event.getSeverityLevel();
        if (severityLevel != SeverityLevel.IGNORE) {
            final String errorMessage = formatter.format(event);
            errorWriter.println(errorMessage);
        }
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        throwable.printStackTrace(errorWriter);
    }

    @Override
    protected void finishLocalSetup() {
        // No code by default.
    }
}
