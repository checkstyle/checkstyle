////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Simple plain logger for text output.
 * This is maybe not very suitable for a text output into a file since it
 * does not need all 'audit finished' and so on stuff, but it looks good on
 * stdout anyway. If there is really a problem this is what XMLLogger is for.
 * It gives structure.
 *
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 * @see XMLLogger
 */
public class DefaultLogger extends AutomaticBean implements AuditListener {

    /**
     * A key pointing to the add exception
     * message in the "messages.properties" file.
     */
    public static final String ADD_EXCEPTION_MESSAGE = "DefaultLogger.addException";
    /**
     * A key pointing to the started audit
     * message in the "messages.properties" file.
     */
    public static final String AUDIT_STARTED_MESSAGE = "DefaultLogger.auditStarted";
    /**
     * A key pointing to the finished audit
     * message in the "messages.properties" file.
     */
    public static final String AUDIT_FINISHED_MESSAGE = "DefaultLogger.auditFinished";

    /** Where to write info messages. **/
    private final PrintWriter infoWriter;
    /** Close info stream after use. */
    private final boolean closeInfo;

    /** Where to write error messages. **/
    private final PrintWriter errorWriter;
    /** Close error stream after use. */
    private final boolean closeError;

    /** Formatter for the log message. */
    private final AuditEventFormatter formatter;

    /**
     * Creates a new {@code DefaultLogger} instance.
     * @param outputStream where to log infos and errors
     * @param closeStreamsAfterUse if oS should be closed in auditFinished()
     * @noinspection BooleanParameter
     */
    public DefaultLogger(OutputStream outputStream, boolean closeStreamsAfterUse) {
        // no need to close oS twice
        this(outputStream, closeStreamsAfterUse, outputStream, false);
    }

    /**
     * Creates a new {@code DefaultLogger} instance.
     * @param infoStream the {@code OutputStream} for info messages.
     * @param closeInfoAfterUse auditFinished should close infoStream.
     * @param errorStream the {@code OutputStream} for error messages.
     * @param closeErrorAfterUse auditFinished should close errorStream
     * @noinspection BooleanParameter
     */
    public DefaultLogger(OutputStream infoStream,
                         boolean closeInfoAfterUse,
                         OutputStream errorStream,
                         boolean closeErrorAfterUse) {
        this(infoStream, closeInfoAfterUse, errorStream, closeErrorAfterUse,
            new AuditEventDefaultFormatter());
    }

    /**
     * Creates a new {@code DefaultLogger} instance.
     *
     * @param infoStream the {@code OutputStream} for info messages
     * @param closeInfoAfterUse auditFinished should close infoStream
     * @param errorStream the {@code OutputStream} for error messages
     * @param closeErrorAfterUse auditFinished should close errorStream
     * @param messageFormatter formatter for the log message.
     * @noinspection BooleanParameter
     */
    public DefaultLogger(OutputStream infoStream,
                         boolean closeInfoAfterUse,
                         OutputStream errorStream,
                         boolean closeErrorAfterUse,
                         AuditEventFormatter messageFormatter) {
        closeInfo = closeInfoAfterUse;
        closeError = closeErrorAfterUse;
        final Writer infoStreamWriter = new OutputStreamWriter(infoStream, StandardCharsets.UTF_8);
        infoWriter = new PrintWriter(infoStreamWriter);

        if (infoStream == errorStream) {
            errorWriter = infoWriter;
        }
        else {
            final Writer errorStreamWriter = new OutputStreamWriter(errorStream,
                    StandardCharsets.UTF_8);
            errorWriter = new PrintWriter(errorStreamWriter);
        }
        formatter = messageFormatter;
    }

    /**
     * Print an Emacs compliant line on the error stream.
     * If the column number is non zero, then also display it.
     * @see AuditListener
     **/
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
        synchronized (errorWriter) {
            final LocalizedMessage addExceptionMessage = new LocalizedMessage(0,
                Definitions.CHECKSTYLE_BUNDLE, ADD_EXCEPTION_MESSAGE,
                new String[] {event.getFileName()}, null,
                LocalizedMessage.class, null);
            errorWriter.println(addExceptionMessage.getMessage());
            throwable.printStackTrace(errorWriter);
        }
    }

    @Override
    public void auditStarted(AuditEvent event) {
        final LocalizedMessage auditStartMessage = new LocalizedMessage(0,
            Definitions.CHECKSTYLE_BUNDLE, AUDIT_STARTED_MESSAGE, null, null,
            LocalizedMessage.class, null);
        infoWriter.println(auditStartMessage.getMessage());
        infoWriter.flush();
    }

    @Override
    public void auditFinished(AuditEvent event) {
        final LocalizedMessage auditFinishMessage = new LocalizedMessage(0,
            Definitions.CHECKSTYLE_BUNDLE, AUDIT_FINISHED_MESSAGE, null, null,
            LocalizedMessage.class, null);
        infoWriter.println(auditFinishMessage.getMessage());
        closeStreams();
    }

    @Override
    public void fileStarted(AuditEvent event) {
        // No need to implement this method in this class
    }

    @Override
    public void fileFinished(AuditEvent event) {
        infoWriter.flush();
    }

    /**
     * Flushes the output streams and closes them if needed.
     */
    private void closeStreams() {
        infoWriter.flush();
        if (closeInfo) {
            infoWriter.close();
        }

        errorWriter.flush();
        if (closeError) {
            errorWriter.close();
        }
    }
}
