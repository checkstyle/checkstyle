////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2014  Oliver Burn
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
import java.io.File;
import org.junit.Test;

public class InnerAssignmentCheckTest
    extends BaseCheckTestSupport
{
    @Test
    public void testIt() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InnerAssignmentCheck.class);
        final String[] expected = {
            "16:15: Inner assignments should be avoided.",
            "16:19: Inner assignments should be avoided.",
            "18:39: Inner assignments should be avoided.",
            "20:35: Inner assignments should be avoided.",

            "38:16: Inner assignments should be avoided.",
            "39:24: Inner assignments should be avoided.",
            "40:19: Inner assignments should be avoided.",
            "41:17: Inner assignments should be avoided.",
            "42:29: Inner assignments should be avoided.",
            "43:20: Inner assignments should be avoided.",
            "44:17: Inner assignments should be avoided.",
            "44:31: Inner assignments should be avoided.",
            "44:41: Inner assignments should be avoided.",
            "45:16: Inner assignments should be avoided.",
            "45:27: Inner assignments should be avoided.",
            "46:32: Inner assignments should be avoided.",
        };
        verify(checkConfig, getPath("coding" + File.separator + "InputInnerAssignment.java"), expected);
    }

    @Test
    public void testLambdexpression() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(InnerAssignmentCheck.class);
        final String[] expected = {

        };
        verify(checkConfig, new File("src/test/resources-noncompilable/com/puppycrawl/tools/"
                + "checkstyle/coding/"
                + "InputInnerAssignmentLambdaExpressions.java").getCanonicalPath(), expected);
    }
}
