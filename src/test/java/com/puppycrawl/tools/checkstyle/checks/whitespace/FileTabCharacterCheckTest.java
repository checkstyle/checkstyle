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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.CONTAINS_TAB;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.FILE_CONTAINS_TAB;

import java.io.File;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public class FileTabCharacterCheckTest
    extends BaseCheckTestSupport {
    @Override
    protected DefaultConfiguration createCheckerConfig(
        Configuration checkConfig) {
        final DefaultConfiguration dc = new DefaultConfiguration("root");
        dc.addChild(checkConfig);
        return dc;
    }

    @Test
    public void testDefault() throws Exception {
        final DefaultConfiguration checkConfig = createConfig(false);
        final String[] expected = {
            "19:25: " + getCheckMessage(FILE_CONTAINS_TAB),
        };
        final File[] files = {
            new File(getPath("InputSimple.java")),
        };
        verify(createChecker(checkConfig), files, getPath("InputSimple.java"),
            expected);
    }

    @Test
    public void testVerbose() throws Exception {
        final DefaultConfiguration checkConfig = createConfig(true);
        final String[] expected = {
            "19:25: " + getCheckMessage(CONTAINS_TAB),
            "145:35: " + getCheckMessage(CONTAINS_TAB),
            "146:64: " + getCheckMessage(CONTAINS_TAB),
            "154:9: " + getCheckMessage(CONTAINS_TAB),
            "155:10: " + getCheckMessage(CONTAINS_TAB),
            "156:1: " + getCheckMessage(CONTAINS_TAB),
            "157:3: " + getCheckMessage(CONTAINS_TAB),
            "158:3: " + getCheckMessage(CONTAINS_TAB),
        };
        final File[] files = {
            new File(getPath("InputSimple.java")),
        };
        verify(createChecker(checkConfig), files, getPath("InputSimple.java"),
            expected);
    }

    @Test
    public void testBadFile() throws Exception {
        final DefaultConfiguration checkConfig = createConfig(false);
        final String path = getPath("Claira");
        String exceptionMessage = " (No such file or directory)";
        if (System.getProperty("os.name")
                .toLowerCase().startsWith("windows")) {
            exceptionMessage = " (The system cannot find the file specified)";
        }

        final String[] expected = {
            "0: Got an exception - " + path + exceptionMessage,
        };
        final File[] files = {
            new File(path),
        };
        verify(createChecker(checkConfig), files, path, expected);
    }

    /**
     * Creates a configuration that is functionally close to that in the docs.
     * @param verbose verbose mode
     */
    private DefaultConfiguration createConfig(boolean verbose) {
        final DefaultConfiguration checkConfig =
            createCheckConfig(FileTabCharacterCheck.class);
        checkConfig.addAttribute("eachLine", Boolean.toString(verbose));
        return checkConfig;
    }
}
