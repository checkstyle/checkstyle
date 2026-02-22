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

package com.puppycrawl.tools.checkstyle.checks.sizes;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.sizes.JavaLineLengthCheck.MSG_KEY;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

public class JavaLineLengthCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/sizes/javalinelength";
    }

    @Test
    public void testGetRequiredTokens() {
        final JavaLineLengthCheck checkObj = new JavaLineLengthCheck();
        assertWithMessage("JavaLineLengthCheck#getRequiredTokens should return empty array "
                + "by default")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void testGetAcceptableTokens() {
        final JavaLineLengthCheck javaLineLengthCheck =
                new JavaLineLengthCheck();
        final int[] actual = javaLineLengthCheck.getAcceptableTokens();
        assertWithMessage("Acceptable tokens should accept all token types")
                .that(actual)
                .isEqualTo(TokenUtil.getAllTokenIds());
    }

    @Test
    public void testOne() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_KEY, 100, 122),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavaLineLengthTextBlockLiteral.java"), expected);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_KEY, 100, 101),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavaLineLengthImport.java"), expected);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_KEY, 100, 105),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavaLineLengthDefault.java"), expected);
    }

    @Test
    public void testFour() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY, 100, 107),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavaLineLengthImportViolation.java"), expected);
    }

    @Test
    public void testClearState() throws Exception {
        final JavaLineLengthCheck check = new JavaLineLengthCheck();

        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputJavaLineLengthImport.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        // Test ignoredLines is cleared
        final Optional<DetailAST> importToken = TestUtil.findTokenInAstByPredicate(root,
                ast -> ast.getType() == TokenTypes.IMPORT);

        assertWithMessage("ignoredLines is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        importToken.orElseThrow(),
                        "ignoredLines",
                        ignoredLines -> ((Collection<?>) ignoredLines).isEmpty()))
                .isTrue();

        // Test lineToAst is cleared
        final Optional<DetailAST> anyToken = TestUtil.findTokenInAstByPredicate(root,
                ast -> ast.getType() == TokenTypes.PACKAGE_DEF);

        assertWithMessage("lineToAst is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        anyToken.orElseThrow(),
                        "lineToAst",
                        lineToAst -> ((Map<?, ?>) lineToAst).isEmpty()))
                .isTrue();
    }
}
