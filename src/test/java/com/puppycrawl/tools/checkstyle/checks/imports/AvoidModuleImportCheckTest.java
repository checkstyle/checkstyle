///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.checks.imports;

import static com.puppycrawl.tools.checkstyle.checks.imports.AvoidModuleImportCheck.MSG_COUNT;
import static com.puppycrawl.tools.checkstyle.checks.imports.AvoidModuleImportCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class AvoidModuleImportCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/imports/avoidmoduleimport";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY, "java.base"),
            "14:1: " + getCheckMessage(MSG_KEY, "java.xml"),
            "16:1: " + getCheckMessage(MSG_KEY, "java.logging"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAvoidModuleImportDefault.java"), expected);
    }

    @Test
    public void testExcludes() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY, "java.net.http"),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAvoidModuleImportExcludes.java"), expected);
    }

    @Test
    public void testMaxAllowed() throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_COUNT, 2),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAvoidModuleImportMaxAllowed.java"), expected);
    }

    @Test
    public void testMaxAllowedAndExcluded() throws Exception {
        final String[] expected = {
            "13:1: " + getCheckMessage(MSG_COUNT, 1),
        };

        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAvoidModuleImportMaxAllowedAndExcluded.java"),
                expected);
    }

    @Test
    public void testMaxAllowedMultipleFiles() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAvoidModuleImportFile1.java"),
                getNonCompilablePath("InputAvoidModuleImportFile2.java"),
                expected);
    }

    @Test
    public void testSingleNameModule() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_KEY, "someModule"),
            "14:1: " + getCheckMessage(MSG_KEY, "otherModule"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputAvoidModuleImportSingleName.java"), expected);
    }

    @Test
    public void testCompactSourceFileDefault() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY, "java.sql"),
            "12:1: " + getCheckMessage(MSG_KEY, "java.base"),
        };

        final String filename = "compact/InputAvoidModuleImportDefaultCompactSource.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;

        final String filename = "compact/InputAvoidModuleImportCompactSource.java";
        verifyWithInlineConfigParser(
                getNonCompilablePath(filename), expected);
    }

}
