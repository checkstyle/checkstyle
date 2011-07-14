////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2011  Oliver Burn
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
package com.puppycrawl.tools.checkstyle.checks.metrics;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import java.io.File;
import org.junit.Test;

public class ClassDataAbstractionCouplingCheckTest extends BaseCheckTestSupport
{
    @Test
    public void test() throws Exception
    {
        DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");

        String[] expected = {
            "6:1: Class Data Abstraction Coupling is 4 (max allowed is 0) classes [AnotherInnerClass, HashMap, HashSet, InnerClass].",
            "7:5: Class Data Abstraction Coupling is 1 (max allowed is 0) classes [ArrayList].",
            "27:1: Class Data Abstraction Coupling is 2 (max allowed is 0) classes [HashMap, HashSet].",
        };

        verify(checkConfig,
               getPath("metrics" + File.separator + "ClassCouplingCheckTestInput.java"),
               expected);
    }
}
