///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.Test;

public class AuditEventTest {

    @Test
    public void test() {
        final AuditEvent event = new AuditEvent(getClass());

        assertWithMessage("invalid file name")
                .that(event.getFileName())
                .isNull();
        assertWithMessage("invalid violation")
                .that(event.getViolation())
                .isNull();
        assertWithMessage("invalid source")
                .that(event.getSource())
                .isEqualTo(getClass());
        assertWithMessage("invalid severity")
                .that(event.getSeverityLevel())
                .isEqualTo(SeverityLevel.INFO);
    }

    @Test
    public void testNoSource() {
        final IllegalArgumentException ex = getExpectedThrowable(IllegalArgumentException.class,
                () -> new AuditEvent(null),
                "IllegalArgumentException expected");
        assertWithMessage("Invalid exception message")
            .that(ex.getMessage())
            .isEqualTo("null source");
    }

    @Test
    public void testFullConstructor() {
        final Violation message = new Violation(1, 2, 3, "bundle", "key", null,
                SeverityLevel.ERROR, "moduleId", getClass(), "customMessage");
        final AuditEvent event = new AuditEvent(getClass(), "fileName", message);

        assertWithMessage("invalid file name")
                .that(event.getFileName())
                .isEqualTo("fileName");
        assertWithMessage("invalid violation")
                .that(event.getViolation())
                .isEqualTo(message);
        assertWithMessage("invalid violation")
                .that(event.getMessage())
                .isEqualTo("customMessage");
        assertWithMessage("invalid source")
                .that(event.getSource())
                .isEqualTo(getClass());
        assertWithMessage("invalid line")
                .that(event.getLine())
                .isEqualTo(1);
        assertWithMessage("invalid column")
                .that(event.getColumn())
                .isEqualTo(2);
        assertWithMessage("invalid severity")
                .that(event.getSeverityLevel())
                .isEqualTo(SeverityLevel.ERROR);
        assertWithMessage("invalid module id")
                .that(event.getModuleId())
                .isEqualTo("moduleId");
        assertWithMessage("invalid source name")
                .that(event.getSourceName())
                .isEqualTo("com.puppycrawl.tools.checkstyle.api.AuditEventTest");
        assertWithMessage("invalid file content")
                .that(event.getFileContent())
                .isEqualTo(null);
    }
}
