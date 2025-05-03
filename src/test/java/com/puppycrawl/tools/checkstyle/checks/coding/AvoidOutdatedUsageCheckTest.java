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

import static com.google.common.truth.Truth.assertThat;
import static com.puppycrawl.tools.checkstyle.checks.coding.AvoidOutdatedUsageCheck.MSG_OUTDATED_API_USAGE;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AvoidOutdatedUsageCheckTest extends AbstractModuleTestSupport {

    public static final String TO_LIST = ".collect(Collectors.toList()) -> .toList()";

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/avoidoutdatedusage";
    }

    @Test
    public void simple() throws Exception {
        final String[] expected = {
            "16:9: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
            "17:20: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
            "18:43: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
        };
        verifyWithInlineConfigParser(getPath("InputAvoidOutdatedUsageSimple.java"), expected);
        assertThat(new AvoidOutdatedUsageCheck().getRequiredTokens())
            .isEqualTo(CommonUtil.EMPTY_INT_ARRAY);
    }

    @Test
    public void collectors() throws Exception {
        final String[] expected = {
            "21:32: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
            "22:43: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
            "25:62: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
            "26:20: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
            "27:9: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
            "39:31: " + getCheckMessage(MSG_OUTDATED_API_USAGE, TO_LIST),
        };
        verifyWithInlineConfigParser(
                getPath("InputAvoidOutdatedUsageForCollectors.java"), expected);
    }
}
