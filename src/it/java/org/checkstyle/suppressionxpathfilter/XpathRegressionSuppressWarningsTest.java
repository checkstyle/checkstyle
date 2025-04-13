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
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import static com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck.MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck;

public class XpathRegressionSuppressWarningsTest extends AbstractXpathTestSupport {
    private final String checkName = SuppressWarningsCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testClassDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsClassDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "3:19: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsClassDefinition']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR[./STRING_LITERAL[@text='']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsClassDefinition']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testParameterDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsParameterDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "5:76: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsParameterDefinition']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsParameterDefinition']]"
                + "/PARAMETERS/PARAMETER_DEF[./IDENT"
                + "[@text='bar']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR[./STRING_LITERAL[@text='']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsParameterDefinition']]"
                + "/OBJBLOCK/CTOR_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsParameterDefinition']]"
                + "/PARAMETERS/PARAMETER_DEF[./IDENT"
                + "[@text='bar']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testVariableDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsVariableDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "4:27: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsVariableDefinition']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT"
                + "[@text='foo']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR[./STRING_LITERAL[@text='']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsVariableDefinition']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT"
                + "[@text='foo']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testEnumDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsEnumDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "3:19: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsEnumDefinition']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR[./STRING_LITERAL[@text='']]",

                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsEnumDefinition']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testInterfaceDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsInterfaceDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "3:19: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsInterfaceDefinition']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR[./STRING_LITERAL[@text='']]",

                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsInterfaceDefinition']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testEnumConstantDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsEnumConstantDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "4:27: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsEnumConstantDefinition']]"
                + "/OBJBLOCK/ENUM_CONSTANT_DEF[./IDENT"
                + "[@text='FOO']]"
                + "/ANNOTATIONS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR[./STRING_LITERAL[@text='']]",

                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsEnumConstantDefinition']]"
                + "/OBJBLOCK/ENUM_CONSTANT_DEF[./IDENT"
                + "[@text='FOO']]"
                + "/ANNOTATIONS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testMethodDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsMethodDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "10:23: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
               "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
               + "[@text='InputXpathSuppressWarningsMethodDefinition']]"
               + "/OBJBLOCK/METHOD_DEF[./IDENT"
               + "[@text='getFoo']]"
               + "/MODIFIERS/ANNOTATION[./IDENT"
               + "[@text='SuppressWarnings']]"
               + "/EXPR[./STRING_LITERAL[@text='']]",

               "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
               + "[@text='InputXpathSuppressWarningsMethodDefinition']]"
               + "/OBJBLOCK/METHOD_DEF[./IDENT"
               + "[@text='getFoo']]"
               + "/MODIFIERS/ANNOTATION[./IDENT"
               + "[@text='SuppressWarnings']]"
               + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testAnnotationDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsAnnotationDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "3:19: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ANNOTATION_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsAnnotationDefinition']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR[./STRING_LITERAL[@text='']]",

                "/COMPILATION_UNIT/ANNOTATION_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsAnnotationDefinition']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testAnnotationFieldDefinition() throws Exception {
        final File fileToCheck =
                new File(getPath("InputXpathSuppressWarningsAnnotationFieldDefinition.java"));

        final DefaultConfiguration configuration =
                createModuleConfig(SuppressWarningsCheck.class);

        final String[] expectedViolations = {
            "4:27: " + getCheckMessage(SuppressWarningsCheck.class,
                    MSG_KEY_SUPPRESSED_WARNING_NOT_ALLOWED,
                    ""),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ANNOTATION_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsAnnotationFieldDefinition']]"
                + "/OBJBLOCK/ANNOTATION_FIELD_DEF[./IDENT"
                + "[@text='foo']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR[./STRING_LITERAL[@text='']]",

                "/COMPILATION_UNIT/ANNOTATION_DEF[./IDENT"
                + "[@text='InputXpathSuppressWarningsAnnotationFieldDefinition']]"
                + "/OBJBLOCK/ANNOTATION_FIELD_DEF[./IDENT"
                + "[@text='foo']]"
                + "/MODIFIERS/ANNOTATION[./IDENT"
                + "[@text='SuppressWarnings']]"
                + "/EXPR/STRING_LITERAL[@text='']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }
}
