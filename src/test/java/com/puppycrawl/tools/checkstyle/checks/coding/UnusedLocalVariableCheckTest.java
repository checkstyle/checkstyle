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
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_NAMED_LOCAL_VARIABLE;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UnusedLocalVariableCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedlocalvariable";
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
            TokenTypes.PATTERN_VARIABLE_DEF,
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
            TokenTypes.PATTERN_VARIABLE_DEF,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testUnusedLocalVariable() throws Exception {
        final String[] expected = {
            "28:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "sameName"),
            "29:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "32:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "testInLambdas"),
            "34:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "coding"),
            "35:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE,
                    "InputUnusedLocalVariable"),
            "51:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "55:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "66:24: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "68:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "72:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p"),
            "82:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "f"),
            "85:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "foo"),
            "92:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable.java"), expected);
    }

    @Test
    public void testUnusedLocalVar2() throws Exception {
        final String[] expected = {
            "18:18: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "i"),
            "20:18: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "31:16: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "32:16: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "40:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "41:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "Test"),
            "42:15: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "62:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "77:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVar3() throws Exception {
        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable3.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarInAnonInnerClasses() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "16:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "18:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "23:22: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "33:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj2"),
            "47:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "m"),
            "48:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "l"),
            "60:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "h"),
            "63:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "v"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAnonInnerClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarGenericAnonInnerClasses() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "l"),
            "16:19: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "35:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "l"),
            "36:23: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj2"),
            "49:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "69:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "variable"),
            "71:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "anotherVar"),
            "80:55: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableGenericAnonInnerClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarDepthOfClasses() throws Exception {
        final String[] expected = {
            "29:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "r"),
            "50:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "65:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "95:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "variable"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableDepthOfClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses() throws Exception {
        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "V"),
            "24:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "S"),
            "25:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "Q"),
            "37:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "variable"),
            "45:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "anotherVariable"),
            "68:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "69:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "n"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses2() throws Exception {
        final String[] expected = {
            "30:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "q"),
            "31:54: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "47:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "58:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "109:37: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses3() throws Exception {
        final String[] expected = {
            "37:27: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p2"),
            "55:20: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "o"),
            "94:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "96:20: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses3.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses4() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "14:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses4.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses5() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "14:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
            "20:15: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "abc"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses5.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses6() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "14:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses6.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarNestedClasses7() throws Exception {
        final String[] expected = {
            "11:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "12:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
            "17:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
            "24:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "25:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
            "29:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableNestedClasses7.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarTestWarningSeverity() throws Exception {
        final String[] expected = {
            "16:34: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p2"),
        };

        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableTestWarningSeverity.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarEnum() throws Exception {
        final String[] expected = {
            "23:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "51:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "78:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "81:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "93:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "d"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableEnum.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarLambdas() throws Exception {
        final String[] expected = {
            "15:16: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "hoo"),
            "20:24: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "30:16: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "hoo2"),
            "31:16: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "hoo3"),
            "33:34: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "myComponent"),
            "35:38: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "myComponent3"),
            "41:32: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "53:28: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "66:24: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ja"),
            "74:16: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "k"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableLambdaExpression.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableLocalClasses() throws Exception {
        final String[] expected = {
            "15:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "16:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ab"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableLocalClasses.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarRecords() throws Exception {
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var1"),
            "26:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var1"),
            "27:16: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "37:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableRecords.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarWithoutPackageStatement() throws Exception {
        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "25:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var2"),
            "46:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "var3"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableWithoutPackageStatement.java"),
                expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = {
            "13:9: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "unused"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableCompactSourceFile.java"),
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
            "61:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableSwitchStatement2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableSwitchExpression() throws Exception {
        final String[] expected = {
            "17:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "line2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableSwitchExpression.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableWithAllowUnnamed() throws Exception {
        final String[] expected = {
            "22:17: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "_x"),
            "23:17: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "__"),
            "37:22: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "__"),
            "47:25: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "__"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableWithAllowUnnamed.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableWithAllowUnnamedFalse() throws Exception {
        final String[] expected = {
            "21:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_x"),
            "22:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "__"),
            "23:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_"),
            "33:22: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_"),
            "36:22: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "__"),
            "44:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "_"),
            "47:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "__"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputUnusedLocalVariableWithAllowUnnamedFalse.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariablePatternVariablesCondition() throws Exception {
        final String[] expected = {
            "20:37: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "redBall"),
            "22:46: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "greenBall"),
            "41:39: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "redBall"),
            "55:42: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "redBall"),
            "61:40: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "redBall"),
            "63:49: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "greenBall"),
            "81:30: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "lr"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariablePatternVariablesCondition.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariablePatternVariablesCondition2() throws Exception {
        final String[] expected = {
            "24:68: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "lr"),
            "44:33: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "59:34: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s1"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariablePatternVariablesCondition2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableNamedPatternVariable() throws Exception {
        final String[] expected = {
            "14:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ignored"),
            "15:26: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ignored2"),
            "21:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "x"),
            "22:38: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ignored"),
            "29:30: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "y"),
            "29:37: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "z"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAllowNamedPatternVariables.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableNamedPatternVariableTrue() throws Exception {
        final String[] expected = {
            "21:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "x"),
            "22:38: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "ignored"),
            "30:46: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAllowNamedPatternVariablesTrue.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariableNamedPatternVariableLegacyJdkFormat() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableJdkVersionLegacyFormat.java"),
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
                        check, "anonInnerAstToTypeDeclDesc", Object.class)))
                .isTrue();
        final Predicate<Object> isQueueClear = anonInnerClassHolders -> {
            return ((Collection<?>) anonInnerClassHolders).isEmpty();
        };
        assertWithMessage("State is not cleared on beginTree")
                .that(isQueueClear.test(TestUtil.getInternalState(
                        check, "anonInnerClassHolders", Object.class)))
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
            "21:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAnonInnerClasses2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVarInAnonInnerClasses3() throws Exception {
        final String[] expected = {
            "14:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "21:15: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "33:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAnonInnerClasses3.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariablePatternVariables() throws Exception {
        final String[] expected = {
            "19:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "20:28: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "r"),
            "43:29: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "44:32: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "r"),
        };
        verifyWithInlineConfigParser(
                getPath(
                    "InputUnusedLocalVariablePatternVariables.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariablePatternVariables2() throws Exception {
        final String[] expected = {
            "14:26: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "i"),
            "28:25: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "47:35: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath(
                    "InputUnusedLocalVariablePatternVariables2.java"),
                expected);
    }

    @Test
    public void testUnusedLocalVariablePatternVariablesAllowUnnamed() throws Exception {
        final String[] expected = {
            "19:28: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "c"),
            "20:28: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "r"),
            "30:32: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "c"),
            "31:32: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "r"),
            "46:35: " + getCheckMessage(MSG_UNUSED_NAMED_LOCAL_VARIABLE, "s"),
        };
        verifyWithInlineConfigParser(
                getPath(
                    "InputUnusedLocalVariablePatternVariablesAllowUnnamed.java"),
                expected);
    }

    /**
     * Use TestUtil.invokeStaticMethod to access the private static method
     * isInsideLocalAnonInnerClass to test this because it produces optimization
     * mutations that we can not kill using verifyWithInlineConfigParser.
     */
    @Test
    public void testIsInsideLocalAnonInnerClass() throws Exception {
        final DetailAST root = JavaParser.parseFile(
                new File(getPath("InputUnusedLocalVariableLambdaAnonInner.java")),
                JavaParser.Options.WITHOUT_COMMENTS);

        final DetailAST fieldObj = TestUtil.findTokenInAstByPredicate(root,
                        ast -> {
                            return ast.getType() == TokenTypes.VARIABLE_DEF
                                    && "fieldObj".equals(ast.findFirstToken(TokenTypes.IDENT)
                                    .getText());
                        })
                .orElseThrow();
        final DetailAST literalNewField = fieldObj.findFirstToken(TokenTypes.ASSIGN)
                .findFirstToken(TokenTypes.EXPR)
                .findFirstToken(TokenTypes.LITERAL_NEW);

        final boolean resultFalse = TestUtil.invokeStaticMethod(
                UnusedLocalVariableCheck.class,
                "isInsideLocalAnonInnerClass",
                Boolean.class,
                literalNewField);

        assertWithMessage("Should be false for field initialization (no SLIST in ancestry)")
                .that(resultFalse)
                .isFalse();

        final DetailAST localObj = TestUtil.findTokenInAstByPredicate(root,
                        ast -> {
                            return ast.getType() == TokenTypes.VARIABLE_DEF
                                    && "localObj".equals(ast.findFirstToken(TokenTypes.IDENT)
                                    .getText());
                        })
                .orElseThrow();
        final DetailAST literalNewLocal = localObj.findFirstToken(TokenTypes.ASSIGN)
                .findFirstToken(TokenTypes.EXPR)
                .findFirstToken(TokenTypes.LITERAL_NEW);

        final boolean resultTrue = TestUtil.invokeStaticMethod(
                UnusedLocalVariableCheck.class,
                "isInsideLocalAnonInnerClass",
                Boolean.class,
                literalNewLocal);

        assertWithMessage("Should be true for local variable initialization")
                .that(resultTrue)
                .isTrue();
    }
}
