////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class CsvFilterElementTest {

    @Test
    public void testDecideSingle() {
        final IntFilterElement filter = new CsvFilterElement("0");
        assertFalse("less than", filter.accept(-1));
        assertTrue("equal", filter.accept(0));
        assertFalse("greater than", filter.accept(1));
    }

    @Test
    public void testDecidePair() {
        final IntFilterElement filter = new CsvFilterElement("0, 2");
        assertFalse("less than", filter.accept(-1));
        assertTrue("equal 0", filter.accept(0));
        assertFalse("greater than", filter.accept(1));
        assertTrue("equal 2", filter.accept(2));
    }

    @Test
    public void testDecideRange() {
        final IntFilterElement filter = new CsvFilterElement("0-2");
        assertFalse("less than", filter.accept(-1));
        assertTrue("equal 0", filter.accept(0));
        assertTrue("equal 1", filter.accept(1));
        assertTrue("equal 2", filter.accept(2));
        assertFalse("greater than", filter.accept(3));
    }

    @Test
    public void testDecideEmptyRange() {
        final IntFilterElement filter = new CsvFilterElement("2-0");
        assertFalse("less than", filter.accept(-1));
        assertFalse("equal 0", filter.accept(0));
        assertFalse("equal 1", filter.accept(1));
        assertFalse("equal 2", filter.accept(2));
        assertFalse("greater than", filter.accept(3));
    }

    @Test
    public void testDecideRangePlusValue() {
        final IntFilterElement filter = new CsvFilterElement("0-2, 10");
        assertFalse("less than", filter.accept(-1));
        assertTrue("equal 0", filter.accept(0));
        assertTrue("equal 1", filter.accept(1));
        assertTrue("equal 2", filter.accept(2));
        assertFalse("greater than", filter.accept(3));
        assertTrue("equal 10", filter.accept(10));
    }

    @Test
    public void testEmptyChain() {
        final CsvFilterElement filter = new CsvFilterElement("");
        assertFalse("0", filter.accept(0));
    }

    @Test
    public void testOneFilter() {
        final CsvFilterElement filter = new CsvFilterElement("");
        filter.addFilter(new IntMatchFilterElement(0));
        assertTrue("0", filter.accept(0));
        assertFalse("1", filter.accept(1));
    }

    @Test
    public void testMultipleFilter() {
        final CsvFilterElement filter = new CsvFilterElement("");
        filter.addFilter(new IntMatchFilterElement(0));
        filter.addFilter(new IntRangeFilterElement(0, 2));
        assertTrue("0", filter.accept(0));
        assertTrue("1", filter.accept(1));
        filter.addFilter(new IntRangeFilterElement(3, 4));
        assertTrue("0 is in [3,4]", filter.accept(0));
    }

    @Test
    public void testGetFilters() {
        final CsvFilterElement filter = new CsvFilterElement("");
        filter.addFilter(new IntMatchFilterElement(0));
        assertEquals("size is the same", 1, filter.getFilters().size());
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(CsvFilterElement.class)
                .usingGetClass().report();
        assertEquals("Error: " + ev.getMessage(), EqualsVerifierReport.SUCCESS, ev);
    }

}
