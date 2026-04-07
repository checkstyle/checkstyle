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
import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_METHOD;
import static com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck.MSG_VARIABLE;

import java.io.File;
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

public class RequireThisCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/requirethis";
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "21:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "35:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "53:13: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "60:9: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "67:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "68:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "69:9: " + getCheckMessage(MSG_METHOD, "instanceMethod", ""),
            "75:13: " + getCheckMessage(MSG_METHOD, "instanceMethod", "Issue2240."),
            "76:13: " + getCheckMessage(MSG_VARIABLE, "i", "Issue2240."),
            "88:9: " + getCheckMessage(MSG_METHOD, "foo", ""),
            "107:16: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "107:20: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "107:24: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "113:16: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "113:20: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "113:24: " + getCheckMessage(MSG_VARIABLE, "b", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisEnumInnerClassesAndBugs.java"),
               expected);
    }

    @Test
    public void testMethodsOnly() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "51:9: " + getCheckMessage(MSG_METHOD, "instanceMethod", ""),
            "57:13: " + getCheckMessage(MSG_METHOD, "instanceMethod", "Issue22402."),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisEnumInnerClassesAndBugs2.java"),
               expected);
    }

    @Test
    public void testFieldsOnly() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "35:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "54:13: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "61:9: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "68:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "69:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "77:13: " + getCheckMessage(MSG_VARIABLE, "i", "Issue22403."),
            "97:16: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "97:20: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "97:24: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "103:16: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "103:20: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "103:24: " + getCheckMessage(MSG_VARIABLE, "b", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisEnumInnerClassesAndBugs3.java"),
               expected);
    }

    @Test
    public void testFieldsInExpressions() throws Exception {
        final String[] expected = {
            "18:28: " + getCheckMessage(MSG_VARIABLE, "id", ""),
            "19:28: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "20:28: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "21:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "22:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "23:25: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "24:25: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "25:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "26:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "27:33: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "28:36: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "29:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "30:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "31:28: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "32:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "33:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "34:26: " + getCheckMessage(MSG_VARIABLE, "length", ""),
            "35:31: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "36:32: " + getCheckMessage(MSG_VARIABLE, "b", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisExpressions.java"),
               expected);
    }

    @Test
    public void testGenerics() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThis15Extensions.java"), expected);
    }

    @Test
    public void testGithubIssue41() throws Exception {
        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_VARIABLE, "number", ""),
            "17:16: " + getCheckMessage(MSG_METHOD, "other", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisSimple.java"),
                expected);
    }

    @Test
    public void testTokensNotNull() {
        final RequireThisCheck check = new RequireThisCheck();
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
    public void testWithAnonymousClass() throws Exception {
        final String[] expected = {
            "29:25: " + getCheckMessage(MSG_METHOD, "doSideEffect", ""),
            "33:24: " + getCheckMessage(MSG_VARIABLE, "bar", "InputRequireThisAnonymousEmpty."),
            "56:17: " + getCheckMessage(MSG_VARIABLE, "foobar", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisAnonymousEmpty.java"),
                expected);
    }

    @Test
    public void testDefaultSwitch() {
        final RequireThisCheck check = new RequireThisCheck();

        final DetailAstImpl ast = new DetailAstImpl();
        ast.initialize(new CommonToken(TokenTypes.ENUM, "ENUM"));

        check.visitToken(ast);
        final SortedSet<Violation> violations = check.getViolations();

        assertWithMessage("No exception violations expected")
            .that(violations)
            .isEmpty();
    }

    @Test
    public void testValidateOnlyOverlappingFalse() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "30:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "31:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "32:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "35:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "39:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "43:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "45:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "47:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "51:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "52:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "62:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "63:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "73:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "77:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "81:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "85:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "106:29: " + getCheckMessage(MSG_VARIABLE, "booleanField", ""),
            "111:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "116:9: " + getCheckMessage(MSG_VARIABLE, "booleanField", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingFalse.java"), expected);
    }

    @Test
    public void testValidateOnlyOverlappingFalse2() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "31:21: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "42:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "50:18: " + getCheckMessage(MSG_METHOD, "addSuf2F", ""),
            "55:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "55:18: " + getCheckMessage(MSG_METHOD, "addSuf2F", ""),
            "60:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "66:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "74:25: " + getCheckMessage(MSG_METHOD, "getAction", ""),
            "76:20: " + getCheckMessage(MSG_METHOD, "processAction", ""),
            "84:16: " + getCheckMessage(MSG_METHOD, "processAction", ""),
            "100:22: " + getCheckMessage(MSG_VARIABLE, "add", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingFalse2.java"), expected);
    }

    @Test
    public void testValidateOnlyOverlappingFalseLeaves() throws Exception {
        final String[] expected = {
            "26:31: " + getCheckMessage(MSG_METHOD, "id", ""),
            "36:16: " + getCheckMessage(MSG_VARIABLE, "_a", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingFalseLeaves.java"),
                expected);
    }

    @Test
    public void testValidateOnlyOverlappingTrue() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "45:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "73:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "81:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "85:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "90:9: " + getCheckMessage(MSG_VARIABLE, "booleanField", ""),
            "94:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "98:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "103:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "109:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingTrue.java"), expected);
    }

    @Test
    public void testValidateOnlyOverlappingTrue2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingTrue2.java"), expected);
    }

    @Test
    public void testReceiverParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisReceiver.java"), expected);
    }

    @Test
    public void testBraceAlone() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisBraceAlone.java"), expected);
    }

    @Test
    public void testStatic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisStatic.java"), expected);
    }

    @Test
    public void testMethodReferences() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_VARIABLE, "tags", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisMethodReferences.java"), expected);
    }

    @Test
    public void testAllowLocalVars() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_VARIABLE, "s1", ""),
            "26:9: " + getCheckMessage(MSG_VARIABLE, "s1", ""),
            "39:9: " + getCheckMessage(MSG_VARIABLE, "s2", ""),
            "44:9: " + getCheckMessage(MSG_VARIABLE, "s2", ""),
            "50:9: " + getCheckMessage(MSG_VARIABLE, "s2", ""),
            "51:16: " + getCheckMessage(MSG_VARIABLE, "s1", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisAllowLocalVars.java"), expected);
    }

    @Test
    public void testAllowLambdaParameters() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_VARIABLE, "s1", ""),
            "46:21: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "71:29: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "71:34: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "81:17: " + getCheckMessage(MSG_VARIABLE, "thread", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisAllowLambdaParameters.java"), expected);
    }

    @Test
    public void testTryWithResources() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisTryWithResources.java"), expected);
    }

    @Test
    public void testTryWithResourcesOnlyOverlappingFalse() throws Exception {
        final String[] expected = {
            "44:23: " + getCheckMessage(MSG_VARIABLE, "fldCharset", ""),
            "57:13: " + getCheckMessage(MSG_VARIABLE, "fldCharset", ""),
            "69:45: " + getCheckMessage(MSG_METHOD, "methodToInvoke", ""),
            "77:24: " + getCheckMessage(MSG_METHOD, "methodToInvoke", ""),
            "103:51: " + getCheckMessage(MSG_VARIABLE, "fldBufferedReader", ""),
            "107:23: " + getCheckMessage(MSG_VARIABLE, "fldBufferedReader", ""),
            "107:54: " + getCheckMessage(MSG_VARIABLE, "fldScanner", ""),
            "110:24: " + getCheckMessage(MSG_VARIABLE, "fldStreamReader", ""),
            "111:23: " + getCheckMessage(MSG_VARIABLE, "fldBufferedReader", ""),
            "111:54: " + getCheckMessage(MSG_VARIABLE, "fldScanner", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisTryWithResourcesOnlyOverlappingFalse.java"), expected);
    }

    @Test
    public void testCatchVariables() throws Exception {
        final String[] expected = {
            "38:21: " + getCheckMessage(MSG_VARIABLE, "ex", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisCatchVariables.java"), expected);
    }

    @Test
    public void testEnumConstant() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisEnumConstant.java"), expected);
    }

    @Test
    public void testAnnotationInterface() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisAnnotationInterface.java"), expected);
    }

    @Test
    public void testFor() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_VARIABLE, "bottom", ""),
            "30:32: " + getCheckMessage(MSG_VARIABLE, "name", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisFor.java"), expected);
    }

    @Test
    public void testFinalInstanceVariable() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_VARIABLE, "y", ""),
            "19:9: " + getCheckMessage(MSG_VARIABLE, "z", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisFinalInstanceVariable.java"), expected);
    }

    @Test
    public void test() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisCaseGroup.java"), expected);
    }

    @Test
    public void testExtendedMethod() throws Exception {
        final String[] expected = {
            "31:9: " + getCheckMessage(MSG_VARIABLE, "EXPR", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisExtendedMethod.java"), expected);
    }

    @Test
    public void testRecordsAndCompactCtors() throws Exception {
        final String[] expected = {
            "18:13: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "19:13: " + getCheckMessage(MSG_METHOD, "method2", ""),
            "20:13: " + getCheckMessage(MSG_METHOD, "method3", ""),
            "30:13: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "56:13: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "57:13: " + getCheckMessage(MSG_METHOD, "method2", ""),
            "58:13: " + getCheckMessage(MSG_METHOD, "method3", ""),
            "68:13: " + getCheckMessage(MSG_METHOD, "method1", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testRecordCompactCtors() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordCompactCtors.java"),
                expected);
    }

    @Test
    public void testRecordsAsTopLevel() throws Exception {
        final String[] expected = {
            "17:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "18:9: " + getCheckMessage(MSG_METHOD, "method2", ""),
            "19:9: " + getCheckMessage(MSG_METHOD, "method3", ""),
            "26:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "30:21: " + getCheckMessage(MSG_VARIABLE, "x", ""),
            "38:17: " + getCheckMessage(MSG_VARIABLE, "y", ""),
            "45:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordAsTopLevel.java"),
                expected);
    }

    @Test
    public void testRecordsDefault() throws Exception {
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_VARIABLE, "x", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordDefault.java"),
                expected);
    }

    @Test
    public void testRecordsWithCheckFields() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordsWithCheckFields.java"),
                expected);
    }

    @Test
    public void testRecordsWithCheckFieldsOverlap() throws Exception {
        final String[] expected = {
            "20:20: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "39:20: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "46:16: " + getCheckMessage(MSG_VARIABLE, "a", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordsWithCheckFieldsOverlap.java"),
                expected);
    }

    @Test
    public void testLocalClassesInsideLambdas() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
            getPath("InputRequireThisLocalClassesInsideLambdas.java"),
            expected);
    }

    /**
     * We cannot confirm the type of the private class unless using reflection.
     * Until <a href="https://github.com/checkstyle/checkstyle/issues/12666">#12666</a>.
     *
     * @throws Exception when code tested throws an exception.
     */
    @Test
    public void testUnusedMethodCatch() throws Exception {
        final DetailAstImpl ident = new DetailAstImpl();
        ident.setText("testName");

        final Class<?> cls = Class.forName(RequireThisCheck.class.getName() + "$CatchFrame");
        final Object o = TestUtil.instantiate(cls, null, ident);

        final DetailAstImpl actual = TestUtil.invokeMethod(o,
                "getFrameNameIdent", DetailAstImpl.class);
        assertWithMessage("expected ident token")
            .that(actual)
            .isSameInstanceAs(ident);
        assertWithMessage("expected catch frame type")
            .that(TestUtil.invokeMethod(o, "getType", Object.class).toString())
            .isEqualTo("CATCH_FRAME");
    }

    /**
     * We cannot confirm the type of the private class unless using reflection.
     * Until <a href="https://github.com/checkstyle/checkstyle/issues/12666">#12666</a>.
     *
     * @throws Exception when code tested throws an exception.
     */
    @Test
    public void testUnusedMethodFor() throws Exception {
        final DetailAstImpl ident = new DetailAstImpl();
        ident.setText("testName");

        final Class<?> cls = Class.forName(RequireThisCheck.class.getName() + "$ForFrame");
        final Object o = TestUtil.instantiate(cls, null, ident);

        assertWithMessage("expected for frame type")
            .that(TestUtil.invokeMethod(o, "getType", Object.class).toString())
            .isEqualTo("FOR_FRAME");
    }

    /**
     * We cannot reproduce situation when visitToken is called and leaveToken is not.
     * So, we have to use reflection to be sure that even in such situation
     * state of the field will be cleared.
     *
     * @throws Exception when code tested throws exception
     */
    @Test
    public void testClearState() throws Exception {
        final RequireThisCheck check = new RequireThisCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputRequireThisSimple.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        final Optional<DetailAST> classDef = TestUtil.findTokenInAstByPredicate(root,
            ast -> ast.getType() == TokenTypes.CLASS_DEF);

        assertWithMessage("Ast should contain CLASS_DEF")
                .that(classDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDef.orElseThrow(),
                        "current", current -> ((Collection<?>) current).isEmpty()))
                .isTrue();
    }

}
