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

package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.TreeWalkerTest;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

public class SuppressElementTest {
    private SuppressElement filter;

    @Before
    public void setUp() {
        filter = new SuppressElement("Test", "Test", null, null, null, null);
    }

    @Test
    public void testDecideDefault() {
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        assertTrue(ev.getFileName(), filter.accept(ev));
    }

    @Test
    public void testDecideLocalizedMessage() {
        final LocalizedMessage message =
            new LocalizedMessage(0, 0, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file and check names
        assertFalse("Names match", filter.accept(ev));
    }

    @Test
    public void testDecideByMessage() {
        final LocalizedMessage message =
            new LocalizedMessage(0, 0, "", "", null, null, getClass(), "Test");
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressElement filter1 =
                new SuppressElement(null, null, "Test", null, null, null);
        final SuppressElement filter2 =
                new SuppressElement(null, null, "Bad", null, null, null);
        assertFalse("Message match", filter1.accept(ev));
        assertTrue("Message not match", filter2.accept(ev));
    }

    @Test
    public void testDecideByLine() {
        final LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressElement filter1 =
                new SuppressElement("Test", "Test", null, null, "1-10", null);
        final SuppressElement filter2 =
                new SuppressElement("Test", "Test", null, null, "1-9, 11", null);
        final SuppressElement filter3 =
                new SuppressElement("Test", "Test", null, null, null, null);
        //deny because there are matches on file name, check name, and line
        assertFalse("In range 1-10", filter1.accept(ev));
        assertTrue("Not in 1-9, 11", filter2.accept(ev));
        assertFalse("none", filter3.accept(ev));
    }

    @Test
    public void testDecideByColumn() {
        final LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressElement filter1 =
                new SuppressElement("Test", "Test", null, null, null, "1-10");
        final SuppressElement filter2 =
                new SuppressElement("Test", "Test", null, null, null, "1-9, 11");

        //deny because there are matches on file name, check name, and column
        assertFalse("In range 1-10", filter1.accept(ev));
        assertTrue("Not in 1-9, 1)", filter2.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingFileNameNull() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, null, message);
        assertTrue("Filter should accept valid event", filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingMessageNull() {
        final AuditEvent ev = new AuditEvent(this, "ATest.java", null);
        assertTrue("Filter should accept valid event", filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleNull() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "MyModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        assertFalse("Filter should not accept invalid event", filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleEqual() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "MyModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressElement myFilter =
                new SuppressElement("Test", "Test", null, "MyModule", null, null);

        assertFalse("Filter should not accept invalid event", myFilter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleNotEqual() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "TheirModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressElement myFilter =
                new SuppressElement("Test", "Test", null, "MyModule", null, null);

        assertTrue("Filter should accept valid event", myFilter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingRegExpNotMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "T1est", message);
        assertTrue("Filter should accept valid event", filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingRegExpMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "TestSUFFIX", message);
        final SuppressElement myFilter =
                new SuppressElement("Test", null, null, null, null, null);
        assertFalse("Filter should not accept invalid event", myFilter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingCheckRegExpNotMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressElement myFilter =
                new SuppressElement("Test", "NON_EXISTING_CHECK", null, "MyModule", null, null);
        assertTrue("Filter should accept valid event", myFilter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingCheckRegExpMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressElement myFilter =
                new SuppressElement("Test", getClass().getCanonicalName(), null, null, null, null);

        assertFalse("Filter should not accept invalid event", myFilter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndSourceNameCheckRegExpNotMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressElement myFilter =
                new SuppressElement("Test", TreeWalkerTest.class.getCanonicalName(),
                        null, null, null, null);

        assertTrue("Filter should not accept invalid event", myFilter.accept(ev));
    }

    @Test
    public void testEquals() {
        // filterBased is used instead of filter field only to satisfy IntelliJ Idea Inspection
        // Inspection "Arguments to assertEquals() in wrong order "
        final SuppressElement filterBased =
                new SuppressElement("Test", "Test", null, null, null, null);

        final SuppressElement filter2 =
                new SuppressElement("Test", "Test", null, null, null, null);
        assertEquals("filter, filter2", filterBased, filter2);
        final SuppressElement filter3 =
                new SuppressElement("Test", "Test3", null, null, null, null);
        assertNotEquals("filter, filter3", filterBased, filter3);
        final SuppressElement filterBased1 =
                new SuppressElement("Test", "Test", null, null, null, "1-10");

        assertNotEquals("filter, filter2", filterBased1, filter2);
        final SuppressElement filter22 =
                new SuppressElement("Test", "Test", null, null, null, "1-10");
        assertEquals("filter, filter2", filterBased1, filter22);
        assertNotEquals("filter, filter2", filterBased1, filter2);
        final SuppressElement filterBased2 =
                new SuppressElement("Test", "Test", null, null, "3,4", null);
        assertNotEquals("filter, filter2", filterBased2, filter2);
        final SuppressElement filter23 =
                new SuppressElement("Test", "Test", null, null, "3,4", null);
        assertEquals("filter, filter2", filterBased2, filter23);
        assertNotEquals("filter, filter2", filterBased2, filter2);
        assertEquals("filter, filter2", filterBased2, filter23);
    }

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(SuppressElement.class)
                .usingGetClass()
                .withIgnoredFields("fileRegexp", "checkRegexp", "messageRegexp", "columnFilter",
                        "lineFilter")
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
