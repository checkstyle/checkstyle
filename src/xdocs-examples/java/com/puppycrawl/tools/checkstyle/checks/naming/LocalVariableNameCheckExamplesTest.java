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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class LocalVariableNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/localvariablename";
    }

    @Test
    public void testExample1() throws Exception {
        final String pattern = "^([a-z][a-zA-Z0-9]*|_)$";
        final String[] expected = {
            "20:14: " + getCheckMessage(MSG_INVALID_PATTERN, "VAR", pattern),
            "23:14: " + getCheckMessage(MSG_INVALID_PATTERN, "var_1", pattern),
            "28:14: " + getCheckMessage(MSG_INVALID_PATTERN, "V", pattern),
            "30:11: " + getCheckMessage(MSG_INVALID_PATTERN, "I", pattern),
            "36:17: " + getCheckMessage(MSG_INVALID_PATTERN, "O", pattern),
            "38:14: " + getCheckMessage(MSG_INVALID_PATTERN, "A", pattern),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String pattern = "^[a-z](_?[a-zA-Z0-9]+)*$";
        final String[] expected = {
            "22:14: " + getCheckMessage(MSG_INVALID_PATTERN, "VAR", pattern),
            "30:14: " + getCheckMessage(MSG_INVALID_PATTERN, "V", pattern),
            "32:11: " + getCheckMessage(MSG_INVALID_PATTERN, "I", pattern),
            "38:17: " + getCheckMessage(MSG_INVALID_PATTERN, "O", pattern),
            "40:14: " + getCheckMessage(MSG_INVALID_PATTERN, "A", pattern),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String pattern = "^[a-z](_?[a-zA-Z0-9]+)*$";
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_INVALID_PATTERN, "K", pattern),
            "23:17: " + getCheckMessage(MSG_INVALID_PATTERN, "O", pattern),
        };

        verifyWithInlineConfigParser(getPath("UseCase1.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String pattern = "^[a-z][_a-zA-Z0-9]+$";
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_INVALID_PATTERN, "g", pattern),
            "23:14: " + getCheckMessage(MSG_INVALID_PATTERN, "VAR", pattern),
            "29:11: " + getCheckMessage(MSG_INVALID_PATTERN, "a", pattern),
            "33:11: " + getCheckMessage(MSG_INVALID_PATTERN, "I", pattern),
            "37:14: " + getCheckMessage(MSG_INVALID_PATTERN, "a", pattern),
            "41:14: " + getCheckMessage(MSG_INVALID_PATTERN, "A", pattern),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String pattern = "^[a-z][_a-zA-Z0-9]{2,}$";
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_INVALID_PATTERN, "i", pattern),
            "19:11: " + getCheckMessage(MSG_INVALID_PATTERN, "j", pattern),
        };

        verifyWithInlineConfigParser(getPath("UseCase2.java"), expected);
    }

}
