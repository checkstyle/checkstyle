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

package com.puppycrawl.tools.checkstyle.filters;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.TreeWalkerTest;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.LocalizedMessage;
import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;
import nl.jqno.equalsverifier.Warning;

public class SuppressFilterElementTest {

    private SuppressFilterElement filter;

    @BeforeEach
    public void setUp() {
        filter = new SuppressFilterElement("Test", "Test", null, null, null, null);
    }

    @Test
    public void testDecideDefault() {
        final AuditEvent ev = new AuditEvent(this, "Test.java");
        assertTrue(filter.accept(ev), ev.getFileName());
    }

    @Test
    public void testDecideLocalizedMessage() {
        final LocalizedMessage message =
            new LocalizedMessage(1, 0, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        // deny because there are matches on file and check names
        assertFalse(filter.accept(ev), "Names match");
    }

    @Test
    public void testDecideByMessage() {
        final LocalizedMessage message =
            new LocalizedMessage(1, 0, "", "", null, null, getClass(), "Test");
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement filter1 =
                new SuppressFilterElement(null, null, "Test", null, null, null);
        final SuppressFilterElement filter2 =
                new SuppressFilterElement(null, null, "Bad", null, null, null);
        assertFalse(filter1.accept(ev), "Message match");
        assertTrue(filter2.accept(ev), "Message not match");
    }

    @Test
    public void testDecideByLine() {
        final LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement filter1 =
                new SuppressFilterElement("Test", "Test", null, null, "1-10", null);
        final SuppressFilterElement filter2 =
                new SuppressFilterElement("Test", "Test", null, null, "1-9, 11", null);
        final SuppressFilterElement filter3 =
                new SuppressFilterElement("Test", "Test", null, null, null, null);
        // deny because there are matches on file name, check name, and line
        assertFalse(filter1.accept(ev), "In range 1-10");
        assertTrue(filter2.accept(ev), "Not in 1-9, 11");
        assertFalse(filter3.accept(ev), "none");
    }

    @Test
    public void testDecideByColumn() {
        final LocalizedMessage message =
            new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement filter1 =
                new SuppressFilterElement("Test", "Test", null, null, null, "1-10");
        final SuppressFilterElement filter2 =
                new SuppressFilterElement("Test", "Test", null, null, null, "1-9, 11");

        // deny because there are matches on file name, check name, and column
        assertFalse(filter1.accept(ev), "In range 1-10");
        assertTrue(filter2.accept(ev), "Not in 1-9, 1)");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingFileNameNull() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, null, message);
        assertTrue(filter.accept(ev), "Filter should accept valid event");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingMessageNull() {
        final AuditEvent ev = new AuditEvent(this, "ATest.java", null);
        assertTrue(filter.accept(ev), "Filter should accept valid event");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleNull() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "MyModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        assertFalse(filter.accept(ev), "Filter should not accept invalid event");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleEqual() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "MyModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter =
                new SuppressFilterElement("Test", "Test", null, "MyModule", null, null);

        assertFalse(myFilter.accept(ev), "Filter should not accept invalid event");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleNotEqual() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, "TheirModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter =
                new SuppressFilterElement("Test", "Test", null, "MyModule", null, null);

        assertTrue(myFilter.accept(ev), "Filter should accept valid event");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingRegExpNotMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "T1est", message);
        assertTrue(filter.accept(ev), "Filter should accept valid event");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingRegExpMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "TestSUFFIX", message);
        final SuppressFilterElement myFilter =
                new SuppressFilterElement("Test", null, null, null, null, null);
        assertFalse(myFilter.accept(ev), "Filter should not accept invalid event");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingCheckRegExpNotMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter = new SuppressFilterElement("Test",
                "NON_EXISTENT_CHECK", null, "MyModule", null, null);
        assertTrue(myFilter.accept(ev), "Filter should accept valid event");
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingCheckRegExpMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter = new SuppressFilterElement("Test",
                getClass().getCanonicalName(), null, null, null, null);

        assertFalse(myFilter.accept(ev), "Filter should not accept invalid event");
    }

    @Test
    public void testDecideByFileNameAndSourceNameCheckRegExpNotMatch() {
        final LocalizedMessage message =
                new LocalizedMessage(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter =
                new SuppressFilterElement("Test", TreeWalkerTest.class.getCanonicalName(),
                        null, null, null, null);

        assertTrue(myFilter.accept(ev), "Filter should not accept invalid event");
    }

    @Test
    public void testEquals() {
        // filterBased is used instead of filter field only to satisfy IntelliJ IDEA Inspection
        // Inspection "Arguments to assertEquals() in wrong order "
        final SuppressFilterElement filterBased =
                new SuppressFilterElement("Test", "Test", null, null, null, null);

        final SuppressFilterElement filter2 =
                new SuppressFilterElement("Test", "Test", null, null, null, null);
        assertEquals(filterBased, filter2, "filter, filter2");
        final SuppressFilterElement filter3 =
                new SuppressFilterElement("Test", "Test3", null, null, null, null);
        assertNotEquals(filterBased, filter3, "filter, filter3");
        final SuppressFilterElement filterBased1 =
                new SuppressFilterElement("Test", "Test", null, null, null, "1-10");

        assertNotEquals(filterBased1, filter2, "filter, filter2");
        final SuppressFilterElement filter22 =
                new SuppressFilterElement("Test", "Test", null, null, null, "1-10");
        assertEquals(filterBased1, filter22, "filter, filter2");
        assertNotEquals(filterBased1, filter2, "filter, filter2");
        final SuppressFilterElement filterBased2 =
                new SuppressFilterElement("Test", "Test", null, null, "3,4", null);
        assertNotEquals(filterBased2, filter2, "filter, filter2");
        final SuppressFilterElement filter23 =
                new SuppressFilterElement("Test", "Test", null, null, "3,4", null);
        assertEquals(filterBased2, filter23, "filter, filter2");
        assertNotEquals(filterBased2, filter2, "filter, filter2");
        assertEquals(filterBased2, filter23, "filter, filter2");
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(SuppressFilterElement.class)
                .usingGetClass()
                .withIgnoredFields("fileRegexp", "checkRegexp", "messageRegexp", "columnFilter",
                        "lineFilter")
                .suppress(Warning.NONFINAL_FIELDS)
                .report();
        assertEquals(EqualsVerifierReport.SUCCESS, ev, "Error: " + ev.getMessage());
    }

}
