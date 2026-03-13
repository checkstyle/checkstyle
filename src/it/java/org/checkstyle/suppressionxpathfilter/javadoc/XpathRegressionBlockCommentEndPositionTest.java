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

package org.checkstyle.suppressionxpathfilter.javadoc;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.BlockCommentEndPositionCheck;

public class XpathRegressionBlockCommentEndPositionTest extends AbstractXpathTestSupport {

    private final String checkName = BlockCommentEndPositionCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/javadoc/blockcommentendposition";
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathBlockCommentEndPositionOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(BlockCommentEndPositionCheck.class);

        final String[] expectedViolation = {
            "6:17: " + getCheckMessage(BlockCommentEndPositionCheck.class,
                    BlockCommentEndPositionCheck.MSG_BLOCK_COMMENT_END,
                    "BLOCK_COMMENT_END"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathBlockCommentEndPositionOne']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                    + "/TYPE/BLOCK_COMMENT_BEGIN"
                    + "[./COMMENT_CONTENT[@text='*\\n     * Comment. ']]"
                    + "/BLOCK_COMMENT_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathBlockCommentEndPositionTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(BlockCommentEndPositionCheck.class);

        moduleConfig.addProperty("strategy", "alone");

        final String[] expectedViolation = {
            "5:14: " + getCheckMessage(BlockCommentEndPositionCheck.class,
                    BlockCommentEndPositionCheck.MSG_BLOCK_COMMENT_END,
                    "BLOCK_COMMENT_END"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathBlockCommentEndPositionTwo']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]"
                    + "/TYPE/BLOCK_COMMENT_BEGIN[./COMMENT_CONTENT"
                    + "[@text='* Text. ']]/BLOCK_COMMENT_END"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
