////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck.MSG_METHOD;
import static com.puppycrawl.tools.checkstyle.checks.coding.RedundantThisCheck.MSG_VARIABLE;

import java.io.File;
import java.lang.reflect.Constructor;
import java.util.Collection;
import java.util.Optional;
import java.util.SortedSet;

import org.antlr.v4.runtime.CommonToken;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

/**
 * Test fixture for the RedundantThisCheck.
 *
 */
public class RedundantThisCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/redundantthis";
    }

    @Test
    public void testSimple() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_VARIABLE, "x"),
            "26:16: " + getCheckMessage(MSG_VARIABLE, "x"),
            "44:9: " + getCheckMessage(MSG_VARIABLE, "y"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisSimple.java"),
               expected);
    }

    @Test
    public void testMethodsOnly() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_VARIABLE, "x"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisMethodsOnly.java"),
               expected);
    }

    @Test
    public void testFieldsOnly() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_VARIABLE, "x"),
            "26:16: " + getCheckMessage(MSG_VARIABLE, "y"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisFieldsOnly.java"),
               expected);
    }

    @Test
    public void testAnonymousObjects() throws Exception {
        final String[] expected = {
            "24:17: " + getCheckMessage(MSG_METHOD, "doSideEffect"),
            "30:9: " + getCheckMessage(MSG_VARIABLE, "bar"),
            "42:25: " + getCheckMessage(MSG_VARIABLE, "name"),
            "51:9: " + getCheckMessage(MSG_METHOD, "methodFour"),
            "55:17: " + getCheckMessage(MSG_VARIABLE, "member"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisAnonymousObjects.java"),
               expected);
    }

    @Test
    public void testBraceAlone() throws Exception {
        final String[] expected = {
            "26:13: " + getCheckMessage(MSG_VARIABLE, "var1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisBraceAlone.java"),
               expected);
    }

    @Test
    public void testCatchVariables() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_VARIABLE, "ex"),
            "22:23: " + getCheckMessage(MSG_VARIABLE, "ex"),
            "30:28: " + getCheckMessage(MSG_VARIABLE, "ex"),
            "31:21: " + getCheckMessage(MSG_METHOD, "debug"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisCatchVariables.java"),
               expected);
    }

    @Test
    public void testFor() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_VARIABLE, "bottom"),
            "29:37: " + getCheckMessage(MSG_VARIABLE, "name"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisFor.java"),
               expected);
    }

    @Test
    public void testCaseGroup() throws Exception {
        final String[] expected = {
            "35:21: " + getCheckMessage(MSG_VARIABLE, "aVariable"),
            "36:29: " + getCheckMessage(MSG_VARIABLE, "aVariable"),
            "61:24: " + getCheckMessage(MSG_METHOD, "method3"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisCaseGroup.java"),
               expected);
    }

    @Test
    public void testEnumInnerClasses() throws Exception {
        final String[] expected = {
            "25:13: " + getCheckMessage(MSG_METHOD, "method1"),
            "29:9: " + getCheckMessage(MSG_VARIABLE, "i"),
            "39:9: " + getCheckMessage(MSG_METHOD, "method3"),
            "49:13: " + getCheckMessage(MSG_VARIABLE, "z"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisEnumInnerClasses.java"),
               expected);
    }

    @Test
    public void testLambdaParameters() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisLambdaParameters.java"),
               expected);
    }

    @Test
    public void testInnerClassAndLimitation() throws Exception {
        final String[] expected = {
            "25:17: " + getCheckMessage(MSG_VARIABLE, "x"),
            "30:13: " + getCheckMessage(MSG_VARIABLE, "x"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisInnerClassAndLimitation.java"),
               expected);
    }

    @Test
    public void testReceiver() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisReceiver.java"),
               expected);
    }

    @Test
    public void testTypeArguments() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_VARIABLE, "i"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisTypeArguments.java"),
               expected);
    }

    @Test
    public void testTryWithResourcesVariables() throws Exception {
        final String[] expected = {
            "23:9: " + getCheckMessage(MSG_VARIABLE, "ex"),
            "24:9: " + getCheckMessage(MSG_VARIABLE, "scanner"),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisTryWithResourcesVariables.java"),
                expected);
    }

    @Test
    public void testFieldsInExpressions() throws Exception {
        final String[] expected = {
            "39:28: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "id", ""),
            "40:28: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "41:28: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "42:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "43:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "44:25: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "45:25: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "46:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "47:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "48:33: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "b", ""),
            "49:36: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "b", ""),
            "50:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "51:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "52:28: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "53:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "54:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "55:26: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
            "56:31: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "b", ""),
            "57:32: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "b", ""),
            "59:38: " + getCheckMessage(RedundantThisCheck.MSG_VARIABLE, "length", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRedundantThisExpressions.java"),
                expected);
    }

    @Test
    public void testRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "31:17: " + getCheckMessage(RedundantThisCheck.MSG_METHOD, "method1", ""),
            "69:17: " + getCheckMessage(RedundantThisCheck.MSG_METHOD, "method1", ""),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputRedundantThisRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testDefaultSwitch() {
        final RedundantThisCheck check = new RedundantThisCheck();

        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.ENUM, "ENUM"));

        check.visitToken(ast);
        final SortedSet<Violation> messages = check.getViolations();

        assertWithMessage("No exception messages expected")
                .that(messages.size())
                .isEqualTo(0);

    }

    @Test
    public void testClearState() throws Exception {
        final RedundantThisCheck check = new RedundantThisCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputRedundantThisSimple.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> classDef = TestUtil.findTokenInAstByPredicate(root,
                ast -> ast.getType() == TokenTypes.CLASS_DEF);

        assertWithMessage("Ast should contain CLASS_DEF")
                .that(classDef.isPresent())
                        .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.get(),
                        "current", current -> ((Collection<?>) current).isEmpty()))
                .isTrue();
    }

    @Test
    public void testUnusedMethod() throws Exception {
        final DetailAstImpl ident = new DetailAstImpl();
        ident.setText("testName");

        final Class<?> cls = Class.forName(AbstractFrameCheck.class.getName() + "$CatchFrame");
        final Constructor<?> constructor = cls.getDeclaredConstructors()[0];
        constructor.setAccessible(true);
        final Object o = constructor.newInstance(null, ident);

        final DetailAstImpl actual = TestUtil.invokeMethod(o, "getFrameNameIdent");
        assertWithMessage("expected ident token")
                .that(actual)
                        .isEqualTo(ident);
        assertWithMessage("expected catch frame type")
                .that("CATCH_FRAME")
                        .isEqualTo(TestUtil.invokeMethod(o, "getType").toString());
    }

    @Test
    public void testTokensNotNull() {
        final RedundantThisCheck check = new RedundantThisCheck();
        assertWithMessage("Acceptable tokens should not be null")
                .that(check.getAcceptableTokens())
                        .isEmpty();
        assertWithMessage("Acceptable tokens should not be null")
                .that(check.getDefaultTokens())
                        .isEmpty();
        assertWithMessage("Acceptable tokens should not be null")
                .that(check.getRequiredTokens())
                .isEmpty();
    }
}
