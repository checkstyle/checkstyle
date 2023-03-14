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

package com.puppycrawl.tools.checkstyle.api;

import static com.google.common.truth.Truth.assertWithMessage;

import org.junit.jupiter.api.Test;

public class SeverityLevelCounterTest {

    @Test
    public void testCtorException() {
        try {
            final Object test = new SeverityLevelCounter(null);
            assertWithMessage("exception expected but got %s", test)
                    .fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Invalid exception message")
                    .that(ex)
                    .hasMessageThat()
                    .isEqualTo("'level' cannot be null");
        }
    }

    @Test
    public void testAddError() {
        final SeverityLevelCounter counter = new SeverityLevelCounter(SeverityLevel.ERROR);
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(0);
        // not counted
        counter.addError(new AuditEvent(this, "ATest.java", null));
        counter.addError(new AuditEvent(this, "ATest.java", new Violation(1, 2, 0, null,
                null, null, SeverityLevel.INFO, null, null, null)));
        // counted
        counter.addError(new AuditEvent(this, "ATest.java", new Violation(1, 2, 0, null,
                null, null, SeverityLevel.ERROR, null, null, null)));
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(1);
    }

    @Test
    public void testAddException() {
        final SeverityLevelCounter counter = new SeverityLevelCounter(SeverityLevel.ERROR);
        final AuditEvent event = new AuditEvent(this, "ATest.java", null);
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(0);
        counter.addException(event, new IllegalStateException("Test IllegalStateException"));
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(1);
    }

    @Test
    public void testAddExceptionWarning() {
        final SeverityLevelCounter counter = new SeverityLevelCounter(SeverityLevel.WARNING);
        final AuditEvent event = new AuditEvent(this, "ATest.java", null);
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(0);
        counter.addException(event, new IllegalStateException("Test IllegalStateException"));
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(0);
    }

    @Test
    public void testAuditStartedClearsState() {
        final SeverityLevelCounter counter = new SeverityLevelCounter(SeverityLevel.ERROR);
        final AuditEvent event = new AuditEvent(this, "ATest.java", null);
        final AuditEvent secondEvent = new AuditEvent(this, "BTest.java", null);

        counter.auditStarted(event);
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(0);

        counter.addException(event, new IllegalStateException("Test IllegalStateException"));
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(1);

        counter.auditStarted(secondEvent);
        assertWithMessage("Invalid severity level count")
                .that(counter.getCount())
                .isEqualTo(0);
    }

}
