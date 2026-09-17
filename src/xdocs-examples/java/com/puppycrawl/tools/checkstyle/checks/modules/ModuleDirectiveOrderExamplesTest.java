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

import static com.puppycrawl.tools.checkstyle.checks.modules.ModuleDirectiveOrderCheck.MSG_GROUPING;
import static com.puppycrawl.tools.checkstyle.checks.modules.ModuleDirectiveOrderCheck.MSG_ORDER;
import static com.puppycrawl.tools.checkstyle.checks.modules.ModuleDirectiveOrderCheck.MSG_SEPARATED_INTERNALLY;
import static com.puppycrawl.tools.checkstyle.checks.modules.ModuleDirectiveOrderCheck.MSG_SEPARATION;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ModuleDirectiveOrderExamplesTest extends AbstractExamplesModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/modules/moduledirectiveorder";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getNonCompilablePath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "18:3: " + getCheckMessage(MSG_ORDER, "uses", "exports"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        verifyWithInlineConfigParser(getNonCompilablePath("Example3.java"), expected);
    }

    @Test
    public void testUseCase1() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_ORDER, "requires", "exports"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("UseCase1.java"), expected);
    }

    @Test
    public void testUseCase2() throws Exception {
        final String[] expected = {
            "13:3: " + getCheckMessage(MSG_SEPARATION, "exports"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("UseCase2.java"), expected);
    }

    @Test
    public void testUseCase3() throws Exception {
        final String[] expected = {
            "15:3: " + getCheckMessage(MSG_SEPARATION, "exports"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("UseCase3.java"), expected);
    }

    @Test
    public void testUseCase4() throws Exception {
        final String[] expected = {
            "16:3: " + getCheckMessage(MSG_GROUPING, "requires"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("UseCase4.java"), expected);
    }

    @Test
    public void testUseCase5() throws Exception {
        final String[] expected = {
            "14:3: " + getCheckMessage(MSG_SEPARATED_INTERNALLY, "requires"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("UseCase5.java"), expected);
    }

}
