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

import static com.puppycrawl.tools.checkstyle.checks.coding.ParameterAssignmentCheck.MSG_KEY;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class ParameterAssignmentCheckTest extends BaseCheckTestSupport {
    @Test
    public void testDefault()
        throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ParameterAssignmentCheck.class);
        final String[] expected = {
            "9:15: " + getCheckMessage(MSG_KEY, "field"),
            "10:15: " + getCheckMessage(MSG_KEY, "field"),
            "12:14: " + getCheckMessage(MSG_KEY, "field"),
            "20:30: " + getCheckMessage(MSG_KEY, "field1"),
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputParameterAssignment.java"),
               expected);
    }

    @Test
    public void testTokensNotNull() {
        ParameterAssignmentCheck check = new ParameterAssignmentCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
