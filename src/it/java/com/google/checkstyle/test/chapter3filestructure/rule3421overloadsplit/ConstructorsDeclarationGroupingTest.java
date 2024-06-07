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

public class ConstructorsDeclarationGroupingTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule3421overloadsplit";
    }

    @Test
    public void testOverloadConstructors() throws Exception {
        final String filePath = getPath("InputConstructorsDeclarationGrouping.java");

        final Map<String, String[]> listOfModules = new HashMap<>();
        listOfModules.put("ConstructorsDeclarationGrouping", null);

        verifyWithGoogleConfigParser(listOfModules, filePath);
    }

    @Test
    public void testOverloadConstructorsRecords() throws Exception {
        final String filePath =
                getNonCompilablePath("InputConstructorsDeclarationGroupingRecords.java");

        final Map<String, String[]> listOfModules = new HashMap<>();
        listOfModules.put("ConstructorsDeclarationGrouping", null);

        verifyWithGoogleConfigParser(listOfModules, filePath);
    }
}
