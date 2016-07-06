////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

/** Tests SuppressElementFilter. */
public class SuppressElementTest {
    private SuppressElement filter;

    @Before
    public void setUp() {
        filter = new SuppressElement("Test");
        filter.setChecks("Test");
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
    public void testDecideByLine() {
        final LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file name, check name, and line
        filter.setLines("1-10");
        assertFalse("In range 1-10", filter.accept(ev));
        filter.setLines("1-9, 11");
        assertTrue("Not in 1-9, 11", filter.accept(ev));
        filter.setLines(null);
        assertFalse("none", filter.accept(ev));
    }

    @Test
    public void testDecideByColumn() {
        final LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        //deny because there are matches on file name, check name, and column
        filter.setColumns("1-10");
        assertFalse("In range 1-10", filter.accept(ev));
        filter.setColumns("1-9, 11");
        assertTrue("Not in 1-9, 1)", filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingFileNameNull() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, null, message);
        assertTrue(filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingMessageNull() {
        final AuditEvent ev = new AuditEvent(this, "ATest.java", null);
        assertTrue(filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleNull() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "MyModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        filter.setModuleId(null);
        assertFalse(filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleEqual() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "MyModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        filter.setModuleId("MyModule");
        assertFalse(filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleNotEqual() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "TheirModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        filter.setModuleId("MyModule");
        assertTrue(filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingRegExpNotMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "T1est", message);
        assertTrue(filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingRegExpMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "TestSUFFIX", message);
        final SuppressElement filterWithoutChecks = new SuppressElement("Test");
        assertFalse(filterWithoutChecks.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingCheckRegExpNotMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        filter.setChecks("NON_EXISTING_CHECK");
        assertTrue(filter.accept(ev));
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingCheckRegExpMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        filter.setChecks(getClass().getCanonicalName());
        assertFalse(filter.accept(ev));
    }

    @Test
    public void testEquals() {
        // filterBased is used instead of filter field only to satisfy IntelijIdea Inspection
        // Inspection "Arguments to assertEquals() in wrong order "
        final SuppressElement filterBased = new SuppressElement("Test");
        filterBased.setChecks("Test");

        final SuppressElement filter2 = new SuppressElement("Test");
        filter2.setChecks("Test");
        assertEquals("filter, filter2", filterBased, filter2);
        final SuppressElement filter3 = new SuppressElement("Test");
        filter3.setChecks("Test3");
        assertNotEquals("filter, filter3", filterBased, filter3);
        filterBased.setColumns("1-10");
        assertNotEquals("filter, filter2", filterBased, filter2);
        filter2.setColumns("1-10");
        assertEquals("filter, filter2", filterBased, filter2);
        filterBased.setColumns(null);
        assertNotEquals("filter, filter2", filterBased, filter2);
        filter2.setColumns(null);
        filterBased.setLines("3,4");
        assertNotEquals("filter, filter2", filterBased, filter2);
        filter2.setLines("3,4");
        assertEquals("filter, filter2", filterBased, filter2);
        filterBased.setColumns("1-10");
        assertNotEquals("filter, filter2", filterBased, filter2);
        filter2.setColumns("1-10");
        assertEquals("filter, filter2", filterBased, filter2);
    }

    @Test
    public void testEqualsAndHashCode() {
        EqualsVerifier.forClass(SuppressElement.class)
                .usingGetClass()
                .withIgnoredFields("fileRegexp", "checkRegexp", "columnFilter", "lineFilter")
                .suppress(Warning.NONFINAL_FIELDS)
                .verify();
    }
}
