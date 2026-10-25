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

import static com.puppycrawl.tools.checkstyle.checks.naming.InterfaceTypeParameterNameCheck.MSG_INVALID_PATTERN;

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.naming.InterfaceTypeParameterNameCheck;

public class XpathRegressionInterfaceTypeParameterNameTest extends AbstractXpathTestSupport {

    private final String checkName = InterfaceTypeParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/interfacetypeparametername";
    }

    @Test
    public void testInsideClass() throws Exception {
        final String[] expectedViolations = {
            "12:29: " + getCheckMessage(InterfaceTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN,
                    "t", "^[A-Z]$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameInsideClass']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='SecondInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT"
                + "[@text='t']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameInsideClass']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='SecondInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='t']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInterfaceTypeParameterNameInsideClass.java"),
                expectedXpathQueries,
                expectedViolations);
    }

    @Test
    public void testInsideInnerClass() throws Exception {
        final String[] expectedViolations = {
            "12:37: " + getCheckMessage(InterfaceTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN,
                    "t", "^[A-Z]$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameInsideInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT"
                + "[@text='InnerClass']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='InnerInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT[@text='t']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameInsideInnerClass']]"
                + "/OBJBLOCK/CLASS_DEF[./IDENT"
                + "[@text='InnerClass']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='InnerInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='t']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInterfaceTypeParameterNameInsideInnerClass.java"),
                expectedXpathQueries,
                expectedViolations);
    }

    @Test
    public void testTopLevelPublic() throws Exception {
        final String[] expectedViolations = {
            "11:69: " + getCheckMessage(InterfaceTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN,
                    "t", "^[A-Z]$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameTopLevelPublic']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT[@text='t']]",

                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameTopLevelPublic']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='t']"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathInterfaceTypeParameterNameTopLevelPublic.java"),
                expectedXpathQueries,
                expectedViolations);
    }

}
