///
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
///

package com.puppycrawl.tools.checkstyle.filters;

import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL;
import static com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck.MSG_KEY;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_RETURN_EXPECTED;
import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_ILLEGAL_REGEXP;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck;
import com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck;

public class SuppressionSingleFilterExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppressionsinglefilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expectedWithoutFilter = {
            "21:17: " + getCheckMessage(MagicNumberCheck.class, MSG_KEY, "100"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expectedWithoutFilter = {
            "19:13: " + getCheckMessage(EqualsAvoidNullCheck.class, MSG_EQUALS_AVOID_NULL),
            "25: " + getCheckMessage(JavadocMethodCheck.class, MSG_RETURN_EXPECTED),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example2.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expectedWithoutFilter = {
            "19: " + getCheckMessage(RegexpCheck.class, MSG_ILLEGAL_REGEXP, "example"),
            "4: " + getCheckMessage(RegexpCheck.class, MSG_ILLEGAL_REGEXP, "example"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example3.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:12: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "."),
            "22:9: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example4.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expectedWithoutFilter = {
            "14:15: " + getCheckMessage(MethodNameCheck.class, "name.invalidPattern",
                    "example_Method", "^[a-z][a-zA-Z0-9]*$"),
            "17:15: " + getCheckMessage(MethodNameCheck.class, "name.invalidPattern",
                    "Another_Method", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example5.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expectedWithoutFilter = {
            "14:28: " + getCheckMessage(ConstantNameCheck.class, "name.invalidPattern",
                    "myConstant", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example6.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expectedWithoutFilter = {
            "15:15: " + getCheckMessage(MemberNameCheck.class, "name.invalidPattern",
                    "MyVariable", "^[a-z][a-zA-Z0-9]*$"),
            "17:15: " + getCheckMessage(MethodNameCheck.class, "name.invalidPattern",
                    "MyMethod", "^[a-z][a-zA-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example7.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expectedWithoutFilter = {
            "18:15: " + getCheckMessage(ParameterNumberCheck.class,
                    ParameterNumberCheck.MSG_KEY, 5, 6),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example8.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample9() throws Exception {
        final String[] expectedWithoutFilter = {
            "1: " + getCheckMessage(FileLengthCheck.class,
                    FileLengthCheck.MSG_KEY, 21, 1),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example9.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample10() throws Exception {
        final String[] expectedWithoutFilter = {
            "16:18: " + getCheckMessage(MemberNameCheck.class, "name.invalidPattern",
                    "log", "^[A-Z][a-zA-Z0-9]*$"),
        };
        final String[] expectedWithFilter = {};

        verifyFilterWithInlineConfigParser(getPath("Example10.java"),
                expectedWithoutFilter, expectedWithFilter);
    }
}
