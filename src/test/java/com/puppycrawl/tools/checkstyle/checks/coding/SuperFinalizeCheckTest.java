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

import static com.puppycrawl.tools.checkstyle.checks.coding.AbstractSuperCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class SuperFinalizeCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/superfinalize";
    }

    @Test
    public void testIt() throws Exception {
        final String[] expected = {
            "34:17: Overriding finalize() method must invoke super.finalize() to ensure proper finalization.",
            "41:17: Overriding finalize() method must invoke super.finalize() to ensure proper finalization.",
            "83:20: Overriding finalize() method must invoke super.finalize() to ensure proper finalization.",
        };
        verifyWithInlineConfigParser(
                getPath("InputSuperFinalizeVariations.java"), expected);
    }

    @Test
    public void testMethodReference() throws Exception {
        final String[] expected = {
            "23:20: Overriding finalize() method must invoke super.finalize() to ensure proper finalization.",
        };
        verifyWithInlineConfigParser(
                getPath("InputSuperFinalizeMethodReference.java"), expected);
    }

}
