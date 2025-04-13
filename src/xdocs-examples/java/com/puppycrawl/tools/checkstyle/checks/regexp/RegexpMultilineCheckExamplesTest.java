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

package com.puppycrawl.tools.checkstyle.checks.regexp;

import static com.puppycrawl.tools.checkstyle.checks.regexp.RegexpCheck.MSG_ILLEGAL_REGEXP;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class RegexpMultilineCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "16: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "20: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "24: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "29: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "31: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "33: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "36: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "38: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
            "40: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.(out)|(err)\\.print(ln)?\\("),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "System\\.out.*?print\\("),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String[] expected = {
            "40: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "Test #[0-9]+:[A-Za-z ]+"),
            "42: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "Test #[0-9]+:[A-Za-z ]+"),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testExample5() throws Exception {
        final String[] expected = {
            "30: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "Test #[0-9]+:[A-Za-z ]+"),
            "39: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "Test #[0-9]+:[A-Za-z ]+"),
            "41: " + getCheckMessage(MSG_ILLEGAL_REGEXP, "Test #[0-9]+:[A-Za-z ]+"),
        };

        verifyWithInlineConfigParser(getPath("Example5.java"), expected);
    }
}
