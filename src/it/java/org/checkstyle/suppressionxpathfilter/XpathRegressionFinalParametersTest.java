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

package org.checkstyle.suppressionxpathfilter;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck;

public class XpathRegressionFinalParametersTest extends AbstractXpathTestSupport {

    private final String checkName = FinalParametersCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expectedViolation = {
            "14:24: " + getCheckMessage(FinalParametersCheck.class,
                    FinalParametersCheck.MSG_KEY, "argOne"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalParametersMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalParametersMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalParametersMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalParametersMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                    + "[@text='InputXpathFinalParametersMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                    + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathFinalParametersMethod.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testCtor() throws Exception {
        final String[] expectedViolation = {
            "14:42: " + getCheckMessage(FinalParametersCheck.class,
                        FinalParametersCheck.MSG_KEY, "argOne"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersCtor']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT["
                        + "@text='InputXpathFinalParametersCtor']]"
                        + "/PARAMETERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersCtor']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT["
                        + "@text='InputXpathFinalParametersCtor']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersCtor']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT["
                        + "@text='InputXpathFinalParametersCtor']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersCtor']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT["
                        + "@text='InputXpathFinalParametersCtor']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersCtor']]"
                        + "/OBJBLOCK/CTOR_DEF[./IDENT["
                        + "@text='InputXpathFinalParametersCtor']]"
                        + "/PARAMETERS/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathFinalParametersCtor.java"),
            expectedXpathQueries,
            expectedViolation);
    }

    @Test
    public void testAnonymous() throws Exception {
        final String[] expectedViolation = {
            "20:32: " + getCheckMessage(FinalParametersCheck.class,
                    FinalParametersCheck.MSG_KEY, "argOne"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersAnonymous']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='createClass']]/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='obj']]/ASSIGN/EXPR"
                        + "/LITERAL_NEW[./IDENT[@text='AnonymousClass']]/OBJBLOCK"
                        + "/METHOD_DEF[./IDENT[@text='method']]/PARAMETERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersAnonymous']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='createClass']]/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='obj']]/ASSIGN/EXPR"
                        + "/LITERAL_NEW[./IDENT[@text='AnonymousClass']]/OBJBLOCK"
                        + "/METHOD_DEF[./IDENT[@text='method']]/PARAMETERS"
                        + "/PARAMETER_DEF[./IDENT[@text='argOne']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersAnonymous']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='createClass']]/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='obj']]/ASSIGN/EXPR"
                        + "/LITERAL_NEW[./IDENT[@text='AnonymousClass']]/OBJBLOCK"
                        + "/METHOD_DEF[./IDENT[@text='method']]/PARAMETERS"
                        + "/PARAMETER_DEF[./IDENT[@text='argOne']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersAnonymous']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='createClass']]/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='obj']]/ASSIGN/EXPR"
                        + "/LITERAL_NEW[./IDENT[@text='AnonymousClass']]/OBJBLOCK"
                        + "/METHOD_DEF[./IDENT[@text='method']]/PARAMETERS"
                        + "/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE[./IDENT[@text='String']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathFinalParametersAnonymous']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='createClass']]/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='obj']]/ASSIGN/EXPR"
                        + "/LITERAL_NEW[./IDENT[@text='AnonymousClass']]/OBJBLOCK"
                        + "/METHOD_DEF[./IDENT[@text='method']]/PARAMETERS"
                        + "/PARAMETER_DEF[./IDENT[@text='argOne']]/TYPE/IDENT[@text='String']"
        );

        verifyXpathWithInlineConfigParser(
            getPath("InputXpathFinalParametersAnonymous.java"),
            expectedXpathQueries,
            expectedViolation);
    }

}
