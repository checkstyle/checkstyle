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

public class SuppressionXpathFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionxpathfilter";
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "21:3: Cyclomatic Complexity is 4 (max allowed is 3).",
        };

        final String[] expectedWithFilter = {};

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {

        final String[] expectedWithoutFilter = {
            "14:1: 'package' should be separated from previous line.",
            "15:1: 'CLASS_DEF' should be separated from previous line.",
        };

        final String[] expectedWithFilter = {
            "15:1: 'CLASS_DEF' should be separated from previous line.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {

        final String[] expectedWithoutFilter = {
            "17:15: Name 'GetSomeVar' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "19:15: Name 'TestMethod' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        final String[] expectedWithFilter = {
            "19:15: Name 'TestMethod' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };

        System.setProperty("config.folder", "src/xdocs-examples/resources/"
            + getPackageLocation());
        verifyFilterWithInlineConfigParser(getPath("Example5.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

}
