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

package com.puppycrawl.tools.checkstyle.checks.modules;

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.checks.modules.ModuleDirectiveOrderCheck.MSG_GROUPING;
import static com.puppycrawl.tools.checkstyle.checks.modules.ModuleDirectiveOrderCheck.MSG_ORDER;
import static com.puppycrawl.tools.checkstyle.checks.modules.ModuleDirectiveOrderCheck.MSG_SEPARATED_INTERNALLY;
import static com.puppycrawl.tools.checkstyle.checks.modules.ModuleDirectiveOrderCheck.MSG_SEPARATION;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ModuleDirectiveOrderCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modules/moduledirectiveorder";
    }

    @Test
    public void testGetRequiredTokens() {
        final ModuleDirectiveOrderCheck check = new ModuleDirectiveOrderCheck();
        final int[] expected = {TokenTypes.MODULE_DEF};
        assertWithMessage("Required tokens are invalid")
            .that(check.getRequiredTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testGetAcceptableTokens() {
        final ModuleDirectiveOrderCheck check = new ModuleDirectiveOrderCheck();
        final int[] expected = {TokenTypes.MODULE_DEF};
        assertWithMessage("Acceptable tokens are invalid")
            .that(check.getAcceptableTokens())
            .isEqualTo(expected);
        assertWithMessage("Default tokens are invalid")
            .that(check.getDefaultTokens())
            .isEqualTo(expected);
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/default/module-info.java"), expected);
    }

    @Test
    public void testWrongOrder() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_ORDER, "requires", "exports"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/unordered/module-info.java"), expected);
    }

    @Test
    public void testWrongOrderReversed() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_ORDER, "opens", "uses"),
            "16:5: " + getCheckMessage(MSG_ORDER, "exports", "opens"),
            "18:5: " + getCheckMessage(MSG_ORDER, "requires", "exports"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/reversed/module-info.java"), expected);
    }

    @Test
    public void testGrouping() throws Exception {
        final String[] expected = {
            "16:5: " + getCheckMessage(MSG_GROUPING, "requires"),
            "21:5: " + getCheckMessage(MSG_GROUPING, "exports"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/grouping/module-info.java"), expected);
    }

    @Test
    public void testSeparation() throws Exception {
        final String[] expected = {
            "13:5: " + getCheckMessage(MSG_SEPARATION, "exports"),
            "17:5: " + getCheckMessage(MSG_SEPARATION, "opens"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/separation/module-info.java"), expected);
    }

    @Test
    public void testSeparatedInternally() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_SEPARATED_INTERNALLY, "requires"),
            "20:5: " + getCheckMessage(MSG_SEPARATED_INTERNALLY, "exports"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/internal/module-info.java"),
                expected);
    }

    @Test
    public void testValidateBlockSeparationFalse() throws Exception {
        final String[] expected = {
            "20:5: " + getCheckMessage(MSG_GROUPING, "requires"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/unseparated/module-info.java"),
                expected);
    }

    @Test
    public void testCustomOrder() throws Exception {
        final String[] expected = {
            "17:5: " + getCheckMessage(MSG_ORDER, "uses", "exports"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/custom/module-info.java"), expected);
    }

    @Test
    public void testCustomOrderSubset() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_ORDER, "requires", "exports"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/subset/module-info.java"), expected);
    }

    @Test
    public void testEmptyModule() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/empty/module-info.java"), expected);
    }

    @Test
    public void testSameLine() throws Exception {
        final String[] expected = {
            "13:25: " + getCheckMessage(MSG_SEPARATION, "exports"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/adjacent/module-info.java"), expected);
    }

    @Test
    public void testCommentBetweenBlocks() throws Exception {
        final String[] expected = {
            "14:5: " + getCheckMessage(MSG_SEPARATION, "exports"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/comment/module-info.java"), expected);
    }

    @Test
    public void testMultiLineDirective() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/multiline/module-info.java"), expected);
    }

    @Test
    public void testDefaultQualifiedForms() throws Exception {
        final String[] expected = {
            "15:5: " + getCheckMessage(MSG_ORDER, "requires", "exports"),
            "18:5: " + getCheckMessage(MSG_SEPARATED_INTERNALLY, "requires"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/modifiers/module-info.java"),
                expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("compact/InputModuleDirectiveOrderCompactSourceFile.java"),
                expected);
    }

    @Test
    public void testCompactSourceFileNonDefault() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath(
                        "compact/InputModuleDirectiveOrderCompactSourceFileNonDefault.java"),
                expected);
    }

    @Test
    public void testSetOrderInvalidValue() {
        final ModuleDirectiveOrderCheck check = new ModuleDirectiveOrderCheck();
        final IllegalArgumentException exc = getExpectedThrowable(IllegalArgumentException.class,
                () -> check.setOrder("requires", "foo"));
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("unable to parse foo");
    }

    @Test
    public void testInvalidOrderProperty() {
        final CheckstyleException exc = getExpectedThrowable(CheckstyleException.class,
                () -> {
                    final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
                    verifyWithInlineConfigParser(
                            getNonCompilablePath("module-info/invalid/module-info.java"),
                            expected);
                });
        assertWithMessage("Invalid exception message")
            .that(exc.getMessage())
            .isEqualTo("cannot initialize module com.puppycrawl.tools.checkstyle.TreeWalker - "
                    + "cannot initialize module com.puppycrawl.tools.checkstyle.checks"
                    + ".modules.ModuleDirectiveOrderCheck");
    }

}
