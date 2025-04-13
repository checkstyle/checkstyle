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

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class ClassTypeParameterNameCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/classtypeparametername";
    }

    @Test
    public void testExample1() throws Exception {
        final String pattern = "^[A-Z]$";
        final String[] expected = {
            "14:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "t", pattern),
            "15:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "abc", pattern),
            "16:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "LISTENER", pattern),
            "17:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "RequestT", pattern),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String pattern = "^[A-Z]{2,}$";
        final String[] expected = {
            "15:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "T", pattern),
            "16:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "t", pattern),
            "17:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "abc", pattern),
            "19:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "RequestT", pattern),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String pattern = "(^[A-Z][0-9]?)$|([A-Z][a-zA-Z0-9]*[T]$)";
        final String[] expected = {
            "16:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "t", pattern),
            "17:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "abc", pattern),
            "18:18: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "LISTENER", pattern),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }
}
