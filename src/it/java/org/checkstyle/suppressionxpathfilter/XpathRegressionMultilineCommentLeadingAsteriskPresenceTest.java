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

package org.checkstyle.suppressionxpathfilter;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.MultilineCommentLeadingAsteriskPresenceCheck;

public class XpathRegressionMultilineCommentLeadingAsteriskPresenceTest
        extends AbstractXpathTestSupport {

    private final String checkName =
                MultilineCommentLeadingAsteriskPresenceCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter"
            + "/multilinecommentleadingasteriskpresence";
    }

    @Test
    public void testMultilineCommentLeadingAsteriskPresence() throws Exception {
        final String[] expectedViolation = {
            "11:5: " + getCheckMessage(
                MultilineCommentLeadingAsteriskPresenceCheck.class,
                MultilineCommentLeadingAsteriskPresenceCheck.MSG_MISSING_ASTERISK, "12"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathMultilineCommentLeadingAsteriskPresenceOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/MODIFIERS"
                    + "/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='  // warn\\n        line5\\n     ']]"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathMultilineCommentLeadingAsteriskPresenceOne.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testMultilineCommentLeadingAsteriskPresenceTwo() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(
                MultilineCommentLeadingAsteriskPresenceCheck.class,
                MultilineCommentLeadingAsteriskPresenceCheck.MSG_MISSING_ASTERISK, "11"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathMultilineCommentLeadingAsteriskPresenceTwo']]"
                    + "/MODIFIERS/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text=' // warn\\n   line4\\n ']]"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathMultilineCommentLeadingAsteriskPresenceTwo.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testMultilineCommentLeadingAsteriskPresenceThree() throws Exception {
        final String[] expectedViolation = {
            "12:9: " + getCheckMessage(
                MultilineCommentLeadingAsteriskPresenceCheck.class,
                MultilineCommentLeadingAsteriskPresenceCheck.MSG_MISSING_ASTERISK, "13"),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathMultilineCommentLeadingAsteriskPresenceThree']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/BLOCK_COMMENT_BEGIN"
                    + "[./COMMENT_CONTENT[@text='  // warn\\n           line6\\n         ']]"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathMultilineCommentLeadingAsteriskPresenceThree.java"),
            expectedXpathQueries,
            expectedViolation);
    }

}
