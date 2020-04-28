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
import com.puppycrawl.tools.checkstyle.checks.coding.UnnecessarySemicolonInTryWithResourcesCheck;

public class XpathRegressionUnnecessarySemicolonInTryWithResourcesTest
        extends AbstractXpathTestSupport {

    private final String checkName =
            UnnecessarySemicolonInTryWithResourcesCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess = new File(
                getPath("SuppressionXpathRegressionUnnecessarySemicolonInTryWithResources.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnnecessarySemicolonInTryWithResourcesCheck.class);
        final String[] expectedViolation = {
            "11:43: " + getCheckMessage(UnnecessarySemicolonInTryWithResourcesCheck.class,
                UnnecessarySemicolonInTryWithResourcesCheck.MSG_SEMI),
            "12:76: " + getCheckMessage(UnnecessarySemicolonInTryWithResourcesCheck.class,
                UnnecessarySemicolonInTryWithResourcesCheck.MSG_SEMI),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text="
                        + "'SuppressionXpathRegressionUnnecessarySemicolonInTryWithResources']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='m']]/SLIST/LITERAL_TRY"
                        + "/RESOURCE_SPECIFICATION/SEMI"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testAllowWhenNoBraceAfterSemicolon() throws Exception {
        final File fileToProcess = new File(getPath(
            "SuppressionXpathRegressionUnnecessarySemicolonInTryWithResourcesNoBrace.java"
        ));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessarySemicolonInTryWithResourcesCheck.class);
        moduleConfig.addAttribute("allowWhenNoBraceAfterSemicolon", "false");

        final String[] expectedViolation = {
            "8:44: " + getCheckMessage(UnnecessarySemicolonInTryWithResourcesCheck.class,
                UnnecessarySemicolonInTryWithResourcesCheck.MSG_SEMI),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/CLASS_DEF[./IDENT[@text="
                    + "'SuppressionXpathRegressionUnnecessarySemicolonInTryWithResourcesNoBrace']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_TRY/RESOURCE_SPECIFICATION/SEMI"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
