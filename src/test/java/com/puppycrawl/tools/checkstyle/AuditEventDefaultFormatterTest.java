////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.lang.reflect.Method;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import com.puppycrawl.tools.checkstyle.api.SeverityLevel;

@RunWith(PowerMockRunner.class)
@PrepareForTest(AuditEvent.class)
public class AuditEventDefaultFormatterTest {

    @Test
    public void testFormatFullyQualifiedModuleNameContainsCheckSuffix() {
        final AuditEvent mock = PowerMockito.mock(AuditEvent.class);
        when(mock.getSourceName()).thenReturn("com.test.package.TestModuleCheck");
        when(mock.getSeverityLevel()).thenReturn(SeverityLevel.WARNING);
        when(mock.getLine()).thenReturn(1);
        when(mock.getColumn()).thenReturn(1);
        when(mock.getMessage()).thenReturn("Mocked message.");
        when(mock.getFileName()).thenReturn("InputMockFile.java");
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. [TestModule]";

        assertEquals("Invalid format", expected, formatter.format(mock));
    }

    @Test
    public void testFormatFullyQualifiedModuleNameDoesNotContainCheckSuffix() {
        final AuditEvent mock = PowerMockito.mock(AuditEvent.class);
        when(mock.getSourceName()).thenReturn("com.test.package.TestModule");
        when(mock.getSeverityLevel()).thenReturn(SeverityLevel.WARNING);
        when(mock.getLine()).thenReturn(1);
        when(mock.getColumn()).thenReturn(1);
        when(mock.getMessage()).thenReturn("Mocked message.");
        when(mock.getFileName()).thenReturn("InputMockFile.java");
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. [TestModule]";

        assertEquals("Invalid format", expected, formatter.format(mock));
    }

    @Test
    public void testFormatModuleNameContainsCheckSuffix() {
        final AuditEvent mock = PowerMockito.mock(AuditEvent.class);
        when(mock.getSourceName()).thenReturn("TestModuleCheck");
        when(mock.getSeverityLevel()).thenReturn(SeverityLevel.WARNING);
        when(mock.getLine()).thenReturn(1);
        when(mock.getColumn()).thenReturn(1);
        when(mock.getMessage()).thenReturn("Mocked message.");
        when(mock.getFileName()).thenReturn("InputMockFile.java");
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. [TestModule]";

        assertEquals("Invalid format", expected, formatter.format(mock));
    }

    @Test
    public void testFormatModuleNameDoesNotContainCheckSuffix() {
        final AuditEvent mock = PowerMockito.mock(AuditEvent.class);
        when(mock.getSourceName()).thenReturn("TestModule");
        when(mock.getSeverityLevel()).thenReturn(SeverityLevel.WARNING);
        when(mock.getLine()).thenReturn(1);
        when(mock.getColumn()).thenReturn(1);
        when(mock.getMessage()).thenReturn("Mocked message.");
        when(mock.getFileName()).thenReturn("InputMockFile.java");
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. [TestModule]";

        assertEquals("Invalid format", expected, formatter.format(mock));
    }

    @Test
    public void testFormatModuleWithModuleId() {
        final AuditEvent mock = PowerMockito.mock(AuditEvent.class);
        when(mock.getSourceName()).thenReturn("TestModule");
        when(mock.getSeverityLevel()).thenReturn(SeverityLevel.WARNING);
        when(mock.getLine()).thenReturn(1);
        when(mock.getColumn()).thenReturn(1);
        when(mock.getMessage()).thenReturn("Mocked message.");
        when(mock.getFileName()).thenReturn("InputMockFile.java");
        when(mock.getModuleId()).thenReturn("ModuleId");
        final AuditEventFormatter formatter = new AuditEventDefaultFormatter();

        final String expected = "[WARN] InputMockFile.java:1:1: Mocked message. [ModuleId]";

        assertEquals("Invalid format", expected, formatter.format(mock));
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

        assertEquals("Buffer length is not expected", 54, result);
    }
}
