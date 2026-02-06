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

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
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
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
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
            TokenTypes.VARIABLE_DEF,
            TokenTypes.IDENT,
        };
        assertWithMessage("Acceptable tokens are invalid")
                .that(actual)
                .isEqualTo(expected);
    }

    @Test
    public void testUnusedprivatefield() throws Exception {
        final String[] expected = {
            "11:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "unused"),
            "25:9: " + getCheckMessage(MSG_PRIVATE_FIELD, "innerUnused"),
            "30:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "field"),
            "32:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "field2"),
        };
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateField.java"), expected);
    }

    @Test
    public void testUnusedprivatefield2() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputUnusedPrivateField2.java"), expected);
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
            "10:5: " + getCheckMessage(MSG_PRIVATE_FIELD, "a"),
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
    public void testClearStateVariables() throws Exception {
        final UnusedPrivateFieldCheck check = new UnusedPrivateFieldCheck();

        final Set<String> dummySet = new HashSet<>();
        dummySet.add("dummy");

        TestUtil.setInternalState(check, "usedFields", dummySet);

        check.beginTree(null);

        final Collection<?> usedFields = (Collection<?>) TestUtil.getInternalState(check,
                "usedFields", Collection.class);

        assertWithMessage("usedFields state is not cleared on beginTree")
                .that(usedFields)
                .isEmpty();
    }

}
