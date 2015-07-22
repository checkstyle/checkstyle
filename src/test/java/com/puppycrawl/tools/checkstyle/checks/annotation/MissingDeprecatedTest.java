////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class MissingDeprecatedTest extends BaseCheckTestSupport {
    /**
     * Tests that memebers that are only deprecated via javadoc are flagged.
     */
    @Test
    public void testBadDeprecatedAnnotation() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

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

        verify(checkConfig, getPath("annotation" + File.separator + "BadDeprecatedAnnotation.java"), expected);
    }

    /**
     * Tests that memebers that are only deprecated via the annotation are flagged.
     */
    @Test
    public void testBadDeprecatedJavadoc() throws Exception {

        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

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

        verify(checkConfig, getPath("annotation" + File.separator + "BadDeprecatedJavadoc.java"), expected);
    }

    /**
     * Tests various special deprecation conditions such as duplicate or empty tags.
     */
    @Test
    public void testSpecialCaseDeprecated() throws Exception {

        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

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

        verify(checkConfig, getPath("annotation" + File.separator + "SpecialCaseDeprecated.java"), expected);
    }

    /**
     * Tests that good forms of deprecation are not flagged.
     */
    @Test
    public void testGoodDeprecated() throws Exception {

        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
        };

        verify(checkConfig, getPath("annotation" + File.separator + "GoodDeprecated.java"), expected);
    }

    @Test
    public void testTwoInJavadocWithoutAnnotation() throws Exception {

        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "8: " + getCheckMessage(MSG_KEY_JAVADOC_DUPLICATE_TAG, "@deprecated"),
            "12: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "InputMissingDeprecatedCheckTest1.java"), expected);
    }

    @Test
    public void testEmptyJavadocLine() throws Exception {

        DefaultConfiguration checkConfig = createCheckConfig(MissingDeprecatedCheck.class);

        final String[] expected = {
            "7: " + getCheckMessage(MSG_KEY_JAVADOC_MISSING),
            "11: " + getCheckMessage(MSG_KEY_ANNOTATION_MISSING_DEPRECATED),
        };

        verify(checkConfig, getPath("annotation" + File.separator + "InputMissingDeprecatedCheckTest2.java"), expected);
    }
}
