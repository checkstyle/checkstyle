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

package com.puppycrawl.tools.checkstyle.checks.coding;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck.MSG_KEY;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OneStatementPerLineCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/onestatementperline";
    }

    @Test
    public void testMultiCaseSmallTalkStyle() throws Exception {
        final String[] expected = {
            "13:59: " + getCheckMessage(MSG_KEY),
            "87:21: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineSingleLineSmallTalkStyle.java"),
                expected);
    }

    @Test
    public void testMultiCaseLoops() throws Exception {
        final String[] expected = {
            "26:18: " + getCheckMessage(MSG_KEY),
            "52:17: " + getCheckMessage(MSG_KEY),
            "64:25: " + getCheckMessage(MSG_KEY),
            "84:23: " + getCheckMessage(MSG_KEY),
            "88:63: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineSingleLineInLoops.java"),
                expected);
    }

    @Test
    public void testMultiCaseDeclarations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineSingleLineForDeclarations.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final OneStatementPerLineCheck check = new OneStatementPerLineCheck();
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
    public void testDeclarationsWithMultilineStatements() throws Exception {
        final String[] expected = {
            "48:21: " + getCheckMessage(MSG_KEY),
            "65:17: " + getCheckMessage(MSG_KEY),
            "73:17: " + getCheckMessage(MSG_KEY),
            "85:10: " + getCheckMessage(MSG_KEY),
            "94:28: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineMultilineForDeclarations.java"),
            expected);
    }

    @Test
    public void testLoopsAndTryWithResourceWithMultilineStatements() throws Exception {
        final String[] expected = {
            "52:39: " + getCheckMessage(MSG_KEY),
            "86:44: " + getCheckMessage(MSG_KEY),
            "98:45: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineMultilineInLoopsAndTryWithResources.java"),
                expected);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {
        final String[] expected = {
            "39:4: " + getCheckMessage(MSG_KEY),
            "46:54: " + getCheckMessage(MSG_KEY),
            "48:54: " + getCheckMessage(MSG_KEY),
            "48:70: " + getCheckMessage(MSG_KEY),
            "54:46: " + getCheckMessage(MSG_KEY),
            "60:81: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOneStatementPerLine.java"), expected);
    }

    @Test
    public void testResourceReferenceVariableIgnored() throws Exception {
        final String[] expected = {
            "32:42: " + getCheckMessage(MSG_KEY),
            "37:43: " + getCheckMessage(MSG_KEY),
            "44:46: " + getCheckMessage(MSG_KEY),
            "49:46: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineTryWithResources.java"),
                expected);
    }

    @Test
    public void testResourcesIgnored() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineTryWithResourcesIgnore.java"),
                expected);
    }

    @Test
    public void testAllTheCodeInSingleLine() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(OneStatementPerLineCheck.class);

        final String[] expected = {
            "5:102: " + getCheckMessage(MSG_KEY),
            "5:131: " + getCheckMessage(MSG_KEY),
            "5:165: " + getCheckMessage(MSG_KEY),
            "5:230: " + getCheckMessage(MSG_KEY),
            "5:402: " + getCheckMessage(MSG_KEY),
            "5:414: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig, getPath("InputOneStatementPerLine.java"),
                expected);
    }

    @Test
    public void testStateIsClearedOnBeginTreeForLastStatementEnd() throws Exception {
        final String inputWithWarnings = getPath("InputOneStatementPerLineBeginTree1.java");
        final String inputWithoutWarnings = getPath("InputOneStatementPerLineBeginTree2.java");
        final List<String> expectedFirstInput = List.of(
            "7:96: " + getCheckMessage(MSG_KEY)
        );
        final List<String> expectedSecondInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        verifyWithInlineConfigParser(inputWithWarnings,
            inputWithoutWarnings, expectedFirstInput, expectedSecondInput);
    }

    @Test
    public void testStateIsClearedOnBeginTreeForLastVariableStatement() throws Exception {
        final String file1 = getPath(
                "InputOneStatementPerLineBeginTreeLastVariableResourcesStatementEnd1.java");
        final String file2 = getPath(
                "InputOneStatementPerLineBeginTreeLastVariableResourcesStatementEnd2.java");
        final List<String> expectedFirstInput = List.of(
            "14:59: " + getCheckMessage(MSG_KEY)
        );
        final List<String> expectedSecondInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        verifyWithInlineConfigParser(file1, file2, expectedFirstInput, expectedSecondInput);
    }
}
