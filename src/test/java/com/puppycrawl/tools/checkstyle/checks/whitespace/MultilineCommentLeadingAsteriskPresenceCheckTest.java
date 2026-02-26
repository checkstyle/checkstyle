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

import static com.puppycrawl.tools.checkstyle.checks.whitespace.MultilineCommentLeadingAsteriskPresenceCheck.MSG_MISSING_ASTERISK;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;

public class MultilineCommentLeadingAsteriskPresenceCheckTest
    extends AbstractModuleTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/checks/whitespace"
                + "/multilinecommentleadingasteriskpresence";
    }

    @Test
    public void testMultilineCommentLeadingAsteriskPresence() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "12:5: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "19:5: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "28:5: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "56:5: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "73:5: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "82:5: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "91:5: " + getCheckMessage(MSG_MISSING_ASTERISK),
        };
        verifyWithInlineConfigParser(getPath(
                "InputMultilineCommentLeadingAsteriskPresence.java"), expected);
    }

    @Test
    public void testMultilineCommentLeadingAsteriskPresenceBetweenClasses() throws Exception {
        final String[] expected = {
            "1:1: " + getCheckMessage(MSG_MISSING_ASTERISK),
            "14:1: " + getCheckMessage(MSG_MISSING_ASTERISK),
        };
        verifyWithInlineConfigParser(getPath(
                "InputMultilineCommentLeadingAsteriskPresenceBetweenClasses.java"), expected);
    }
}
