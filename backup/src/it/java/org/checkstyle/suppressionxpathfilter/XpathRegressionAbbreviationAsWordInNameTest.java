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
import com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck;

public class XpathRegressionAbbreviationAsWordInNameTest extends AbstractXpathTestSupport {

    private final String checkName = AbbreviationAsWordInNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testAnnotation() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameAnnotation.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "5:16: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "ANNOTATION", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameAnnotation']]"
                    + "/OBJBLOCK/ANNOTATION_DEF/IDENT[@text='ANNOTATION']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testAnnotationField() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameAnnotationField.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "5:12: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "ANNOTATION_FIELD", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/ANNOTATION_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameAnnotationField']]"
                    + "/OBJBLOCK/ANNOTATION_FIELD_DEF/IDENT[@text='ANNOTATION_FIELD']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testClass() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "5:11: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "CLASS", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameClass']]"
                    + "/OBJBLOCK/CLASS_DEF/IDENT[@text='CLASS']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testEnum() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameEnum.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "5:10: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "ENUMERATION", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameEnum']]"
                    + "/OBJBLOCK/ENUM_DEF/IDENT[@text='ENUMERATION']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testField() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameField.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "5:9: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "FIELD", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameField']]"
                    + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='FIELD']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testInterface() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameInterface.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "5:15: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "INTERFACE", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameInterface']]"
                    + "/OBJBLOCK/INTERFACE_DEF/IDENT[@text='INTERFACE']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testMethod() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameMethod.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "5:10: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "METHOD", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameMethod']]"
                    + "/OBJBLOCK/METHOD_DEF/IDENT[@text='METHOD']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testParameter() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameParameter.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "5:21: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "PARAMETER", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameParameter']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF/IDENT[@text='PARAMETER']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testVariable() throws Exception {
        final File fileToProcess = new File(getPath(
                "SuppressionXpathRegressionAbbreviationAsWordInNameVariable.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(AbbreviationAsWordInNameCheck.class);

        final String[] expectedViolation = {
            "6:13: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "VARIABLE", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='SuppressionXpathRegressionAbbreviationAsWordInNameVariable']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/SLIST/VARIABLE_DEF/IDENT[@text='VARIABLE']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
