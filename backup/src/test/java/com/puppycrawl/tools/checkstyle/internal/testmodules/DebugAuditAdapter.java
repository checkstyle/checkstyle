///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.AuditListener;

public final class DebugAuditAdapter implements AuditListener {

    /** Keeps track whether this {@code AuditListener} was called. */
    private boolean called;

    /** Keeps track whether this {@code AuditListener} was given {@code AuditEvent}. */
    private boolean passedEvent;

    /** Keeps track of the number of files started. */
    private int numFilesStarted;

    /** Keeps track of the number of files finished. */
    private int numFilesFinished;

    public int getNumFilesStarted() {
        return numFilesStarted;
    }

    public int getNumFilesFinished() {
        return numFilesFinished;
    }

    public boolean wasCalled() {
        return called;
    }

    public boolean wasEventPassed() {
        return passedEvent;
    }

    public void resetListener() {
        called = false;
        passedEvent = false;
    }

    @Override
    public void addError(AuditEvent event) {
        called = true;
        if (event != null) {
            passedEvent = true;
        }
    }

    @Override
    public void addException(AuditEvent event, Throwable throwable) {
        called = true;
        if (event != null) {
            passedEvent = true;
        }
    }

    @Override
    public void auditStarted(AuditEvent event) {
        called = true;
        if (event != null) {
            passedEvent = true;
        }
    }

    @Override
    public void fileStarted(AuditEvent event) {
        called = true;
        numFilesStarted++;
        if (event != null) {
            passedEvent = true;
        }
    }

    @Override
    public void auditFinished(AuditEvent event) {
        called = true;
        if (event != null) {
            passedEvent = true;
        }
    }

    @Override
    public void fileFinished(AuditEvent event) {
        called = true;
        numFilesFinished++;
        if (event != null) {
            passedEvent = true;
        }
    }

}
