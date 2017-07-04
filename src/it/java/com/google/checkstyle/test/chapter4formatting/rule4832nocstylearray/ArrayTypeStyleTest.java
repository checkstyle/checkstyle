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

package com.google.checkstyle.test.chapter4formatting.rule4832nocstylearray;

import static com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck;

public class ArrayTypeStyleTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule4832nocstylearray"
                + File.separator + fileName);
    }

    @Test
    public void arrayTypeStyleTest() throws Exception {

        final String[] expected = {
            "9:23: " + getCheckMessage(ArrayTypeStyleCheck.class, MSG_KEY),
            "15:44: " + getCheckMessage(ArrayTypeStyleCheck.class, MSG_KEY),
            "21:20: " + getCheckMessage(ArrayTypeStyleCheck.class, MSG_KEY),
            "22:23: " + getCheckMessage(ArrayTypeStyleCheck.class, MSG_KEY),
            "41:16: " + getCheckMessage(ArrayTypeStyleCheck.class, MSG_KEY),
            "42:19: " + getCheckMessage(ArrayTypeStyleCheck.class, MSG_KEY),
        };

        final Configuration checkConfig = getCheckConfig("ArrayTypeStyle");
        final String filePath = getPath("InputArrayTypeStyle.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
