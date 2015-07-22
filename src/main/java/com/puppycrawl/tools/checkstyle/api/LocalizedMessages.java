////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.util.Set;
import java.util.SortedSet;

import com.google.common.collect.Sets;

/**
 * Collection of messages.
 * @author Oliver Burn
 */
public final class LocalizedMessages {
    /** contains the messages logged **/
    private final Set<LocalizedMessage> messages = Sets.newTreeSet();

    /** @return the logged messages **/
    public SortedSet<LocalizedMessage> getMessages() {
        return Sets.newTreeSet(messages);
    }

    /** Reset the object. **/
    public void reset() {
        messages.clear();
    }

    /**
     * Logs a message to be reported.
     * @param aMsg the message to log
     **/
    public void add(LocalizedMessage aMsg) {
        messages.add(aMsg);
    }

    /** @return the number of messages */
    public int size() {
        return messages.size();
    }
}
