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

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.EqualsVerifierReport;

public class LineColumnTest {

    @Test
    public void testCompareToBothEqual() {
        final int actual = new LineColumn(0, 0).compareTo(new LineColumn(0, 0));
        assertWithMessage("Invalid LineColumn comparing result")
                .that(actual)
                .isEqualTo(0);
    }

    @Test
    public void testCompareToFirstLarger() {
        final LineColumn lineColumn = new LineColumn(0, 0);

        final int line1column0 = new LineColumn(1, 0).compareTo(lineColumn);
        assertWithMessage("Invalid LineColumn comparison result")
                .that(line1column0)
                .isEqualTo(1);
        final int line2Column1 = new LineColumn(0, 1).compareTo(lineColumn);
        assertWithMessage("Invalid LineColumn comparison result")
                .that(line2Column1)
                .isEqualTo(1);
    }

    @Test
    public void testCompareToFirstSmaller() {
        final Comparable<LineColumn> lineColumn = new LineColumn(0, 0);

        final int line1Column0 = lineColumn.compareTo(new LineColumn(1, 0));
        assertWithMessage("Invalid LineColumn comparison result")
                .that(line1Column0)
                .isEqualTo(-1);
        final int line0Column1 = lineColumn.compareTo(new LineColumn(0, 1));
        assertWithMessage("Invalid LineColumn comparison result")
                .that(line0Column1)
                .isEqualTo(-1);
    }

    @Test
    public void testEqualsAndHashCode() {
        final EqualsVerifierReport ev = EqualsVerifier.forClass(LineColumn.class).usingGetClass()
                .report();
        assertWithMessage("Error: " + ev.getMessage())
                .that(ev.isSuccessful())
                .isTrue();
    }

    @Test
    public void testGetters() {
        final LineColumn lineColumn = new LineColumn(2, 3);

        assertWithMessage("Invalid LineColumn comparison result")
                .that(lineColumn.getLine())
                .isEqualTo(2);
        assertWithMessage("Invalid LineColumn comparison result")
                .that(lineColumn.getColumn())
                .isEqualTo(3);
    }

}
