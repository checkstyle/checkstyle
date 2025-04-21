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

import static com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryNullCheckWithInstanceOfCheck.MSG_UNNECESSARY_NULLCHECK;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import org.junit.jupiter.api.Test;

/**
 * Unit tests for the UnnecessaryNullCheckWithInstanceofCheck.
 *
 * <p>This test class verifies the behavior of the check that detects
 * unnecessary null checks when used with instanceof operators.</p>
 */
public class UnnecessaryNullCheckWithInstanceOfCheckTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/coding/unnecessarynullcheckwithinstanceof";
    }

    @Test
    public void testUnnecessaryNullCheckWithInstanceof() throws Exception {

        final String[] expected = {
            "13:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
            "19:13: " + getCheckMessage(MSG_UNNECESSARY_NULLCHECK),
        };

        verifyWithInlineConfigParser(getPath("InputUnnecessaryNullCheckWithInstanceOf.java"), expected);
    }

}
