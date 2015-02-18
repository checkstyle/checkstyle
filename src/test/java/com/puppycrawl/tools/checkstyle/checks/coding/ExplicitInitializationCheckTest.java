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
import java.io.File;
import org.junit.Test;

public class ExplicitInitializationCheckTest extends BaseCheckTestSupport
{
    @Test
    public void testDefault() throws Exception
    {
        final DefaultConfiguration checkConfig =
            createCheckConfig(ExplicitInitializationCheck.class);
        final String[] expected = {
            "4:17: Variable 'x' explicitly initialized to '0' (default value for its type).",
            "5:20: Variable 'bar' explicitly initialized to 'null' (default value for its type).",
            "9:18: Variable 'y4' explicitly initialized to '0' (default value for its type).",
            "10:21: Variable 'b1' explicitly initialized to 'false' (default value for its type).",
            "14:22: Variable 'str1' explicitly initialized to 'null' (default value for its type).",
            "14:35: Variable 'str3' explicitly initialized to 'null' (default value for its type).",
            "15:9: Variable 'ar1' explicitly initialized to 'null' (default value for its type).",
            "18:11: Variable 'f1' explicitly initialized to '0' (default value for its type).",
            "19:12: Variable 'd1' explicitly initialized to '0' (default value for its type).",
            "22:17: Variable 'ch1' explicitly initialized to '\\0' (default value for its type).",
            "23:17: Variable 'ch2' explicitly initialized to '\\0' (default value for its type).",
            "39:25: Variable 'bar' explicitly initialized to 'null' (default value for its type).",
            "40:27: Variable 'barArray' explicitly initialized to 'null' (default value for its type).",
            "47:21: Variable 'x' explicitly initialized to '0' (default value for its type).",
            "48:29: Variable 'bar' explicitly initialized to 'null' (default value for its type).",
            "49:31: Variable 'barArray' explicitly initialized to 'null' (default value for its type).",
            "52:17: Variable 'x' explicitly initialized to '0' (default value for its type).",
            "53:25: Variable 'bar' explicitly initialized to 'null' (default value for its type).",
            "54:27: Variable 'barArray' explicitly initialized to 'null' (default value for its type).",
        };
        verify(checkConfig,
               getPath("coding" + File.separator + "InputExplicitInit.java"),
               expected);
    }
}
