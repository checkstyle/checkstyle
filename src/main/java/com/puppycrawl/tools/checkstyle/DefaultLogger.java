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
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Simple plain logger for text output.
 * This is maybe not very suitable for a text output into a file since it
 * does not need all 'audit finished' and so on stuff, but it looks good on
 * stdout anyway. If there is really a problem this is what XMLLogger is for.
 * It gives structure.
 *
 * @see XMLLogger
 */
public class DefaultLogger extends AbstractAutomaticBean implements AuditListener {

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
     *
     * @param outputStream where to log audit events
     * @param outputStreamOptions if {@code CLOSE} that should be closed in auditFinished()
     * @noinspection deprecation
     * @noinspectionreason We are forced to keep AutomaticBean compatability
     *     because of maven-checkstyle-plugin. Until #12873.
     */
    public DefaultLogger(OutputStream outputStream,
                         AutomaticBean.OutputStreamOptions outputStreamOptions) {
        this(outputStream, OutputStreamOptions.valueOf(outputStreamOptions.name()));
    }

    /**
     * Creates a new {@code DefaultLogger} instance.
     *
     * @param outputStream where to log audit events
     * @param outputStreamOptions if {@code CLOSE} that should be closed in auditFinished()
     */
    public DefaultLogger(OutputStream outputStream, OutputStreamOptions outputStreamOptions) {
        // no need to close oS twice
        this(outputStream, outputStreamOptions, outputStream, OutputStreamOptions.NONE);
    }

    /**
     * Creates a new {@code DefaultLogger} instance.
     *
     * @param infoStream the {@code OutputStream} for info messages.
     * @param infoStreamOptions if {@code CLOSE} info should be closed in auditFinished()
     * @param errorStream the {@code OutputStream} for error messages.
     * @param errorStreamOptions if {@code CLOSE} error should be closed in auditFinished()
     */
    public DefaultLogger(OutputStream infoStream,
                         OutputStreamOptions infoStreamOptions,
                         OutputStream errorStream,
                         OutputStreamOptions errorStreamOptions) {
        this(infoStream, infoStreamOptions, errorStream, errorStreamOptions,
                new AuditEventDefaultFormatter());
    }

    /**
     * Creates a new {@code DefaultLogger} instance.
     *
     * @param infoStream the {@code OutputStream} for info messages
     * @param infoStreamOptions if {@code CLOSE} info should be closed in auditFinished()
     * @param errorStream the {@code OutputStream} for error messages
     * @param errorStreamOptions if {@code CLOSE} error should be closed in auditFinished()
     * @param messageFormatter formatter for the log message.
     * @throws IllegalArgumentException if stream options are null
     * @noinspection WeakerAccess
     * @noinspectionreason WeakerAccess - we avoid 'protected' when possible
     */
    public DefaultLogger(OutputStream infoStream,
                         OutputStreamOptions infoStreamOptions,
                         OutputStream errorStream,
                         OutputStreamOptions errorStreamOptions,
                         AuditEventFormatter messageFormatter) {
        if (infoStreamOptions == null) {
            throw new IllegalArgumentException("Parameter infoStreamOptions can not be null");
        }
        closeInfo = infoStreamOptions == OutputStreamOptions.CLOSE;
        if (errorStreamOptions == null) {
            throw new IllegalArgumentException("Parameter errorStreamOptions can not be null");
        }
        closeError = errorStreamOptions == OutputStreamOptions.CLOSE;
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

    @Override
    protected void finishLocalSetup() {
        // No code by default
    }

    /**
     * Print an Emacs compliant line on the error stream.
     * If the column number is non-zero, then also display it.
     *
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
        final LocalizedMessage exceptionMessage = new LocalizedMessage(
                Definitions.CHECKSTYLE_BUNDLE, DefaultLogger.class,
                ADD_EXCEPTION_MESSAGE, event.getFileName());
        errorWriter.println(exceptionMessage.getMessage());
        throwable.printStackTrace(errorWriter);
    }

    @Override
    public void auditStarted(AuditEvent event) {
        final LocalizedMessage auditStartMessage = new LocalizedMessage(
                Definitions.CHECKSTYLE_BUNDLE, DefaultLogger.class,
                AUDIT_STARTED_MESSAGE);
        infoWriter.println(auditStartMessage.getMessage());
        infoWriter.flush();
    }

    @Override
    public void auditFinished(AuditEvent event) {
        final LocalizedMessage auditFinishMessage = new LocalizedMessage(
                Definitions.CHECKSTYLE_BUNDLE, DefaultLogger.class,
                AUDIT_FINISHED_MESSAGE);
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
