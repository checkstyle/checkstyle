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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_CLASS;
import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_FILE;
import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_METHOD;
import static com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck.MSG_RECORD;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Test case for the JavaNCSS-Check.
 *
 */
// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class JavaNCSSCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/metrics/javancss";
    }

    @Test
    public void test() throws Exception {

        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_FILE, 39, 2),
            "19:1: " + getCheckMessage(MSG_CLASS, 22, 1),
            "24:3: " + getCheckMessage(MSG_METHOD, 2, 0),
            "31:3: " + getCheckMessage(MSG_METHOD, 4, 0),
            "40:3: " + getCheckMessage(MSG_METHOD, 12, 0),
            "52:7: " + getCheckMessage(MSG_METHOD, 2, 0),
            "59:3: " + getCheckMessage(MSG_CLASS, 2, 1),
            "66:1: " + getCheckMessage(MSG_CLASS, 10, 1),
            "71:3: " + getCheckMessage(MSG_METHOD, 8, 0),
            "90:1: " + getCheckMessage(MSG_CLASS, 4, 1),
            "91:3: " + getCheckMessage(MSG_METHOD, 1, 0),
            "92:3: " + getCheckMessage(MSG_METHOD, 1, 0),
            "93:3: " + getCheckMessage(MSG_METHOD, 1, 0),
        };

        verifyWithInlineConfigParser(
                getPath("InputJavaNCSS.java"), expected);
    }

    @Test
    public void testEqualToMax() throws Exception {

        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputJavaNCSS2.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputJavaNCSS3.java"), expected);
    }

    @Test
    public void testRecordsAndCompactCtors() throws Exception {

        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_FILE, 89, 2),
            "16:1: " + getCheckMessage(MSG_CLASS, 87, 3),
            "18:3: " + getCheckMessage(MSG_CLASS, 7, 3),
            "36:3: " + getCheckMessage(MSG_RECORD, 6, 4),
            "45:3: " + getCheckMessage(MSG_RECORD, 15, 4),
            "56:5: " + getCheckMessage(MSG_METHOD, 8, 7),
            "75:3: " + getCheckMessage(MSG_RECORD, 6, 4),
            "109:3: " + getCheckMessage(MSG_RECORD, 8, 4),
            "130:3: " + getCheckMessage(MSG_CLASS, 11, 3),
            "151:3: " + getCheckMessage(MSG_RECORD, 12, 4),
            "152:5: " + getCheckMessage(MSG_METHOD, 11, 7),
            "166:3: " + getCheckMessage(MSG_CLASS, 12, 3),
            "167:5: " + getCheckMessage(MSG_METHOD, 11, 7),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavaNCSSRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testForMutation() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_CLASS, 84, 80),
            "16:5: " + getCheckMessage(MSG_CLASS, 83, 80),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavaNCSSResolveMutation.java"),
                expected);
    }

    @Test
    public void testRecordMax() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_CLASS, 152, 80),
            "15:5: " + getCheckMessage(MSG_RECORD, 151, 150),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputJavaNCSSRecordsMax.java"),
                expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavaNCSSCheck javaNcssCheckObj = new JavaNCSSCheck();
        final int[] actual = javaNcssCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_CONTINUE,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.EXPR,
            TokenTypes.LABELED_STAT,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertWithMessage("Acceptable tokens should not be null")
            .that(actual)
            .isNotNull();
        assertWithMessage("Invalid acceptable tokens")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testGetRequiredTokens() {
        final JavaNCSSCheck javaNcssCheckObj = new JavaNCSSCheck();
        final int[] actual = javaNcssCheckObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.STATIC_INIT,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.IMPORT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.CTOR_CALL,
            TokenTypes.SUPER_CTOR_CALL,
            TokenTypes.LITERAL_IF,
            TokenTypes.LITERAL_ELSE,
            TokenTypes.LITERAL_WHILE,
            TokenTypes.LITERAL_DO,
            TokenTypes.LITERAL_FOR,
            TokenTypes.LITERAL_SWITCH,
            TokenTypes.LITERAL_BREAK,
            TokenTypes.LITERAL_CONTINUE,
            TokenTypes.LITERAL_RETURN,
            TokenTypes.LITERAL_THROW,
            TokenTypes.LITERAL_SYNCHRONIZED,
            TokenTypes.LITERAL_CATCH,
            TokenTypes.LITERAL_FINALLY,
            TokenTypes.EXPR,
            TokenTypes.LABELED_STAT,
            TokenTypes.LITERAL_CASE,
            TokenTypes.LITERAL_DEFAULT,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertWithMessage("Required tokens should not be null")
            .that(actual)
            .isNotNull();
        assertWithMessage("Invalid required tokens")
            .that(actual)
            .isEqualTo(expected);
    }

}
