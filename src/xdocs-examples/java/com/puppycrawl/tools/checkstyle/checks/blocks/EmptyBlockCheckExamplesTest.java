///
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
///

package com.puppycrawl.tools.checkstyle.checks.blocks;

import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck.MSG_KEY_BLOCK_EMPTY;
import static com.puppycrawl.tools.checkstyle.checks.blocks.EmptyBlockCheck.MSG_KEY_BLOCK_NO_STATEMENT;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractExamplesModuleTestSupport;

public class EmptyBlockCheckExamplesTest extends AbstractExamplesModuleTestSupport {
    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/blocks/emptyblock";
    }

    @Test
    public void testExample1() throws Exception {
        final String[] expected = {
            "14:34: " + getCheckMessage(MSG_KEY_BLOCK_NO_STATEMENT, "for"),
            "17:9: " + getCheckMessage(MSG_KEY_BLOCK_NO_STATEMENT, "try"),
        };

        verifyWithInlineConfigParser(getPath("Example1.java"), expected);
    }

    @Test
    public void testExample2() throws Exception {
        final String[] expected = {
            "21:9: " + getCheckMessage(MSG_KEY_BLOCK_EMPTY, "try"),
        };

        verifyWithInlineConfigParser(getPath("Example2.java"), expected);
    }

    @Test
    public void testExample3() throws Exception {
        final String[] expected = {
            "19:16: " + getCheckMessage(MSG_KEY_BLOCK_NO_STATEMENT, "case"),
            "22:15: " + getCheckMessage(MSG_KEY_BLOCK_NO_STATEMENT, "case"),
            "24:15: " + getCheckMessage(MSG_KEY_BLOCK_NO_STATEMENT, "case"),
            "27:17: " + getCheckMessage(MSG_KEY_BLOCK_NO_STATEMENT, "case"),
            "44:17: " + getCheckMessage(MSG_KEY_BLOCK_NO_STATEMENT, "case"),
            "45:18: " + getCheckMessage(MSG_KEY_BLOCK_NO_STATEMENT, "case"),
        };

        verifyWithInlineConfigParser(getNonCompilablePath("Example3.java"), expected);
    }
}
