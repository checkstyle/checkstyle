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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck.MSG_KEY;

import java.io.File;
import java.util.List;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.google.common.collect.ImmutableMap;
import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UncommentedMainCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/uncommentedmain";
    }

    @Test
    public void testDefaults()
            throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY),
            "26:5: " + getCheckMessage(MSG_KEY),
            "35:5: " + getCheckMessage(MSG_KEY),
            "99:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputUncommentedMain.java"), expected);
    }

    @Test
    public void testExcludedClasses()
            throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_KEY),
            "35:5: " + getCheckMessage(MSG_KEY),
            "99:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputUncommentedMain6.java"), expected);
    }

    @Test
    public void testTokens() {
        final UncommentedMainCheck check = new UncommentedMainCheck();
        assertWithMessage("Required tokens should not be null")
            .that(check.getRequiredTokens())
            .isNotNull();
        assertWithMessage("Acceptable tokens should not be null")
            .that(check.getAcceptableTokens())
            .isNotNull();
        assertWithMessage("Invalid default tokens")
            .that(check.getAcceptableTokens())
            .isEqualTo(check.getDefaultTokens());
        assertWithMessage("Invalid acceptable tokens")
            .that(check.getRequiredTokens())
            .isEqualTo(check.getDefaultTokens());
    }

    @Test
    public void testDeepDepth() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUncommentedMain2.java"), expected);
    }

    @Test
    public void testVisitPackage() throws Exception {
        final String[] expected = {
            "21:5: " + getCheckMessage(MSG_KEY),
        };
        verifyWithInlineConfigParser(
                getPath("InputUncommentedMain5.java"), expected);
    }

    @Test
    public void testWrongName() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUncommentedMain3.java"), expected);
    }

    @Test
    public void testWrongArrayType() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUncommentedMain4.java"), expected);
    }

    @Test
    public void testIllegalStateException() {
        final UncommentedMainCheck check = new UncommentedMainCheck();
        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.CTOR_DEF, "ctor"));
        try {
            check.visitToken(ast);
            assertWithMessage("IllegalStateException is expected").fail();
        }
        catch (IllegalStateException ex) {
            assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo(ast.toString());
        }
    }

    @Test
    public void testRecords()
            throws Exception {

        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_KEY),
            "20:9: " + getCheckMessage(MSG_KEY),
            "25:13: " + getCheckMessage(MSG_KEY),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath(
                "InputUncommentedMainRecords.java"), expected);
    }

    @Test
    public void testStateIsClearedOnBeginTree() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        final String file1 = getNonCompilablePath(
                "InputUncommentedMainRecords2.java");
        final String file2 = getNonCompilablePath(
                "InputUncommentedMainBeginTree2.java");
        final List<String> expectedFirstInput = List.of(
                "12:5: " + getCheckMessage(MSG_KEY),
                "21:24: " + getCheckMessage(MSG_KEY)
        );
        final List<String> expectedSecondInput = List.of(
                "13:13: " + getCheckMessage(MSG_KEY)
        );
        final File[] inputs = {new File(file1), new File(file2)};

        verify(createChecker(checkConfig), inputs, ImmutableMap.of(
            file1, expectedFirstInput,
            file2, expectedSecondInput));
    }

    @Test
    public void testStateIsClearedOnBeginTree2() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(UncommentedMainCheck.class);
        checkConfig.addProperty("excludedClasses",
                "uncommentedmain\\.InputUncommentedMainBeginTreePackage2");
        final String file1 = getPath(
                "InputUncommentedMainBeginTreePackage1.java");
        final String file2 = getPath(
                "InputUncommentedMainBeginTreePackage2.java");
        final List<String> expectedFirstInput = List.of(CommonUtil.EMPTY_STRING_ARRAY);
        final List<String> expectedSecondInput = List.of(
                "3:5: " + getCheckMessage(MSG_KEY),
                "12:5: " + getCheckMessage(MSG_KEY)
        );
        final File[] inputs = {new File(file1), new File(file2)};

        verify(createChecker(checkConfig), inputs, ImmutableMap.of(
            file1, expectedFirstInput,
            file2, expectedSecondInput));
    }
}
