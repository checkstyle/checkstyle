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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocContentLocationCheck.MSG_JAVADOC_CONTENT_FIRST_LINE;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocContentLocationCheck.MSG_JAVADOC_CONTENT_SECOND_LINE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocContentLocationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoccontentlocation";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocContentLocationCheck checkObj = new JavadocContentLocationCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertWithMessage("Acceptable tokens are invalid")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetDefaultTokens() {
        final JavadocContentLocationCheck checkObj = new JavadocContentLocationCheck();
        final int[] expected = {TokenTypes.BLOCK_COMMENT_BEGIN };
        assertWithMessage("Default tokens are invalid")
            .that(checkObj.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_JAVADOC_CONTENT_SECOND_LINE),
            "21:5: " + getCheckMessage(MSG_JAVADOC_CONTENT_SECOND_LINE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocContentLocationDefault.java"), expected);
    }

    @Test
    public void testFirstLine() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_JAVADOC_CONTENT_FIRST_LINE),
            "21:5: " + getCheckMessage(MSG_JAVADOC_CONTENT_FIRST_LINE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocContentLocationFirstLine.java"), expected);
    }

    @Test
    public void testPackage() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_JAVADOC_CONTENT_SECOND_LINE),
        };
        verifyWithInlineConfigParser(
                getPath("package-info.java"), expected);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_JAVADOC_CONTENT_FIRST_LINE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocContentLocationInterface.java"), expected);
    }

    @Test
    public void testOptionalSpacesAndAsterisks() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocContentLocationTrailingSpace.java"), expected);
    }

    @Test
    public void testTrimOptionProperty() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_JAVADOC_CONTENT_FIRST_LINE),
            "21:5: " + getCheckMessage(MSG_JAVADOC_CONTENT_FIRST_LINE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocContentLocationTrimOptionProperty.java"), expected);
    }

    @Test
    public void testDefault2() throws Exception {
        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_JAVADOC_CONTENT_SECOND_LINE),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocContentLocation.java"), expected);
    }
}
