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
import com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck;
import com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck;

public class SuppressWithNearbyCommentFilterExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithnearbycommentfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "13:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] expectedWithFilter = {
            "17:30: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "array", pattern),
        };
        final String[] expectedWithoutFilter = {
            "17:30: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "array", pattern),
            "19:27: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "lowerCaseConstant", pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "27:5: " + getCheckMessage(IllegalCatchCheck.class, IllegalCatchCheck.MSG_KEY,
                    "RuntimeException"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example3.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "15:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example4.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expectedWithFilter = {
            "15:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };
        final String[] expectedWithoutFilter = {
            "15:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example5.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expectedWithFilter = {};

        final String[] expectedWithoutFilter = {
            "15:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example7.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expectedWithFilter = {};

        final String[] expectedWithoutFilter = {
            "15:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example8.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expectedWithFilter = {
            "19:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };
        final String[] expectedWithoutFilter = {
            "19:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
        };

        verifyFilterWithInlineConfigParser(getPath("Example6.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] expectedWithFilter = {
            "26:20: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "lowerCaseConstant5", pattern),
        };
        final String[] expectedWithoutFilter = {
            "19:20: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "lowerCaseConstant1", pattern),
            "21:20: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "lowerCaseConstant2", pattern),
            "23:20: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "lowerCaseConstant3", pattern),
            "25:20: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "lowerCaseConstant4", pattern),
            "26:20: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "lowerCaseConstant5", pattern),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "17:15: " + getCheckMessage(MemberNameCheck.class, MSG_INVALID_PATTERN,
                    "D2", "^[a-z][a-zA-Z0-9]*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase2.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expectedWithFilters = {

        };
        final String[] expectedWithoutFilters = {
            "19:27: " + getCheckMessage(NoWhitespaceAfterCheck.class,
                    NoWhitespaceAfterCheck.MSG_KEY, "int"),
            "19:30: " + getCheckMessage(ConstantNameCheck.class, MSG_INVALID_PATTERN,
                    "array", "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase3.java"), expectedWithoutFilters,
                expectedWithFilters);
    }

    @Test
    public void testUseCase4() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {

        };

        verifyFilterWithInlineConfigParser(getPath("UseCase4.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

    @Test
    public void testUseCase5() throws Exception {
        final String[] expectedWithFilter = {

        };
        final String[] expectedWithoutFilter = {
            "21:1: " + getCheckMessage(ClassDataAbstractionCouplingCheck.class,
                    ClassDataAbstractionCouplingCheck.MSG_KEY, 2, 1, "[Example1, Example2]"),
            "24:23: " + getCheckMessage(MagicNumberCheck.class, MagicNumberCheck.MSG_KEY,
                    "10022"),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase5.java"), expectedWithoutFilter,
                expectedWithFilter);
    }

}
