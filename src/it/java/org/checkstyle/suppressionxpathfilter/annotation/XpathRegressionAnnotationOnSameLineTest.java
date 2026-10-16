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

package org.checkstyle.suppressionxpathfilter.annotation;

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.annotation.AnnotationOnSameLineCheck;

public class XpathRegressionAnnotationOnSameLineTest extends AbstractXpathTestSupport {

    private final String checkName = AnnotationOnSameLineCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/annotation/annotationonsameline";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(AnnotationOnSameLineCheck.class,
                AnnotationOnSameLineCheck.MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAnnotationOnSameLineMethod.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "15:5: " + getCheckMessage(AnnotationOnSameLineCheck.class,
                AnnotationOnSameLineCheck.MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAnnotationOnSameLineField.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "12:1: " + getCheckMessage(AnnotationOnSameLineCheck.class,
                AnnotationOnSameLineCheck.MSG_KEY_ANNOTATION_ON_SAME_LINE, "Deprecated"),
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

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAnnotationOnSameLineInterface.java"),
                expectedXpathQueries, expectedViolation);
    }

}
