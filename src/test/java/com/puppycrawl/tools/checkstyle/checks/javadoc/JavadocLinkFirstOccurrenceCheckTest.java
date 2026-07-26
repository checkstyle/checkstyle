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

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class JavadocLinkFirstOccurrenceCheckTest extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/javadoc/javadoclinkfirstoccurrence";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expected = {
            "18:12: " + getCheckMessage(MSG_KEY, "String"),
            "30:12: " + getCheckMessage(MSG_KEY, "String"),
            "30:46: " + getCheckMessage(MSG_KEY, "Object"),
            "36:12: " + getCheckMessage(MSG_KEY, "String"),
            "72:15: " + getCheckMessage(MSG_KEY, "String"),
            "73:20: " + getCheckMessage(MSG_KEY, "String"),
            "82:13: " + getCheckMessage(MSG_KEY, "String"),
            "83:13: " + getCheckMessage(MSG_KEY, "Object"),
            "89:15: " + getCheckMessage(MSG_KEY, "#method"),
            "95:15: " + getCheckMessage(MSG_KEY, "String#length()"),
            "110:12: " + getCheckMessage(MSG_KEY, "java.lang.String"),
            "116:12: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkFirstOccurrence.java"), expected);
    }

    @Test
    public void testStarImport() throws Exception {
        final String[] expected = {
            "13:12: " + getCheckMessage(MSG_KEY, "java.util.List"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkFirstOccurrenceStarImport.java"), expected);
    }

}
