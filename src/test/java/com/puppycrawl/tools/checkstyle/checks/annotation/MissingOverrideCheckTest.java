////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2018 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck.MSG_KEY_TAG_NOT_VALID_ON;
import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "false");

        final String[] expected = {
            "8: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "30: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "41: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "50: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        verify(checkConfig, getPath("InputMissingOverrideBadOverrideFromObject.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag even in Java 5 compatibility mode.
     */
    @Test
    public void testBadOverrideFromObjectJ5Compatible() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = {
            "8: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "30: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "41: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "50: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        verify(checkConfig, getPath("InputMissingOverrideBadOverrideFromObject.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testBadOverrideFromOther() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "10: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "26: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "34: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "40: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "47: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "53: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "63: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        verify(checkConfig, getPath("InputMissingOverrideBadOverrideFromOther.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will NOT be flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testBadOverrideFromOtherJ5Compatible() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingOverrideBadOverrideFromOther.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are correctly flagged
     * for only including the inheritDoc tag.
     */
    @Test
    public void testBadAnnotationOverride() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "10: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "16: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "29: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "35: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        verify(checkConfig, getPath("InputMissingOverrideBadAnnotation.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are NOT flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testBadAnnotationOverrideJ5Compatible() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingOverrideBadAnnotation.java"), expected);
    }

    /**
     * Tests that inheritDoc misuse is properly flagged or missing Javadocs do not cause a problem.
     */
    @Test
    public void testNotOverride() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "8: " + getCheckMessage(MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
            "15: " + getCheckMessage(MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
        };

        verify(checkConfig, getPath("InputMissingOverrideNotOverride.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testGoodOverrideFromObject() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "false");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingOverrideGoodOverrideFromObject.java"), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag even in Java 5 compatibility mode.
     */
    @Test
    public void testGoodOverrideFromObjectJ5Compatible() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingOverrideGoodOverrideFromObject.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testGoodOverrideFromOther() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingOverrideGoodOverrideFromOther.java"), expected);
    }

    /**
     * This tests classes that are extending things explicitly will NOT be flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testGoodOverrideFromOtherJ5Compatible() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingOverrideGoodOverrideFromOther.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are correctly flagged
     * for only including the inheritDoc tag.
     */
    @Test
    public void testGoodAnnotationOverride() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingOverrideGoodOverride.java"), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are NOT flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testGoodAnnotationOverrideJ5Compatible() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingOverrideGoodOverride.java"), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] expectedTokens = {TokenTypes.METHOD_DEF };
        final MissingOverrideCheck check = new MissingOverrideCheck();
        final int[] actual = check.getAcceptableTokens();
        assertEquals("Invalid acceptable token size", 1, actual.length);
        Assert.assertArrayEquals("Default required tokens are invalid", expectedTokens, actual);
    }

}
