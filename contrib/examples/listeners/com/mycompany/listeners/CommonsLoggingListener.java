package com.mycompany.listeners;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogConfigurationException;
import org.apache.commons.logging.LogFactory;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * Jakarta Commons Logging listener.
 * Note: With Ant, do not use the SimpleLog as your logger implementation as it
 * causes an infinite loop since it writes to System.err, which Ant traps
 * and reroutes to the logger/listener layer.
 * Based on
 * <a href="http://ant.apache.org/index.html">org.apache.tools.ant.listener.CommonsLoggingListener>org.apache.tools.ant.listener.CommonsLoggingListener</a>
 * @author Rick Giles
 */
public class CommonsLoggingListener
    implements AuditListener
{
    /** cushion for avoiding StringBuffer.expandCapacity */
    private static final int BUFFER_CUSHION = 30;

    /** true if the log factory has been initialized */
    private boolean mInitialized = false;

    /** Factory for creating org.apache.commons.logging.Log instances */
    private LogFactory mLogFactory;

    /**
     * Creates a <code>CommonsLoggingListener. Initializes its log factory.
     * @throws CheckstyleException if  if the implementation class is not
     * available or cannot be instantiated.
     */
    public CommonsLoggingListener() throws CheckstyleException
    {
        try {
            mLogFactory = LogFactory.getFactory();
        }
        catch (LogConfigurationException e) {
            throw new CheckstyleException("log configuration exception", e);
        }
        mInitialized = true;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void auditStarted(AuditEvent aEvt)
    {
        if (mInitialized) {
            final Log log = mLogFactory.getInstance(Checker.class);
            log.info("Audit started.");
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void auditFinished(AuditEvent aEvt)
    {
        if (mInitialized) {
            final Log log = mLogFactory.getInstance(Checker.class);
            log.info("Audit finished.");
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void fileStarted(AuditEvent aEvt)
    {
        if (mInitialized) {
            final Log log = mLogFactory.getInstance(Checker.class);
            log.info("File \"" + aEvt.getFileName() + "\" started.");
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void fileFinished(AuditEvent aEvt)
    {
        if (mInitialized) {
            final Log log = mLogFactory.getInstance(Checker.class);
            log.info("File \"" + aEvt.getFileName() + "\" finished.");
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void addError(AuditEvent aEvt)
    {
        final SeverityLevel severityLevel = aEvt.getSeverityLevel();
        if (mInitialized && !SeverityLevel.IGNORE.equals(severityLevel)) {
            final Log log = mLogFactory.getInstance(aEvt.getSourceName());

            final String fileName = aEvt.getFileName();
            final String message = aEvt.getMessage();

            // avoid StringBuffer.expandCapacity
            final int bufLen = message.length() + BUFFER_CUSHION;
            final StringBuffer sb = new StringBuffer(bufLen);

            sb.append("Line: ").append(aEvt.getLine());
            if (aEvt.getColumn() > 0) {
                sb.append(" Column: ").append(aEvt.getColumn());
            }
            sb.append(" Message: ").append(message);

            if (aEvt.getSeverityLevel().equals(SeverityLevel.WARNING)) {
                log.warn(sb.toString());
            }
            else if (aEvt.getSeverityLevel().equals(SeverityLevel.INFO)) {
                log.info(sb.toString());
            }
            else {
                log.error(sb.toString());
            }
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        if (mInitialized) {
            final Log log = mLogFactory.getInstance(aEvt.getSourceName());
            log.error("Error auditing " + aEvt.getFileName(), aThrowable);
        }
    }

}
