////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2003  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.api;


/**
   Users should extend this class to implement customized
   audit event filtering.

   <p>This abstract class assumes and also imposes that filters be
   organized in a linear chain. The {@link #decide
   decide(AuditEvent)} method of each filter is called sequentially,
   in the order of their addition to the chain.

   <p>The {@link #decide decide(AuditEvent)} method must return one
   of the integer constants {@link #DENY}, {@link #NEUTRAL} or {@link
   #ACCEPT}.

   <p>If the value {@link #DENY} is returned, then the audit event is
   dropped immediately without consulting with the remaining
   filters.

   <p>If the value {@link #NEUTRAL} is returned, then the next filter
   in the chain is consulted. If there are no more filters in the
   chain, then the audit event is reported. Thus, in the presence of no
   filters, the default behaviour is to report all audit events.

   <p>If the value {@link #ACCEPT} is returned, then the audit
   event is reported without consulting the remaining filters.

   <p>The philosophy of Checkstyle filters is largely inspired from the
   log4j filters which were inspired by Linux ipchains.

 * @author Rick Giles
 * @version 11-Jul-2003
 */
public abstract class Filter
    extends AutomaticBean
{
    /**
     * The audit event must be logged immediately without consulting with
     * the remaining filters, if any, in the chain.
     */
    public static final int ACCEPT = -1;

    /**
     * The audit event must be dropped immediately without consulting with
     * the remaining filters, if any, in the chain.
     */
    public static final int DENY = 0;

    /**
     * This filter is neutral with respect to the audit event.
     * The remaining filters, if any, should be consulted for a final decision.
     */
    public static final int NEUTRAL = 1;

    /**
     * Determines the filtering of an audit event.
     * If the decision is <code>DENY</code>, then the event will be dropped.
     * If the decision is <code>NEUTRAL</code>, then the next filter, if any,
     * will be invoked.
     * If the decision is <code>ACCEPT</code> then the event will be logged
     * without consulting with other filters in the chain.
     * @param aEvent the audit event to decide on.
     * @return the decision of the filter.
     */
    public abstract int decide(AuditEvent aEvent);
}
