////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001  Oliver Burn
//
// This program is free software; you can redistribute it and/or
// modify it under the terms of the GNU General Public License
// as published by the Free Software Foundation; either version 2
// of the License, or (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place - Suite 330, Boston, MA  02111-1307, USA.
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle;

import java.util.EventListener;

/**
 * Listener in charge of receiving events from the Checker.
 * Typical events sequence is :
 * <pre>
 * auditStarted
 *   (fileStarted
 *     (addError)*
 *     (addException)*
 *   fileFinished )*
 *   (addException)*
 * auditFinished
 * </pre>
 * @author <a href="stephane.bailliez@wanadoo.fr">Stephane Bailliez</a>
 */
public interface AuditListener
    extends EventListener
{
    /**
     * notify that the audit is about to start
     * @param aEvt the event details
     */
    void auditStarted(AuditEvent aEvt);

    /**
     * notify that the audit is finished
     * @param aEvt the event details
     */
    void auditFinished(AuditEvent aEvt);

    /**
     * notify that audit is about to start on a specific file
     * @param aEvt the event details
     */
    void fileStarted(AuditEvent aEvt);

    /**
     * notify that audit is finished on a specific file
     * @param aEvt the event details
     */
    void fileFinished(AuditEvent aEvt);

    /**
     * notify that an audit error was discovered on a specific file
     * @param aEvt the event details
     */
    void addError(AuditEvent aEvt);

    /**
     * notify that an exception happened while performing audit
     * @param aEvt the event details
     * @param aThrowable details of the exception
     */
    void addException(AuditEvent aEvt, Throwable aThrowable);
}
