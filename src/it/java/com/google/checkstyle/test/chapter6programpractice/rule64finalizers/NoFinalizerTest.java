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

package com.google.checkstyle.test.chapter6programpractice.rule64finalizers;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck;

public class NoFinalizerTest extends BaseCheckTestSupport {

    @Override
    protected String getPath(String fileName) throws IOException {
        return super.getPath("chapter6programpractice" + File.separator + "rule64finalizers"
                + File.separator + fileName);
    }

    @Test
    public void noFinalizerBasicTest() throws Exception {

        final String msg = getCheckMessage(NoFinalizerCheck.class, "avoid.finalizer.method");

        final String[] expected = {
            "5: " + msg,
        };

        final Configuration checkConfig = getCheckConfig("NoFinalizer");
        final String filePath = getPath("InputNoFinalizer.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void noFinalizerExtendedTest() throws Exception {

        final String msg = getCheckMessage(NoFinalizerCheck.class, "avoid.finalizer.method");

        final String[] expected = {
            "9: " + msg,
            "21: " + msg,
            "33: " + msg,
            "45: " + msg,
            "57: " + msg,
            "69: " + msg,
            "79: " + msg,
            "119: " + msg,
            "136: " + msg,
        };

        final Configuration checkConfig = getCheckConfig("NoFinalizer");
        final String filePath = getPath("InputNoFinalizeExtend.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
