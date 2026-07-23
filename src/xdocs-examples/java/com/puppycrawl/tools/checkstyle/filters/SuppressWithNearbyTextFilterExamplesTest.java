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
import com.puppycrawl.tools.checkstyle.checks.UniquePropertiesCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;

public class SuppressWithNearbyTextFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbytextfilter";
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "18:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "19:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "21: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "28: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        final String[] expectedWithFilter = {
            "19:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "21: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "28: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {

        final String[] expectedWithoutFilter = {
            "21:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "22:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "27: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "31: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        final String[] expectedWithFilter = {
            "21:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "22:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "27: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "31: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {

        final String[] expectedWithoutFilter = {
            "15:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "16:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
        };

        final String[] expectedWithFilter = {
            "16:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {

        final String[] expectedWithoutFilter = {
            "21:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "22:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "27: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "31: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        final String[] expectedWithFilter = {
            "21:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "22:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {

        final String[] expectedWithoutFilter = {
            "20:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "21:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "23: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "26: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "30: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        final String[] expectedWithFilter = {
            "21:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "23: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "26: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "30: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {

        final String[] expectedWithoutFilter = {
            "21:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "22:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "27: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "31: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        final String[] expectedWithFilter = {
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "27: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "31: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        verifyFilterWithInlineConfigParser(getPath("Example5.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase2() throws Exception {

        final String[] expectedWithoutFilters = {
            "2: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "key.one", 2),
            "4: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "key.two", 2),
        };

        final String[] expectedWithFilters = {
            "4: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "key.two", 2),
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
            "17:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "18:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
        };

        final String[] expectedWithFilter = {
            "18:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase4() throws Exception {

        final String[] expectedWithoutFilter = {
            "18:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "19:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "20:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "44"),
            "21:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "45"),
            "22:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "46"),
        };

        final String[] expectedWithFilter = {
            "22:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "46"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample9() throws Exception {

        final String[] expectedWithoutFilter = {
            "23:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "24:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "26: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "29: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
            "33: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 72),
        };

        final String[] expectedWithFilter = {
            "23:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "42"),
            "24:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "43"),
            "26: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 74),
            "29: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 55, 84),
        };

        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

}
