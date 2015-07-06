////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2015 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.coding;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import org.junit.Test;

import static com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck.MSG_KEY;
public class OneStatementPerLineCheckTest extends BaseCheckTestSupport {
    @Test
    public void testMultiCaseClass() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "24:59: " + getCheckMessage(MSG_KEY),
            "112:21: " + getCheckMessage(MSG_KEY),
            "139:14: " + getCheckMessage(MSG_KEY),
            "165:15: " + getCheckMessage(MSG_KEY),
            "177:23: " + getCheckMessage(MSG_KEY),
            "197:19: " + getCheckMessage(MSG_KEY),
            "200:59: " + getCheckMessage(MSG_KEY),
            "209:4: " + getCheckMessage(MSG_KEY),
            "228:58: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
            getPath("checks/coding/OneStatementPerLineCheckInput.java"),
            expected);
    }

    @Test
    public void testWithMultilineStatements() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(OneStatementPerLineCheck.class);
        final String[] expected = {
            "43:21: " + getCheckMessage(MSG_KEY),
            "60:17: " + getCheckMessage(MSG_KEY),
            "68:17: " + getCheckMessage(MSG_KEY),
            "80:10: " + getCheckMessage(MSG_KEY),
            "89:20: " + getCheckMessage(MSG_KEY),
        };

        verify(checkConfig,
            getPath("checks/coding/OneStatementPerLineCheckInput2.java"),
            expected);
    }
}
