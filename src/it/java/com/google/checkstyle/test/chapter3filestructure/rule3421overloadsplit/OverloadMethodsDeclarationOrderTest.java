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

package com.google.checkstyle.test.chapter3filestructure.rule3421overloadsplit;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck;

public class OverloadMethodsDeclarationOrderTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule3421overloadsplit";
    }

    @Test
    public void testOverloadMethods() throws Exception {
        final Class<OverloadMethodsDeclarationOrderCheck> clazz =
            OverloadMethodsDeclarationOrderCheck.class;
        final String messageKey = "overload.methods.declaration";

        final String[] expected = {
            "26:5: " + getCheckMessage(clazz, messageKey, 15),
            "54:9: " + getCheckMessage(clazz, messageKey, 43),
            "66:5: " + getCheckMessage(clazz, messageKey, 64),
            "109:5: " + getCheckMessage(clazz, messageKey, 98),
        };

        final Configuration checkConfig = getModuleConfig("OverloadMethodsDeclarationOrder");
        final String filePath = getPath("InputOverloadMethodsDeclarationOrder.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
