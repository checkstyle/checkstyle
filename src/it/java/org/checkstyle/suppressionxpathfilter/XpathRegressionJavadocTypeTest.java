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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_MISSING_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck.MSG_TAG_FORMAT;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTagInfo;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck;

public class XpathRegressionJavadocTypeTest extends AbstractXpathTestSupport {

    private final String checkName = JavadocTypeCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocTypeOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocTypeCheck.class);

        moduleConfig.addProperty("authorFormat", "\\S");

        final String[] expectedViolation = {
            "6:1: " + getCheckMessage(JavadocTypeCheck.class,
                MSG_MISSING_TAG, "@author"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionJavadocTypeOne']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionJavadocTypeOne']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionJavadocTypeOne']]"
                        + "/MODIFIERS/LITERAL_PUBLIC");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocTypeTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocTypeCheck.class);

        moduleConfig.addProperty("authorFormat", "foo");

        final String[] expectedViolation = {
            "7:1: " + getCheckMessage(JavadocTypeCheck.class,
                MSG_TAG_FORMAT, "@author", "foo"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionJavadocTypeTwo']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionJavadocTypeTwo']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='SuppressionXpathRegressionJavadocTypeTwo']]"
                        + "/MODIFIERS/LITERAL_PUBLIC");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocTypeThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocTypeCheck.class);

        final String[] expectedViolation = {
            "8:1: " + getCheckMessage(JavadocTypeCheck.class,
                MSG_MISSING_TAG, JavadocTagInfo.PARAM.getText() + " <C>"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionJavadocTypeThree']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionJavadocTypeThree']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='SuppressionXpathRegressionJavadocTypeThree']]"
                        + "/MODIFIERS/LITERAL_PUBLIC");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
