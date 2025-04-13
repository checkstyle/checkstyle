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
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ConstantNameCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/naming/constantname";
    }

    @Test
    public void testGetRequiredTokens() {
        final ConstantNameCheck checkObj = new ConstantNameCheck();
        final int[] expected = {TokenTypes.VARIABLE_DEF};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testIllegalRegexp()
            throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(ConstantNameCheck.class);
        checkConfig.addProperty("format", "\\");
        try {
            createChecker(checkConfig);
            assertWithMessage("CheckstyleException is expected").fail();
        }
        catch (CheckstyleException ex) {
            assertWithMessage("Invalid exception message")
                .that(ex.getMessage())
                .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks."
                    + "naming.ConstantNameCheck - "
                    + "illegal value '\\' for property 'format'");
        }
    }

    @Test
    public void testDefault()
            throws Exception {

        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] expected = {
            "31:29: " + getCheckMessage(MSG_INVALID_PATTERN, "badConstant", pattern),
            "148:30: " + getCheckMessage(MSG_INVALID_PATTERN, "BAD__NAME", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputConstantNameSimple1.java"), expected);
    }

    @Test
    public void testAccessControlTuning()
            throws Exception {

        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] expected = {
            "148:30: " + getCheckMessage(MSG_INVALID_PATTERN, "BAD__NAME", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputConstantNameSimple2.java"), expected);
    }

    @Test
    public void testInterfaceAndAnnotation()
            throws Exception {

        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] expected = {
            "31:16: " + getCheckMessage(MSG_INVALID_PATTERN, "data", pattern),
            "71:16: " + getCheckMessage(MSG_INVALID_PATTERN, "data", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputConstantNameInner.java"), expected);
    }

    @Test
    public void testDefault1()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputConstantName.java"), expected);
    }

    @Test
    public void testIntoInterface() throws Exception {

        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";

        final String[] expected = {
            "56:16: " + getCheckMessage(MSG_INVALID_PATTERN, "mPublic", pattern),
            "57:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mProtected", pattern),
            "58:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPackage", pattern),
            "59:9: " + getCheckMessage(MSG_INVALID_PATTERN, "mPrivate", pattern),
            "61:16: " + getCheckMessage(MSG_INVALID_PATTERN, "_public", pattern),
            "62:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_protected", pattern),
            "63:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_package", pattern),
            "64:9: " + getCheckMessage(MSG_INVALID_PATTERN, "_private", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputConstantNameMemberExtended.java"), expected);
    }

    @Test
    public void testIntoInterfaceExcludePublic() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputConstantNameInterfaceIgnorePublic.java"), expected);
    }

    @Test
    public void testStaticMethodInInterface()
            throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputConstantNameStaticModifierInInterface.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ConstantNameCheck constantNameCheckObj = new ConstantNameCheck();
        final int[] actual = constantNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertWithMessage("Default acceptable should not be null")
            .that(actual)
            .isNotNull();
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

}
