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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck.MSG_ILLEGAL_ABSTRACT_CLASS_NAME;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractClassNameCheck.MSG_NO_ABSTRACT_CLASS_MODIFIER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class AbstractClassNameCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/abstractclassname";
    }

    @Test
    public void testIllegalAbstractClassName() throws Exception {

        final String pattern = "^Abstract.+$";

        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassName",
                pattern),
            "17:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractClassName",
                pattern),
            "22:5: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClass",
                pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbstractClassName.java"), expected);
    }

    @Test
    public void testCustomFormat() throws Exception {

        final String[] expected = {
            "13:1: "
                + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassNameCustom",
                "^NonAbstract.+$"),
            "20:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "AbstractClassOtherCustom",
                "^NonAbstract.+$"),
            "32:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "AbstractClassName2Custom",
                "^NonAbstract.+$"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbstractClassNameCustom.java"), expected);
    }

    @Test
    public void testIllegalClassType() throws Exception {

        final String[] expected = {
            "18:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClassType"),
            "27:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClassTypes"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbstractClassNameType.java"), expected);
    }

    @Test
    public void testAllVariants() throws Exception {

        final String pattern = "^Abstract.+$";

        final String[] expected = {
            "13:1: "
                + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "InputAbstractClassNameVariants",
                pattern),
            "17:1: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractClassNameVa",
                pattern),
            "22:5: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClassVa",
                pattern),
            "30:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClassVa"),
            "35:5: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractInnerClassVa"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbstractClassNameVariants.java"), expected);
    }

    @Test
    public void testFalsePositive() throws Exception {
        final String pattern = "^Abstract.+$";
        final String[] expected = {
            "13:1: "
                + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME,
                        "InputAbstractClassNameFormerFalsePositive",
                pattern),
            "21:5: " + getCheckMessage(MSG_ILLEGAL_ABSTRACT_CLASS_NAME, "NonAbstractInnerClassFP",
                pattern),
            "29:1: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractClassNameFP"),
            "34:5: " + getCheckMessage(MSG_NO_ABSTRACT_CLASS_MODIFIER, "AbstractInnerClassFP"),
        };

        verifyWithInlineConfigParser(
                getPath("InputAbstractClassNameFormerFalsePositive.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final AbstractClassNameCheck classNameCheckObj = new AbstractClassNameCheck();
        final int[] actual = classNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
        };
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final AbstractClassNameCheck classNameCheckObj = new AbstractClassNameCheck();
        final int[] actual = classNameCheckObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
        };
        assertWithMessage("Invalid required tokens")
            .that(actual)
            .isEqualTo(expected);
    }

}
