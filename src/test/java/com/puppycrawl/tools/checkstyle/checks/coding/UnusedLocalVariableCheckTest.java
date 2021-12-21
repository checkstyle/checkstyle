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
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE;

import java.io.File;
import java.util.Collection;
import java.util.Map;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;

public class UnusedLocalVariableCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
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
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testUnusedLocalVariable() throws Exception {
        final String[] expected = {
            "31:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "sameName"),
            "32:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "35:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "testInLambdas"),
            "37:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "coding"),
            "38:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE,
                    "InputUnusedLocalVariable"),
            "54:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "58:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "66:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "90:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "92:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "c"),
            "96:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "p"),
            "106:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "f"),
            "109:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "foo"),
            "116:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "130:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "i"),
            "133:14: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "145:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "146:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "159:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "162:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "j"),
            "182:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "183:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "Test"),
            "184:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "204:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "218:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariable.java"), expected);
    }

    @Test
    public void testUnusedLocalVarInAnonInnerClasses() throws Exception {
        final String[] expected = {
            "15:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "16:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "b"),
            "18:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "23:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj"),
            "33:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "obj2"),
            "47:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "m"),
            "48:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "l"),
            "60:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "h"),
            "63:17: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "v"),
            "98:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "V"),
            "100:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "S"),
            "101:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "Q"),
            "112:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "variable"),
            "120:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "anotherVariable"),
            "143:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "s"),
            "144:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "n"),
            "206:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "r"),
            "226:21: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "240:13: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "265:9: " + getCheckMessage(MSG_UNUSED_LOCAL_VARIABLE, "variable"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedLocalVariableAnonInnerClasses.java"),
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
        final DetailAST variableDef = methodDef.get().getLastChild()
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
        final DetailAST classDefToken = classDef.get();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDefToken,
                        "classes",
                        classes -> {
                            return ((Collection<?>) classes).isEmpty();
                        }))
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDefToken,
                        "classesWithInstVarAndClassVar",
                        classesWithInstVarAndClassVar -> {
                            return ((Map<?, ?>) classesWithInstVarAndClassVar).isEmpty();
                        }))
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, classDefToken,
                        "classesWithInstVarAndClassVar",
                        classesWithInstVarAndClassVar -> {
                            return ((Map<?, ?>) classesWithInstVarAndClassVar).isEmpty();
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
        check.visitToken(literalNew.get());
        check.beginTree(null);
        Predicate<Object> isClear = outerClassOfAnonInnerClasses -> {
            return ((Map<?, ?>) outerClassOfAnonInnerClasses).isEmpty();
        };
        assertWithMessage("State is not cleared on beginTree")
                .that(isClear.test(TestUtil.getInternalState(
                        check, "outerClassOfAnonInnerClasses")))
                .isTrue();
        Predicate<Object> isQueueClear = anonInnerClassHolders -> {
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
        final DetailAST packageDefToken = packageDef.get();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check, packageDefToken,
                        "packageName",
                        ""::equals))
                .isTrue();
    }
}
