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

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck;

public class XpathRegressionIllegalTokenTextTest extends AbstractXpathTestSupport {

    private final String checkName = IllegalTokenTextCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/illegaltokentext";
    }

    @Test
    public void testField() throws Exception {
        final String[] expectedViolation = {
            "15:33: " + getCheckMessage(IllegalTokenTextCheck.class,
                        IllegalTokenTextCheck.MSG_KEY, "12345"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT"
                    + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTokenTextField']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='illegalNumber']]"
                    + "/ASSIGN/EXPR[./NUM_INT[@text='12345']]",
                "/COMPILATION_UNIT"
                    + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTokenTextField']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='illegalNumber']]"
                    + "/ASSIGN/EXPR/NUM_INT[@text='12345']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathIllegalTokenTextField.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expectedViolation = {
            "16:32: " + getCheckMessage(IllegalTokenTextCheck.class,
                        IllegalTokenTextCheck.MSG_KEY, "forbiddenText"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT"
                    + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTokenTextMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod']]"
                    + "/SLIST/VARIABLE_DEF[./IDENT[@text='illegalString']]"
                    + "/ASSIGN/EXPR[./STRING_LITERAL[@text='forbiddenText']]",
                "/COMPILATION_UNIT"
                    + "/CLASS_DEF[./IDENT[@text='InputXpathIllegalTokenTextMethod']]"
                    + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod']]"
                    + "/SLIST/VARIABLE_DEF[./IDENT[@text='illegalString']]"
                    + "/ASSIGN/EXPR/STRING_LITERAL[@text='forbiddenText']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathIllegalTokenTextMethod.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expectedViolation = {
            "15:10: " + getCheckMessage(IllegalTokenTextCheck.class,
                        IllegalTokenTextCheck.MSG_KEY, "invalidIdentifier"),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                    + "/INTERFACE_DEF[./IDENT[@text='InputXpathIllegalTokenTextInterface']]"
                    + "/OBJBLOCK/METHOD_DEF/IDENT[@text='invalidIdentifier']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathIllegalTokenTextInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
