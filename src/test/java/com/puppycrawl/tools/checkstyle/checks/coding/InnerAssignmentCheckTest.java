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

import static com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck.MSG_KEY;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;

public class InnerAssignmentCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("checks" + File.separator
                + "coding" + File.separator + filename);
    }

    @Test
    public void testIt() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InnerAssignmentCheck.class);
        final String[] expected = {
            "16:15: " + getCheckMessage(MSG_KEY),
            "16:19: " + getCheckMessage(MSG_KEY),
            "18:39: " + getCheckMessage(MSG_KEY),
            "20:35: " + getCheckMessage(MSG_KEY),

            "38:16: " + getCheckMessage(MSG_KEY),
            "39:24: " + getCheckMessage(MSG_KEY),
            "40:19: " + getCheckMessage(MSG_KEY),
            "41:17: " + getCheckMessage(MSG_KEY),
            "42:29: " + getCheckMessage(MSG_KEY),
            "43:20: " + getCheckMessage(MSG_KEY),
            "44:17: " + getCheckMessage(MSG_KEY),
            "44:31: " + getCheckMessage(MSG_KEY),
            "44:41: " + getCheckMessage(MSG_KEY),
            "45:16: " + getCheckMessage(MSG_KEY),
            "45:27: " + getCheckMessage(MSG_KEY),
            "46:32: " + getCheckMessage(MSG_KEY),
        };
        verify(checkConfig, getPath("InputInnerAssignment.java"), expected);
    }

    @Test
    public void testLambdaExpression() throws Exception {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InnerAssignmentCheck.class);
        final String[] expected = ArrayUtils.EMPTY_STRING_ARRAY;
        verify(checkConfig, getNonCompilablePath("InputInnerAssignmentLambdaExpressions.java"),
            expected);
    }

    @Test
    public void testTokensNotNull() {
        final InnerAssignmentCheck check = new InnerAssignmentCheck();
        Assert.assertNotNull(check.getAcceptableTokens());
        Assert.assertNotNull(check.getDefaultTokens());
        Assert.assertNotNull(check.getRequiredTokens());
    }
}
