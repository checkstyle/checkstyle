///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
            "13:11: '42' is a magic number.",
            "14:11: '43' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "14:11: '43' is a magic number.",
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
            "20: Line is longer than 70 characters (found 74).",
            "23: Line is longer than 70 characters (found 84).",
            "27: Line is longer than 70 characters (found 72).",
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
            "21:11: '43' is a magic number.",
            "23: Line is longer than 55 characters (found 63).",
            "24: Line is longer than 55 characters (found 74).",
            "27: Line is longer than 55 characters (found 84).",
            "31: Line is longer than 55 characters (found 72).",
        };

        final String[] expectedWithFilter = {
            "21:11: '43' is a magic number.",
            "23: Line is longer than 55 characters (found 63).",
            "24: Line is longer than 55 characters (found 74).",
            "27: Line is longer than 55 characters (found 84).",
            "31: Line is longer than 55 characters (found 72).",
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
            "22: Line is longer than 70 characters (found 74).",
            "25: Line is longer than 70 characters (found 84).",
            "29: Line is longer than 70 characters (found 72).",
        };

        final String[] expectedWithFilter = {
            "22: Line is longer than 70 characters (found 74).",
            "25: Line is longer than 70 characters (found 84).",
        };

        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter, expectedWithFilter);
    }
}
