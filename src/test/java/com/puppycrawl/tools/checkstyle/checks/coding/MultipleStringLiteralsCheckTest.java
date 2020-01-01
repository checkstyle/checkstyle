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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleStringLiteralsCheck.MSG_KEY;
import static org.junit.jupiter.api.Assertions.assertNotNull;

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
        final DefaultConfiguration checkConfig =
            createModuleConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "2");
        checkConfig.addAttribute("ignoreStringsRegexp", "");

        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "8:17: " + getCheckMessage(MSG_KEY, "\"\"", 4),
            "10:23: " + getCheckMessage(MSG_KEY, "\", \"", 3),
        };

        verify(checkConfig,
               getPath("InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testItIgnoreEmpty() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "2");

        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "10:23: " + getCheckMessage(MSG_KEY, "\", \"", 3),
        };

        verify(checkConfig,
               getPath("InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testMultipleInputs() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "2");

        final String firstInput = getPath("InputMultipleStringLiterals.java");
        final String secondInput = getPath("InputMultipleStringLiteralsNoWarnings.java");

        final File[] inputs = {new File(firstInput), new File(secondInput)};

        final List<String> expectedFirstInput = Arrays.asList(
            "5:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "10:23: " + getCheckMessage(MSG_KEY, "\", \"", 3)
        );
        final List<String> expectedSecondInput = Arrays.asList(CommonUtil.EMPTY_STRING_ARRAY);

        verify(createChecker(checkConfig), inputs,
            ImmutableMap.of(firstInput, expectedFirstInput,
                secondInput, expectedSecondInput));
    }

    @Test
    public void testItIgnoreEmptyAndComspace() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "2");
        checkConfig.addAttribute("ignoreStringsRegexp", "^((\"\")|(\", \"))$");

        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
        };

        verify(checkConfig,
               getPath("InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testItWithoutIgnoringAnnotations() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("allowedDuplicates", "3");
        checkConfig.addAttribute("ignoreOccurrenceContext", "");

        final String[] expected = {
            "19:23: " + getCheckMessage(MSG_KEY, "\"unchecked\"", 4),
        };

        verify(checkConfig,
               getPath("InputMultipleStringLiterals.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        final MultipleStringLiteralsCheck check = new MultipleStringLiteralsCheck();
        assertNotNull(check.getAcceptableTokens(), "Acceptable tokens should not be null");
        assertNotNull(check.getDefaultTokens(), "Default tokens should not be null");
        assertNotNull(check.getRequiredTokens(), "Required tokens should not be null");
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MultipleStringLiteralsCheck.class);
        final String[] expected = {
            "5:16: " + getCheckMessage(MSG_KEY, "\"StringContents\"", 3),
            "7:17: " + getCheckMessage(MSG_KEY, "\"DoubleString\"", 2),
            "10:23: " + getCheckMessage(MSG_KEY, "\", \"", 3),
        };

        createChecker(checkConfig);
        verify(checkConfig,
            getPath("InputMultipleStringLiterals.java"),
            expected);
    }

    @Test
    public void testIgnores() throws Exception {
        final DefaultConfiguration checkConfig =
            createModuleConfig(MultipleStringLiteralsCheck.class);
        checkConfig.addAttribute("ignoreStringsRegexp", null);
        checkConfig.addAttribute("ignoreOccurrenceContext", "VARIABLE_DEF");
        final String[] expected = {
            "19:23: " + getCheckMessage(MSG_KEY, "\"unchecked\"", 4),
        };

        createChecker(checkConfig);
        verify(checkConfig,
            getPath("InputMultipleStringLiterals.java"),
            expected);
    }

}
