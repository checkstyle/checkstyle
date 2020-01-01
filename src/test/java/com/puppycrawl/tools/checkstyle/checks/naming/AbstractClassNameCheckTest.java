////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.naming;

import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck.MSG_NO_ABSTRACT_CLASS_MODIFIER;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AbstractClassNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abstractclassname";
    }

    @Test
    public void testIllegalAbstractClassName() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("ignoreName", "false");
        checkConfig.addAttribute("ignoreModifier", "true");

        final String pattern = "^Abstract.+$";

        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassName",
                pattern),
            "6:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractClassName",
                pattern),
            "10:5: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClass",
                pattern),
        };

        verify(checkConfig, getPath("InputAbstractClassName.java"), expected);
    }

    @Test
    public void testCustomFormat() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("ignoreName", "false");
        checkConfig.addAttribute("ignoreModifier", "true");
        checkConfig.addAttribute("format", "^NonAbstract.+$");

        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassName",
                "^NonAbstract.+$"),
            "9:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "AbstractClassOther",
                "^NonAbstract.+$"),
            "21:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "AbstractClassName2",
                "^NonAbstract.+$"),
        };

        verify(checkConfig, getPath("InputAbstractClassName.java"), expected);
    }

    @Test
    public void testIllegalClassType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("ignoreName", "true");
        checkConfig.addAttribute("ignoreModifier", "false");

        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClass"),
            "22:5: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractInnerClass"),
        };

        verify(checkConfig, getPath("InputAbstractClassName.java"), expected);
    }

    @Test
    public void testAllVariants() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AbstractClassNameCheck.class);
        checkConfig.addAttribute("ignoreName", "false");
        checkConfig.addAttribute("ignoreModifier", "false");

        final String pattern = "^Abstract.+$";

        final String[] expected = {
            "3:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassName",
                pattern),
            "6:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractClassName",
                pattern),
            "10:5: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClass",
                pattern),
            "18:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClass"),
            "22:5: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractInnerClass"),
        };

        verify(checkConfig, getPath("InputAbstractClassName.java"), expected);
    }

    @Test
    public void testFalsePositive() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AbstractClassNameCheck.class);

        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClass"),
        };

        verify(checkConfig, getPath("InputAbstractClassNameFormerFalsePositive.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AbstractClassNameCheck classNameCheckObj = new AbstractClassNameCheck();
        final int[] actual = classNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
        };
        assertArrayEquals(expected, actual, "Invalid acceptable tokens");
    }

    @Test
    public void testGetRequiredTokens() {
        final AbstractClassNameCheck classNameCheckObj = new AbstractClassNameCheck();
        final int[] actual = classNameCheckObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
        };
        assertArrayEquals(expected, actual, "Invalid required tokens");
    }

}
