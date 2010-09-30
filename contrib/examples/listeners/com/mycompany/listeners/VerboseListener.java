package com.mycompany.listeners;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;
import com.puppycrawl.tools.checkstyle.api.AutomaticBean;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/**
 * An AuditListener that reports every event to an output stream.
 * @author Rick Giles
 */
public class VerboseListener
    extends AutomaticBean
    implements AuditListener
{
    /** where to write messages */
    private PrintWriter mWriter = new PrintWriter(System.out);

    /** close output stream */   
    private boolean mCloseOut = false;
    
    /** total number of errors and exceptions */
    private int mTotalErrors;

    /** number of errors and exceptions in the audit of one file */
    private int mErrors;

    /**
     * Sets the output stream to a file.
     * @param aFileName name of the output file.
     * @throws FileNotFoundException if an error occurs.
     */
    public void setFile(String aFileName)
        throws FileNotFoundException
    {
        final OutputStream out = new FileOutputStream(aFileName);
        mWriter = new PrintWriter(out);
        mCloseOut = true;
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void auditStarted(AuditEvent aEvt)
    {
        mTotalErrors = 0;
        mWriter.println("Audit started.");
    }
    
    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void auditFinished(AuditEvent aEvt)
    {
        mWriter.println("Audit finished. Total errors: " + mTotalErrors);
        mWriter.flush();
        if (mCloseOut) {
            mWriter.close();
        }
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void fileStarted(AuditEvent aEvt)
    {
        mErrors = 0;
        mWriter.println(
            "Started checking file '" + aEvt.getFileName() + "'.");
    }

    /** @see com.puppycrawl.tools.checkstyle.api.AuditListener */
    public void fileFinished(AuditEvent aEvt)
    {
        mWriter.println("Finished checking file '" + aEvt.getFileName()
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
        mWriter.println("Logging error -"
            + " file: '" + aEvt.getFileName() + "'"
            + " line: " + aEvt.getLine()
            + " column: " + aEvt.getColumn()
            + " severity: " + aEvt.getSeverityLevel()
            + " message: " + aEvt.getMessage()
            + " source: " + aEvt.getSourceName());
    }
}
