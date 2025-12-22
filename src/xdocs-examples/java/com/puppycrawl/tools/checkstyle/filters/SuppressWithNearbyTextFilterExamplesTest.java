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

public class SuppressWithNearbyTextFilterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbytextfilter";
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "13:20: '24' is a magic number.",
            "14:20: '7' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "14:20: '7' is a magic number.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {

        final String[] expectedWithoutFilter = {
            "15:11: '42' is a magic number.",
            "16:11: '43' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "16:11: '43' is a magic number.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {

        final String[] expectedWithoutFilter = {
            "16: Line is longer than 70 characters (found 74).",
        };

        final String[] expectedWithFilter = {

        };

        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {

        final String[] expectedWithoutFilter = {
            "20:11: '42' is a magic number.",
            "21: Line is longer than 55 characters (found 65).",
        };

        final String[] expectedWithFilter = {
            "21: Line is longer than 55 characters (found 65).",
        };

        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {

        final String[] expectedWithoutFilters = {
            "1: Duplicated property 'key.one' (3 occurrence(s)).",
            "5: Duplicated property 'key.two' (2 occurrence(s)).",
        };

        final String[] expectedWithFilters = {
            "5: Duplicated property 'key.two' (2 occurrence(s)).",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(
                getPath("Example5.java"),
                getPath("Example5.properties"),
                expectedWithoutFilters,
                expectedWithFilters);
    }

    @Test
    public void testExample6() throws Exception {

        final String[] expectedWithoutFilters = {
            "2: Duplicated property 'key.one' (2 occurrence(s)).",
            "4: Duplicated property 'key.two' (2 occurrence(s)).",
        };

        final String[] expectedWithFilters = {
            "4: Duplicated property 'key.two' (2 occurrence(s)).",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(
                getPath("Example6.java"),
                getPath("Example6.properties"),
                expectedWithoutFilters,
                expectedWithFilters);
    }

    @Test
    public void testExample7() throws Exception {

        final String[] expectedWithoutFilter = {
            "17:11: '42' is a magic number.",
            "18:11: '43' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "18:11: '43' is a magic number.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example7.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {

        final String[] expectedWithoutFilter = {
            "18:11: '42' is a magic number.",
            "19:11: '43' is a magic number.",
            "20:11: '44' is a magic number.",
            "21:11: '45' is a magic number.",
            "22:11: '46' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "22:11: '46' is a magic number.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example8.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample9() throws Exception {

        final String[] expectedWithoutFilter = {
            "19: Line is longer than 70 characters (found 72).",
        };

        final String[] expectedWithFilter = {

        };

        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter, expectedWithFilter);
    }
}
