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
        return "xdocs-test";
    }

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abstractclassname";
    }

    @Test
    public void testXdocExample1() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "SecondClass",
                DEFAULT_PATTERN),
            "14:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractThirdClass",
                DEFAULT_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("XDocExample1.java"), expected);
    }

    @Test
    public void testXdocExample2() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "SixthClass",
                DEFAULT_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("XDocExample2.java"), expected);
    }

    @Test
    public void testXdocExample3() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractEleventhClass",
                DEFAULT_PATTERN),
        };

        verifyWithInlineConfigParser(getPath("XDocExample3.java"), expected);
    }

    @Test
    public void testXdocExample4() throws Exception {
        final String format = "^Generator.+$";

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "FourteenthClass",
                format),
            "14:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "GeneratorFifteenthClass",
                format),
        };

        verifyWithInlineConfigParser(getPath("XDocExample4.java"), expected);
    }
}