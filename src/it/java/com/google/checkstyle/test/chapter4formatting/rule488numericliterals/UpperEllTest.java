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

package com.google.checkstyle.test.chapter4formatting.rule488numericliterals;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class UpperEllTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule488numericliterals"
                + File.separator + fileName);
    }

    @Test
    public void upperEllTest() throws Exception {

        final String[] expected = {
            "6:36: Should use uppercase 'L'.",
            "12:27: Should use uppercase 'L'.",
            "14:32: Should use uppercase 'L'.",
            "17:19: Should use uppercase 'L'.",
            "21:29: Should use uppercase 'L'.",
            "22:22: Should use uppercase 'L'.",
            "25:15: Should use uppercase 'L'.",
            "34:47: Should use uppercase 'L'.",
            "40:31: Should use uppercase 'L'.",
            "42:36: Should use uppercase 'L'.",
            "45:23: Should use uppercase 'L'.",
            "50:33: Should use uppercase 'L'.",
            "51:26: Should use uppercase 'L'.",
            "56:23: Should use uppercase 'L'.",
            "65:48: Should use uppercase 'L'.",
            "71:39: Should use uppercase 'L'.",
            "73:44: Should use uppercase 'L'.",
            "76:31: Should use uppercase 'L'.",
            "80:41: Should use uppercase 'L'.",
            "81:34: Should use uppercase 'L'.",
            "84:27: Should use uppercase 'L'.",
            "97:46: Should use uppercase 'L'.",
            "99:29: Should use uppercase 'L'.",
            "100:22: Should use uppercase 'L'.",
        };

        final Configuration checkConfig = getCheckConfig("UpperEll");
        final String filePath = getPath("InputUpperEll.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
