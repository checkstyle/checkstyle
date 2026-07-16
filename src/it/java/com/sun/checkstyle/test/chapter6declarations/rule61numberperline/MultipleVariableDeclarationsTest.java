///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.sun.checkstyle.test.chapter6declarations.rule61numberperline;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck;
import com.sun.checkstyle.test.base.AbstractSunModuleTestSupport;

public class MultipleVariableDeclarationsTest extends AbstractSunModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/sun/checkstyle/test/chapter6declarations/rule61numberperline";
    }

    @Test
    public void testMultipleVariableDeclarations() throws Exception {
        final String msgComma = getCheckMessage(MultipleVariableDeclarationsCheck.class,
            "multiple.variable.declarations.comma");
        final String msg = getCheckMessage(MultipleVariableDeclarationsCheck.class,
            "multiple.variable.declarations");

        final String[] expected = {
            "6:5: " + msgComma,
            "7:5: " + msg,
            "10:9: " + msgComma,
            "11:9: " + msg,
            "15:5: " + msg,
            "18:5: " + msg,
            "32:9: " + msgComma,
            "33:9: " + msg,
            "36:13: " + msgComma,
            "37:13: " + msg,
            "41:9: " + msg,
            "44:9: " + msg,
            "58:13: " + msgComma,
            "59:13: " + msg,
            "62:17: " + msgComma,
            "63:17: " + msg,
            "67:13: " + msg,
            "70:13: " + msg,
            "89:5: " + msgComma,
            "92:5: " + msgComma,
        };

        final Configuration checkConfig = getModuleConfig("MultipleVariableDeclarations");
        final String filePath = getPath("InputMultipleVariableDeclarations.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
