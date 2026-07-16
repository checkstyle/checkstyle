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

    /**
     * Creates a new {@code SuppressWithNearbyTextFilterExamplesTest} instance.
     */
    public SuppressWithNearbyTextFilterExamplesTest() {
        // no code by default
    }

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbytextfilter";
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "18:11: '42' is a magic number.",
            "19:11: '43' is a magic number.",
            "21: Line is longer than 55 characters (found 74).",
            "24: Line is longer than 55 characters (found 84).",
            "28: Line is longer than 55 characters (found 72).",
        };

        final String[] expectedWithFilter = {
            "19:11: '43' is a magic number.",
            "21: Line is longer than 55 characters (found 74).",
            "24: Line is longer than 55 characters (found 84).",
            "28: Line is longer than 55 characters (found 72).",
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {

        final String[] expectedWithoutFilter = {
            "15:11: '42' is a magic number.",
            "16:11: '43' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "16:11: '43' is a magic number.",
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {

        final String[] expectedWithoutFilter = {
            "21:11: '42' is a magic number.",
            "22:11: '43' is a magic number.",
            "24: Line is longer than 55 characters (found 74).",
            "27: Line is longer than 55 characters (found 84).",
            "31: Line is longer than 55 characters (found 72).",
        };

        final String[] expectedWithFilter = {
            "21:11: '42' is a magic number.",
            "22:11: '43' is a magic number.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {

        final String[] expectedWithoutFilter = {
            "20:11: '42' is a magic number.",
            "21:11: '43' is a magic number.",
            "23: Line is longer than 55 characters (found 74).",
            "26: Line is longer than 55 characters (found 84).",
            "30: Line is longer than 55 characters (found 72).",
        };

        final String[] expectedWithFilter = {
            "21:11: '43' is a magic number.",
            "23: Line is longer than 55 characters (found 74).",
            "26: Line is longer than 55 characters (found 84).",
            "30: Line is longer than 55 characters (found 72).",
        };

        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {

        final String[] expectedWithoutFilter = {
            "21:11: '42' is a magic number.",
            "22:11: '43' is a magic number.",
            "24: Line is longer than 55 characters (found 74).",
            "27: Line is longer than 55 characters (found 84).",
            "31: Line is longer than 55 characters (found 72).",
        };

        final String[] expectedWithFilter = {
            "24: Line is longer than 55 characters (found 74).",
            "27: Line is longer than 55 characters (found 84).",
            "31: Line is longer than 55 characters (found 72).",
        };

        verifyFilterWithInlineConfigParser(getPath("Example5.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase2() throws Exception {

        final String[] expectedWithoutFilters = {
            "2: Duplicated property 'key.one' (2 occurrence(s)).",
            "4: Duplicated property 'key.two' (2 occurrence(s)).",
        };

        final String[] expectedWithFilters = {
            "4: Duplicated property 'key.two' (2 occurrence(s)).",
        };

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(
                getPath("UseCase2.java"),
                getPath("UseCase2.properties"),
                expectedWithoutFilters,
                expectedWithFilters);
    }

    @Test
    public void testUseCase3() throws Exception {

        final String[] expectedWithoutFilter = {
            "17:11: '42' is a magic number.",
            "18:11: '43' is a magic number.",
        };

        final String[] expectedWithFilter = {
            "18:11: '43' is a magic number.",
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase4() throws Exception {

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

        verifyFilterWithInlineConfigParser(getPath("UseCase4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample9() throws Exception {

        final String[] expectedWithoutFilter = {
            "23:11: '42' is a magic number.",
            "24:11: '43' is a magic number.",
            "26: Line is longer than 55 characters (found 74).",
            "29: Line is longer than 55 characters (found 84).",
            "33: Line is longer than 55 characters (found 72).",
        };

        final String[] expectedWithFilter = {
            "23:11: '42' is a magic number.",
            "24:11: '43' is a magic number.",
            "26: Line is longer than 55 characters (found 74).",
            "29: Line is longer than 55 characters (found 84).",
        };

        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

}
