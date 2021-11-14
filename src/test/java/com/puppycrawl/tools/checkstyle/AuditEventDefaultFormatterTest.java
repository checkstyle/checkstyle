////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

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

        assertEquals(expected, formatter.format(event), "Invalid format");
    }

    @Test
    public void testFormatFullyQualifiedModuleNameDoesNotContainCheckSuffix() {
        final Violation violation = new Violation(1, 1, null, null, null,
                SeverityLevel.WARNING, null, TestModule.class, "Mocked violation.");
        final AuditEvent event = new AuditEvent("", "InputMockFile.java", violation);
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked violation. "
                + "[AuditEventDefaultFormatterTest$TestModule]";

        assertEquals(expected, formatter.format(event), "Invalid format");
    }

    @Test
    public void testFormatModuleWithModuleId() {
        final Violation violation = new Violation(1, 1, null, null, null,
                SeverityLevel.WARNING, "ModuleId", TestModule.class, "Mocked violation.");
        final AuditEvent event = new AuditEvent("", "InputMockFile.java", violation);
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked violation. [ModuleId]";

        assertEquals(expected, formatter.format(event), "Invalid format");
    }

    @Test
    public void testCalculateBufferLength() throws Exception {
        final Violation violation = new Violation(1, 1,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                getClass(), null);
        final AuditEvent auditEvent = new AuditEvent(new Object(), "fileName", violation);
        final int result = TestUtil.invokeStaticMethod(AuditEventDefaultFormatter.class,
                "calculateBufferLength", auditEvent, SeverityLevel.ERROR.ordinal());

        assertEquals(54, result, "Buffer length is not expected");
    }

    /**
     * This test mocks {@code AuditEvent} to emulate an event from a top level class
     * to gain 100% coverage.
     */
    @Test
    public void testFormatTopLevelModuleNameContainsCheckSuffix() {
        final AuditEvent mock = mock(AuditEvent.class);
        when(mock.getSourceName()).thenReturn("TestModuleCheck");
        when(mock.getSeverityLevel()).thenReturn(SeverityLevel.WARNING);
        when(mock.getLine()).thenReturn(1);
        when(mock.getColumn()).thenReturn(1);
        when(mock.getMessage()).thenReturn("Mocked message.");
        when(mock.getFileName()).thenReturn("InputMockFile.java");
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. [TestModule]";
        assertWithMessage("Invalid format")
                .that(formatter.format(mock))
                .isEqualTo(expected);
    }

    /**
     * This test mocks {@code AuditEvent} to emulate an event from a top level class
     * to gain 100% coverage.
     */
    @Test
    public void testFormatTopLevelModuleNameDoesNotContainCheckSuffix() {
        final AuditEvent mock = mock(AuditEvent.class);
        when(mock.getSourceName()).thenReturn("TestModule");
        when(mock.getSeverityLevel()).thenReturn(SeverityLevel.WARNING);
        when(mock.getLine()).thenReturn(1);
        when(mock.getColumn()).thenReturn(1);
        when(mock.getMessage()).thenReturn("Mocked message.");
        when(mock.getFileName()).thenReturn("InputMockFile.java");
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. [TestModule]";
        assertWithMessage("Invalid format")
                .that(formatter.format(mock))
                .isEqualTo(expected);
    }

    private static class TestModuleCheck {

        // no code

    }

    private static class TestModule {

        // no code

    }

}
