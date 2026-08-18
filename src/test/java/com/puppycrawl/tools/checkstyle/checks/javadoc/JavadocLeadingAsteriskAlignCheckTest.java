///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLeadingAsteriskAlignCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocLeadingAsteriskAlignCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocleadingasteriskalign";
    }

    @Test
    public void testCorrectJavadoc() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignCorrect.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testIncorrectJavadoc() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 0, 1),
            "19:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 1),
            "26:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 0, 1),
            "33:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 1),
            "38:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 0, 1),
            "43:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 4, 1),
            "49:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, -2, 1),
            "56:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 4, 1),
            "62:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, -2, 1),
            "68:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 4, 1),
            "69:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 1),
            "74:9: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 4, 1),
            "80:8: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 3, 1),
            "90:4: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, -1, 1),
            "96:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 1),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignIncorrect.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testTabs() throws Exception {
        final String[] expected = {
            "50:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 1),
            "62:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 0, 1),
            "63:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 1),
            "74:7: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 1),
            "75:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 0, 1),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignTabs.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testMultipleLeadingAsterisks() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 1),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignMultipleAsterisks.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testLeadingAsteriskOnOpeningLine() throws Exception {
        final String[] expected = {
            "18:6: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 6),
            "19:6: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 6),
            "20:6: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 6),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignOpeningLine.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testIndentationZero() throws Exception {
        final String[] expected = {
            "25:4: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 0),
            "26:4: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 0),
            "32:5: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 2, 0),
            "38:1: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, -2, 0),
            "49:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 0, 6),
            "50:3: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 0, 6),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignIndentationZero.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

    @Test
    public void testIndentationTwo() throws Exception {
        final String[] expected = {
            "20:4: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 2),
            "21:4: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 1, 2),
            "32:8: " + getCheckMessage(JavadocLeadingAsteriskAlignCheck.class, MSG_KEY, 5, 2),
        };

        final String filePath = getPath("InputJavadocLeadingAsteriskAlignIndentationTwo.java");
        verifyWithInlineConfigParser(filePath, expected);
    }

}
