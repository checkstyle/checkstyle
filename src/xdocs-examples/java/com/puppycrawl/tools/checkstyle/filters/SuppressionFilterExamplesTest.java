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

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.UniquePropertiesCheck;
import com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;

public class SuppressionFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionfilter";
    }

    @Test
    public void testExample1() throws Exception {

        final String[] expectedWithoutFilter = {
            "20:7: " + getCheckMessage(MemberNameCheck.class, MSG_INVALID_PATTERN,
                    "MyVariable", "^[a-z][a-zA-Z0-9]*$"),
            "22:11: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "10"),
            "26:15: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY, "100"),
            "28:15: " + getCheckMessage(EmptyBlockCheck.class,
                    EmptyBlockCheck.MSG_KEY_BLOCK_NO_STATEMENT),
        };

        final String[] expectedWithFilter = {
            "28:15: " + getCheckMessage(EmptyBlockCheck.class,
                    EmptyBlockCheck.MSG_KEY_BLOCK_NO_STATEMENT),
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {

        final String[] expectedWithoutFilter = {
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 80, 84),
            "31:22: " + getCheckMessage(EqualsAvoidNullCheck.class,
                    EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL),
            "35:32: " + getCheckMessage(EqualsAvoidNullCheck.class,
                    EqualsAvoidNullCheck.MSG_EQUALS_IGNORE_CASE_AVOID_NULL),
        };

        final String[] expectedWithFilter = {
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 80, 84),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase2() throws Exception {

        final String[] expectedWithoutFilter = {
            "1: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyB", 2),
            "4: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyC", 2),
        };

        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParserSeparateConfigAndTarget(
                getPath("UseCase2.java"),
                getPath(".hidden/hidden.properties"),
                expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String memberPattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";
        final String constantPattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] expectedWithoutFilter = {
            "20:14: " + getCheckMessage(MemberNameCheck.class, MSG_INVALID_PATTERN,
                    "log", memberPattern),
            "23:14: " + getCheckMessage(MemberNameCheck.class, MSG_INVALID_PATTERN,
                    "constant", memberPattern),
            "29:27: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "log", constantPattern),
            "32:30: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "line", constantPattern),
        };

        final String[] expectedWithFilter = {
            "23:14: " + getCheckMessage(MemberNameCheck.class, MSG_INVALID_PATTERN,
                    "constant", memberPattern),
            "32:30: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "line", constantPattern),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

}
