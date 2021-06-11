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

package com.puppycrawl.tools.checkstyle.checks.annotation;

import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck.MSG_KEY_ANNOTATION_MISSING_OVERRIDE;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingOverrideCheck.MSG_KEY_TAG_NOT_VALID_ON;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.bdd.BddParser;
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
        final String filename = "InputMissingOverrideBadOverrideFromObject.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);

        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "35:9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "44:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "53:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag even in Java 5 compatibility mode.
     */
    @Test
    public void testBadOverrideFromObjectJ5Compatible() throws Exception {
        final String filename = "InputMissingOverrideBadOverrideFromObjectJava5.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "35:9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "44:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "53:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests classes that are extending things explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testBadOverrideFromOther() throws Exception {
        final String filename = "InputMissingOverrideBadOverrideFromOther.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "33:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "41:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "46:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "53:9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "58:9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "68:5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests classes that are extending things explicitly will NOT be flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testBadOverrideFromOtherJ5Compatible() throws Exception {
        final String filename = "InputMissingOverrideBadOverrideFromOtherJava5.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are correctly flagged
     * for only including the inheritDoc tag.
     */
    @Test
    public void testBadAnnotationOverride() throws Exception {
        final String filename = "InputMissingOverrideBadAnnotation.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "23:17: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "36:13: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
            "42:21: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_OVERRIDE),
        };

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are NOT flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testBadAnnotationOverrideJ5Compatible() throws Exception {
        final String filename = "InputMissingOverrideBadAnnotationJava5.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * Tests that inheritDoc misuse is properly flagged or missing Javadocs do not cause a problem.
     */
    @Test
    public void testNotOverride() throws Exception {
        final String filename = "InputMissingOverrideNotOverride.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
            "20:5: " + getCheckMessage(MSG_KEY_TAG_NOT_VALID_ON, "{@inheritDoc}"),
        };

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testGoodOverrideFromObject() throws Exception {
        final String filename = "InputMissingOverrideGoodOverrideFromObject.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests that classes not extending anything explicitly will be correctly
     * flagged for only including the inheritDoc tag even in Java 5 compatibility mode.
     */
    @Test
    public void testGoodOverrideFromObjectJ5Compatible() throws Exception {
        final String filename = "InputMissingOverrideGoodOverrideFromObjectJava5.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests classes that are extending things explicitly will be correctly
     * flagged for only including the inheritDoc tag.
     */
    @Test
    public void testGoodOverrideFromOther() throws Exception {
        final String filename = "InputMissingOverrideGoodOverrideFromOther.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests classes that are extending things explicitly will NOT be flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testGoodOverrideFromOtherJ5Compatible() throws Exception {
        final String filename = "InputMissingOverrideGoodOverrideFromOtherJava5.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are correctly flagged
     * for only including the inheritDoc tag.
     */
    @Test
    public void testGoodAnnotationOverride() throws Exception {
        final String filename = "InputMissingOverrideGoodOverride.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    /**
     * This tests anonymous inner classes that are overriding methods are NOT flagged while in
     * Java 5 compatibility mode.
     */
    @Test
    public void testGoodAnnotationOverrideJ5Compatible() throws Exception {
        final String filename = "InputMissingOverrideGoodOverrideJava5.java";
        final String filePath = getAbsolutePath(filename);

        final DefaultConfiguration checkConfig = createModuleConfig(MissingOverrideCheck.class);
        checkConfig.addAttribute("javaFiveCompatibility", "true");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final BddParser bdd = new BddParser(filename, filePath);
        bdd.parse();
        final DefaultConfiguration config = bdd.getCheckConfig();
        verifyConfig(checkConfig, config);
        verify(createChecker(config, ModuleCreationOption.IN_TREEWALKER),
                getPath(filename), expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final int[] expectedTokens = {TokenTypes.METHOD_DEF };
        final MissingOverrideCheck check = new MissingOverrideCheck();
        final int[] actual = check.getAcceptableTokens();
        assertEquals(1, actual.length, "Invalid acceptable token size");
        assertArrayEquals(expectedTokens, actual, "Default required tokens are invalid");
    }

}
