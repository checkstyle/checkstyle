////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
        checkConfig.addProperty("ignoreName", "false");
        checkConfig.addProperty("ignoreModifier", "true");

        final String pattern = "^Abstract.+$";

        final String[] expected = {
            "6:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassName",
                pattern),
            "9:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractClassName",
                pattern),
            "13:5: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClass",
                pattern),
        };

        verify(checkConfig, getPath("InputAbstractClassName.java"), expected);
    }

    @Test
    public void testCustomFormat() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(AbstractClassNameCheck.class);
        checkConfig.addProperty("ignoreName", "false");
        checkConfig.addProperty("ignoreModifier", "true");
        checkConfig.addProperty("format", "^NonAbstract.+$");

        final String[] expected = {
            "6:1: "
                + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassNameCustom",
                "^NonAbstract.+$"),
            "12:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "AbstractClassOtherCustom",
                "^NonAbstract.+$"),
            "24:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "AbstractClassName2Custom",
                "^NonAbstract.+$"),
        };

        verify(checkConfig, getPath("InputAbstractClassNameCustom.java"), expected);
    }

    @Test
    public void testIllegalClassType() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AbstractClassNameCheck.class);
        checkConfig.addProperty("ignoreName", "true");
        checkConfig.addProperty("ignoreModifier", "false");

        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClassType"),
            "21:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClassTypes"),
        };

        verify(checkConfig, getPath("InputAbstractClassNameType.java"), expected);
    }

    @Test
    public void testAllVariants() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AbstractClassNameCheck.class);
        checkConfig.addProperty("ignoreName", "false");
        checkConfig.addProperty("ignoreModifier", "false");

        final String pattern = "^Abstract.+$";

        final String[] expected = {
            "6:1: "
                + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassNameVariants",
                pattern),
            "9:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractClassNameVa",
                pattern),
            "13:5: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClassVa",
                pattern),
            "21:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClassVa"),
            "25:5: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractInnerClassVa"),
        };

        verify(checkConfig, getPath("InputAbstractClassNameVariants.java"), expected);
    }

    @Test
    public void testFalsePositive() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(AbstractClassNameCheck.class);
        final String pattern = "^Abstract.+$";
        final String[] expected = {
            "6:1: "
                + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME,
                        "InputAbstractClassNameFormerFalsePositive",
                pattern),
            "13:5: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClassFP",
                pattern),
            "21:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClassNameFP"),
            "25:5: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractInnerClassFP"),
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
