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

import java.util.LinkedHashMap;

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
            "17:5: " + getCheckMessage(clazz, messageKey, 13),
            "21:5: " + getCheckMessage(clazz, messageKey, 13),
            "37:9: " + getCheckMessage(clazz, messageKey, 31),
            "46:13: " + getCheckMessage(clazz, messageKey, 40),
            "49:9: " + getCheckMessage(clazz, messageKey, 31),
            "52:5: " + getCheckMessage(clazz, messageKey, 13),
            "54:5: " + getCheckMessage(clazz, messageKey, 13),
            "72:7: " + getCheckMessage(clazz, messageKey, 68),
            "76:7: " + getCheckMessage(clazz, messageKey, 68),
            "78:7: " + getCheckMessage(clazz, messageKey, 68),
            "81:5: " + getCheckMessage(clazz, messageKey, 13),
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
            "14:9: " + getCheckMessage(clazz, messageKey, 6),
            "16:9: " + getCheckMessage(clazz, messageKey, 6),
            "20:9: " + getCheckMessage(clazz, messageKey, 6),
            "36:9: " + getCheckMessage(clazz, messageKey, 30),
        };

        final Configuration checkConfig = getModuleConfig("ConstructorsDeclarationGrouping");
        final String filePath =
                getNonCompilablePath("InputConstructorsDeclarationGroupingRecords.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
