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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.OuterTypeNumberCheck.MSG_KEY;

import java.io.File;
import java.util.Optional;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class OuterTypeNumberCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/outertypenumber";
    }

    @Test
    public void testGetRequiredTokens() {
        final OuterTypeNumberCheck checkObj = new OuterTypeNumberCheck();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };
        assertWithMessage("Default required tokens are invalid")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final OuterTypeNumberCheck outerTypeNumberObj =
            new OuterTypeNumberCheck();
        final int[] actual = outerTypeNumberObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.RECORD_DEF,
        };

        assertWithMessage("Default acceptable tokens are invalid")
            .that(actual)
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, 3, 1),
        };
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeNumberSimple.java"), expected);
    }

    @Test
    public void testMax30() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeNumberSimple1.java"), expected);
    }

    @Test
    public void testWithInnerClass() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputOuterTypeNumberEmptyInner.java"), expected);
    }

    @Test
    public void testWithRecords() throws Exception {

        final int max = 1;

        final String[] expected = {
            "9:1: " + getCheckMessage(MSG_KEY, 2, max),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputOuterTypeNumberRecords.java"), expected);
    }

    /**
     * Checks if the private field {@code currentDepth} and {@code outerNum} is
     * properly cleared during the start of processing the next file in the
     * check as they are file specific values.
     *
     * @throws Exception if there is an error.
     */
    @Test
    public void testClearState() throws Exception {
        final OuterTypeNumberCheck check = new OuterTypeNumberCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputOuterTypeNumberSimple.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> classDef = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.CLASS_DEF);

        assertWithMessage("Ast should contain CLASS_DEF")
                .that(classDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(
                    TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.orElseThrow(),
                            "currentDepth",
                            currentDepth -> ((Number) currentDepth).intValue() == 0))
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(
                    TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.get(),
                            "outerNum",
                            outerNum -> ((Number) outerNum).intValue() == 0))
                .isTrue();
    }

}
