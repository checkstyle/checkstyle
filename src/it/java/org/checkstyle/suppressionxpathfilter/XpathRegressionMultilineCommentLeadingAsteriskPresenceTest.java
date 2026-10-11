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

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.MultilineCommentLeadingAsteriskPresenceCheck;

public class XpathRegressionMultilineCommentLeadingAsteriskPresenceTest
        extends AbstractXpathTestSupport {

    private final String checkName =
                MultilineCommentLeadingAsteriskPresenceCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testMultilineCommentLeadingAsteriskPresence() throws Exception {
        final File fileProcess =
            new File(getPath("InputXpathMultilineCommentLeadingAsteriskPresenceOne.java"));
        final DefaultConfiguration config =
            createModuleConfig(MultilineCommentLeadingAsteriskPresenceCheck.class);

        final String[] expected = {
            "4:5: " + getCheckMessage(
                MultilineCommentLeadingAsteriskPresenceCheck.class,
                MultilineCommentLeadingAsteriskPresenceCheck.MSG_MISSING_ASTERISK, "5"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathMultilineCommentLeadingAsteriskPresenceOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/MODIFIERS"
                    + "/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='  // warn\\n        line5\\n     ']]"
        );

        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testMultilineCommentLeadingAsteriskPresenceTwo() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathMultilineCommentLeadingAsteriskPresenceTwo.java"));
        final DefaultConfiguration config =
                createModuleConfig(MultilineCommentLeadingAsteriskPresenceCheck.class);

        final String[] expected = {
            "3:1: " + getCheckMessage(
                    MultilineCommentLeadingAsteriskPresenceCheck.class,
                    MultilineCommentLeadingAsteriskPresenceCheck.MSG_MISSING_ASTERISK, "4"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathMultilineCommentLeadingAsteriskPresenceTwo']]"
                        + "/MODIFIERS/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                        + "[@text=' // warn\\n   line4\\n ']]"
        );

        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

    @Test
    public void testMultilineCommentLeadingAsteriskPresenceThree() throws Exception {
        final File fileProcess =
                new File(getPath("InputXpathMultilineCommentLeadingAsteriskPresenceThree.java"));
        final DefaultConfiguration config =
                createModuleConfig(MultilineCommentLeadingAsteriskPresenceCheck.class);

        final String[] expected = {
            "5:9: " + getCheckMessage(
                    MultilineCommentLeadingAsteriskPresenceCheck.class,
                    MultilineCommentLeadingAsteriskPresenceCheck.MSG_MISSING_ASTERISK, "6"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathMultilineCommentLeadingAsteriskPresenceThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/BLOCK_COMMENT_BEGIN"
                        + "[./COMMENT_CONTENT[@text='  // warn\\n           line6\\n         ']]"
        );

        runVerifications(config, fileProcess, expected, expectedXpathQueries);
    }

}
