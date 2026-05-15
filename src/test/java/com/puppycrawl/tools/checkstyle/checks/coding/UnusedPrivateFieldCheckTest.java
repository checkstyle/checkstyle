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
import static com.puppycrawl.tools.checkstyle.checks.coding.UnusedPrivateFieldCheck.MSG_PRIVATE_FIELD;

import java.util.AbstractMap;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.TestUtil;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class UnusedPrivateFieldCheckTest extends AbstractModuleTestSupport {
    @Override
    public String getPackageLocation() {

        return "com/puppycrawl/tools/checkstyle/checks/coding/unusedprivatefield";
    }

    @Test
    public void testGetRequiredTokens() {
        final UnusedPrivateFieldCheck checkObj =
                new UnusedPrivateFieldCheck();
        final int[] actual = checkObj.getRequiredTokens();

        final int[] expected = {
            TokenTypes.OBJBLOCK,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.PARAMETERS,
            TokenTypes.SLIST,
            TokenTypes.IDENT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
        assertWithMessage("Required tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final UnusedPrivateFieldCheck typeParameterNameCheckObj =
                new UnusedPrivateFieldCheck();
        final int[] actual = typeParameterNameCheckObj.getAcceptableTokens();

        final int[] expected = {
            TokenTypes.OBJBLOCK,
            TokenTypes.VARIABLE_DEF,
            TokenTypes.PARAMETER_DEF,
            TokenTypes.PARAMETERS,
            TokenTypes.SLIST,
            TokenTypes.IDENT,
            TokenTypes.METHOD_DEF,
            TokenTypes.CTOR_DEF,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testUnusedprivatefield() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "unused"),
            "24:9: " + getCheckMessage(MSG_PRIVATE_FIELD, "innerUnused"),
            "29:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "field"),
            "31:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "field2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateField.java"), expected);
    }

    @Test
    public void testUnusedprivatefieldnames() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "value"),
            "27:9: " + getCheckMessage(MSG_PRIVATE_FIELD, "data"),
            "53:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "i"),

        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldNameConflict.java"), expected);
    }

    @Test
    public void testUnusedprivatefieldScope() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "count"),
            "24:9: " + getCheckMessage(MSG_PRIVATE_FIELD, "size"),
            "29:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "flag"),
            "40:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "limit"),
            "42:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "index"),

        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldScopeEdgeCases.java"), expected);
    }

    @Test
    public void testUnusedprivatefield2() throws Exception {
        final String[] expected = {
            "10:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "unused"),
            "12:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "anotherUnused"),
            "14:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "yetAnother"),
            "23:9: " + getCheckMessage(MSG_PRIVATE_FIELD, "field"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateField2.java"), expected);
    }

    @Test
    public void testUnusedprivatefieldthis() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "value"),
            "28:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "count"),
            "41:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "flag"),
            "49:9: " + getCheckMessage(MSG_PRIVATE_FIELD, "data"),
            "58:9: " + getCheckMessage(MSG_PRIVATE_FIELD, "size"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldThisAccess.java"), expected);
    }

    @Test
    public void testUnusedprivatefieldString() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputUnusedPrivateFieldEmptyString.java"),
                expected);
    }

    @Test
    public void testUnusedprivatefieldNested() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldNestedAccess.java"), expected);
    }

    @Test
    public void testUnusedprivatefieldNestedScopes() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldNestedScopes.java"), expected);
    }

    @Test
    public void testUnusedprivateFieldMethodDef() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldMethodDef.java"), expected);
    }

    @Test
    public void testUnusedprivatefield3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateField3.java"), expected);
    }

    @Test
    public void testUsedFieldBranch() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateField4.java"), expected);
    }

    @Test
    public void testStateClearedWithinSingleFile() throws Exception {
        final String[] expected = {
            "9:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "a"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldState.java"), expected);
    }

    @Test
    public void testStateClearedBetweenFiles() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateFieldFirst.java"),
                getPath("InputUnusedPrivateFieldTwo.java"), expected
        );
    }

    @Test
    public void testClearStateVariables() {
        final UnusedPrivateFieldCheck check = new UnusedPrivateFieldCheck();
        final Deque<Set<String>> usedFieldsStack = new ArrayDeque<>();
        usedFieldsStack.push(new HashSet<>());
        TestUtil.setInternalState(check, "usedFieldsStack", usedFieldsStack);
        check.beginTree(null);
        final Deque<?> clearedStack = (Deque<?>) TestUtil.getInternalState(check,
                "usedFieldsStack", Deque.class);
        assertWithMessage("usedFieldsStack state is not cleared on beginTree")
                .that(clearedStack)
                .isEmpty();
    }

    @Test
    public void testClearStatePrivateFieldsStack() {
        final UnusedPrivateFieldCheck check = new UnusedPrivateFieldCheck();

        final Deque<Map<String, DetailAST>> stack = new ArrayDeque<>();
        stack.push(new HashMap<>());
        TestUtil.setInternalState(check, "privateFields", stack);
        check.beginTree(null);

        final Deque<?> privateFieldsStack = (Deque<?>) TestUtil.getInternalState(check,
            "privateFields", Deque.class);
        assertWithMessage("privateFields state is not cleared on beginTree")
                .that(privateFieldsStack)
                .isEmpty();
    }

    @Test
    public void testClearStateGlobalFields() {
        final UnusedPrivateFieldCheck check = new UnusedPrivateFieldCheck();
        final Set<String> globalUsedFields = new HashSet<>();
        globalUsedFields.add("someField");
        TestUtil.setInternalState(check, "globalUsedFields", globalUsedFields);
        check.beginTree(null);
        final Set<?> cleared = (Set<?>) TestUtil.getInternalState(check,
                "globalUsedFields", Set.class);
        assertWithMessage("globalUsedFields state is not cleared on beginTree")
                .that(cleared)
                .isEmpty();
    }

    @Test
    public void testClearStateScopeStack() {
        final UnusedPrivateFieldCheck check = new UnusedPrivateFieldCheck();

        final Deque<Set<String>> stack = new ArrayDeque<>();
        stack.push(new HashSet<>());
        TestUtil.setInternalState(check, "scopeStack", stack);
        check.beginTree(null);

        final Deque<?> scopeStack = (Deque<?>) TestUtil.getInternalState(check,
            "scopeStack", Deque.class);

        assertWithMessage("scopeStack state is not cleared on beginTree")
                .that(scopeStack)
                .isEmpty();
    }

    @Test
    public void testClearStatescopeStackSnapshots() {
        final UnusedPrivateFieldCheck check = new UnusedPrivateFieldCheck();

        final Deque<Set<String>> stack = new ArrayDeque<>();
        stack.push(new HashSet<>());
        TestUtil.setInternalState(check, "scopeStackSnapshots", stack);
        check.beginTree(null);

        final Deque<?> scopeStack = (Deque<?>) TestUtil.getInternalState(check,
            "scopeStackSnapshots", Deque.class);

        assertWithMessage("scopeStackSnapshots state is not cleared on beginTree")
                .that(scopeStack)
                .isEmpty();
    }

    @Test
    public void testClearStatePendingFields() {
        final UnusedPrivateFieldCheck check = new UnusedPrivateFieldCheck();
        final List<Map.Entry<String, DetailAST>> list = new ArrayList<>();
        list.add(new AbstractMap.SimpleEntry<>("someField", null));

        TestUtil.setInternalState(check, "pendingFields", list);
        check.beginTree(null);

        final List<?> pendingFields =
                (List<?>) TestUtil.getInternalState(check, "pendingFields", List.class);

        assertWithMessage("pendingFields state is not cleared on beginTree")
                .that(pendingFields)
                .isEmpty();
    }
}
