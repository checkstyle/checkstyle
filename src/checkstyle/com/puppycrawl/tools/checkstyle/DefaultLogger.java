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
    /** where to log **/
    private OutputStream mStream;
    /** where to write **/
    private PrintWriter mWriter;

    /**
     * Creates a new <code>DefaultLogger</code> instance.
     */
    public DefaultLogger()
    {
    }

    /**
     * Creates a new <code>DefaultLogger</code> instance.
     * @param aOS where to log
     */
    public DefaultLogger(OutputStream aOS)
    {
        setOutputStream(aOS);
    }

    /** @see AuditListener **/
    public void setOutputStream(OutputStream aOS)
    {
        mWriter = new PrintWriter(aOS);
        mStream = aOS;
    }

    /** @see AuditListener **/
    public OutputStream getOutputStream()
    {
        return mStream;
    }

    /** @see AuditListener **/
    public void addError(AuditEvent aEvt)
    {
        mWriter.println(aEvt);
    }

    /** @see AuditListener **/
    public void addException(AuditEvent aEvt, Throwable aThrowable)
    {
        synchronized (mWriter) {
            mWriter.println("Error auditing " + aEvt.getFileName());
            aThrowable.printStackTrace(mWriter);
        }
    }

    /** @see AuditListener **/
    public void auditStarted(AuditEvent aEvt)
    {
        mWriter.println("Starting audit...");
    }

    /** @see AuditListener **/
    public void fileFinished(AuditEvent aEvt)
    {
    }

    /** @see AuditListener **/
    public void fileStarted(AuditEvent aEvt)
    {
    }

    /** @see AuditListener **/
    public void auditFinished(AuditEvent aEvt)
    {
        mWriter.println("Audit done.");
        mWriter.flush();
    }
}
