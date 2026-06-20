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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocParamOrderCheck.MSG_JAVADOC_PARAM_ORDER;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocParamOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadocparamorder";
    }

    @Test
    public void testCorrect() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocParamOrderCorrect.java"), expected);
    }

    @Test
    public void testCorrectDuplicate() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavadocParamOrderCorrectDuplicate.java"), expected);
    }

    @Test
    public void testViolation() throws Exception {
        final String[] expected = {
            "15: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "16: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "17: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "24: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "26: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "34: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "35: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "44: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "46: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "53: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "54: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "65: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "66: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "74: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "75: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderViolation.java"), expected);
    }

    @Test
    public void testTypeParams() throws Exception {
        final String[] expected = {
            "24: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "25: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "26: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "33: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "34: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "51: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "52: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "60: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "61: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "62: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "63: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "89: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "90: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "91: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "92: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "107: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "108: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderTypeParams.java"), expected);
    }

    @Test
    public void testEdgeCases() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderEdgeCases.java"), expected);
    }

    @Test
    public void testComplex() throws Exception {
        final String[] expected = {
            "16: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "17: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "26: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "28: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "32: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "40: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "42: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "51: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "52: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "53: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "61: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "62: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "63: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "64: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "65: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "79: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "81: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "88: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "89: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "96: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "98: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "105: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "106: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "113: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
            "114: " + getCheckMessage(MSG_JAVADOC_PARAM_ORDER),
        };
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderComplex.java"), expected);
    }

    @Test
    public void testMoreCases() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(getPath("InputJavadocParamOrderMoreCases.java"), expected);
    }
}
