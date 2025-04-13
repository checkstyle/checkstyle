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
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocBlockTagLocationCheck.MSG_BLOCK_TAG_LOCATION;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.JavadocTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocBlockTagLocationCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocblocktaglocation";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocBlockTagLocationCheck checkObj = new JavadocBlockTagLocationCheck();
        final int[] expected = {
            JavadocTokenTypes.TEXT,
        };
        assertWithMessage("Default acceptable tokens are invalid")
            .that(checkObj.getAcceptableJavadocTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocBlockTagLocationCorrect.java"), expected);
    }

    /**
     * This test is needed to indicate the difference between Javadoc and Checkstyle parsers
     * when a block tag is inside an inline tag.
     * Javadoc tool parser sees a block tag here, while Checkstyle parser treat the
     * inner block tag as plain text. If ever the checkstyle parser is changed,
     * this test and the check itself should be reviewed.
     *
     * @throws Exception if exception occurs during verification process.
     */
    @Test
    public void testMultilineCodeBlock() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavadocBlockTagLocationMultilineCodeBlock.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "author"),
            "16: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "since"),
            "17: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "param"),
            "19: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "throws"),
            "20: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "see"),
            "21: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "return"),
            "21: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "throws"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocBlockTagLocationIncorrect.java"), expected);
    }

    @Test
    public void testCustomTags() throws Exception {
        final String[] expected = {
            "14: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "apiNote"),
            "14: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "implNote"),
            "14: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "implSpec"),
            "16: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "apiNote"),
            "17: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "implNote"),
            "18: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "implSpec"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocBlockTagLocationCustomTags.java"), expected);
    }

}
