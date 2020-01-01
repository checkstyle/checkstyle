////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.lang.reflect.Method;

import org.junit.jupiter.api.Test;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

public class AuditEventDefaultFormatterTest {

    @Test
    public void testFormatFullyQualifiedModuleNameContainsCheckSuffix() {
        final LocalizedMessage message = new LocalizedMessage(1, 1, null, null, null,
                SeverityLevel.WARNING, null, TestModuleCheck.class, "Mocked message.");
        final AuditEvent event = new AuditEvent("", "InputMockFile.java", message);
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. "
                + "[AuditEventDefaultFormatterTest$TestModule]";

        assertEquals(expected, formatter.format(event), "Invalid format");
    }

    @Test
    public void testFormatFullyQualifiedModuleNameDoesNotContainCheckSuffix() {
        final LocalizedMessage message = new LocalizedMessage(1, 1, null, null, null,
                SeverityLevel.WARNING, null, TestModule.class, "Mocked message.");
        final AuditEvent event = new AuditEvent("", "InputMockFile.java", message);
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. "
                + "[AuditEventDefaultFormatterTest$TestModule]";

        assertEquals(expected, formatter.format(event), "Invalid format");
    }

    @Test
    public void testFormatModuleWithModuleId() {
        final LocalizedMessage message = new LocalizedMessage(1, 1, null, null, null,
                SeverityLevel.WARNING, "ModuleId", TestModule.class, "Mocked message.");
        final AuditEvent event = new AuditEvent("", "InputMockFile.java", message);
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. [ModuleId]";

        assertEquals(expected, formatter.format(event), "Invalid format");
    }

    @Test
    public void testCalculateBufferLength() throws Exception {
        final Method calculateBufferLengthMethod =
                Whitebox.getMethod(AuditEventDefaultFormatter.class,
                        "calculateBufferLength", AuditEvent.class, int.class);
        final LocalizedMessage localizedMessage = new LocalizedMessage(1, 1,
                "messages.properties", "key", null, SeverityLevel.ERROR, null,
                getClass(), null);
        final AuditEvent auditEvent = new AuditEvent(new Object(), "fileName", localizedMessage);
        final int result = (int) calculateBufferLengthMethod.invoke(null,
                auditEvent, SeverityLevel.ERROR.ordinal());

        assertEquals(54, result, "Buffer length is not expected");
    }

    private static class TestModuleCheck {

        // no code

    }

    private static class TestModule {

        // no code

    }

}
