package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

/**
 * Simple logger for metadata generator util.
 */
public class MetadataGeneratorLogger extends AutomaticBean implements AuditListener {

    /** Where to write error messages. **/
    private final PrintWriter errorWriter;

    /** Formatter for the log message. */
    private final AuditEventFormatter formatter;

    /**
     * Creates a new {@link MetadataGeneratorLogger} instance.
     *
     * @param outputStream where to log audit events
     */
    public MetadataGeneratorLogger(OutputStream outputStream) {
        final Writer errorStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        this.errorWriter = new PrintWriter(errorStreamWriter);
        this.formatter = new AuditEventDefaultFormatter();
    }

    @Override
    public void auditStarted(AuditEvent event) {
        errorWriter.flush();
    }

    @Override
    public void auditFinished(AuditEvent event) {
        errorWriter.flush();
    }

    @Override
    public void fileStarted(AuditEvent event) {

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
        synchronized (errorWriter) {
            throwable.printStackTrace(errorWriter);
        }
    }

    @Override
    protected void finishLocalSetup() throws CheckstyleException {
        // No code by default.
    }
}
