///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleStringLiteralsCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MultipleStringLiteralsCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/multiplestringliterals";
    }

    @Test
    public void testIt() throws Exception {

        final String[] expected = {
            "14:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "17:17: " + getCheckMessage(MSG_KEY, "\"\"", 4),
            "19:23: " + getCheckMessage(MSG_KEY, "\", \"", 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testItIgnoreEmpty() throws Exception {

        final String[] expected = {
            "14:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "19:23: " + getCheckMessage(MSG_KEY, "\", \"", 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultipleStringLiterals2.java"),
               expected);
    }

    @Test
    public void testMultipleInputs() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addProperty("allowedDuplicates", "2");

        final String firstInput = getPath("InputMultipleStringLiterals3.java");
        final String secondInput = getPath("InputMultipleStringLiteralsNoWarnings.java");

        final File[] inputs = {new File(firstInput), new File(secondInput)};

        final List<String> expectedFirstInput = Arrays.asList(
            "14:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "19:23: " + getCheckMessage(MSG_KEY, "\", \"", 3)
        );
        final List<String> expectedSecondInput = Arrays.asList(CommonUtil.EMPTY_STRING_ARRAY);

        verify(createChecker(checkConfig), inputs,
            ImmutableMap.of(firstInput, expectedFirstInput,
                secondInput, expectedSecondInput));
    }

    @Test
    public void testItIgnoreEmptyAndComspace() throws Exception {

        final String[] expected = {
            "14:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultipleStringLiterals4.java"),
               expected);
    }

    @Test
    public void testItWithoutIgnoringAnnotations() throws Exception {

        final String[] expected = {
            "28:23: " + getCheckMessage(MSG_KEY, "\"unchecked\"", 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultipleStringLiterals5.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        final MultipleStringLiteralsCheck check = new MultipleStringLiteralsCheck();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Default tokens should not be null")
            .that(check.getDefaultTokens())
            .isNotNull();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final String[] expected = {
            "14:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "16:17: " + getCheckMessage(MSG_KEY, "\"DoubleString\"", 2),
            "19:23: " + getCheckMessage(MSG_KEY, "\", \"", 3),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultipleStringLiterals6.java"),
            expected);
    }

    @Test
    public void testIgnores() throws Exception {
        final String[] expected = {
            "28:23: " + getCheckMessage(MSG_KEY, "\"unchecked\"", 4),
        };

        verifyWithInlineConfigParser(
                getPath("InputMultipleStringLiterals7.java"),
            expected);
    }

    @Test
    public void testMultipleStringLiteralsTextBlocks() throws Exception {

        final String[] expected = {
            "14:22: " + getCheckMessage(MSG_KEY, "\"string\"", 3),
            "19:25: " + getCheckMessage(MSG_KEY, "\"other string\"", 2),
            "23:25: " + getCheckMessage(MSG_KEY, "\"other string\\n\"", 2),
            "30:25: " + getCheckMessage(MSG_KEY, "\"<html>\\u000D\\u000A\\n\\u2000\\n "
                + "   <body>\\u000D\\u000A\\n\\u2000\\n        <p>Hello, world</p>\\u000D\\"
                + "u000A\\n\\u2000\\n    </body>\\u000D\\u000A\\n\\u2000\\n</html>\\u000D\\"
                + "u000A\\u2000\\n\"", 2),
            "37:34: " + getCheckMessage(MSG_KEY, "\"fun with\\n\\n whitespace\\t"
                + "\\r\\n and other escapes \\\"\"\"\\n\"", 2),
            "42:34: " + getCheckMessage(MSG_KEY, "\"\\b \\f \\\\ \\0 \\1 \\2 "
                + "\\r \\r\\n \\\\r\\\\n \\\\''\\n\\\\11 \\\\57 \\n\\\\n\\n\\\\\\n\\n \\\\ \"\"a "
                + "\"a\\n\\\\' \\\\\\' \\'\\n\"", 2),
            "65:20: " + getCheckMessage(MSG_KEY, "\"foo\"", 4),
            "73:19: " + getCheckMessage(MSG_KEY, "\"another test\"", 2),
            "77:20: " + getCheckMessage(MSG_KEY, "\"\"", 6),
            "88:23: " + getCheckMessage(MSG_KEY, "\"        .\\n         .\\n.\\n\"", 2),
            "104:24: " + getCheckMessage(MSG_KEY, "\"             foo\\n\\n\\n "
                + "       bar\"", 2),
            };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputMultipleStringLiteralsTextBlocks.java"),
            expected);
    }

}
