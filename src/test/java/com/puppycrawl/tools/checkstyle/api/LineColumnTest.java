////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class LineColumnTest {

    @Test
    public void testCompareToBothEqual() {
        assertEquals("Invalid LineColumn comparing result",
                0, new LineColumn(0, 0).compareTo(new LineColumn(0, 0)));
    }

    @Test
    public void testCompareToFirstLarger() {
        final LineColumn lineColumn = new LineColumn(0, 0);

        assertEquals("Invalid LineColumn comparison result",
                1, new LineColumn(1, 0).compareTo(lineColumn));
        assertEquals("Invalid LineColumn comparison result",
                1, new LineColumn(0, 1).compareTo(lineColumn));
    }

    @Test
    public void testCompareToFirstSmaller() {
        final Comparable<LineColumn> lineColumn = new LineColumn(0, 0);

        assertEquals("Invalid LineColumn comparison result", -1,
                lineColumn.compareTo(new LineColumn(1, 0)));
        assertEquals("Invalid LineColumn comparison result", -1,
                lineColumn.compareTo(new LineColumn(0, 1)));
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(LineColumn.class).usingGetClass()
                .report();
        assertEquals("Error: " + ev.getMessage(), EqualsVerifierReport.SUCCESS, ev);
    }

    @Test
    public void testGetters() {
        final LineColumn lineColumn = new LineColumn(2, 3);

        assertEquals("Invalid LineColumn comparison result",
                2, lineColumn.getLine());
        assertEquals("Invalid LineColumn comparison result",
                3, lineColumn.getColumn());
    }

}
