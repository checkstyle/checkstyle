///////////////////////////////////////////////////////////////////////////////////////////////
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
    void it() throws Exception {
        final String[] expected = {
            "20:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "26:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "40:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "58:13: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "65:9: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "122:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "123:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "124:9: " + getCheckMessage(MSG_METHOD, "instanceMethod", ""),
            "130:13: " + getCheckMessage(MSG_METHOD, "instanceMethod", "Issue2240."),
            "131:13: " + getCheckMessage(MSG_VARIABLE, "i", "Issue2240."),
            "143:9: " + getCheckMessage(MSG_METHOD, "foo", ""),
            "151:9: " + getCheckMessage(MSG_VARIABLE, "s", ""),
            "177:16: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "177:20: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "177:24: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "183:16: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "183:20: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "183:24: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "211:25: " + getCheckMessage(MSG_VARIABLE, "field", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisEnumInnerClassesAndBugs.java"),
               expected);
    }

    @Test
    void methodsOnly() throws Exception {
        final String[] expected = {
            "25:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "124:9: " + getCheckMessage(MSG_METHOD, "instanceMethod", ""),
            "130:13: " + getCheckMessage(MSG_METHOD, "instanceMethod", "Issue22402."),
            "143:9: " + getCheckMessage(MSG_METHOD, "foo", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisEnumInnerClassesAndBugs2.java"),
               expected);
    }

    @Test
    void fieldsOnly() throws Exception {
        final String[] expected = {
            "19:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "39:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "58:13: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "65:9: " + getCheckMessage(MSG_VARIABLE, "z", ""),
            "122:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "123:9: " + getCheckMessage(MSG_VARIABLE, "i", ""),
            "131:13: " + getCheckMessage(MSG_VARIABLE, "i", "Issue22403."),
            "152:9: " + getCheckMessage(MSG_VARIABLE, "s", ""),
            "179:16: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "179:20: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "179:24: " + getCheckMessage(MSG_VARIABLE, "a", ""),
            "185:16: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "185:20: " + getCheckMessage(MSG_VARIABLE, "b", ""),
            "185:24: " + getCheckMessage(MSG_VARIABLE, "b", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisEnumInnerClassesAndBugs3.java"),
               expected);
    }

    @Test
    void fieldsInExpressions() throws Exception {
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
    void generics() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThis15Extensions.java"), expected);
    }

    @Test
    void githubIssue41() throws Exception {
        final String[] expected = {
            "16:19: " + getCheckMessage(MSG_VARIABLE, "number", ""),
            "17:16: " + getCheckMessage(MSG_METHOD, "other", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisSimple.java"),
                expected);
    }

    @Test
    void tokensNotNull() {
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
    void withAnonymousClass() throws Exception {
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
    void defaultSwitch() {
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
    void validateOnlyOverlappingFalse() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "30:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "31:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "32:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "36:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "37:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "38:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "42:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "46:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "50:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "52:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "54:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "58:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "59:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "69:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "70:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "89:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "128:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "137:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "141:9: " + getCheckMessage(MSG_METHOD, "method1", ""),
            "177:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "178:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "179:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "181:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "185:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal1", ""),
            "186:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal2", ""),
            "187:9: " + getCheckMessage(MSG_VARIABLE, "fieldFinal3", ""),
            "189:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "194:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "198:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "219:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "226:29: " + getCheckMessage(MSG_VARIABLE, "booleanField", ""),
            "237:21: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "247:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "262:9: " + getCheckMessage(MSG_VARIABLE, "booleanField", ""),
            "271:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "279:18: " + getCheckMessage(MSG_METHOD, "addSuf2F", ""),
            "284:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "284:18: " + getCheckMessage(MSG_METHOD, "addSuf2F", ""),
            "310:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "349:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "383:25: " + getCheckMessage(MSG_METHOD, "getAction", ""),
            "385:20: " + getCheckMessage(MSG_METHOD, "processAction", ""),
            "393:16: " + getCheckMessage(MSG_METHOD, "processAction", ""),
            "499:22: " + getCheckMessage(MSG_VARIABLE, "add", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingFalse.java"), expected);
    }

    @Test
    void validateOnlyOverlappingFalseLeaves() throws Exception {
        final String[] expected = {
            "26:31: " + getCheckMessage(MSG_METHOD, "id", ""),
            "36:16: " + getCheckMessage(MSG_VARIABLE, "_a", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingFalseLeaves.java"),
                expected);
    }

    @Test
    void validateOnlyOverlappingTrue() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "52:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "89:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "128:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "181:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "189:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "247:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "262:9: " + getCheckMessage(MSG_VARIABLE, "booleanField", ""),
            "271:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "284:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "310:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
            "348:9: " + getCheckMessage(MSG_VARIABLE, "field1", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingTrue.java"), expected);
    }

    @Test
    void validateOnlyOverlappingTrue2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisValidateOnlyOverlappingTrue2.java"), expected);
    }

    @Test
    void receiverParameter() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisReceiver.java"), expected);
    }

    @Test
    void braceAlone() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisBraceAlone.java"), expected);
    }

    @Test
    void testStatic() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisStatic.java"), expected);
    }

    @Test
    void methodReferences() throws Exception {
        final String[] expected = {
            "24:9: " + getCheckMessage(MSG_VARIABLE, "tags", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisMethodReferences.java"), expected);
    }

    @Test
    void allowLocalVars() throws Exception {
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
    void allowLambdaParameters() throws Exception {
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
    void tryWithResources() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisTryWithResources.java"), expected);
    }

    @Test
    void tryWithResourcesOnlyOverlappingFalse() throws Exception {
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
    void catchVariables() throws Exception {
        final String[] expected = {
            "38:21: " + getCheckMessage(MSG_VARIABLE, "ex", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisCatchVariables.java"), expected);
    }

    @Test
    void enumConstant() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisEnumConstant.java"), expected);
    }

    @Test
    void annotationInterface() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisAnnotationInterface.java"), expected);
    }

    @Test
    void testFor() throws Exception {
        final String[] expected = {
            "22:13: " + getCheckMessage(MSG_VARIABLE, "bottom", ""),
            "30:32: " + getCheckMessage(MSG_VARIABLE, "name", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisFor.java"), expected);
    }

    @Test
    void finalInstanceVariable() throws Exception {
        final String[] expected = {
            "18:9: " + getCheckMessage(MSG_VARIABLE, "y", ""),
            "19:9: " + getCheckMessage(MSG_VARIABLE, "z", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisFinalInstanceVariable.java"), expected);
    }

    @Test
    void test() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisCaseGroup.java"), expected);
    }

    @Test
    void extendedMethod() throws Exception {
        final String[] expected = {
            "31:9: " + getCheckMessage(MSG_VARIABLE, "EXPR", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisExtendedMethod.java"), expected);
    }

    @Test
    void recordsAndCompactCtors() throws Exception {
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
    void recordCompactCtors() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordCompactCtors.java"),
                expected);
    }

    @Test
    void recordsAsTopLevel() throws Exception {
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
    void recordsDefault() throws Exception {
        final String[] expected = {
            "26:9: " + getCheckMessage(MSG_VARIABLE, "x", ""),
        };
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordDefault.java"),
                expected);
    }

    @Test
    void recordsWithCheckFields() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputRequireThisRecordsWithCheckFields.java"),
                expected);
    }

    @Test
    void recordsWithCheckFieldsOverlap() throws Exception {
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
    void localClassesInsideLambdas() throws Exception {
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
    void unusedMethodCatch() throws Exception {
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
    void unusedMethodFor() throws Exception {
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
    void clearState() throws Exception {
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
