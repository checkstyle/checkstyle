////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
 * @author <a href="mailto:stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 * @see XMLLogger
 */
public class DefaultLogger
    extends AutomaticBean
    implements AuditListener
{
    /** cushion for avoiding StringBuffer.expandCapacity */
    private static final int BUFFER_CUSHION = 12;

    /** where to write info messages **/
    private final PrintWriter mInfoWriter;
    /** close info stream after use */
    private final boolean mCloseInfo;

    /** where to write error messages **/
    private final PrintWriter mErrorWriter;
    /** close error stream after use */
    private final boolean mCloseError;

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
    public DefaultLogger(OutputStream aInfoStream,
                         boolean aCloseInfoAfterUse,
                         OutputStream aErrorStream,
                         boolean aCloseErrorAfterUse)
    {
        mCloseInfo = aCloseInfoAfterUse;
        mCloseError = aCloseErrorAfterUse;
        mInfoWriter = new PrintWriter(aInfoStream);
        mErrorWriter = (aInfoStream == aErrorStream)
            ? mInfoWriter
            : new PrintWriter(aErrorStream);
    }

    /**
     * Print an Emacs compliant line on the error stream.
     * If the column number is non zero, then also display it.
     * @param aEvt {@inheritDoc}
     * @see AuditListener
     **/
    public void addError(AuditEvent aEvt)
    {
        final SeverityLevel severityLevel = aEvt.getSeverityLevel();
        if (!SeverityLevel.IGNORE.equals(severityLevel)) {

            final String fileName = aEvt.getFileName();
            final String message = aEvt.getMessage();

            // avoid StringBuffer.expandCapacity
            final int bufLen = fileName.length() + message.length()
                + BUFFER_CUSHION;
            final StringBuffer sb = new StringBuffer(bufLen);

            sb.append(fileName);
            sb.append(':').append(aEvt.getLine());
            if (aEvt.getColumn() > 0) {
                sb.append(':').append(aEvt.getColumn());
            }
            if (SeverityLevel.WARNING.equals(severityLevel)) {
                sb.append(": warning");
            }
            sb.append(": ").append(message);
            mErrorWriter.println(sb.toString());
        }
    }

    /** {@inheritDoc} */
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        synchronized (mErrorWriter) {
            mErrorWriter.println("Error auditing " + aEvt.getFileName());
            aThrowable.printStackTrace(mErrorWriter);
        }
    }

    /** {@inheritDoc} */
    public void auditStarted(AuditEvent aEvt)
    {
        mInfoWriter.println("Starting audit...");
    }

    /** {@inheritDoc} */
    public void fileFinished(AuditEvent aEvt)
    {
    }

    /** {@inheritDoc} */
    public void fileStarted(AuditEvent aEvt)
    {
    }

    /** {@inheritDoc} */
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
        mInfoWriter.flush();
        if (mCloseInfo) {
            mInfoWriter.close();
        }

        mErrorWriter.flush();
        if (mCloseError) {
            mErrorWriter.close();
        }
    }
}
