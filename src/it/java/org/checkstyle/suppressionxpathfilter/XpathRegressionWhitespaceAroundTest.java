////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.whitespace.WhitespaceAroundCheck;

public class XpathRegressionWhitespaceAroundTest extends AbstractXpathTestSupport {

    private final String checkName = WhitespaceAroundCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testWhitespaceAroundNotPreceded() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionWhitespaceAroundNotPreceded.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(WhitespaceAroundCheck.class);

        final String[] expectedViolation = {
            "4:12: " + getCheckMessage(WhitespaceAroundCheck.class,
                    WhitespaceAroundCheck.MSG_WS_NOT_PRECEDED, "="),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT["
                + "@text='SuppressionXpathRegressionWhitespaceAroundNotPreceded']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/ASSIGN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testWhitespaceAroundNotFollowed() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionWhitespaceAroundNotFollowed.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(WhitespaceAroundCheck.class);

        final String[] expectedViolation = {
            "4:13: " + getCheckMessage(WhitespaceAroundCheck.class,
                    WhitespaceAroundCheck.MSG_WS_NOT_FOLLOWED, "="),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT["
                + "@text='SuppressionXpathRegressionWhitespaceAroundNotFollowed']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='bad']]/ASSIGN"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
