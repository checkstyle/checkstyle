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

package com.puppycrawl.tools.checkstyle.checks;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE;
import static com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck.MSG_INVALID_PATTERN;
import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.DetailAstImpl;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.AuditEvent;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.api.Violation;
import com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.AbstractParenPadCheck;
import com.puppycrawl.tools.checkstyle.checks.whitespace.TypecastParenPadCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class SuppressWarningsHolderTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/suppresswarningsholder";
    }

    @AfterEach
    public void cleanUp() {
        // clear cache that may have been set by tests

        new SuppressWarningsHolder().beginTree(null);

        final Map<String, String> map = TestUtil.getInternalStaticState(
                SuppressWarningsHolder.class, "CHECK_ALIAS_MAP");
        map.clear();
    }

    @Test
    public void testGet() {
        final SuppressWarningsHolder checkObj = new SuppressWarningsHolder();
        final int[] expected = {TokenTypes.ANNOTATION};
        assertWithMessage("Required token array differs from expected")
            .that(checkObj.getRequiredTokens())
            .isEqualTo(expected);
        assertWithMessage("Required token array differs from expected")
            .that(checkObj.getAcceptableTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testOnComplexAnnotations() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("InputSuppressWarningsHolder.java"), expected);
    }

    @Test
    public void testOnComplexAnnotationsNonConstant() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsHolderNonConstant.java"), expected);
    }

    @Test
    public void testCustomAnnotation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("InputSuppressWarningsHolder5.java"), expected);
    }

    @Test
    public void testAll() throws Exception {
        final String[] expected = {
            "21:23: "
                    + getCheckMessage(TypecastParenPadCheck.class,
                            AbstractParenPadCheck.MSG_WS_NOT_PRECEDED, ")"),
        };

        verifyWithInlineConfigParser(getPath("InputSuppressWarningsHolder6.java"), expected);
    }

    @Test
    public void testGetDefaultAlias() {
        assertWithMessage("Default alias differs from expected")
            .that(SuppressWarningsHolder.getDefaultAlias("SomeName"))
            .isEqualTo("somename");
        assertWithMessage("Default alias differs from expected")
            .that(SuppressWarningsHolder.getDefaultAlias("SomeNameCheck"))
            .isEqualTo("somename");
    }

    @Test
    public void testSetAliasListEmpty() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        holder.setAliasList("");
        assertWithMessage("Empty alias list should not be set")
            .that(SuppressWarningsHolder.getAlias(""))
            .isEqualTo("");
    }

    @Test
    public void testSetAliasListCorrect() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        holder.setAliasList("alias=value");
        assertWithMessage("Alias differs from expected")
            .that(SuppressWarningsHolder.getAlias("alias"))
            .isEqualTo("value");
    }

    @Test
    public void testSetAliasListWrong() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();

        try {
            holder.setAliasList("=SomeAlias");
            assertWithMessage("Exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("'=' expected in alias list item: =SomeAlias");
        }
    }

    @Test
    public void testAliasCombo() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineXmlConfig(getPath("InputSuppressWarningsHolderAlias3.java"), expected);
    }

    @Test
    public void testIsSuppressed() throws Exception {
        populateHolder("MockEntry", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 10);

        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(event))
                .isFalse();
    }

    @Test
    public void testIsSuppressedByName() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final AuditEvent event = createAuditEvent("id", 110, 10);
        holder.setAliasList(MemberNameCheck.class.getName() + "=check");

        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(event))
                .isTrue();
    }

    @Test
    public void testIsSuppressedByModuleId() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 350, 350);

        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(event))
                .isTrue();
    }

    @Test
    public void testIsSuppressedAfterEventEnd() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 350, 352);

        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(event))
                .isFalse();
    }

    @Test
    public void testIsSuppressedAfterEventEnd2() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 400, 10);

        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(event))
                .isFalse();
    }

    @Test
    public void testIsSuppressedAfterEventStart() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 100);

        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(event))
                .isTrue();
    }

    @Test
    public void testIsSuppressedAfterEventStart2() throws Exception {
        populateHolder("check", 100, 100, 350, 350);
        final AuditEvent event = createAuditEvent("check", 100, 0);

        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(event))
                .isTrue();
    }

    @Test
    public void testIsSuppressedWithAllArgument() throws Exception {
        populateHolder("all", 100, 100, 350, 350);

        final Checker source = new Checker();
        final Violation firstViolationForTest =
            new Violation(100, 10, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent firstEventForTest =
            new AuditEvent(source, "fileName", firstViolationForTest);
        assertWithMessage("Event is suppressed")
                .that(SuppressWarningsHolder.isSuppressed(firstEventForTest))
                .isFalse();

        final Violation secondViolationForTest =
            new Violation(100, 150, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent secondEventForTest =
            new AuditEvent(source, "fileName", secondViolationForTest);
        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(secondEventForTest))
                .isTrue();

        final Violation thirdViolationForTest =
            new Violation(200, 1, null, null, null, "id", MemberNameCheck.class, "msg");
        final AuditEvent thirdEventForTest =
            new AuditEvent(source, "fileName", thirdViolationForTest);
        assertWithMessage("Event is not suppressed")
                .that(SuppressWarningsHolder.isSuppressed(thirdEventForTest))
                .isTrue();
    }

    @Test
    public void testAnnotationInTry() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("InputSuppressWarningsHolder2.java"), expected);
    }

    @Test
    public void testEmptyAnnotation() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("InputSuppressWarningsHolder3.java"), expected);
    }

    @Test
    public void testGetAllAnnotationValuesWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAllAnnotationValues = holder.getClass()
                .getDeclaredMethod("getAllAnnotationValues", DetailAST.class);
        getAllAnnotationValues.setAccessible(true);

        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");
        methodDef.setLineNo(0);
        methodDef.setColumnNo(0);

        final DetailAstImpl lparen = new DetailAstImpl();
        lparen.setType(TokenTypes.LPAREN);

        final DetailAstImpl parent = new DetailAstImpl();
        parent.addChild(lparen);
        parent.addChild(methodDef);

        try {
            getAllAnnotationValues.invoke(holder, parent);
            assertWithMessage("Exception expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Error type is unexpected")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(IllegalArgumentException.class);
            assertWithMessage("Error message is unexpected")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("Unexpected AST: Method Def[0x0]");
        }
    }

    @Test
    public void testGetAnnotationValuesWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAllAnnotationValues = holder.getClass()
                .getDeclaredMethod("getAnnotationValues", DetailAST.class);
        getAllAnnotationValues.setAccessible(true);

        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");
        methodDef.setLineNo(0);
        methodDef.setColumnNo(0);

        try {
            getAllAnnotationValues.invoke(holder, methodDef);
            assertWithMessage("Exception expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Error type is unexpected")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(IllegalArgumentException.class);
            assertWithMessage("Error message is unexpected")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("Expression or annotation array initializer AST expected: "
                        + "Method Def[0x0]");
        }
    }

    @Test
    public void testGetAnnotationTargetWrongArg() throws ReflectiveOperationException {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final Method getAnnotationTarget = holder.getClass()
                .getDeclaredMethod("getAnnotationTarget", DetailAST.class);
        getAnnotationTarget.setAccessible(true);

        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);
        methodDef.setText("Method Def");

        final DetailAstImpl parent = new DetailAstImpl();
        parent.setType(TokenTypes.ASSIGN);
        parent.setText("Parent ast");
        parent.addChild(methodDef);
        parent.setLineNo(0);
        parent.setColumnNo(0);

        try {
            getAnnotationTarget.invoke(holder, methodDef);
            assertWithMessage("Exception expected").fail();
        }
        catch (ReflectiveOperationException ex) {
            assertWithMessage("Error type is unexpected")
                    .that(ex)
                    .hasCauseThat()
                    .isInstanceOf(IllegalArgumentException.class);
            assertWithMessage("Error message is unexpected")
                .that(ex)
                .hasCauseThat()
                .hasMessageThat()
                .isEqualTo("Unexpected container AST: Parent ast[0x0]");
        }
    }

    @Test
    public void testAstWithoutChildren() {
        final SuppressWarningsHolder holder = new SuppressWarningsHolder();
        final DetailAstImpl methodDef = new DetailAstImpl();
        methodDef.setType(TokenTypes.METHOD_DEF);

        try {
            holder.visitToken(methodDef);
            assertWithMessage("Exception expected").fail();
        }
        catch (IllegalArgumentException ex) {
            assertWithMessage("Error message is unexpected")
                .that(ex.getMessage())
                .isEqualTo("Identifier AST expected, but get null.");
        }
    }

    @Test
    public void testAnnotationWithFullName() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("InputSuppressWarningsHolder4.java"), expected);
    }

    @Test
    public void testSuppressWarningsAsAnnotationProperty() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getPath("InputSuppressWarningsHolder7.java"), expected);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testClearState() throws Exception {
        final SuppressWarningsHolder check = new SuppressWarningsHolder();

        final Optional<DetailAST> annotationDef = TestUtil.findTokenInAstByPredicate(
                JavaParser.parseFile(
                    new File(getPath("InputSuppressWarningsHolder.java")),
                    JavaParser.Options.WITHOUT_COMMENTS),
            ast -> ast.getType() == TokenTypes.ANNOTATION);

        assertWithMessage("Ast should contain ANNOTATION")
                .that(annotationDef.isPresent())
                .isTrue();
        assertWithMessage("State is not cleared on beginTree")
                .that(TestUtil.isStatefulFieldClearedDuringBeginTree(check,
                        annotationDef.orElseThrow(), "ENTRIES",
                        entries -> ((ThreadLocal<List<Object>>) entries).get().isEmpty()))
                .isTrue();
    }

    private static void populateHolder(String checkName, int firstLine,
                                                         int firstColumn, int lastLine,
                                                         int lastColumn) throws Exception {
        final Class<?> entry = Class
                .forName("com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder$Entry");
        final Constructor<?> entryConstr = entry.getDeclaredConstructor(String.class, int.class,
                int.class, int.class, int.class);
        entryConstr.setAccessible(true);

        final Object entryInstance = entryConstr.newInstance(checkName, firstLine,
                firstColumn, lastLine, lastColumn);

        final ThreadLocal<List<Object>> entries = TestUtil
                .getInternalStaticState(SuppressWarningsHolder.class, "ENTRIES");
        entries.get().add(entryInstance);
    }

    private static AuditEvent createAuditEvent(String moduleId, int line, int column) {
        final Checker source = new Checker();
        final Violation violation = new Violation(line, column, null, null, null,
                moduleId, MemberNameCheck.class, "violation");
        return new AuditEvent(source, "filename", violation);
    }

    @Test
    public void testSuppressWarningsTextBlocks() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "31:12: " + getCheckMessage(MemberNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "STRING3", pattern),
            "33:12: " + getCheckMessage(MemberNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "STRING4", pattern),
            "61:12: " + getCheckMessage(MemberNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "STRING8", pattern),
            };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsHolderTextBlocks.java"), expected);

    }

    @Test
    public void testWithAndWithoutCheckSuffixDifferentCases() throws Exception {
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";
        final String[] expected = {
            "16:30: " + getCheckMessage(ConstantNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "a", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolderWithAndWithoutCheckSuffixDifferentCases.java"),
                expected);
    }

    @Test
    public void testAliasList() throws Exception {
        final String[] expected = {
            "16:17: " + getCheckMessage(ParameterNumberCheck.class,
                    ParameterNumberCheck.MSG_KEY, 7, 8),
            "28:17: " + getCheckMessage(ParameterNumberCheck.class,
                    ParameterNumberCheck.MSG_KEY, 7, 8),
        };
        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolderAlias.java"),
                expected);
    }

    @Test
    public void testAliasList2() throws Exception {
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";
        final String[] expected = {
            "16:29: " + getCheckMessage(ConstantNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "a", pattern),
            "19:30: " + getCheckMessage(ConstantNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "b", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolderAlias2.java"),
                expected);
    }

    @Test
    public void testAliasList3() throws Exception {
        final String[] expected = {
            "16:17: " + getCheckMessage(ParameterNumberCheck.class,
                    ParameterNumberCheck.MSG_KEY, 7, 8),
            "28:17: " + getCheckMessage(ParameterNumberCheck.class,
                    ParameterNumberCheck.MSG_KEY, 7, 8),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolderAlias5.java"),
                expected);
    }

    @Test
    public void testAliasList4() throws Exception {
        final String pattern = "^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$";
        final String[] expected = {
            "16:30: " + getCheckMessage(ConstantNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "a", pattern),
            "19:30: " + getCheckMessage(ConstantNameCheck.class,
                AbstractNameCheck.MSG_INVALID_PATTERN, "b", pattern),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolderAlias4.java"),
                expected);
    }

    @Test
    public void testAliasList5() throws Exception {
        final String[] expected = {
            "18: " + getCheckMessage(LineLengthCheck.class, MSG_KEY, 80, 83),
            "28: " + getCheckMessage(LineLengthCheck.class, MSG_KEY, 75, 96),
            "28: " + getCheckMessage(LineLengthCheck.class, MSG_KEY, 80, 96),
            "58: " + getCheckMessage(LineLengthCheck.class, MSG_KEY, 75, 76),
            "65: " + getCheckMessage(LineLengthCheck.class, MSG_KEY, 75, 87),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolderAlias6.java"),
                expected);
    }

    @Test
    public void testAliasList6() throws Exception {
        final String pattern1 = "^[a-z][a-zA-Z0-9]*$";
        final String pattern2 = "^[A-Z][a-zA-Z0-9]*$";

        final String[] expected = {
            "25:18: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Method3", pattern1),
            "30:20: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "Method5", pattern1),
            "35:17: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "method7", pattern2),
            "40:18: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "method9", pattern2),
            "45:20: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "method11", pattern2),
            "47:17: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "_methodCheck1", pattern2),
            "53:18: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "_methodCheck3", pattern2),
            "53:18: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "_methodCheck3", pattern1),
            "61:20: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "_methodCheck5", pattern2),
            "61:20: " + getCheckMessage(MethodNameCheck.class, MSG_INVALID_PATTERN,
                    "_methodCheck5", pattern1),
        };

        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolderAlias7.java"),
                expected);
    }

    @Test
    public void testIdent() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsHolder1.java"),
                expected);
    }

    @Test
    public void testIdent2() throws Exception {
        final String[] expected = {
            "37:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                    MSG_UNUSED_LOCAL_VARIABLE, "a"),
            "42:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                    MSG_UNUSED_LOCAL_VARIABLE, "a"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputSuppressWarningsHolder2.java"),
                expected);
    }

    @Test
    public void test3() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expected = {
            "18:16: " + getCheckMessage(MemberNameCheck.class,
                    AbstractNameCheck.MSG_INVALID_PATTERN, "K", pattern),
        };
        verifyWithInlineConfigParser(
                getPath("InputSuppressWarningsHolder8.java"),
                expected);
    }
}
