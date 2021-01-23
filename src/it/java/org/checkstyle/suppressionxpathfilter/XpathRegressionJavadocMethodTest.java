////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_EXPECTED_TAG;
import static com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck.MSG_INVALID_INHERIT_DOC;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck;

public class XpathRegressionJavadocMethodTest extends AbstractXpathTestSupport {

    private final String checkName = JavadocMethodCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocMethodOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocMethodCheck.class);

        final String[] expectedViolation = {
            "14:5: " + getCheckMessage(JavadocMethodCheck.class, MSG_INVALID_INHERIT_DOC),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionJavadocMethodOne']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='uninheritableMethod']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionJavadocMethodOne']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='uninheritableMethod']]/MODIFIERS",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionJavadocMethodOne']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='uninheritableMethod']]/MODIFIERS"
                        + "/LITERAL_PRIVATE");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocMethodTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocMethodCheck.class);

        final String[] expectedViolation = {
            "13:31: " + getCheckMessage(JavadocMethodCheck.class, MSG_EXPECTED_TAG,
                    "@param", "x"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionJavadocMethodTwo']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='checkParam']]/PARAMETERS"
                        + "/PARAMETER_DEF/IDENT[@text='x']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocMethodThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocMethodCheck.class);

        final String[] expectedViolation = {
            "14:13: " + getCheckMessage(JavadocMethodCheck.class, MSG_EXPECTED_TAG,
                    "@param", "<T>"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionJavadocMethodThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='checkTypeParam']]/TYPE_PARAMETERS"
                        + "/TYPE_PARAMETER[./IDENT[@text='T']]",
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionJavadocMethodThree']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='checkTypeParam']]/TYPE_PARAMETERS"
                        + "/TYPE_PARAMETER/IDENT[@text='T']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocMethodFour.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocMethodCheck.class);

        moduleConfig.addAttribute("validateThrows", "true");

        final String[] expectedViolation = {
            "12:30: " + getCheckMessage(JavadocMethodCheck.class, MSG_EXPECTED_TAG,
                    "@throws", "Exception"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionJavadocMethodFour']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                        + "/LITERAL_THROWS/IDENT[@text='Exception']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testFive() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionJavadocMethodFive.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavadocMethodCheck.class);

        moduleConfig.addAttribute("validateThrows", "true");

        final String[] expectedViolation = {
            "13:19: " + getCheckMessage(JavadocMethodCheck.class, MSG_EXPECTED_TAG,
                    "@throws", "org.apache.tools.ant.BuildException"),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/CLASS_DEF[./IDENT[@text='SuppressionXpathRegressionJavadocMethodFive']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='bar']]/SLIST"
                        + "/LITERAL_THROW/EXPR/LITERAL_NEW"
                        + "/DOT[./IDENT[@text='BuildException']]"
                        + "/DOT[./IDENT[@text='ant']]"
                        + "/DOT[./IDENT[@text='tools']]"
                        + "/DOT/IDENT[@text='org']");

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
