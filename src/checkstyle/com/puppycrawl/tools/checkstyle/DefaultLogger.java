////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2002  Oliver Burn
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
import java.io.PrintWriter;

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
public class DefaultLogger
    implements AuditListener
{
    /** where to write info messages **/
    private PrintWriter mInfoWriter;
    /** close info stream after use */
    private boolean mCloseInfo;

    /** where to write error messages **/
    private PrintWriter mErrorWriter;
    /** close error stream after use */
    private boolean mCloseError;

    /**
     * Creates a new <code>DefaultLogger</code> instance.
     * @param aOS where to log infos and errors
     * @param aCloseStreamsAfterUse if aOS should be closed in auditFinished()
     */
    public DefaultLogger(OutputStream aOS, boolean aCloseStreamsAfterUse)
    {
        // no need to close aOS twice
        this(aOS, aCloseStreamsAfterUse, aOS, false);
    }

    /**
     * Creates a new <code>DefaultLogger</code> instance.
     *
     * @param aInfoStream the <code>OutputStream</code> for info messages
     * @param aCloseInfoAfterUse auditFinished should close aInfoStream
     * @param aErrorStream the <code>OutputStream</code> for error messages
     * @param aCloseErrorAfterUse auditFinished should close aErrorStream
     */
    public DefaultLogger(
        OutputStream aInfoStream,
        boolean aCloseInfoAfterUse,
        OutputStream aErrorStream,
        boolean aCloseErrorAfterUse)
    {
        mCloseInfo = aCloseInfoAfterUse;
        mCloseError = aCloseErrorAfterUse;
        mInfoWriter = new PrintWriter(aInfoStream);
        if (aInfoStream == aErrorStream) {
            mErrorWriter = mInfoWriter;
        }
        else {
            mErrorWriter = new PrintWriter(aErrorStream);
        }
    }

    /**
     * Print an Emacs compliant line on the error stream.
     * If the column number is non zero, then also display it.
     * @see AuditListener
     **/
    public void addError(AuditEvent aEvt)
    {
        if (aEvt.getColumn() > 0) {
            mErrorWriter.println(aEvt.getFileName()
                                 + ":" + aEvt.getLine()
                                 + ":" + aEvt.getColumn()
                                 + ": " + aEvt.getMessage());
        }
        else {
            mErrorWriter.println(aEvt.getFileName()
                                 + ":" + aEvt.getLine()
                                 + ": " + aEvt.getMessage());
        }
    }

    /** @see AuditListener **/
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        synchronized (mErrorWriter) {
            mErrorWriter.println("Error auditing " + aEvt.getFileName());
            aThrowable.printStackTrace(mErrorWriter);
        }
    }

    /** @see AuditListener **/
    public void auditStarted(AuditEvent aEvt)
    {
        mInfoWriter.println("Starting audit...");
    }

    /** @see AuditListener **/
    public void fileFinished(AuditEvent aEvt)
    {
        mInfoWriter.println("finished checking " + aEvt.getFileName());
    }

    /** @see AuditListener **/
    public void fileStarted(AuditEvent aEvt)
    {
        mInfoWriter.println("Started checking " + aEvt.getFileName());
    }

    /** @see AuditListener **/
    public void auditFinished(AuditEvent aEvt)
    {
        mInfoWriter.println("Audit done.");
        closeStreams();
    }

    /**
     * Flushes the output streams and closes them if needed.
     */
    protected void closeStreams()
    {
        if (mCloseInfo) {
            mInfoWriter.flush();
        }
        else {
            mInfoWriter.flush();
            mInfoWriter.close();
        }

        if (mCloseError) {
            mErrorWriter.flush();
        }
        else {
            mErrorWriter.flush();
            mErrorWriter.close();
        }
    }
}
