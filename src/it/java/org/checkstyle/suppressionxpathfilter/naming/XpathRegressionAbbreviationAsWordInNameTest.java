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

package org.checkstyle.suppressionxpathfilter.naming;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck;

public class XpathRegressionAbbreviationAsWordInNameTest extends AbstractXpathTestSupport {

    private final String checkName = AbbreviationAsWordInNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/abbreviationaswordinname";
    }

    @Test
    public void testAnnotation() throws Exception {
        final String[] expectedViolation = {
            "20:16: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "ANNOTATION", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameAnnotation']]"
                    + "/OBJBLOCK/ANNOTATION_DEF/IDENT[@text='ANNOTATION']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameAnnotation.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testAnnotationField() throws Exception {
        final String[] expectedViolation = {
            "20:12: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "ANNOTATION_FIELD", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/ANNOTATION_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameAnnotationField']]"
                    + "/OBJBLOCK/ANNOTATION_FIELD_DEF/IDENT[@text='ANNOTATION_FIELD']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameAnnotationField.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testClass() throws Exception {
        final String[] expectedViolation = {
            "20:11: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "CLASS", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameClass']]"
                    + "/OBJBLOCK/CLASS_DEF/IDENT[@text='CLASS']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEnum() throws Exception {
        final String[] expectedViolation = {
            "20:10: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "ENUMERATION", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameEnum']]"
                    + "/OBJBLOCK/ENUM_DEF/IDENT[@text='ENUMERATION']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameEnum.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testField() throws Exception {
        final String[] expectedViolation = {
            "20:9: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "FIELD", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameField']]"
                    + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='FIELD']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameField.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expectedViolation = {
            "20:15: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "INTERFACE", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameInterface']]"
                    + "/OBJBLOCK/INTERFACE_DEF/IDENT[@text='INTERFACE']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expectedViolation = {
            "20:10: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "METHOD", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameMethod']]"
                    + "/OBJBLOCK/METHOD_DEF/IDENT[@text='METHOD']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameMethod.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testParameter() throws Exception {
        final String[] expectedViolation = {
            "20:21: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "PARAMETER", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameParameter']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF/IDENT[@text='PARAMETER']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameParameter.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testVariable() throws Exception {
        final String[] expectedViolation = {
            "21:13: " + getCheckMessage(AbbreviationAsWordInNameCheck.class,
                AbbreviationAsWordInNameCheck.MSG_KEY, "VARIABLE", 4),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputXpathAbbreviationAsWordInNameVariable']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/SLIST/VARIABLE_DEF/IDENT[@text='VARIABLE']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathAbbreviationAsWordInNameVariable.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
