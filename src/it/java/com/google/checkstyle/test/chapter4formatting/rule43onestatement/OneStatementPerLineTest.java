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

package com.google.checkstyle.test.chapter4formatting.rule43onestatement;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck;

public class OneStatementPerLineTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter4formatting" + File.separator + "rule43onestatement"
                + File.separator + fileName);
    }

    @Test
    public void oneStatementTest() throws Exception {

        final String msg = getCheckMessage(OneStatementPerLineCheck.class,
            "multiple.statements.line");

        final String[] expected = {
            "6:59: " + msg,
            "50:21: " + msg,
            "52:21: " + msg,
            "54:42: " + msg,
            "57:25: " + msg,
            "58:35: " + msg,
            "68:14: " + msg,
            "95:25: " + msg,
            "97:25: " + msg,
            "99:46: " + msg,
            "102:29: " + msg,
            "103:39: " + msg,
            "111:15: " + msg,
            "123:23: " + msg,
            "138:59: " + msg,
            "170:19: " + msg,
            "188:15: " + msg,
            "196:15: " + msg,
            "208:6: " + msg,
            "217:22: " + msg,
            "307:39: " + msg,
        };

        final Configuration checkConfig = getCheckConfig("OneStatementPerLine");
        final String filePath = getPath("InputOneStatementPerLine.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void oneStatementNonCompilableInputTest() throws Exception {

        final String msg = getCheckMessage(OneStatementPerLineCheck.class,
            "multiple.statements.line");

        final String[] expected = {
            "32:6: " + msg,
            "37:58: " + msg,
            "38:58: " + msg,
            "38:74: " + msg,
            "39:50: " + msg,
            "43:91: " + msg,
        };

        final Configuration checkConfig = getCheckConfig("OneStatementPerLine");
        final String filePath = new File("src/test/resources-noncompilable/"
            + "com/puppycrawl/tools/checkstyle/checks/coding/onestatementperline/"
            + "InputOneStatementPerLine.java").getCanonicalPath();

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
