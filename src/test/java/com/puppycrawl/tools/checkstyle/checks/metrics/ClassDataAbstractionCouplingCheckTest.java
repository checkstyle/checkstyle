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

package com.puppycrawl.tools.checkstyle.checks.metrics;

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck.MSG_KEY;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Test;

import antlr.CommonHiddenStreamToken;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassDataAbstractionCouplingCheckTest extends BaseCheckTestSupport {
    @Test
    public void test() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);

        checkConfig.addAttribute("max", "0");
        checkConfig.addAttribute("excludedClasses", "InnerClass");

        String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 4, 0, "[AnotherInnerClass, HashMap, HashSet, int]"),
            "7:5: " + getCheckMessage(MSG_KEY, 1, 0, "[ArrayList]"),
            "27:1: " + getCheckMessage(MSG_KEY, 2, 0, "[HashMap, HashSet]"),
        };

        verify(checkConfig,
               getPath("metrics" + File.separator + "ClassCouplingCheckTestInput.java"),
               expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(ClassDataAbstractionCouplingCheck.class);
        String[] expected = {
        };

        try {
            createChecker(checkConfig);
            verify(checkConfig,
                getPath("metrics" + File.separator + "ClassCouplingCheckTestInput.java"),
                expected);
        }
        catch (Exception ex) {
            //Exception is not expected
            fail();
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongToken() {
        ClassDataAbstractionCouplingCheck classDataAbstractionCouplingCheckObj = new ClassDataAbstractionCouplingCheck();
        DetailAST ast = new DetailAST();
        ast.initialize(new CommonHiddenStreamToken(TokenTypes.CTOR_DEF, "ctor"));
        classDataAbstractionCouplingCheckObj.visitToken(ast);
    }
}
