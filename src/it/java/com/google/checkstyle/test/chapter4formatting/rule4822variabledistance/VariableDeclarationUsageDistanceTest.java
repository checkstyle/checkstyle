////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.google.checkstyle.test.chapter4formatting.rule4822variabledistance;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.VariableDeclarationUsageDistanceCheck;

public class VariableDeclarationUsageDistanceTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule4822variabledistance"
                + File.separator + fileName);
    }

    @Test
    public void arrayTypeStyleTest() throws Exception {

        final String msgExt = "variable.declaration.usage.distance.extend";
        final Class<VariableDeclarationUsageDistanceCheck> clazz =
                VariableDeclarationUsageDistanceCheck.class;

        final String[] expected = {
            "71: " + getCheckMessage(clazz, msgExt, "count", 4, 3),
            "219: " + getCheckMessage(clazz, msgExt, "t", 5, 3),
            "479: " + getCheckMessage(clazz, msgExt, "myOption", 7, 3),
            "491: " + getCheckMessage(clazz, msgExt, "myOption", 6, 3),
        };

        final Configuration checkConfig =
            getCheckConfig("VariableDeclarationUsageDistance");
        final String filePath = getPath("InputVariableDeclarationUsageDistanceCheck.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
