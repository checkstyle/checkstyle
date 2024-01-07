///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class StaticVariableNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    public static final String STATIC_VAR_NAME_PATTERN_1 = "^[a-z][a-zA-Z0-9]*$";
    public static final String STATIC_VAR_NAME_PATTERN_2 = "^[a-z](_?[a-zA-Z0-9]+)*$";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/staticvariablename";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "15:21: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "GoodStatic1", STATIC_VAR_NAME_PATTERN_1),
            "17:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "GoodStatic2", STATIC_VAR_NAME_PATTERN_1),
            "19:22: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "BadStatic", STATIC_VAR_NAME_PATTERN_1),
            "21:21: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "good_static", STATIC_VAR_NAME_PATTERN_1),
            "23:21: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "Bad_Static", STATIC_VAR_NAME_PATTERN_1),
            "25:22: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "Good_Static1", STATIC_VAR_NAME_PATTERN_1),
            "27:14: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "Good_Static2", STATIC_VAR_NAME_PATTERN_1),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "20:22: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "BadStatic", STATIC_VAR_NAME_PATTERN_1),
            "24:22: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "Good_Static1", STATIC_VAR_NAME_PATTERN_1),
            "26:14: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "Good_Static2", STATIC_VAR_NAME_PATTERN_1),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "19:21: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "GoodStatic1", STATIC_VAR_NAME_PATTERN_2),
            "21:24: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "GoodStatic2", STATIC_VAR_NAME_PATTERN_2),
            "25:21: " + getCheckMessage(MSG_INVALID_PATTERN,
                    "Bad_Static", STATIC_VAR_NAME_PATTERN_2),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }
}
