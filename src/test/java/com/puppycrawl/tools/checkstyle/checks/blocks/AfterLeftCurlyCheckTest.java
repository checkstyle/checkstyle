////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.blocks;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import org.junit.jupiter.api.Test;

import static com.puppycrawl.tools.checkstyle.checks.blocks.AfterLeftCurlyCheck.MSG_SHOULD_BE_FOLLOWED_BY_EMPTY_LINE;
import static com.puppycrawl.tools.checkstyle.checks.blocks.AfterLeftCurlyCheck.MSG_SHOULD_NOT_BE_FOLLOWED_BY_EMPTY_LINE;

public class AfterLeftCurlyCheckTest
        extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/afterleftcurly";
    }

    @Test
    void testDefault() throws Exception {
        final String[] expected = {
                "9:47: " + getCheckMessage(MSG_SHOULD_BE_FOLLOWED_BY_EMPTY_LINE, ",")
        };
        verifyWithInlineConfigParser(
                getPath("InputAfterLeftCurlyDefaultConfig.java"),
                expected);
    }

    @Test
    void testNoBlankLineAfterLeftCurly() throws Exception {
        final String[] expected = {
                "9:51: " + getCheckMessage(MSG_SHOULD_NOT_BE_FOLLOWED_BY_EMPTY_LINE, ",")
        };
        verifyWithInlineConfigParser(
                getPath("InputAfterLeftCurlyNoBlankLineConfig.java"),
                expected);
    }
}
