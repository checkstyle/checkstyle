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

package com.puppycrawl.tools.checkstyle.filters;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Violation;

public class SeverityMatchFilterTest {

    private final SeverityMatchFilter filter = new SeverityMatchFilter();

    @Test
    public void testDefault() {
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        assertWithMessage("no message")
            .that(filter.accept(ev))
            .isFalse();
        final SeverityLevel errorLevel = SeverityLevel.ERROR;
        final Violation errorMessage =
            new Violation(1, 0, "", "", null,
                errorLevel, null, getClass(), null);
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", errorMessage);
        assertWithMessage("level:" + errorLevel)
                .that(filter.accept(ev2))
                .isTrue();
        final SeverityLevel infoLevel = SeverityLevel.INFO;
        final Violation infoViolation =
                new Violation(1, 0, "", "", null, infoLevel, null, getClass(), null);
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", infoViolation);
        assertWithMessage("level:" + infoLevel)
            .that(filter.accept(ev3))
            .isFalse();
    }

    @Test
    public void testSeverity() {
        filter.setSeverity(SeverityLevel.INFO);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        // event with no message has severity level INFO
        assertWithMessage("no message")
                .that(filter.accept(ev))
                .isTrue();
        final SeverityLevel errorLevel = SeverityLevel.ERROR;
        final Violation errorMessage =
            new Violation(1, 0, "", "", null,
                errorLevel, null, getClass(), null);
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", errorMessage);
        assertWithMessage("level:" + errorLevel)
            .that(filter.accept(ev2))
            .isFalse();
        final SeverityLevel infoLevel = SeverityLevel.INFO;
        final Violation infoMessage =
                new Violation(1, 0, "", "", null, infoLevel, null, getClass(), null);
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", infoMessage);
        assertWithMessage("level:" + infoLevel)
                .that(filter.accept(ev3))
                .isTrue();
    }

    @Test
    public void testAcceptOnMatch() {
        filter.setSeverity(SeverityLevel.INFO);
        filter.setAcceptOnMatch(false);
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        // event with no message has severity level INFO
        assertWithMessage("no message")
            .that(filter.accept(ev))
            .isFalse();
        final SeverityLevel errorLevel = SeverityLevel.ERROR;
        final Violation errorViolation =
            new Violation(1, 0, "", "", null,
                errorLevel, null, getClass(), null);
        final AuditEvent ev2 = new AuditEvent(this, "ATest.java", errorViolation);
        assertWithMessage("level:" + errorLevel)
                .that(filter.accept(ev2))
                .isTrue();
        final SeverityLevel infoLevel = SeverityLevel.INFO;
        final Violation infoViolation = new Violation(1, 0, "", "", null, infoLevel,
            null, getClass(), null);
        final AuditEvent ev3 = new AuditEvent(this, "ATest.java", infoViolation);
        assertWithMessage("level:" + infoLevel)
            .that(filter.accept(ev3))
            .isFalse();
    }

    @Test
    public void testConfigure() throws CheckstyleException {
        filter.configure(new DefaultConfiguration("test"));
        assertWithMessage("object exists")
            .that(filter)
            .isNotNull();
    }

}
