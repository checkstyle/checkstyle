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

package com.google.checkstyle.test.chapter3filestructure.rule331nowildcard;

import org.junit.jupiter.api.Test;

import com.google.checkstyle.test.base.AbstractGoogleModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class AvoidStarImportTest extends AbstractGoogleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter3filestructure/rule331nowildcard";
    }

    @Test
    public void testStarImport() throws Exception {
        final String[] expected = {
            "3:15: Using the '.*' form of import should be avoided - java.io.*.",
            "4:17: Using the '.*' form of import should be avoided - java.lang.*.",
            "18:42: Using the '.*' form of import should be avoided - "
                + "javax.swing.WindowConstants.*.",
            "19:42: Using the '.*' form of import should be avoided - "
                + "javax.swing.WindowConstants.*.",
        };

        final Configuration checkConfig = getModuleConfig("AvoidStarImport");
        final String filePath = getPath("InputAvoidStarImport.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
