///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.google.checkstyle.test.chapter6programpractice.rule64finalizers;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck;

public class NoFinalizerTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter6programpractice/rule64finalizers";
    }

    @Test
    public void testNoFinalizerBasic() throws Exception {
        final String msg = getCheckMessage(NoFinalizerCheck.class, "avoid.finalizer.method");

        final String[] expected = {
            "5:5: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("NoFinalizer");
        final String filePath = getPath("InputNoFinalizer.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testNoFinalizerExtended() throws Exception {
        final String msg = getCheckMessage(NoFinalizerCheck.class, "avoid.finalizer.method");

        final String[] expected = {
            "9:5: " + msg,
            "21:5: " + msg,
            "33:5: " + msg,
            "45:5: " + msg,
            "57:5: " + msg,
            "69:5: " + msg,
            "79:9: " + msg,
            "119:13: " + msg,
            "136:5: " + msg,
        };

        final Configuration checkConfig = getModuleConfig("NoFinalizer");
        final String filePath = getPath("InputNoFinalizeExtend.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
