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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.InappropriateJavadocBlockTagsOnFieldCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class InappropriateJavadocBlockTagsOnFieldCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/"
                + "inappropriatejavadocblocktagsonfield";
    }

    @Test
    public void testGetDefaultJavadocTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        final int[] expected = {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
        };
        assertWithMessage("Default javadoc tokens are invalid")
                .that(checkObj.getDefaultJavadocTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableJavadocTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        final int[] expected = {
            JavadocCommentsTokenTypes.PARAM_BLOCK_TAG,
            JavadocCommentsTokenTypes.RETURN_BLOCK_TAG,
            JavadocCommentsTokenTypes.THROWS_BLOCK_TAG,
            JavadocCommentsTokenTypes.EXCEPTION_BLOCK_TAG,
        };
        assertWithMessage("Acceptable javadoc tokens are invalid")
                .that(checkObj.getAcceptableJavadocTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredJavadocTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        assertWithMessage("Required javadoc tokens are invalid")
                .that(checkObj.getRequiredJavadocTokens())
                .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetDefaultTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertWithMessage("Default tokens are invalid")
                .that(checkObj.getDefaultTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        final int[] expected = {
            TokenTypes.VARIABLE_DEF,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(checkObj.getAcceptableTokens())
                .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        assertWithMessage("Required tokens are invalid")
                .that(checkObj.getRequiredTokens())
                .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "27:8: " + getCheckMessage(MSG_KEY, "return", "invalidReturn"),
            "35:8: " + getCheckMessage(MSG_KEY, "param", "invalidParam"),
            "43:8: " + getCheckMessage(MSG_KEY, "throws", "invalidThrows"),
            "51:8: " + getCheckMessage(MSG_KEY, "exception", "invalidException"),
            "59:8: " + getCheckMessage(MSG_KEY, "return", "multipleInvalid"),
            "60:8: " + getCheckMessage(MSG_KEY, "param", "multipleInvalid"),
            "61:8: " + getCheckMessage(MSG_KEY, "throws", "multipleInvalid"),
            "62:8: " + getCheckMessage(MSG_KEY, "exception", "multipleInvalid"),
            "74:8: " + getCheckMessage(MSG_KEY, "return", "mixedTags"),
            "76:8: " + getCheckMessage(MSG_KEY, "throws", "mixedTags"),
            "108:16: " + getCheckMessage(MSG_KEY, "return", "localClassField"),
            "152:12: " + getCheckMessage(MSG_KEY, "return", "INTERFACE_CONST"),
            "153:12: " + getCheckMessage(MSG_KEY, "param", "INTERFACE_CONST"),
            "173:12: " + getCheckMessage(MSG_KEY, "throws", "enumField"),
            "190:12: " + getCheckMessage(MSG_KEY, "return", "RECORD_STATIC_FIELD"),
            "207:12: " + getCheckMessage(MSG_KEY, "exception", "ANNOTATION_FIELD"),
            "217:12: " + getCheckMessage(MSG_KEY, "param", "nestedField"),
            "227:12: " + getCheckMessage(MSG_KEY, "return", "innerField"),
            "238:16: " + getCheckMessage(MSG_KEY, "return", "anonField"),
        };

        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnFieldDefault.java"),
                expected);
    }

    @Test
    public void testCustomJavadocTokens() throws Exception {
        final String[] expected = {
            "18:8: " + getCheckMessage(MSG_KEY, "return", "fieldReturn"),
            "26:8: " + getCheckMessage(MSG_KEY, "param", "fieldParam"),
        };

        verifyWithInlineConfigParser(
                getPath("InputInappropriateJavadocBlockTagsOnFieldCustom.java"),
                expected);
    }

    @Test
    public void testSetJavadocTokens() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        checkObj.setJavadocTokens("PARAM_BLOCK_TAG");
        assertWithMessage("Check should not be null")
                .that(checkObj)
                .isNotNull();
    }

    @Test
    public void testSetViolateExecutionOnNonTightHtml() {
        final InappropriateJavadocBlockTagsOnFieldCheck checkObj =
                new InappropriateJavadocBlockTagsOnFieldCheck();
        checkObj.setViolateExecutionOnNonTightHtml(true);
        assertWithMessage("Check should not be null")
                .that(checkObj)
                .isNotNull();
    }

}
