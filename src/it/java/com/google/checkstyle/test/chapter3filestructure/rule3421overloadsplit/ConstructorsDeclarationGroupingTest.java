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

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
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
            "17:5: " + getCheckMessage(clazz, messageKey, 13),
            "22:5: " + getCheckMessage(clazz, messageKey, 13),
            "39:9: " + getCheckMessage(clazz, messageKey, 33),
            "48:13: " + getCheckMessage(clazz, messageKey, 42),
            "51:9: " + getCheckMessage(clazz, messageKey, 33),
            "54:5: " + getCheckMessage(clazz, messageKey, 13),
            "57:5: " + getCheckMessage(clazz, messageKey, 13),
            "76:7: " + getCheckMessage(clazz, messageKey, 72),
            "80:7: " + getCheckMessage(clazz, messageKey, 72),
            "83:7: " + getCheckMessage(clazz, messageKey, 72),
            "86:5: " + getCheckMessage(clazz, messageKey, 13),
        };

        final String filePath = getPath("InputConstructorsDeclarationGrouping.java");

        final Map<String, String[]> listOfModules = new HashMap<>();
        listOfModules.put("ConstructorsDeclarationGrouping", null);

        verifyWithGoogleConfigParser(listOfModules, filePath, expected);
    }

    @Test
    public void testOverloadConstructorsRecords() throws Exception {
        final Class<ConstructorsDeclarationGroupingCheck> clazz =
                ConstructorsDeclarationGroupingCheck.class;
        final String messageKey = "constructors.declaration.grouping";

        final String[] expected = {
            "14:9: " + getCheckMessage(clazz, messageKey, 6),
            "16:9: " + getCheckMessage(clazz, messageKey, 6),
            "21:9: " + getCheckMessage(clazz, messageKey, 6),
            "38:9: " + getCheckMessage(clazz, messageKey, 32),
        };

        final String filePath =
                getNonCompilablePath("InputConstructorsDeclarationGroupingRecords.java");

        final Map<String, String[]> listOfModules = new HashMap<>();
        listOfModules.put("ConstructorsDeclarationGrouping", null);

        verifyWithGoogleConfigParser(listOfModules, filePath, expected);
    }
}
