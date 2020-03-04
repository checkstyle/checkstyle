////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck.MSG_KEY_ANNOTATION_MISSING_DEPRECATED;
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck.MSG_KEY_JAVADOC_DUPLICATE_TAG;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingDeprecatedCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/annotation/missingdeprecated";
    }

    @Test
    public void testGetDefaultJavadocTokens() {
        final MissingDeprecatedCheck missingDeprecatedCheck =
                new MissingDeprecatedCheck();
        final int[] expected = {
            JavadocTokenTypes.JAVADOC,
        };

        assertArrayEquals(expected, missingDeprecatedCheck.getDefaultJavadocTokens(),
                "Default javadoc tokens are invalid");
    }

    @Test
    public void testGetRequiredJavadocTokens() {
        final MissingDeprecatedCheck checkObj = new MissingDeprecatedCheck();
        final int[] expected = {
            JavadocTokenTypes.JAVADOC,
        };
        assertArrayEquals(expected, checkObj.getRequiredJavadocTokens(),
                "Default required javadoc tokens are invalid");
    }

    /**
     * Tests that members that are only deprecated via javadoc are flagged.
     */
    @Test
    public void testBadDeprecatedAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "12: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "19: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "26: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "31: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "38: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "43: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "51: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "56: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedBadDeprecated.java"), expected);
    }

    /**
     * Tests that members that are only deprecated via the annotation are flagged.
     */
    @Test
    public void testBadDeprecatedJavadoc() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "11: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "29: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "38: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "48: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "55: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedBadJavadoc.java"), expected);
    }

    /**
     * Tests various special deprecation conditions such as duplicate or empty tags.
     */
    @Test
    public void testSpecialCaseDeprecated() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "5: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "12: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "14: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "19: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "33: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "42: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "51: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "92: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "99: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "106: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedSpecialCase.java"), expected);
    }

    /**
     * Tests that good forms of deprecation are not flagged.
     */
    @Test
    public void testGoodDeprecated() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingDeprecatedGood.java"), expected);
    }

    @Test
    public void testTwoInJavadocWithoutAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "8: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "12: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedClass.java"), expected);
    }

    @Test
    public void testEmptyJavadocLine() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "11: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedMethod.java"), expected);
    }

    @Test
    public void testPackageInfo() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "2: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("package-info.java"), expected);
    }

    @Test
    public void testDepPackageInfoBelowComment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedAbovePackage.java"), expected);
    }

    @Test
    public void testPackageInfoBelowComment() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(MissingDeprecatedCheck.class);

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingDeprecatedSingleComment.java"), expected);
    }
}
