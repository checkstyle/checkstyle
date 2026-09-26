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

package com.puppycrawl.tools.checkstyle.checks.javadoc;

import static com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocModuleCheck.MSG_MODULE_JAVADOC_MISSING;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class MissingJavadocModuleCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadocmodule";
    }

    @Test
    public void testDocumented() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/documented/module-info.java"), expected);
    }

    @Test
    public void testDocumentedOpen() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/documentedopen/module-info.java"), expected);
    }

    @Test
    public void testAnnotated() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/annotated/module-info.java"), expected);
    }

    @Test
    public void testMissing() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_MODULE_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/missing/module-info.java"), expected);
    }

    @Test
    public void testMissingAnnotated() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_MODULE_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/missingannotated/module-info.java"), expected);
    }

    @Test
    public void testBlockComment() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_MODULE_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/blockcomment/module-info.java"), expected);
    }

    @Test
    public void testLineComment() throws Exception {
        final String[] expected = {
            "12:1: " + getCheckMessage(MSG_MODULE_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/linecomment/module-info.java"), expected);
    }

    @Test
    public void testDirective() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_MODULE_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/directive/module-info.java"), expected);
    }

    @Test
    public void testAfterOpen() throws Exception {
        final String[] expected = {
            "11:1: " + getCheckMessage(MSG_MODULE_JAVADOC_MISSING),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/afteropen/module-info.java"), expected);
    }

    @Test
    public void testMultipleComments() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getNonCompilablePath("module-info/multiplecomments/module-info.java"), expected);
    }

    @Test
    public void testCompactSourceFile() throws Exception {
        verifyWithInlineConfigParser(getNonCompilablePath(
                "compact/InputMissingJavadocModuleCompactSourceFile.java"),
                CommonUtil.EMPTY_STRING_ARRAY);
    }

}
