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
import java.nio.charset.StandardCharsets;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Simple logger for metadata generator util.
 */
public class MetadataGeneratorLogger extends AbstractAutomaticBean implements AuditListener {

    /**
     * Where to write writer messages.
     */
    private final PrintWriter writer;

    /**
     * Formatter for the log message.
     */
    private final AuditEventFormatter formatter = new AuditEventDefaultFormatter();;

    /**
     * Close output stream in audit finished.
     */
    private final boolean closeWriter;

    /**
     * Creates a new MetadataGeneratorLogger instance.
     *
     * @param outputStream where to log audit events
     * @param options if {@code CLOSE} writer should be closed in auditFinished()
     */
    public MetadataGeneratorLogger(OutputStream outputStream, OutputStreamOptions options) {
        writer = new PrintWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));
        closeWriter = options == OutputStreamOptions.CLOSE;
    }

    @Override
    public void auditStarted(AuditEvent event) {
        writer.flush();
    }

    @Override
    public void auditFinished(AuditEvent event) {
        writer.flush();
        if (closeWriter) {
            writer.close();
        }
    }

    @Override
    public void fileStarted(AuditEvent event) {
    }

    @Override
    public void fileFinished(AuditEvent event) {
        writer.flush();
    }

    @Override
    public void addError(AuditEvent event) {
        if (event.getSeverityLevel() != SeverityLevel.IGNORE) {
            writer.println(formatter.format(event));
        }
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        throwable.printStackTrace(writer);
    }

    @Override
    protected void finishLocalSetup() {
    }
}
