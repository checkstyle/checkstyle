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

package com.puppycrawl.tools.checkstyle.filters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertWithMessage;
import com.puppycrawl.tools.checkstyle.TreeWalkerTest;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

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
        assertWithMessage(ev.getFileName())
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testDecideViolation() {
        final Violation violation =
            new Violation(1, 0, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", violation);
        // deny because there are matches on file and check names
        assertWithMessage("Names match")
            .that(filter.accept(ev))
            .isFalse();
    }

    @Test
    public void testDecideByMessage() {
        final Violation violation =
            new Violation(1, 0, "", "", null, null, getClass(), "Test");
        final AuditEvent ev = new AuditEvent(this, "ATest.java", violation);
        final SuppressFilterElement filter1 =
                new SuppressFilterElement(null, null, "Test", null, null, null);
        final SuppressFilterElement filter2 =
                new SuppressFilterElement(null, null, "Bad", null, null, null);
        assertWithMessage("Message match")
                .that(filter1.accept(ev))
                .isFalse();
        assertWithMessage("Message not match")
                .that(filter2.accept(ev))
                .isTrue();
    }

    @Test
    public void testDecideByLine() {
        final Violation violation =
            new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", violation);
        final SuppressFilterElement filter1 =
                new SuppressFilterElement("Test", "Test", null, null, "1-10", null);
        final SuppressFilterElement filter2 =
                new SuppressFilterElement("Test", "Test", null, null, "1-9, 11", null);
        final SuppressFilterElement filter3 =
                new SuppressFilterElement("Test", "Test", null, null, null, null);
        // deny because there are matches on file name, check name, and line
        assertWithMessage("In range 1-10")
                .that(filter1.accept(ev))
                .isFalse();
        assertWithMessage("Not in 1-9, 11")
                .that(filter2.accept(ev))
                .isTrue();
        assertWithMessage("none")
                .that(filter3.accept(ev))
                .isFalse();
    }

    @Test
    public void testDecideByColumn() {
        final Violation violation =
            new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", violation);
        final SuppressFilterElement filter1 =
                new SuppressFilterElement("Test", "Test", null, null, null, "1-10");
        final SuppressFilterElement filter2 =
                new SuppressFilterElement("Test", "Test", null, null, null, "1-9, 11");

        // deny because there are matches on file name, check name, and column
        assertWithMessage("In range 1-10")
                .that(filter1.accept(ev))
                .isFalse();
        assertWithMessage("Not in 1-9, 1)")
                .that(filter2.accept(ev))
                .isTrue();
    }

    @Test
    public void testDecideByColumnWhenColumnsIsNull() {
        final Violation violation =
            new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", violation);
        // When columns is null, columnFilter should be null,
        // so column filtering doesn't apply
        // This means isLineAndColumnMatching should return true
        // when both lineFilter and columnFilter are null
        final SuppressFilterElement filterWithNullColumns =
                new SuppressFilterElement("Test", "Test", null, null, null, null);
        // Event should be rejected because file and check match,
        // and both line and column filters are null
        // If columnFilter is not properly set to null,
        // the isLineAndColumnMatching logic would fail
        assertWithMessage("When columns is null, columnFilter should be null "
                + "and event should be rejected")
                .that(filterWithNullColumns.accept(ev))
                .isFalse();
    }

    @Test
    public void testDecideByLineAndColumnWhenBothAreNull() {
        final Violation violation =
            new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", violation);
        // When both lines and columns are null,
        // both lineFilter and columnFilter should be null
        // The isLineAndColumnMatching method should return true when both are null
        // This test specifically targets the mutation
        // that removes columnFilter = null assignment
        final SuppressFilterElement filterWithNullLineAndColumn =
                new SuppressFilterElement("Test", "Test", null, null, null, null);
        // When both filters are null, isLineAndColumnMatching returns true
        // So the event should be rejected (accept returns false)
        // because file and check match
        assertWithMessage("When both lines and columns are null, both filters should be null "
                + "and event should be rejected")
                .that(filterWithNullLineAndColumn.accept(ev))
                .isFalse();
    }

    @Test
    public void testColumnFilterIsNullWhenColumnsIsNull() {
        // This test directly verifies that columnFilter is set to null
        // when columns parameter is null
        // This kills the mutation that removes the assignment columnFilter = null;
        final SuppressFilterElement testFilter =
                new SuppressFilterElement("Test", "Test", null, null, null, null);
        final Object columnFilter = TestUtil.getInternalState(testFilter, "columnFilter", Object.class);
        assertWithMessage("When columns is null, columnFilter should be explicitly set to null")
                .that(columnFilter)
                .isNull();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingFileNameNull() {
        final Violation message =
                new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, null, message);
        assertWithMessage("Filter should accept valid event")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingMessageNull() {
        final AuditEvent ev = new AuditEvent(this, "ATest.java", null);
        assertWithMessage("Filter should accept valid event")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleNull() {
        final Violation violation =
                new Violation(10, 10, "", "", null, "MyModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", violation);
        assertWithMessage("Filter should not accept invalid event")
                .that(filter.accept(ev))
                .isFalse();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleEqual() {
        final Violation violation =
                new Violation(10, 10, "", "", null, "MyModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", violation);
        final SuppressFilterElement myFilter =
                new SuppressFilterElement("Test", "Test", null, "MyModule", null, null);

        assertWithMessage("Filter should not accept invalid event")
                .that(myFilter.accept(ev))
                .isFalse();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingModuleNotEqual() {
        final Violation message =
                new Violation(10, 10, "", "", null, "TheirModule", getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter =
                new SuppressFilterElement("Test", "Test", null, "MyModule", null, null);

        assertWithMessage("Filter should accept valid event")
                .that(myFilter.accept(ev))
                .isTrue();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingRegExpNotMatch() {
        final Violation message =
                new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "T1est", message);
        assertWithMessage("Filter should accept valid event")
                .that(filter.accept(ev))
                .isTrue();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingRegExpMatch() {
        final Violation message =
                new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "TestSUFFIX", message);
        final SuppressFilterElement myFilter =
                new SuppressFilterElement("Test", null, null, null, null, null);
        assertWithMessage("Filter should not accept invalid event")
                .that(myFilter.accept(ev))
                .isFalse();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingCheckRegExpNotMatch() {
        final Violation message =
                new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter = new SuppressFilterElement("Test",
                "NON_EXISTENT_CHECK", null, "MyModule", null, null);
        assertWithMessage("Filter should accept valid event")
                .that(myFilter.accept(ev))
                .isTrue();
    }

    @Test
    public void testDecideByFileNameAndModuleMatchingCheckRegExpMatch() {
        final Violation message =
                new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter = new SuppressFilterElement("Test",
                getClass().getCanonicalName(), null, null, null, null);

        assertWithMessage("Filter should not accept invalid event")
                .that(myFilter.accept(ev))
                .isFalse();
    }

    @Test
    public void testDecideByFileNameAndSourceNameCheckRegExpNotMatch() {
        final Violation message =
                new Violation(10, 10, "", "", null, null, getClass(), null);
        final AuditEvent ev = new AuditEvent(this, "ATest.java", message);
        final SuppressFilterElement myFilter =
                new SuppressFilterElement("Test", TreeWalkerTest.class.getCanonicalName(),
                        null, null, null, null);

        assertWithMessage("Filter should not accept invalid event")
                .that(myFilter.accept(ev))
                .isTrue();
    }

    @Test
    public void testEquals() {
        // filterBased is used instead of filter field only to satisfy IntelliJ IDEA Inspection
        // Inspection "Arguments to assertEquals() in wrong order "
        final SuppressFilterElement filterBased =
                new SuppressFilterElement("Test", "Test", null, null, null, null);

        final SuppressFilterElement filter2 =
                new SuppressFilterElement("Test", "Test", null, null, null, null);
        assertWithMessage("filter, filter2")
            .that(filter2)
            .isEqualTo(filterBased);
        final SuppressFilterElement filter3 =
                new SuppressFilterElement("Test", "Test3", null, null, null, null);
        assertWithMessage("filter, filter3")
            .that(filter3)
            .isNotEqualTo(filterBased);
        final SuppressFilterElement filterBased1 =
                new SuppressFilterElement("Test", "Test", null, null, null, "1-10");

        assertWithMessage("filter, filter2")
            .that(filter2)
            .isNotEqualTo(filterBased1);
        final SuppressFilterElement filter22 =
                new SuppressFilterElement("Test", "Test", null, null, null, "1-10");
        assertWithMessage("filter, filter2")
            .that(filter22)
            .isEqualTo(filterBased1);
        assertWithMessage("filter, filter2")
            .that(filter2)
            .isNotEqualTo(filterBased1);
        final SuppressFilterElement filterBased2 =
                new SuppressFilterElement("Test", "Test", null, null, "3,4", null);
        assertWithMessage("filter, filter2")
            .that(filter2)
            .isNotEqualTo(filterBased2);
        final SuppressFilterElement filter23 =
                new SuppressFilterElement("Test", "Test", null, null, "3,4", null);
        assertWithMessage("filter, filter2")
            .that(filter23)
            .isEqualTo(filterBased2);
        assertWithMessage("filter, filter2")
            .that(filter2)
            .isNotEqualTo(filterBased2);
        assertWithMessage("filter, filter2")
            .that(filter23)
            .isEqualTo(filterBased2);
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(SuppressFilterElement.class)
                .usingGetClass()
                .withIgnoredFields("columnFilter", "lineFilter")
                .suppress(Warning.NONFINAL_FIELDS)
                .report();
        assertWithMessage("Error: %s", ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

}
