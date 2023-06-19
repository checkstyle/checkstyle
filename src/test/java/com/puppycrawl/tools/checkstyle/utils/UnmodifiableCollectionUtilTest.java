///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.utils;

import static com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck.MSG_EQUALS_AVOID_NULL;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.EqualsAvoidNullCheck;

public class UnmodifiableCollectionUtilTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/utils/unmodifiablecollectionutil";
    }

    @Test
    public void testFullIdentCreateFullIdentBelow() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(EqualsAvoidNullCheck.class);
        final String[] expected = {
            "6:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "7:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "8:17: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
            "11:22: " + getCheckMessage(MSG_EQUALS_AVOID_NULL),
        };

        verify(checkConfig, getPath("InputUnmodifiableCollectionUtil.java"),
                expected);
    }

}
