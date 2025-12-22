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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class SuppressionFilterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionfilter";
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "20: First sentence should end with a period.",
            "23:11: '10' is a magic number.",
            "27:15: '100' is a magic number.",
            "29:15: Must have at least one statement.",
        };

        final String[] expectedWithFilter = {
            "29:15: Must have at least one statement.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {

        final String[] expectedWithoutFilter = {
            "24: Line is longer than 80 characters (found 84).",
            "31:22: String literal expressions should be on the left side of an equals comparison.",
            "35:32: String literal expressions should be on the left side of "
                    + "an equalsIgnoreCase comparison.",
        };

        final String[] expectedWithFilter = {
            "24: Line is longer than 80 characters (found 84).",
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {

        final String[] expectedWithoutFilter = {
            "1: Duplicated property 'keyB' (2 occurrence(s)).",
            "4: Duplicated property 'keyC' (2 occurrence(s)).",
        };

        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(
                getPath("Example3.java"),
                getPath(".hidden/hidden.properties"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {

        final String[] expectedWithoutFilter = {
            "20:14: Name 'log' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "23:14: Name 'constant' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "29:27: Name 'log' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "32:30: Name 'line' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };

        final String[] expectedWithFilter = {
            "23:14: Name 'constant' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
            "32:30: Name 'line' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }
}
