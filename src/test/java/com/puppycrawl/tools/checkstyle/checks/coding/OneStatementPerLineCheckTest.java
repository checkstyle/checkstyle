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
            "14:59: " + getCheckMessage(MSG_KEY),
            "88:21: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineSingleLineSmallTalkStyle.java"),
                expected);
    }

    @Test
    public void testMultiCaseLoops() throws Exception {
        final String[] expected = {
            "27:18: " + getCheckMessage(MSG_KEY),
            "53:17: " + getCheckMessage(MSG_KEY),
            "65:25: " + getCheckMessage(MSG_KEY),
            "85:23: " + getCheckMessage(MSG_KEY),
            "89:63: " + getCheckMessage(MSG_KEY),
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
            "49:21: " + getCheckMessage(MSG_KEY),
            "66:17: " + getCheckMessage(MSG_KEY),
            "74:17: " + getCheckMessage(MSG_KEY),
            "86:10: " + getCheckMessage(MSG_KEY),
            "95:28: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineMultilineForDeclarations.java"),
            expected);
    }

    @Test
    public void testLoopsAndTryWithResourceWithMultilineStatements() throws Exception {
        final String[] expected = {
            "53:39: " + getCheckMessage(MSG_KEY),
            "87:44: " + getCheckMessage(MSG_KEY),
            "99:45: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineMultilineInLoopsAndTryWithResources.java"),
                expected);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {
        final String[] expected = {
            "39:4: " + getCheckMessage(MSG_KEY),
            "43:75: " + getCheckMessage(MSG_KEY),
            "47:54: " + getCheckMessage(MSG_KEY),
            "49:54: " + getCheckMessage(MSG_KEY),
            "49:70: " + getCheckMessage(MSG_KEY),
            "55:46: " + getCheckMessage(MSG_KEY),
            "61:81: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOneStatementPerLine.java"), expected);
    }

    @Test
    public void testResourceReferenceVariableIgnored() throws Exception {
        final String[] expected = {
            "33:42: " + getCheckMessage(MSG_KEY),
            "38:43: " + getCheckMessage(MSG_KEY),
            "45:46: " + getCheckMessage(MSG_KEY),
            "50:46: " + getCheckMessage(MSG_KEY),
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
            "15:59: " + getCheckMessage(MSG_KEY)
        );
        final List<String> expectedSecondInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        verifyWithInlineConfigParser(file1, file2, expectedFirstInput, expectedSecondInput);
    }

    @Test
    public void testEmptyStatement() throws Exception {
        final String[] expected = {
            "13:11: " + getCheckMessage(MSG_KEY),
            "17:29: " + getCheckMessage(MSG_KEY),
            "17:40: " + getCheckMessage(MSG_KEY),
            "22:11: " + getCheckMessage(MSG_KEY),
            "22:22: " + getCheckMessage(MSG_KEY),
            "24:20: " + getCheckMessage(MSG_KEY),
            "24:22: " + getCheckMessage(MSG_KEY),
            "25:19: " + getCheckMessage(MSG_KEY),
            "25:20: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineEmptyStatement.java"),
                expected);
    }

    @Test
    public void testEmptyStatement2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineEmptyStatement2.java"),
                expected);
    }

    @Test
    public void testAnnotation() throws Exception {
        final String[] expected = {
            "14:27: " + getCheckMessage(MSG_KEY),
            "18:22: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineAnnotation.java"),
                expected);
    }

    @Test
    public void testTryResourcesInLambda() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLine2.java"),
                expected);
    }

    @Test
    public void testTryResourcesAndLoops() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineBeginTree3.java"),
                expected);
    }

    @Test
    public void testLastStatementEnd() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineLastStatementEnd.java"),
                getPath("InputOneStatementPerLineLastStatementEnd2.java"),
                expected);
    }

    @Test
    public void testAnonymousClassAndStatements() throws Exception {
        final String[] expected = {
            "16:82: " + getCheckMessage(MSG_KEY),
            "19:66: " + getCheckMessage(MSG_KEY),
            "24:51: " + getCheckMessage(MSG_KEY),
            "27:82: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineAnonymousClass.java"),
                expected
        );
    }

    @Test
    public void testNestedLambdaAndStatements() throws Exception {
        final String[] expected = {
            "17:57: " + getCheckMessage(MSG_KEY),
            "20:20: " + getCheckMessage(MSG_KEY),
            "25:81: " + getCheckMessage(MSG_KEY),
            "25:85: " + getCheckMessage(MSG_KEY),
            "31:79: " + getCheckMessage(MSG_KEY),
            "34:14: " + getCheckMessage(MSG_KEY),
            "38:41: " + getCheckMessage(MSG_KEY),
            "42:62: " + getCheckMessage(MSG_KEY),
            "45:41: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineNestedLambda.java"),
                expected
        );
    }

    @Test
    public void testAnonymousClassAndLambda() throws Exception {
        final String[] expected = {
            "17:69: " + getCheckMessage(MSG_KEY),
            "17:72: " + getCheckMessage(MSG_KEY),
            "24:58: " + getCheckMessage(MSG_KEY),
            "26:11: " + getCheckMessage(MSG_KEY),
            "30:51: " + getCheckMessage(MSG_KEY),
            "48:43: " + getCheckMessage(MSG_KEY),
            "55:12: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineAnonymousClassAndLambda.java"),
                expected
        );
    }

    @Test
    public void testImportStatements() throws Exception {
        final String[] expected = {
            "11:35: " + getCheckMessage(MSG_KEY),
            "14:28: " + getCheckMessage(MSG_KEY),
            "18:12: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getPath("InputOneStatementPerLineImportStatements.java"),
                expected
        );
    }

    @Test
    public void testNonCompilableModuleImportStatements() throws Exception {
        final String[] expected = {
            "12:24: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOneStatementPerLineImportStatement.java"),
                expected
        );
    }

}
