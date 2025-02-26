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

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_NAMED_LOCAL_VARIABLE;

public class UnusedPrivateMethodTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedprivatemethod";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedLocalVariableCheck checkObj =
                new UnusedLocalVariableCheck();
        final int[] actual = checkObj.getRequiredTokens();
        final int[] expected = {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_FOR,
            TokenTypes.OBJBLOCK,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.COMPILATION_UNIT,
            TokenTypes.LAMBDA,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertWithMessage("Required tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final UnusedLocalVariableCheck typeParameterNameCheckObj =
                new UnusedLocalVariableCheck();
        final int[] actual = typeParameterNameCheckObj.getAcceptableTokens();
        final int[] expected = {
            TokenTypes.DOT,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
            TokenTypes.SLIST,
            TokenTypes.LITERAL_FOR,
            TokenTypes.OBJBLOCK,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ANNOTATION_DEF,
            TokenTypes.PACKAGE_DEF,
            TokenTypes.LITERAL_NEW,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
            TokenTypes.STATIC_INIT,
            TokenTypes.INSTANCE_INIT,
            TokenTypes.COMPILATION_UNIT,
            TokenTypes.LAMBDA,
            TokenTypes.ENUM_DEF,
            TokenTypes.RECORD_DEF,
            TokenTypes.COMPACT_CTOR_DEF,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testUnusedLocalVariable() throws Exception {
        final String[] expected = {
            "27:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "sameName"),
            "28:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "31:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "testInLambdas"),
            "33:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "coding"),
            "34:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE,
                    "InputUnusedLocalVariable"),
            "50:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "54:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "65:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "67:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "71:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p"),
            "81:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "f"),
            "84:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "foo"),
            "91:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable.java"), expected);
    }

    @Test
    public void testUnusedLocalVar2() throws Exception {
        final String[] expected = {
            "17:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "i"),
            "19:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "30:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "31:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "39:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "40:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "Test"),
            "41:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "61:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "76:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVar3() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable3.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarInAnonInnerClasses() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "15:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "17:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "22:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "32:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj2"),
            "46:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "m"),
            "47:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "l"),
            "59:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "h"),
            "62:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "v"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAnonInnerClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarGenericAnonInnerClasses() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "l"),
            "14:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "33:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "l"),
            "34:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj2"),
            "47:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "67:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "variable"),
            "69:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "anotherVar"),
            "78:47: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableGenericAnonInnerClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarDepthOfClasses() throws Exception {
        final String[] expected = {
            "28:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "r"),
            "49:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "64:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "94:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "variable"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableDepthOfClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "V"),
            "23:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "S"),
            "24:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "Q"),
            "36:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "variable"),
            "44:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "anotherVariable"),
            "67:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "68:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "n"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses2() throws Exception {
        final String[] expected = {
            "29:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "q"),
            "30:51: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "46:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "57:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "108:33: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses3() throws Exception {
        final String[] expected = {
            "36:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p2"),
            "54:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "o"),
            "93:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "95:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses3.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses4() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "13:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses4.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses5() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "13:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
            "19:11: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "abc"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses5.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses6() throws Exception {
        final String[] expected = {
            "12:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "13:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses6.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses7() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "11:5: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
            "16:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
            "23:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "24:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
            "28:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses7.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarTestWarningSeverity() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableTestWarningSeverity.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarEnum() throws Exception {
        final String[] expected = {
            "22:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "50:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "77:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "80:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "92:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "d"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableEnum.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarLambdas() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "hoo"),
            "19:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "29:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "hoo2"),
            "30:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "hoo3"),
            "32:15: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "myComponent"),
            "34:19: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "myComponent3"),
            "40:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "52:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "65:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ja"),
            "73:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "k"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableLambdaExpression.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableLocalClasses() throws Exception {
        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "15:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableLocalClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarRecords() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var1"),
            "25:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var1"),
            "26:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "36:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var2"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableRecords.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarWithoutPackageStatement() throws Exception {
        final String[] expected = {
            "12:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "24:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var2"),
            "45:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var3"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableWithoutPackageStatement.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableTernaryAndExpressions() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableTernaryAndExpressions.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableSwitchStatement() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableSwitchStatement.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableSwitchStatement2() throws Exception {
        final String[] expected = {
            "59:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableSwitchStatement2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableSwitchExpression() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "line2"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableSwitchExpression.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableWithAllowUnnamed() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "_x"),
            "21:13: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "__"),
            "35:14: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "__"),
            "45:14: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "__"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableWithAllowUnnamed.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableWithAllowUnnamedFalse() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_x"),
            "21:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "__"),
            "22:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_"),
            "32:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_"),
            "35:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "__"),
            "43:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_"),
            "46:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "__"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableWithAllowUnnamedFalse.java"),
                expected);
    }

    @Test
    public void testClearStateVariables() throws Exception {
        final UnusedLocalVariableCheck check = new UnusedLocalVariableCheck();
        final Optional<DetailAST> methodDef = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                        new File(getPath("InputUnusedLocalVariable.java")),
                        JavaParser.Options.WITHOUT_COMMENTS),
                ast -> ast.getType() == TokenTypes.METHOD_DEF);
        assertWithMessage("Ast should contain METHOD_DEF")
                .that(methodDef.isPresent())
                .isTrue();
        final DetailAST variableDef = methodDef.orElseThrow().getLastChild()
                .findFirstToken(TokenTypes.VARIABLE_DEF);
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, variableDef,
                        "variables",
                        variables -> {
                            return ((Collection<?>) variables).isEmpty();
                        }))
                .isTrue();
    }

    @Test
    public void testClearStateClasses() throws Exception {
        final UnusedLocalVariableCheck check = new UnusedLocalVariableCheck();
        final Optional<DetailAST> classDef = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                        new File(getPath("InputUnusedLocalVariable.java")),
                        JavaParser.Options.WITHOUT_COMMENTS),
                ast -> ast.getType() == TokenTypes.CLASS_DEF);
        assertWithMessage("Ast should contain CLASS_DEF")
                .that(classDef.isPresent())
                .isTrue();
        final DetailAST classDefToken = classDef.orElseThrow();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDefToken,
                        "typeDeclarations",
                        typeDeclarations -> {
                            return ((Collection<?>) typeDeclarations).isEmpty();
                        }))
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDefToken,
                        "typeDeclAstToTypeDeclDesc",
                        typeDeclAstToTypeDeclDesc -> {
                            return ((Map<?, ?>) typeDeclAstToTypeDeclDesc).isEmpty();
                        }))
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDefToken,
                        "depth",
                        depth -> {
                            return (int) depth == 0;
                        }))
                .isTrue();
    }

    @Test
    public void testClearStateAnonInnerClass() throws Exception {
        final UnusedLocalVariableCheck check = new UnusedLocalVariableCheck();
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputUnusedLocalVariableAnonInnerClasses.java")),
                JavaParser.Options.WITHOUT_COMMENTS);
        // Not using TestUtil.isStatefulFieldClearedDuringBeginTree(..) because other
        // nodes need to be processed first as those collections, maps are dependent on them.
        final DetailAST classDefAst = root.findFirstToken(TokenTypes.CLASS_DEF);
        final Optional<DetailAST> literalNew = TestUtil.findTokenInAstByPredicate(root,
                ast -> ast.getType() == TokenTypes.LITERAL_NEW);
        assertWithMessage("Ast should contain LITERAL_NEW")
                .that(literalNew.isPresent())
                .isTrue();
        check.beginTree(root);
        check.visitToken(classDefAst);
        check.visitToken(literalNew.orElseThrow());
        check.beginTree(null);
        final Predicate<Object> isClear = anonInnerAstToTypeDesc -> {
            return ((Map<?, ?>) anonInnerAstToTypeDesc).isEmpty();
        };
        assertWithMessage("State is not cleared on beginTree")
                .that(isClear.test(TestUtil.getInternalState(
                        check, "anonInnerAstToTypeDeclDesc")))
                .isTrue();
        final Predicate<Object> isQueueClear = anonInnerClassHolders -> {
            return ((Collection<?>) anonInnerClassHolders).isEmpty();
        };
        assertWithMessage("State is not cleared on beginTree")
                .that(isQueueClear.test(TestUtil.getInternalState(
                        check, "anonInnerClassHolders")))
                .isTrue();
    }

    @Test
    public void testClearStatePackageDef() throws Exception {
        final UnusedLocalVariableCheck check = new UnusedLocalVariableCheck();
        final Optional<DetailAST> packageDef = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                        new File(getPath("InputUnusedLocalVariable.java")),
                        JavaParser.Options.WITHOUT_COMMENTS),
                ast -> ast.getType() == TokenTypes.PACKAGE_DEF);
        assertWithMessage("Ast should contain PACKAGE_DEF")
                .that(packageDef.isPresent())
                .isTrue();
        final DetailAST packageDefToken = packageDef.orElseThrow();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, packageDefToken,
                        "packageName",
                        Objects::isNull))
                .isTrue();
    }

    @Test
    public void testUnusedLocalVarInAnonInnerClasses2() throws Exception {
        final String[] expected = {
            "20:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAnonInnerClasses2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarInAnonInnerClasses3() throws Exception {
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "20:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "32:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAnonInnerClasses3.java"),
                expected);
    }
}
