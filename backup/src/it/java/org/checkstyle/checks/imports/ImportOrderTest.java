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

package org.checkstyle.checks.imports;

import org.checkstyle.base.AbstractCheckstyleModuleTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class ImportOrderTest extends AbstractCheckstyleModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/checks/imports/importorder";
    }

    @Test
    public void testAndroid() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportOrderCheck.class);
        checkConfig.addProperty("groups",
            "android,androidx,com.android,dalvik,com,gov,junit,libcore,net,org,java,javax");
        checkConfig.addProperty("option", "top");
        checkConfig.addProperty("ordered", "true");
        checkConfig.addProperty("separated", "true");
        checkConfig.addProperty("separatedStaticGroups", "true");
        checkConfig.addProperty("staticGroups",
            "android,androidx,com.android,dalvik,com,gov,junit,libcore,net,org,java,javax");

        final String filePath = getNonCompilablePath("InputFromAndroid.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testSpotify() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportOrderCheck.class);
        checkConfig.addProperty("groups", "android,com,net,junit,org,java,javax");
        checkConfig.addProperty("option", "bottom");
        checkConfig.addProperty("ordered", "true");
        checkConfig.addProperty("separated", "true");
        checkConfig.addProperty("separatedStaticGroups", "true");
        checkConfig.addProperty("staticGroups", "android,com,net,junit,org,java,javax");

        final String filePath = getNonCompilablePath("InputFromSpotify.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

    @Test
    public void testTwitter() throws Exception {
        final DefaultConfiguration checkConfig = createModuleConfig(ImportOrderCheck.class);
        checkConfig.addProperty("caseSensitive", "true");
        checkConfig.addProperty("groups", "android,com.twitter,com,junit,net,org,java,javax");
        checkConfig.addProperty("option", "bottom");
        checkConfig.addProperty("ordered", "true");
        checkConfig.addProperty("separated", "true");
        checkConfig.addProperty("separatedStaticGroups", "true");
        checkConfig.addProperty("staticGroups",
            "android,com.twitter,com,junit,net,org,java,javax");

        final String filePath = getNonCompilablePath("InputFromTwitter.java");
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        final Integer[] warnList = getLinesWithWarn(filePath);
        verify(checkConfig, filePath, expected, warnList);
    }

}
