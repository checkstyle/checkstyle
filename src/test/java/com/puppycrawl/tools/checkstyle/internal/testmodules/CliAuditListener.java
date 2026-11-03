///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal.testmodules;

import java.util.ArrayList;
import java.util.List;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

public final class CliAuditListener implements AuditListener {

    private static final List<String> EVENTS = new ArrayList<>();

    public static void clearEvents() {
        EVENTS.clear();
    }

    public static List<String> getEvents() {
        return List.copyOf(EVENTS);
    }

    @Override
    public void auditStarted(AuditEvent event) {
        EVENTS.add("auditStarted");
    }

    @Override
    public void auditFinished(AuditEvent event) {
        EVENTS.add("auditFinished");
    }

    @Override
    public void fileStarted(AuditEvent event) {
        EVENTS.add("fileStarted");
    }

    @Override
    public void fileFinished(AuditEvent event) {
        EVENTS.add("fileFinished");
    }

    @Override
    public void addError(AuditEvent event) {
        EVENTS.add("addError");
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        EVENTS.add("addException");
    }

}
