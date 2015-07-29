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

import static com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck.MSG_KEY;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

public class ClassFanOutComplexityCheckTest extends BaseCheckTestSupport {
    @Test
    public void test() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        String[] expected = {
            "6:1: " + getCheckMessage(MSG_KEY, 3, 0),
            "38:1: " + getCheckMessage(MSG_KEY, 1, 0),
        };

        verify(checkConfig,
               getPath("metrics" + File.separator + "ClassCouplingCheckTestInput.java"),
               expected);
    }

    @Test
    public void test15() throws Exception {
        DefaultConfiguration checkConfig = createCheckConfig(ClassFanOutComplexityCheck.class);

        checkConfig.addAttribute("max", "0");

        String[] expected = {
        };

        verify(checkConfig, getPath("Input15Extensions.java"), expected);
    }

    @Test
    public void testDefaultConfiguration() throws Exception {
        DefaultConfiguration checkConfig =
            createCheckConfig(ClassFanOutComplexityCheck.class);
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

    @Test
    public void testGetAcceptableTokens() {
        ClassFanOutComplexityCheck classFanOutComplexityCheckObj = new ClassFanOutComplexityCheck();
        int[] actual = classFanOutComplexityCheckObj.getAcceptableTokens();
        int[] expected = new int[] {
            TokenTypes.PACKAGE_DEF,
            TokenTypes.CLASS_DEF,
            TokenTypes.INTERFACE_DEF,
            TokenTypes.ENUM_DEF,
            TokenTypes.TYPE,
            TokenTypes.LITERAL_NEW,
            TokenTypes.LITERAL_THROWS,
            TokenTypes.ANNOTATION_DEF,
        };
        Assert.assertNotNull(actual);
        Assert.assertArrayEquals(expected, actual);
    }
}
