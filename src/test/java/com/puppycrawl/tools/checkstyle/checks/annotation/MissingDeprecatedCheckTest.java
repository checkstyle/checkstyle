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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck.MSG_KEY_ANNOTATION_MISSING_DEPRECATED;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck.MSG_KEY_JAVADOC_DUPLICATE_TAG;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingDeprecatedCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/missingdeprecated";
    }

    @Test
    public void getDefaultJavadocTokens() {
        final MissingDeprecatedCheck missingDeprecatedCheck =
                new MissingDeprecatedCheck();
        final int[] expected = {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
        };

        assertWithMessage("Default javadoc tokens are invalid")
                .that(missingDeprecatedCheck.getDefaultJavadocTokens())
                .isEqualTo(expected);
    }

    @Test
    public void getRequiredJavadocTokens() {
        final MissingDeprecatedCheck checkObj = new MissingDeprecatedCheck();
        final int[] expected = {
            JavadocCommentsTokenTypes.JAVADOC_CONTENT,
        };
        assertWithMessage("Default required javadoc tokens are invalid")
                .that(checkObj.getRequiredJavadocTokens())
                .isEqualTo(expected);
    }

    /**
     * Tests that members that are only deprecated via javadoc are flagged.
     */
    @Test
    public void badDeprecatedAnnotation() throws Exception {

        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "19: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "26: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "33: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "38: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "45: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "50: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "58: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "63: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingDeprecatedBadDeprecated.java"), expected);
    }

    /**
     * Tests that members that are only deprecated via the annotation are flagged.
     */
    @Test
    public void badDeprecatedJavadoc() throws Exception {

        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "36: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "45: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "55: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "62: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingDeprecatedBadJavadoc.java"), expected);
    }

    /**
     * Tests various special deprecation conditions such as duplicate or empty tags.
     */
    @Test
    public void specialCaseDeprecated() throws Exception {

        final String[] expected = {
            "12: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "19: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "21: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "26: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "40: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "49: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "58: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "99: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "106: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "113: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingDeprecatedSpecialCase.java"), expected);
    }

    /**
     * Tests that good forms of deprecation are not flagged.
     */
    @Test
    public void goodDeprecated() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingDeprecatedGood.java"), expected);
    }

    @Test
    public void twoInJavadocWithoutAnnotation() throws Exception {

        final String[] expected = {
            "15: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "19: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingDeprecatedClass.java"), expected);
    }

    @Test
    public void emptyJavadocLine() throws Exception {

        final String[] expected = {
            "18: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingDeprecatedMethod.java"), expected);
    }

    @Test
    public void packageInfo() throws Exception {

        final String[] expected = {
            "9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verifyWithInlineConfigParser(
                getPath("package-info.java"), expected);
    }

    @Test
    public void depPackageInfoBelowComment() throws Exception {

        final String[] expected = {
            "14: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingDeprecatedAbovePackage.java"), expected);
    }

    @Test
    public void packageInfoBelowComment() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingDeprecatedSingleComment.java"), expected);
    }
}
