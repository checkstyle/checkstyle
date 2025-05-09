///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.checks.design;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.design.HideUtilityClassConstructorCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

class HideUtilityClassConstructorCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/design/hideutilityclassconstructor";
    }

    @Test
    void getRequiredTokens() {
        final HideUtilityClassConstructorCheck checkObj =
            new HideUtilityClassConstructorCheck();
        final int[] expected = {TokenTypes.CLASS_DEF};
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    void utilClass() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorInnerStaticClasses.java"),
                expected);
    }

    @Test
    void utilClassPublicCtor() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorPublic.java"), expected);
    }

    @Test
    void utilClassPrivateCtor() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorPrivate.java"), expected);
    }

    /** Non-static methods - always OK. */
    @Test
    void nonUtilClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorDesignForExtension.java"),
                expected);
    }

    @Test
    void derivedNonUtilClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorNonUtilityClass.java"),
                expected);
    }

    @Test
    void onlyNonStaticFieldNonUtilClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorRegression.java"),
                expected);
    }

    @Test
    void emptyAbstractClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorAbstractSerializable.java"),
                expected);
    }

    @Test
    void emptyAbstractClass2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorAbstract.java"),
                expected);
    }

    @Test
    void emptyClassWithOnlyPrivateFields() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorSerializable.java"),
                expected);
    }

    @Test
    void classWithStaticInnerClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath(
                "InputHideUtilityClassConstructorSerializableInnerStatic.java"),
                expected);
    }

    @Test
    void protectedCtor() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructor.java"), expected);
    }

    @Test
    void getAcceptableTokens() {
        final HideUtilityClassConstructorCheck obj = new HideUtilityClassConstructorCheck();
        final int[] expected = {TokenTypes.CLASS_DEF};
        assertWithMessage("Default acceptable tokens are invalid")
            .that(obj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    void ignoreAnnotatedBy() throws Exception {
        final String[] expected = {
            "30:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructorIgnoreAnnotationBy.java"),
                expected
        );
    }

    @Test
    void ignoreAnnotatedByFullQualifier() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputHideUtilityClassConstructor"
                        + "IgnoreAnnotationByFullyQualifiedName.java"),
                expected
        );
    }
}
