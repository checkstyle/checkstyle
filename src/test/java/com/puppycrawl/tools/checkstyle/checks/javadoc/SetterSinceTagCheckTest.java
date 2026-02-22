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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.SetterSinceTagCheck.MSG_MISSING_SINCE_TAG;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SetterSinceTagCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/settersincetag";
    }

    /**
     * Verifies acceptable tokens match required tokens.
     */
    @Test
    public void testGetAcceptableTokens() {
        final SetterSinceTagCheck checkObj = new SetterSinceTagCheck();
        final int[] actual = checkObj.getAcceptableTokens();
        final int[] expected = {TokenTypes.METHOD_DEF};
        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    /**
     * Verifies that no violation is raised when the setter has a proper since tag.
     */
    @Test
    public void testSetterWithSinceTag() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputSetterSinceTagCheckValid.java"), expected);
    }

    /**
     * Verifies that a violation is raised when the setter is missing a since tag.
     */
    @Test
    public void testSetterMissingSinceTag() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_MISSING_SINCE_TAG, "setEnabled"),
            "26:5: " + getCheckMessage(MSG_MISSING_SINCE_TAG, "setEnabled2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputSetterSinceTagCheckMissingCheck.java"), expected);
    }

    /**
     * Verifies that abstract classes are skipped (no violations expected).
     */
    @Test
    public void testAbstractClassIsSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputSetterSinceTagCheckAbstractClassCheck.java"), expected);
    }

    /**
     * Verifies that non-Check/Filter classes are skipped (no violations expected).
     */
    @Test
    public void testNonCheckClassIsSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputSetterSinceTagCheckNonCheckClass.java"), expected);
    }

    /**
     * Verifies that non-setter public methods (no "set" prefix) are skipped.
     */
    @Test
    public void testNonSetterMethodIsSkipped() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputSetterSinceTagCheckNonSetterFilter.java"), expected);
    }

    /**
     * Verifies that a violation is raised when the setter
     * is missing a since tag in the nested class.
     */
    @Test
    public void testSetterMissingSinceTagInNestedClass() throws Exception {
        final String[] expected = {
            "32:9: " + getCheckMessage(MSG_MISSING_SINCE_TAG, "setId2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputSetterSinceTagCheckNestedClassCheck.java"), expected);
    }

    /**
     * Verifies that a no violation is raised when the setter
     * is missing a since tag in an interface.
     */
    @Test
    public void testSetterMissingSinceTagInInterface() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputSetterSinceTagCheckInterfaceCheck.java"), expected);
    }

    /**
     * Verifies that violation is raised when the setter
     * is missing a since tag and javadoc is absent.
     */
    @Test
    public void testSetterMissingSinceTagAndNoJavaDoc() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_MISSING_SINCE_TAG, "setEnabled"),
        };
        verifyWithInlineConfigParser(
                getPath("InputSetterSinceTagCheckNoJavadocCheck.java"), expected);
    }
}
