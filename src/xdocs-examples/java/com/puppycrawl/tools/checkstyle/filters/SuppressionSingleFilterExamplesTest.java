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

public class SuppressionSingleFilterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionsinglefilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expectedWithoutFilter = {
            "26:17: '100' is a magic number.",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expectedWithoutFilter = {
            "23:13: String literal expressions should be on the left side of an equals comparison.",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expectedWithoutFilter = {
            "18: Line matches the illegal pattern 'example'.",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:12: '.' is followed by whitespace.", "22:9: 'int' is followed by whitespace.",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expectedWithoutFilter = {
            "14:15: Name 'example_Method' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "17:15: Name 'Another_Method' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example5.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expectedWithoutFilter = {
            "14:28: Name 'myConstant' must match pattern '^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$'.",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example6.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expectedWithoutFilter = {
            "19:15: Name 'MyVariable' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
            "21:15: Name 'PrintHello' must match pattern '^[a-z][a-zA-Z0-9]*$'.",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example7.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:15: More than 5 parameters (found 6).",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example8.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expectedWithoutFilter = {
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expectedWithoutFilter = {
            "16:18: Name 'log' must match pattern '^[A-Z][a-zA-Z0-9]*$'.",
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example10.java"),
                expectedWithoutFilter, expectedWithFilter);
    }
}
