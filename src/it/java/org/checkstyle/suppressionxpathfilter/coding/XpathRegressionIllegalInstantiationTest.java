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

package org.checkstyle.suppressionxpathfilter.coding;

import static com.puppycrawl.tools.checkstyle.checks.coding.IllegalInstantiationCheck.MSG_KEY;

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.IllegalInstantiationCheck;

public class XpathRegressionIllegalInstantiationTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return IllegalInstantiationCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/illegalinstantiation";
    }

    @Test
    public void testSimple() throws Exception {
        final String fileToProcess = getNonCompilablePath(
                "InputXpathIllegalInstantiationSimple.java");

        final String[] expectedViolation = {
            "16:21: " + getCheckMessage(IllegalInstantiationCheck.class, MSG_KEY,
                    "java.lang.Boolean"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathIllegalInstantiationSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                + "VARIABLE_DEF[./IDENT[@text='x']]/ASSIGN/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathIllegalInstantiationSimple']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='x']]/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='Boolean']]"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testAnonymous() throws Exception {
        final String fileToProcess = getNonCompilablePath(
                "InputXpathIllegalInstantiationAnonymous.java");

        final String[] expectedViolation = {
            "18:25: " + getCheckMessage(IllegalInstantiationCheck.class, MSG_KEY,
                    "java.lang.Integer"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathIllegalInstantiationAnonymous']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT[@text='e']]/ASSIGN/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathIllegalInstantiationAnonymous']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='test']]/SLIST/VARIABLE_DEF[./IDENT[@text='e']]"
                + "/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='Integer']]"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInterface() throws Exception {
        final String fileToProcess = getNonCompilablePath(
                "InputXpathIllegalInstantiationInterface.java");

        final String[] expectedViolation = {
            "18:24: " + getCheckMessage(IllegalInstantiationCheck.class, MSG_KEY,
                    "java.lang.String"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathIllegalInstantiationInterface']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Inner']]/"
                + "OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/"
                + "VARIABLE_DEF[./IDENT[@text='s']]/ASSIGN/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathIllegalInstantiationInterface']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT[@text='Inner']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='s']]/ASSIGN/EXPR/LITERAL_NEW[./IDENT[@text='String']]"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries,
                expectedViolation);
    }

}
