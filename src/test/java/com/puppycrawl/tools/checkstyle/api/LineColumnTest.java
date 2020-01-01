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

import org.junit.jupiter.api.Test;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class LineColumnTest {

    @Test
    public void testCompareToBothEqual() {
        final int actual = new LineColumn(0, 0).compareTo(new LineColumn(0, 0));
        assertEquals(0, actual, "Invalid LineColumn comparing result");
    }

    @Test
    public void testCompareToFirstLarger() {
        final LineColumn lineColumn = new LineColumn(0, 0);

        final int line1column0 = new LineColumn(1, 0).compareTo(lineColumn);
        assertEquals(1, line1column0, "Invalid LineColumn comparison result");
        final int line2Column1 = new LineColumn(0, 1).compareTo(lineColumn);
        assertEquals(1, line2Column1, "Invalid LineColumn comparison result");
    }

    @Test
    public void testCompareToFirstSmaller() {
        final Comparable<LineColumn> lineColumn = new LineColumn(0, 0);

        final int line1Column0 = lineColumn.compareTo(new LineColumn(1, 0));
        assertEquals(-1, line1Column0, "Invalid LineColumn comparison result");
        final int line0Column1 = lineColumn.compareTo(new LineColumn(0, 1));
        assertEquals(-1, line0Column1, "Invalid LineColumn comparison result");
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(LineColumn.class).usingGetClass()
                .report();
        assertEquals(EqualsVerifierReport.SUCCESS, ev, "Error: " + ev.getMessage());
    }

    @Test
    public void testGetters() {
        final LineColumn lineColumn = new LineColumn(2, 3);

        assertEquals(2, lineColumn.getLine(), "Invalid LineColumn comparison result");
        assertEquals(3, lineColumn.getColumn(), "Invalid LineColumn comparison result");
    }

}
