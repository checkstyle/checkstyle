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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck.MSG_KEY_TAG_NOT_VALID_ON;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingOverrideCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/missingoverride";
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testBadOverrideFromObject() throws Exception {

        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "36:9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "46:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "51:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "74:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideBadOverrideFromObject.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag even in Java 5 compatibility mode.
     */
    @Test
    public void testBadOverrideFromObjectJ5Compatible() throws Exception {

        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "36:9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "46:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "55:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideBadOverrideFromObjectJava5.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testBadOverrideFromOther() throws Exception {
        final String[] expected = {
            "17:3: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "33:3: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "41:3: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "46:3: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "53:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "58:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "68:3: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "73:3: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "79:3: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "85:3: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideBadOverrideFromOther.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will NOT be flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testBadOverrideFromOtherJ5Compatible() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideBadOverrideFromOtherJava5.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are correctly flagged
     * for only including the inheritDoc tag.
     */
    @Test
    public void testBadAnnotationOverride() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "23:9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "36:7: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "42:11: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideBadAnnotation.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are NOT flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testBadAnnotationOverrideJ5Compatible() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideBadAnnotationJava5.java"), expected);
    }

    /**
     * Tests that inheritDoc misuse is properly flagged or missing Javadocs do not cause a problem.
     */
    @Test
    public void testNotOverride() throws Exception {
        final String[] expected = {
            "15:3: " + getCheckMessage(MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
            "20:3: " + getCheckMessage(MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
        };

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideNotOverride.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testGoodOverrideFromObject() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideGoodOverrideFromObject.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag even in Java 5 compatibility mode.
     */
    @Test
    public void testGoodOverrideFromObjectJ5Compatible() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideGoodOverrideFromObjectJava5.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testGoodOverrideFromOther() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideGoodOverrideFromOther.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will NOT be flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testGoodOverrideFromOtherJ5Compatible() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideGoodOverrideFromOtherJava5.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are correctly flagged
     * for only including the inheritDoc tag.
     */
    @Test
    public void testGoodAnnotationOverride() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideGoodOverride.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are NOT flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testGoodAnnotationOverrideJ5Compatible() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputMissingOverrideGoodOverrideJava5.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] expectedTokens = {TokenTypes.METHOD_DEF };
        final MissingOverrideCheck check = new MissingOverrideCheck();
        final int[] actual = check.getAcceptableTokens();
        assertWithMessage("Invalid acceptable token size")
            .that(actual.length)
            .isEqualTo(1);
        assertWithMessage("Default required tokens are invalid")
            .that(actual)
            .isEqualTo(expectedTokens);
    }

}
