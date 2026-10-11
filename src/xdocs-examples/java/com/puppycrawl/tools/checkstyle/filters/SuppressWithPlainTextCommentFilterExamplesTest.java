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
import com.puppycrawl.tools.checkstyle.checks.OrderedPropertiesCheck;
import com.puppycrawl.tools.checkstyle.checks.UniquePropertiesCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWithPlainTextCommentFilterExamplesTest
        extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/filters/suppresswithplaintextcommentfilter";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expectedWithoutFilter = {
            "11: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyB", 2),
            "15: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyC", 2),
        };

        final String[] expectedWithFilter = {
            "15: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyC", 2),
        };

        verifyFilterWithInlineConfigParser(getPath("Example1.properties"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expectedWithoutFilter = {
            "15: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyB", 2),
            "19: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyC", 2),
        };

        final String[] expectedWithFilter = {
            "19: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyC", 2),
        };

        verifyFilterWithInlineConfigParser(getPath("Example2.properties"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expectedWithoutFilter = {
            "18: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyB", 2),
            "21: " + getCheckMessage(OrderedPropertiesCheck.class, OrderedPropertiesCheck.MSG_KEY,
                    "keyA", "keyB"),
            "24: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyC", 2),
        };

        final String[] expectedWithFilter = {
            "21: " + getCheckMessage(OrderedPropertiesCheck.class, OrderedPropertiesCheck.MSG_KEY,
                    "keyA", "keyB"),
            "24: " + getCheckMessage(UniquePropertiesCheck.class, UniquePropertiesCheck.MSG_KEY,
                    "keyC", 2),
        };

        verifyFilterWithInlineConfigParser(getPath("Example3.properties"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expectedWithoutFilter = {
            "26: Type code is not allowed. Use type raw instead.",
            "33: Type code is not allowed. Use type raw instead.",
        };

        final String[] expectedWithFilter = {
            "33: Type code is not allowed. Use type raw instead.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example4.xml"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expectedWithoutFilter = {
            "26: Type code is not allowed. Use type raw instead.",
            "33: Type code is not allowed. Use type raw instead.",
        };

        final String[] expectedWithFilter = {
            "26: Type code is not allowed. Use type raw instead.",
            "33: Type code is not allowed. Use type raw instead.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example5.xml"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample6() throws Exception {
        final String[] expectedWithoutFilter = {
            "33: Type config is not allowed in this file.",
            "39: Type code is not allowed. Use type raw instead.",
        };

        final String[] expectedWithFilter = {
            "33: Type config is not allowed in this file.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example6.xml"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample7() throws Exception {
        final String[] expectedWithoutFilter = {
            "34: Type config is not allowed in this file.",
            "40: Type code is not allowed. Use type raw instead.",
        };

        final String[] expectedWithFilter = {
            "34: Type config is not allowed in this file.",
        };

        verifyFilterWithInlineConfigParser(getPath("Example7.xml"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testExample8() throws Exception {
        final String[] expectedWithoutFilter = {
            "29: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 60, 66),
        };

        final String[] expectedWithFilter = CommonUtil.EMPTY_STRING_ARRAY;

        verifyFilterWithInlineConfigParser(getPath("Example8.sql"),
                expectedWithoutFilter, expectedWithFilter);
    }

    @Test
    public void testUseCase1() throws Exception {

        final String[] expectedWithoutFilter = {
            "23: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 100, 147),
            "24: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 100, 133),
            "25: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 100, 116),
            "32: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 100, 183),
        };

        final String[] expectedWithFilter = {
            "32: " + getCheckMessage(LineLengthCheck.class, LineLengthCheck.MSG_KEY, 100, 183),
        };

        verifyFilterWithInlineConfigParser(getPath("UseCase1.java"),
                expectedWithoutFilter, expectedWithFilter);
    }

}
