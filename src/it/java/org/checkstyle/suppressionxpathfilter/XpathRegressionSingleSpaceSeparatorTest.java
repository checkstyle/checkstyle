///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.SingleSpaceSeparatorCheck;

public class XpathRegressionSingleSpaceSeparatorTest extends AbstractXpathTestSupport {

    private final String checkName = SingleSpaceSeparatorCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testSingleSpaceSeparator() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathSingleSpaceSeparator.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SingleSpaceSeparatorCheck.class);

        final String[] expectedViolation = {
            "4:11: " + getCheckMessage(SingleSpaceSeparatorCheck.class,
                    SingleSpaceSeparatorCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathSingleSpaceSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF/IDENT[@text='bad']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testValidateComments() throws Exception {
        final File fileToProcess = new File(getPath(
            "InputXpathSingleSpaceSeparatorValidateComments.java"
        ));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(SingleSpaceSeparatorCheck.class);
        moduleConfig.addProperty("validateComments", "true");

        final String[] expectedViolation = {
            "4:17: " + getCheckMessage(SingleSpaceSeparatorCheck.class,
                    SingleSpaceSeparatorCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF[."
                + "/IDENT[@text='InputXpathSingleSpaceSeparatorValidateComments']]"
                + "/OBJBLOCK/SINGLE_LINE_COMMENT[./COMMENT_CONTENT"
                + "[@text=' an invalid comment // warn\\n']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
