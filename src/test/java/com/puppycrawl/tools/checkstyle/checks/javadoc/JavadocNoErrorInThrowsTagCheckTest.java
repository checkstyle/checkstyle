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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocNoErrorInThrowsTagCheckTest extends AbstractModuleTestSupport {

    private static final String MSG_KEY = JavadocNoErrorInThrowsTagCheck.MSG_KEY;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocnoerrorinthrowstag";
    }

    @Test
    public void testCorrect() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocNoErrorInThrowsTagCorrect.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "16:8: " + getCheckMessage(MSG_KEY, "Error", "@throws"),
            "26:8: " + getCheckMessage(MSG_KEY, "java.lang.Error", "@throws"),
            "37:8: " + getCheckMessage(MSG_KEY, "OutOfMemoryError", "@throws"),
            "53:8: " + getCheckMessage(MSG_KEY, "StackOverflowError", "@exception"),
            "66:8: " + getCheckMessage(MSG_KEY, "com.example.CustomError", "@throws"),
            "78:12: " + getCheckMessage(MSG_KEY, "AssertionError", "@throws"),
            "89:8: " + getCheckMessage(MSG_KEY, "Error", "@throws"),
            "100:8: " + getCheckMessage(MSG_KEY, "Error", "@throws"),
            "110:8: " + getCheckMessage(MSG_KEY, "Error", "@throws"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocNoErrorInThrowsTagIncorrect.java"), expected);
    }

    @Test
    public void testStatefulProcessing() throws Exception {
        final String[] expected = {
            "24:8: " + getCheckMessage(MSG_KEY, "Error", "@throws"),
            "50:8: " + getCheckMessage(MSG_KEY, "CreateError", "@throws"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocNoErrorInThrowsTagStateful.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "13:4: " + getCheckMessage(MSG_KEY, "AssertionError", "@throws"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputJavadocNoErrorInThrowsTagCompactDefault.java"),
                expected);
    }

}
