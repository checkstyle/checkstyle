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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocLinkFirstOccurrenceCheck.MSG_KEY;

import java.io.File;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.utils.CommonUtil;

public class JavadocLinkFirstOccurrenceCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoclinkfirstoccurrence";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "17:12: " + getCheckMessage(MSG_KEY, "String"),
            "29:12: " + getCheckMessage(MSG_KEY, "String"),
            "29:46: " + getCheckMessage(MSG_KEY, "Object"),
            "35:12: " + getCheckMessage(MSG_KEY, "String"),
            "71:15: " + getCheckMessage(MSG_KEY, "String"),
            "72:20: " + getCheckMessage(MSG_KEY, "String"),
            "81:13: " + getCheckMessage(MSG_KEY, "String"),
            "82:13: " + getCheckMessage(MSG_KEY, "Object"),
            "88:15: " + getCheckMessage(MSG_KEY, "#method"),
            "94:15: " + getCheckMessage(MSG_KEY, "String#length()"),
            "109:12: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "115:12: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkFirstOccurrence.java"), expected);
    }

    @Test
    public void testAdditionalCases() throws Exception {
        final String[] expected = {
            "18:12: " + getCheckMessage(MSG_KEY, "java.util.Map.Entry"),
            "39:12: " + getCheckMessage(MSG_KEY, "String"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkFirstOccurrenceAdditional.java"), expected);
    }

    @Test
    public void testMemberFullyQualifiedName() throws Exception {
        final String[] expected = {
            "12:12: " + getCheckMessage(MSG_KEY, "java.util.List#add(Object)"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkFirstOccurrenceMemberFullyQualifiedName.java"), expected);
    }

    @Test
    public void testStarImport() throws Exception {
        final String[] expected = {
            "12:12: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkFirstOccurrenceStarImport.java"), expected);
    }

    @Test
    public void testEmptyFile() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocLinkFirstOccurrenceCheck.class);
        final String path = getPath("InputJavadocLinkFirstOccurrenceEmptyFile.java");
        verify(createChecker(checkConfig), new File[] {
            new File(path),
        }, path, CommonUtil.EMPTY_STRING_ARRAY);
    }

    @Test
    public void testClearStateBetweenFiles() throws Exception {
        final DefaultConfiguration checkConfig =
                createModuleConfig(JavadocLinkFirstOccurrenceCheck.class);
        final String pathFile2 = getPath("InputJavadocLinkFirstOccurrenceClearState2.java");
        final String[] expected = {
            "6:36: " + getCheckMessage(MSG_KEY, "String"),
        };
        verify(createChecker(checkConfig), new File[] {
            new File(getPath("InputJavadocLinkFirstOccurrenceClearState1.java")),
            new File(pathFile2),
        }, pathFile2, expected);
    }

}
