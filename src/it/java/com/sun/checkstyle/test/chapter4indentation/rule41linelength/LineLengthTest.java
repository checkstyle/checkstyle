////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.sun.checkstyle.test.chapter4indentation.rule41linelength;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck;
import com.sun.checkstyle.test.base.AbstractSunModuleTestSupport;

public class LineLengthTest extends AbstractSunModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/sun/checkstyle/test/chapter4indentation/rule41linelength";
    }

    @Test
    public void testLineLength() throws Exception {
        final String[] expected = {
            "9: " + getCheckMessage(LineLengthCheck.class,
            "maxLineLen",80,100),
            "65: " + getCheckMessage(LineLengthCheck.class,"maxLineLen",80,91)
        };

        final Configuration checkConfig = getModuleConfig("LineLength");
        final String filePath = getPath("InputLineLength.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
