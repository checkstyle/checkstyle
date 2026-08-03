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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocRegexpCheck.MSG_JAVADOC_REGEXP;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.JavadocCommentsTokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocRegexpCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocregexp";
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavadocRegexpCheck check = new JavadocRegexpCheck();
        final int[] expected = {
            JavadocCommentsTokenTypes.TEXT,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(check.getAcceptableJavadocTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpDefault.java"), CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIgnoreMarkupTrue() throws Exception {
        final String format = "(^|\\W)(aka|i\\.e\\.|e\\.g\\.|viz\\.)(\\W|$)";
        final String[] expected = {
            "24: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "31: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "38: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpIgnoreMarkupTrue.java"), expected);
    }

    @Test
    public void testIgnoreMarkupFalse() throws Exception {
        final String format = "<br\\s*[/]?>|aka";
        final String[] expected = {
            "17: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
            "24: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpIgnoreMarkupFalse.java"), expected);
    }

    @Test
    public void testIgnoreCaseFalse() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpIgnoreCaseFalse.java"), CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIgnoreCaseTrue() throws Exception {
        final String format = "AKA";
        final String[] expected = {
            "18: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpIgnoreCaseTrue.java"), expected);
    }

    @Test
    public void testTextSplitAcrossLines() throws Exception {
        final String format = "^first second";
        final String[] expected = {
            "17: " + getCheckMessage(MSG_JAVADOC_REGEXP, format),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocRegexpTextSplitAcrossLines.java"), expected);
    }

}
