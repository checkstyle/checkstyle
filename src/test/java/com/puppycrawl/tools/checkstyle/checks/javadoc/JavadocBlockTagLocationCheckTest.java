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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocBlockTagLocationCheck.MSG_BLOCK_TAG_LOCATION;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
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
        assertArrayEquals(expected, checkObj.getAcceptableJavadocTokens(),
                "Default acceptable tokens are invalid");
    }

    @Test
    public void testCorrect() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocBlockTagLocationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig, getPath("InputJavadocBlockTagLocationCorrect.java"), expected);
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
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocBlockTagLocationCheck.class);
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verify(checkConfig,
                getPath("InputJavadocBlockTagLocationMultilineCodeBlock.java"), expected);
    }

    @Test
    public void testIncorrect() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocBlockTagLocationCheck.class);
        final String[] expected = {
            "9: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "author"),
            "10: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "since"),
            "11: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "param"),
            "13: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "throws"),
            "14: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "see"),
            "15: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "return"),
            "15: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "throws"),
        };
        verify(checkConfig,
                getPath("InputJavadocBlockTagLocationIncorrect.java"), expected);
    }

    @Test
    public void testCustomTags() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocBlockTagLocationCheck.class);
        checkConfig.addAttribute("tags", "apiNote");
        checkConfig.addAttribute("tags", "implSpec, implNote");
        final String[] expected = {
            "10: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "apiNote"),
            "10: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "implNote"),
            "10: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "implSpec"),
            "12: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "apiNote"),
            "13: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "implNote"),
            "14: " + getCheckMessage(MSG_BLOCK_TAG_LOCATION, "implSpec"),
        };
        verify(checkConfig,
                getPath("InputJavadocBlockTagLocationCustomTags.java"), expected);
    }

}
