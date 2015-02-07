////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
    private final PrintWriter infoWriter;
    /** close info stream after use */
    private final boolean closeInfo;

    /** where to write error messages **/
    private final PrintWriter errorWriter;
    /** close error stream after use */
    private final boolean closeError;

    /**
     * Creates a new <code>DefaultLogger</code> instance.
     * @param os where to log infos and errors
     * @param closeStreamsAfterUse if oS should be closed in auditFinished()
     */
    public DefaultLogger(OutputStream os, boolean closeStreamsAfterUse)
    {
        // no need to close oS twice
        this(os, closeStreamsAfterUse, os, false);
    }

    /**
     * Creates a new <code>DefaultLogger</code> instance.
     *
     * @param infoStream the <code>OutputStream</code> for info messages
     * @param closeInfoAfterUse auditFinished should close infoStream
     * @param errorStream the <code>OutputStream</code> for error messages
     * @param closeErrorAfterUse auditFinished should close errorStream
     */
    public DefaultLogger(OutputStream infoStream,
                         boolean closeInfoAfterUse,
                         OutputStream errorStream,
                         boolean closeErrorAfterUse)
    {
        closeInfo = closeInfoAfterUse;
        closeError = closeErrorAfterUse;
        infoWriter = new PrintWriter(infoStream);
        errorWriter = (infoStream == errorStream)
            ? infoWriter
            : new PrintWriter(errorStream);
    }

    /**
     * Print an Emacs compliant line on the error stream.
     * If the column number is non zero, then also display it.
     * @param evt {@inheritDoc}
     * @see AuditListener
     **/
    @Override
    public void addError(AuditEvent evt)
    {
        final SeverityLevel severityLevel = evt.getSeverityLevel();
        if (!SeverityLevel.IGNORE.equals(severityLevel)) {

            final String fileName = evt.getFileName();
            final String message = evt.getMessage();

            // avoid StringBuffer.expandCapacity
            final int bufLen = fileName.length() + message.length()
                + BUFFER_CUSHION;
            final StringBuffer sb = new StringBuffer(bufLen);

            sb.append(fileName);
            sb.append(':').append(evt.getLine());
            if (evt.getColumn() > 0) {
                sb.append(':').append(evt.getColumn());
            }
            if (SeverityLevel.WARNING.equals(severityLevel)) {
                sb.append(": warning");
            }
            sb.append(": ").append(message);
            errorWriter.println(sb.toString());
        }
    }

    /** {@inheritDoc} */
    @Override
    public void addException(AuditEvent evt, Throwable throwable)
    {
        synchronized (errorWriter) {
            errorWriter.println("Error auditing " + evt.getFileName());
            throwable.printStackTrace(errorWriter);
        }
    }

    /** {@inheritDoc} */
    @Override
    public void auditStarted(AuditEvent evt)
    {
        infoWriter.println("Starting audit...");
    }

    /** {@inheritDoc} */
    @Override
    public void fileFinished(AuditEvent evt)
    {
    }

    /** {@inheritDoc} */
    @Override
    public void fileStarted(AuditEvent evt)
    {
    }

    /** {@inheritDoc} */
    @Override
    public void auditFinished(AuditEvent evt)
    {
        infoWriter.println("Audit done.");
        closeStreams();
    }

    /**
     * Flushes the output streams and closes them if needed.
     */
    protected void closeStreams()
    {
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
