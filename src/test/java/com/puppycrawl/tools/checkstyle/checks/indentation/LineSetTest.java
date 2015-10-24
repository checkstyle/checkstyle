////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.indentation;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class LineSetTest {

    @Test
    public void testToStringShowingFirstAndLastLine() {
        final LineSet lineSet = new LineSet();
        lineSet.addLineAndCol(0, 1);
        lineSet.addLineAndCol(2, 3);

        final String result = lineSet.toString();

        assertEquals("LineSet[firstLine=0, lastLine=2]", result);
    }
}
