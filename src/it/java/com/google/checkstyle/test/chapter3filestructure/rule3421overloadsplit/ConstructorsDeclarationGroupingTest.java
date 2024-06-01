///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.google.checkstyle.test.chapter3filestructure.rule3421overloadsplit;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.ConstructorsDeclarationGroupingCheck;

public class ConstructorsDeclarationGroupingTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule3421overloadsplit";
    }

    @Test
    public void testOverloadConstructors() throws Exception {
        final Class<ConstructorsDeclarationGroupingCheck> clazz =
                ConstructorsDeclarationGroupingCheck.class;
        final String messageKey = "constructors.declaration.grouping";

        final String[] expected = {
            "23:5: " + getCheckMessage(clazz, messageKey, 19),
            "27:5: " + getCheckMessage(clazz, messageKey, 19),
            "43:9: " + getCheckMessage(clazz, messageKey, 37),
            "52:13: " + getCheckMessage(clazz, messageKey, 46),
            "55:9: " + getCheckMessage(clazz, messageKey, 37),
            "58:5: " + getCheckMessage(clazz, messageKey, 19),
            "60:5: " + getCheckMessage(clazz, messageKey, 19),
            "78:7: " + getCheckMessage(clazz, messageKey, 74),
            "82:7: " + getCheckMessage(clazz, messageKey, 74),
            "84:7: " + getCheckMessage(clazz, messageKey, 74),
            "87:5: " + getCheckMessage(clazz, messageKey, 19),
        };

        final Configuration checkConfig = getModuleConfig("ConstructorsDeclarationGrouping");
        final String filePath = getPath("InputConstructorsDeclarationGrouping.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testOverloadConstructorsRecords() throws Exception {
        final Class<ConstructorsDeclarationGroupingCheck> clazz =
                ConstructorsDeclarationGroupingCheck.class;
        final String messageKey = "constructors.declaration.grouping";

        final String[] expected = {
            "20:9: " + getCheckMessage(clazz, messageKey, 12),
            "22:9: " + getCheckMessage(clazz, messageKey, 12),
            "26:9: " + getCheckMessage(clazz, messageKey, 12),
            "42:9: " + getCheckMessage(clazz, messageKey, 36),
        };

        final Configuration checkConfig = getModuleConfig("ConstructorsDeclarationGrouping");
        final String filePath =
                getNonCompilablePath("InputConstructorsDeclarationGroupingRecords.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
