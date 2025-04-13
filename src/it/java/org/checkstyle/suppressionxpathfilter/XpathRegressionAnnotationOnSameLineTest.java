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
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck;

public class XpathRegressionAnnotationOnSameLineTest extends AbstractXpathTestSupport {

    private final String checkName = AnnotationOnSameLineCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
            new File(getPath(
                "InputXpathAnnotationOnSameLineMethod.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(AnnotationOnSameLineCheck.class);

        moduleConfig.addProperty("tokens",
            "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, "
                + "CTOR_DEF, VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, "
                + "LITERAL_THROWS, IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, "
                + "ANNOTATION_FIELD_DEF");

        final String[] expectedViolation = {
            "6:5: " + getCheckMessage(AnnotationOnSameLineCheck.class,
                AnnotationOnSameLineCheck.MSG_KEY_ANNOTATION_ON_SAME_LINE,
                "Deprecated"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotationOnSameLineMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='getX']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotationOnSameLineMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='getX']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotationOnSameLineMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='getX']]/MODIFIERS"
                + "/ANNOTATION[./IDENT[@text='Deprecated']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotationOnSameLineMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='getX']]"
                + "/MODIFIERS/ANNOTATION[./IDENT[@text='Deprecated']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
            new File(getPath(
                "InputXpathAnnotationOnSameLineField.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(AnnotationOnSameLineCheck.class);

        final String[] expectedViolation = {
            "7:5: " + getCheckMessage(AnnotationOnSameLineCheck.class,
                AnnotationOnSameLineCheck.MSG_KEY_ANNOTATION_ON_SAME_LINE,
                "Deprecated"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotationOnSameLineField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='names']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotationOnSameLineField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='names']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotationOnSameLineField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='names']]/MODIFIERS"
                + "/ANNOTATION[./IDENT[@text='Deprecated']]",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAnnotationOnSameLineField']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='names']]/MODIFIERS"
                + "/ANNOTATION[./IDENT[@text='Deprecated']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
            new File(getPath(
                "InputXpathAnnotationOnSameLineInterface.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(AnnotationOnSameLineCheck.class);
        moduleConfig.addProperty("tokens", "CLASS_DEF, INTERFACE_DEF, ENUM_DEF, METHOD_DEF, "
            + "CTOR_DEF, VARIABLE_DEF, PARAMETER_DEF, ANNOTATION_DEF, TYPECAST, "
            + "LITERAL_THROWS, IMPLEMENTS_CLAUSE, TYPE_ARGUMENT, LITERAL_NEW, DOT, "
            + "ANNOTATION_FIELD_DEF");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(AnnotationOnSameLineCheck.class,
                AnnotationOnSameLineCheck.MSG_KEY_ANNOTATION_ON_SAME_LINE,
                "Deprecated"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/INTERFACE_DEF["
                + "./IDENT[@text='InputXpathAnnotationOnSameLineInterface']]",
            "/COMPILATION_UNIT/INTERFACE_DEF["
                + "./IDENT[@text='InputXpathAnnotationOnSameLineInterface']]"
                + "/MODIFIERS",
            "/COMPILATION_UNIT/INTERFACE_DEF["
                + "./IDENT[@text='InputXpathAnnotationOnSameLineInterface']]"
                + "/MODIFIERS/ANNOTATION[./IDENT[@text='Deprecated']]",
            "/COMPILATION_UNIT/INTERFACE_DEF["
                + "./IDENT[@text='InputXpathAnnotationOnSameLineInterface']]"
                + "/MODIFIERS/ANNOTATION[./IDENT[@text='Deprecated']]/AT"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
