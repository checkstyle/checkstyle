package com.mycompany.listeners;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * An AuditListener that reports every event to standard output.
 * @author Rick Giles
 */
public class VerboseListener
    implements AuditListener
{
    /** total number of errors and exceptions */
    private int mTotalErrors;

    /** number of errors and exceptions in the audit of one file */
    private int mErrors;

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void auditStarted(AuditEvent aEvt)
    {
        mTotalErrors = 0;
        System.out.println("Audit started.");
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void auditFinished(AuditEvent aEvt)
    {
        System.out.println("Audit finished. Total errors: " + mTotalErrors);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void fileStarted(AuditEvent aEvt)
    {
        mErrors = 0;
        System.out.println(
            "Started checking file '" + aEvt.getFileName() + "'.");
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void fileFinished(AuditEvent aEvt)
    {
        System.out.println("Finished checking file '" + aEvt.getFileName()
            + "'. Errors: " + mErrors);
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void addError(AuditEvent aEvt)
    {
        printEvent(aEvt);
        if (SeverityLevel.ERROR.equals(aEvt.getSeverityLevel())) {
            mErrors++;
            mTotalErrors++;
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        printEvent(aEvt);
        aThrowable.printStackTrace(System.out);
        mErrors++;
        mTotalErrors++;
    }

    /**
     * Prints event information to standard output.
     * @param aEvt the event to print.
     */
    private void printEvent(AuditEvent aEvt)
    {
        System.out.println("Logging error -"
            + " file: '" + aEvt.getFileName() + "'"
            + " line: " + aEvt.getLine()
            + " column: " + aEvt.getColumn()
            + " severity: " + aEvt.getSeverityLevel()
            + " message: " + aEvt.getMessage()
            + " source: " + aEvt.getSourceName());
    }


}
