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

package com.puppycrawl.tools.checkstyle.checks.whitespace;

import static com.puppycrawl.tools.checkstyle.checks.whitespace.NoLineWrapCheck.MSG_KEY;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class NoLineWrapCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace/nolinewrap";
    }

    @Test
    public void testCaseWithoutLineWrapping() throws Exception {
        final String[] expected = CommonUtil.EMPTY_STRING_ARRAY;
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapGood.java"), expected);
    }

    @Test
    public void testDefaultTokensLineWrapping() throws Exception {
        final String[] expected = {
            "8:1: " + getCheckMessage(MSG_KEY, "package"),
            "13:1: " + getCheckMessage(MSG_KEY, "import"),
            "17:1: " + getCheckMessage(MSG_KEY, "import"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapBad.java"), expected);
    }

    @Test
    public void testCustomTokensLineWrapping()
            throws Exception {
        final String[] expected = {
            "14:1: " + getCheckMessage(MSG_KEY, "import"),
            "18:1: " + getCheckMessage(MSG_KEY, "import"),
            "21:1: " + getCheckMessage(MSG_KEY, "CLASS_DEF"),
            "24:9: " + getCheckMessage(MSG_KEY, "METHOD_DEF"),
            "31:1: " + getCheckMessage(MSG_KEY, "ENUM_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapBad2.java"), expected);
    }

    @Test
    public void testNoLineWrapRecordsAndCompactCtors()
            throws Exception {

        final String[] expected = {
            "14:9: " + getCheckMessage(MSG_KEY, "CTOR_DEF"),
            "20:5: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "29:9: " + getCheckMessage(MSG_KEY, "CTOR_DEF"),
            "35:5: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "37:9: " + getCheckMessage(MSG_KEY, "COMPACT_CTOR_DEF"),
            "42:5: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "44:9: " + getCheckMessage(MSG_KEY, "COMPACT_CTOR_DEF"),
            "49:9: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "51:13: " + getCheckMessage(MSG_KEY, "COMPACT_CTOR_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapRecordsAndCompactCtors.java"),
                expected);
    }

    @Test
    public void testNoLineWrapModuleImport() throws Exception {
        final String[] expected = {
            "15:1: " + getCheckMessage(MSG_KEY, "import"),
            "22:1: " + getCheckMessage(MSG_KEY, "import"),
        };
        verifyWithInlineConfigParser(
                getNonCompilablePath("InputNoLineWrapModuleImport.java"), expected);
    }

    @Test
    public void testNoLineWrapWithSkipAnnotationsFalse() throws Exception {
        final String[] expected = {
            "10:1: " + getCheckMessage(MSG_KEY, "CLASS_DEF"),
            "14:5: " + getCheckMessage(MSG_KEY, "CTOR_DEF"),
            "19:5: " + getCheckMessage(MSG_KEY, "METHOD_DEF"),
            "25:5: " + getCheckMessage(MSG_KEY, "ENUM_DEF"),
            "31:5: " + getCheckMessage(MSG_KEY, "RECORD_DEF"),
            "34:9: " + getCheckMessage(MSG_KEY, "COMPACT_CTOR_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapSkipAnnotationsFalse.java"), expected);
    }

    @Test
    public void testNoLineWrapWithSkipAnnotationsTrue() throws Exception {
        final String[] expected = {
            "22:5: " + getCheckMessage(MSG_KEY, "METHOD_DEF"),
        };
        verifyWithInlineConfigParser(
                getPath("InputNoLineWrapSkipAnnotationsTrue.java"), expected);
    }

}
