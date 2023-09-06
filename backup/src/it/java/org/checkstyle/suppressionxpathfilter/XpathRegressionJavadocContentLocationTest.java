///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocContentLocationCheck;

public class XpathRegressionJavadocContentLocationTest extends AbstractXpathTestSupport {

    private final String checkName = JavadocContentLocationCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocContentLocationOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocContentLocationCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(JavadocContentLocationCheck.class,
                    JavadocContentLocationCheck.MSG_JAVADOC_CONTENT_SECOND_LINE),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/INTERFACE_DEF"
                + "[./IDENT[@text='SuppressionXpathRegressionJavadocContentLocationOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/BLOCK_COMMENT_BEGIN"
                + "[./COMMENT_CONTENT[@text='* Text. // warn\\n     ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocContentLocationTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocContentLocationCheck.class);

        moduleConfig.addProperty("location", "first_line");

        final String[] expectedViolation = {
            "5:16: " + getCheckMessage(JavadocContentLocationCheck.class,
                    JavadocContentLocationCheck.MSG_JAVADOC_CONTENT_FIRST_LINE),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                    + "[@text='SuppressionXpathRegressionJavadocContentLocationTwo']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/TYPE/BLOCK_COMMENT_BEGIN"
                    + "[./COMMENT_CONTENT[@text='*\\n     * Text.\\n     ']]"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
