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

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class AuditEventDefaultFormatterTest {

    @Test
    public void testFormatFullyQualifiedModuleNameContainsCheckSuffix() {
        final Violation violation = new Violation(1, 1, null, null, null,
                SeverityLevel.WARNING, null, TestModuleCheck.class, "Mocked violation.");
        final AuditEvent event = new AuditEvent("", "InputMockFile.java", violation);
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked violation. "
                + "[AuditEventDefaultFormatterTest$TestModule]";

        assertWithMessage("Invalid format")
                .that(formatter.format(event))
                .isEqualTo(expected);
    }

    @Test
    public void testFormatFullyQualifiedModuleNameDoesNotContainCheckSuffix() {
        final Violation violation = new Violation(1, 1, null, null, null,
                SeverityLevel.WARNING, null, TestModule.class, "Mocked violation.");
        final AuditEvent event = new AuditEvent("", "InputMockFile.java", violation);
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked violation. "
                + "[AuditEventDefaultFormatterTest$TestModule]";

        assertWithMessage("Invalid format")
                .that(formatter.format(event))
                .isEqualTo(expected);
    }

    @Test
    public void testFormatModuleWithModuleId() {
        final Violation violation = new Violation(1, 1, null, null, null,
                SeverityLevel.WARNING, "ModuleId", TestModule.class, "Mocked violation.");
        final AuditEvent event = new AuditEvent("", "InputMockFile.java", violation);
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked violation. [ModuleId]";

        assertWithMessage("Invalid format")
                .that(formatter.format(event))
                .isEqualTo(expected);
    }

    @Test
    public void testCalculateBufferLength() throws Exception {
        final Violation violation = new Violation(1, 1,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                getClass(), null);
        final AuditEvent auditEvent = new AuditEvent(new Object(), "fileName", violation);
        final int result = TestUtil.invokeStaticMethod(AuditEventDefaultFormatter.class,
                "calculateBufferLength", auditEvent, SeverityLevel.ERROR.ordinal());

        assertWithMessage("Buffer length is not expected")
                .that(result)
                .isEqualTo(54);
    }

    private static class TestModuleCheck {

        // no code

    }

    private static class TestModule {

        // no code

    }

}
