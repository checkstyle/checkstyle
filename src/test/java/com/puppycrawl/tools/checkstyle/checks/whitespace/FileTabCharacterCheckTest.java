////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2022 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_CONTAINS_TAB;
import static com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck.MSG_FILE_CONTAINS_TAB;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.api.Violation;

public class FileTabCharacterCheckTest
    extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/filetabcharacter";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "22:25: " + getCheckMessage(MSG_FILE_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterSimple.java"),
            expected);
    }

    @Test
    public void testVerbose() throws Exception {
        final String[] expected = {
            "22:25: " + getCheckMessage(MSG_CONTAINS_TAB),
            "148:35: " + getCheckMessage(MSG_CONTAINS_TAB),
            "151:28: " + getCheckMessage(MSG_CONTAINS_TAB),
            "159:9: " + getCheckMessage(MSG_CONTAINS_TAB),
            "160:10: " + getCheckMessage(MSG_CONTAINS_TAB),
            "161:1: " + getCheckMessage(MSG_CONTAINS_TAB),
            "162:3: " + getCheckMessage(MSG_CONTAINS_TAB),
            "163:3: " + getCheckMessage(MSG_CONTAINS_TAB),
        };
        verifyWithInlineConfigParser(
                getPath("InputFileTabCharacterSimple1.java"),
            expected);
    }

    @Test
    public void testBadFile() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(FileTabCharacterCheck.class);
        checkConfig.addProperty("eachLine", "false");
        final String path = getPath("Claira");
        final String exceptionMessage = " (No such file or directory)";
        final Violation violation = new Violation(1,
                Definitions.CHECKSTYLE_BUNDLE, "general.exception",
                new String[] {path + exceptionMessage}, null, getClass(), null);

        final String[] expected = {
            "1: " + violation.getViolation(),
        };
        verify(createChecker(checkConfig), path, expected);
    }

}
