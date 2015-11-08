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

package com.google.checkstyle.test.chapter2filebasic.rule231filetab;

import java.io.File;

import org.junit.BeforeClass;
import org.junit.Test;

import com.google.checkstyle.test.base.BaseCheckTestSupport;
import com.google.checkstyle.test.base.ConfigurationBuilder;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck;

public class FileTabCharacterTest extends BaseCheckTestSupport {

    private static ConfigurationBuilder builder;

    @BeforeClass
    public static void setConfigurationBuilder() {
        builder = new ConfigurationBuilder(new File("src/it/"));
    }

    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration aConfig) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(aConfig);
        return dc;
    }

    @Test
    public void fileTabTest() throws Exception {

        final DefaultConfiguration checkConfig = createConfig(true);
        final String[] expected = {
            "8:25: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "51:5: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "121:35: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "122:64: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "130:9: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "131:10: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "132:1: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "133:3: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
            "134:3: " + getCheckMessage(FileTabCharacterCheck.class, "containsTab"),
        };

        final String filePath = builder.getFilePath("InputFileTabCharacter");
        final Integer[] warnList = builder.getLinesWithWarn(filePath);
        verify(createChecker(checkConfig), filePath,
            expected, warnList);
    }

    /**
     * Creates a configuration that is functionally close to that in the docs.
     */
    private static DefaultConfiguration createConfig(boolean verbose) {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FileTabCharacterCheck.class);
        checkConfig.addAttribute("eachLine", Boolean.toString(verbose));
        return checkConfig;
    }
}
