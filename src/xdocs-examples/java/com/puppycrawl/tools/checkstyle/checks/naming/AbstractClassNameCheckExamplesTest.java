///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck.MSG_NO_ABSTRACT_CLASS_MODIFIER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class AbstractClassNameCheckExamplesTest extends AbstractModuleTestSupport {
    private static final String DEFAULT_PATTERN = "^Abstract.+$";

    @Override
    protected String getResourceLocation() {
        return "xdocs-examples";
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abstractclassname";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "12:3: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "Second", DEFAULT_PATTERN),
            "13:3: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractThird",
                DEFAULT_PATTERN),
            "15:3: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "GeneratorFifth",
                    DEFAULT_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "13:3: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "Second", DEFAULT_PATTERN),
            "16:3: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "GeneratorFifth",
                    DEFAULT_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractThird",
                DEFAULT_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("Example3.java"), expected);
    }

    @Test
    public void testExample4() throws Exception {
        final String pattern = "^Generator.+$";

        final String[] expected = {
            "13:3: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "AbstractFirst", pattern),
            "14:3: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "Second", pattern),
            "18:3: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "GeneratorSixth",
                pattern),
        };

        verifyWithInlineConfigParser(getPath("Example4.java"), expected);
    }
}
