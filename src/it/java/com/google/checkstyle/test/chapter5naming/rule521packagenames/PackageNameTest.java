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

package com.google.checkstyle.test.chapter5naming.rule521packagenames;

import java.io.File;
import java.io.IOException;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtils;

public class PackageNameTest extends AbstractModuleTestSupport {

    private static final String MSG_KEY = "name.invalidPattern";
    private static Configuration checkConfig;
    private static String format;

    @Override
    protected String getPackageLocation() {
        return "com/google/checkstyle/test/chapter5naming";
    }

    private String getPath(String packageName, String fileName) throws IOException {
        return super.getPath("rule521" + packageName + File.separator + fileName);
    }

    @BeforeClass
    public static void setConfigurationBuilder() throws CheckstyleException {
        checkConfig = getModuleConfig("PackageName");
        format = checkConfig.getAttribute("format");
    }

    @Test
    public void testGoodPackageName() throws Exception {

        final String[] expected = CommonUtils.EMPTY_STRING_ARRAY;

        final String filePath = getPath("packagenames", "InputPackageNameGood.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testBadPackageName() throws Exception {

        final String packagePath =
                "com.google.checkstyle.test.chapter5naming.rule521packageNamesCamelCase";
        final String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        final String filePath = getPath("packageNamesCamelCase", "InputPackageNameBad.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testBadPackageName2() throws Exception {

        final String packagePath = "com.google.checkstyle.test.chapter5naming.rule521_packagenames";
        final String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        final String filePath = getPath("_packagenames", "InputBadPackageName2.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testBadPackageName3() throws Exception {

        final String packagePath = "com.google.checkstyle.test.chapter5naming.rule521$packagenames";
        final String msg = getCheckMessage(checkConfig.getMessages(), MSG_KEY, packagePath, format);

        final String[] expected = {
            "1:9: " + msg,
        };

        final String filePath = getPath("$packagenames", "InputPackageBadName3.java");

        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }
}
