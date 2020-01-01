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

package com.puppycrawl.tools.checkstyle.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;

public class AuditEventTest {

    @Test
    public void test() {
        final AuditEvent event = new AuditEvent(getClass());

        assertNull(event.getFileName(), "invalid file name");
        assertNull(event.getLocalizedMessage(), "invalid localized message");
        assertEquals(getClass(), event.getSource(), "invalid source");
        assertEquals(SeverityLevel.INFO, event.getSeverityLevel(), "invalid severity");
    }

    @Test
    public void testFullConstructor() {
        final LocalizedMessage message = new LocalizedMessage(1, 2, 3, "bundle", "key", null,
                SeverityLevel.ERROR, "moduleId", getClass(), "customMessage");
        final AuditEvent event = new AuditEvent(getClass(), "fileName", message);

        assertEquals("fileName", event.getFileName(), "invalid file name");
        assertEquals(message, event.getLocalizedMessage(), "invalid localized message");
        assertEquals("customMessage", event.getMessage(), "invalid message");
        assertEquals(getClass(), event.getSource(), "invalid source");
        assertEquals(1, event.getLine(), "invalid line");
        assertEquals(2, event.getColumn(), "invalid column");
        assertEquals(SeverityLevel.ERROR, event.getSeverityLevel(), "invalid severity");
        assertEquals("moduleId", event.getModuleId(), "invalid module id");
        assertEquals("com.puppycrawl.tools.checkstyle.api.AuditEventTest",
                event.getSourceName(), "invalid source name");
    }
}
