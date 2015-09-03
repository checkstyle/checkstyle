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

package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

/** Tests SeverityMatchFilter. */
public class SeverityMatchFilterTest {
    private final SeverityMatchFilter filter = new SeverityMatchFilter();

    @Test
    public void testDefault() {
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        assertFalse("no message", filter.accept(ev));
        SeverityLevel errorLevel = SeverityLevel.ERROR;
        LocalizedMessage errorMessage =
            new LocalizedMessage(0, 0, "", "", null,
                errorLevel, null, getClass(), null);
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", errorMessage);
        assertTrue("level:" + errorLevel, filter.accept(ev2));
        SeverityLevel infoLevel = SeverityLevel.INFO;
        LocalizedMessage infoMessage =
                new LocalizedMessage(0, 0, "", "", null, infoLevel, null, getClass(), null);
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", infoMessage);
        assertFalse("level:" + infoLevel, filter.accept(ev3));
    }

    @Test
    public void testSeverity() {
        filter.setSeverity("info");
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        // event with no message has severity level INFO
        assertTrue("no message", filter.accept(ev));
        SeverityLevel errorLevel = SeverityLevel.ERROR;
        LocalizedMessage errorMessage =
            new LocalizedMessage(0, 0, "", "", null,
                errorLevel, null, getClass(), null);
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", errorMessage);
        assertFalse("level:" + errorLevel, filter.accept(ev2));
        SeverityLevel infoLevel = SeverityLevel.INFO;
        LocalizedMessage infoMessage =
                new LocalizedMessage(0, 0, "", "", null, infoLevel, null, getClass(), null);
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", infoMessage);
        assertTrue("level:" + infoLevel, filter.accept(ev3));
    }

    @Test
    public void testAcceptOnMatch() {
        filter.setSeverity("info");
        filter.setAcceptOnMatch(false);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        // event with no message has severity level INFO
        assertFalse("no message", filter.accept(ev));
        SeverityLevel errorLevel = SeverityLevel.ERROR;
        LocalizedMessage errorMessage =
            new LocalizedMessage(0, 0, "", "", null,
                errorLevel, null, getClass(), null);
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", errorMessage);
        assertTrue("level:" + errorLevel, filter.accept(ev2));
        SeverityLevel infoLevel = SeverityLevel.INFO;
        LocalizedMessage infoMessage = new LocalizedMessage(0, 0, "", "", null, infoLevel, null, getClass(), null);
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", infoMessage);
        assertFalse("level:" + infoLevel, filter.accept(ev3));
    }
}
