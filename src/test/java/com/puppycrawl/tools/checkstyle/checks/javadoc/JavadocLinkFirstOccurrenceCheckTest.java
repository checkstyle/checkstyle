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
            "22:12: " + getCheckMessage(MSG_KEY, "String"),
            "38:12: " + getCheckMessage(MSG_KEY, "String"),
            "38:46: " + getCheckMessage(MSG_KEY, "Object"),
            "48:12: " + getCheckMessage(MSG_KEY, "String"),
            "56:12: " + getCheckMessage(MSG_KEY, "String"),
            "68:36: " + getCheckMessage(MSG_KEY, "String"),
            "91:15: " + getCheckMessage(MSG_KEY, "String"),
            "92:20: " + getCheckMessage(MSG_KEY, "String"),
            "101:13: " + getCheckMessage(MSG_KEY, "String"),
            "102:13: " + getCheckMessage(MSG_KEY, "Object"),
            "108:34: " + getCheckMessage(MSG_KEY, "String"),
        };
        verifyWithInlineConfigParser(
                getPath("InputJavadocLinkFirstOccurrence.java"), expected);
    }

}
