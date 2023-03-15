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

package com.google.checkstyle.test.chapter4formatting.rule4822variabledistance;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck;

public class VariableDeclarationUsageDistanceTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter4formatting/rule4822variabledistance";
    }

    @Test
    public void testArrayTypeStyle() throws Exception {
        final String msgExt = "variable.declaration.usage.distance.extend";
        final Class<VariableDeclarationUsageDistanceCheck> clazz =
                VariableDeclarationUsageDistanceCheck.class;

        final String[] expected = {
            "71:9: " + getCheckMessage(clazz, msgExt, "count", 4, 3),
            "219:9: " + getCheckMessage(clazz, msgExt, "t", 5, 3),
            "483:9: " + getCheckMessage(clazz, msgExt, "myOption", 7, 3),
            "495:9: " + getCheckMessage(clazz, msgExt, "myOption", 6, 3),
        };

        final Configuration checkConfig =
            getModuleConfig("VariableDeclarationUsageDistance");
        final String filePath = getPath("InputVariableDeclarationUsageDistanceCheck.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
