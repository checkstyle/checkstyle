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

public class JavadocSeeTagOrderCheckTest extends AbstractModuleTestSupport {

    private static final String MSG_KEY = JavadocSeeTagOrderCheck.MSG_KEY;

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocseetagorder";
    }

    @Test
    public void testCorrect() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocSeeTagOrderCorrect.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testIncorrect() throws Exception {
        final String[] expected = {
            "20:8: " + getCheckMessage(MSG_KEY,
                    "#InputJavadocSeeTagOrderIncorrect()",
                    "#InputJavadocSeeTagOrderIncorrect(String)"),
            "33:8: " + getCheckMessage(MSG_KEY, "#getName(String)",
                    "#setName(String)"),
            "44:8: " + getCheckMessage(MSG_KEY, "#setName()",
                    "#setName(String)"),
            "55:8: " + getCheckMessage(MSG_KEY, "#field", "OtherClass"),
            "66:8: " + getCheckMessage(MSG_KEY, "OtherClass", "OtherClass#field"),
            "77:8: " + getCheckMessage(MSG_KEY, "OtherClass", "java.util.List"),
            "88:8: " + getCheckMessage(MSG_KEY, "java.util.List", "java.util"),
            "99:8: " + getCheckMessage(MSG_KEY, "java.util.List#add(Object)",
                    "java.util.List#add(int, Object)"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocSeeTagOrderIncorrect.java"), expected);
    }

    @Test
    public void testIncorrect2() throws Exception {
        final String[] expected = {
            "24:8: " + getCheckMessage(MSG_KEY, "#field1", "#beta()"),
            "25:8: " + getCheckMessage(MSG_KEY, "#field2", "#beta()"),
            "49:8: " + getCheckMessage(MSG_KEY, "#name", "#age"),
            "67:8: " + getCheckMessage(MSG_KEY, "java.awt.List", "java.util.Map"),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavadocSeeTagOrderIncorrect2.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocSeeTagOrderEdgeCases.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testEdgeCases2() throws Exception {
        verifyWithInlineConfigParser(
                getPath("InputJavadocSeeTagOrderEdgeCases2.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

}
