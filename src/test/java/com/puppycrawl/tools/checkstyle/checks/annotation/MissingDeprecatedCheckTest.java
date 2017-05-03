////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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
import static com.puppycrawl.tools.checkstyle.checks.annotation.MissingDeprecatedCheck.MSG_KEY_JAVADOC_MISSING;
import static org.junit.Assert.assertArrayEquals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class MissingDeprecatedCheckTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "annotation" + File.separator
                + "missingdeprecated" + File.separator
                + filename);
    }

    @Test
    public void testGetRequiredTokens() {
        final MissingDeprecatedCheck checkObj = new MissingDeprecatedCheck();
        final int[] expected = {
            TokenTypes.INTERFACE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.ENUM_CONSTANT_DEF,
            TokenTypes.ANNOTATION_FIELD_DEF,
        };
        assertArrayEquals(expected, checkObj.getRequiredTokens());
    }

    /**
     * Tests that members that are only deprecated via javadoc are flagged.
     */
    @Test
    public void testBadDeprecatedAnnotation() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

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

        final DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "5: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "11: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "16: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "23: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "29: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "38: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "40: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
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

        final DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "5: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "12: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "14: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "17: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "19: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "24: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "32: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "33: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "33: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "42: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "42: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "50: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "51: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedSpecialCase.java"), expected);
    }

    /**
     * Tests that good forms of deprecation are not flagged.
     */
    @Test
    public void testGoodDeprecated() throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputMissingDeprecatedGood.java"), expected);
    }

    @Test
    public void testTwoInJavadocWithoutAnnotation() throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "8: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "12: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedClass.java"), expected);
    }

    @Test
    public void testEmptyJavadocLine() throws Exception {

        final DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "11: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedMethod.java"), expected);
    }

    @Test
    public void testSkipNoJavadocOption() throws Exception {
        final DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);
        checkConfig.addAttribute("skipNoJavadoc", "true");

        final String[] expected = {
            "10: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
            "26: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("InputMissingDeprecatedSkipNoJavadoc.java"), expected);
    }
}
