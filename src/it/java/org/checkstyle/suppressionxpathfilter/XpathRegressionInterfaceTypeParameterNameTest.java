///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

import static com.puppycrawl.tools.checkstyle.checks.naming.InterfaceTypeParameterNameCheck.MSG_INVALID_PATTERN;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.InterfaceTypeParameterNameCheck;

public class XpathRegressionInterfaceTypeParameterNameTest extends AbstractXpathTestSupport {
    private final String checkName = InterfaceTypeParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToCheck =
                new File(getPath(
                "InputXpathInterfaceTypeParameterNameDefault.java"
                ));

        final DefaultConfiguration configuration =
                createModuleConfig(InterfaceTypeParameterNameCheck.class);

        final String[] expectedViolations = {
            "5:29: " + getCheckMessage(InterfaceTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN,
                    "t", "^[A-Z]$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameDefault']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='SecondInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT"
                + "[@text='t']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameDefault']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='SecondInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='t']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testOnlySingleLetterLowercase() throws Exception {
        final File fileToCheck =
                new File(getPath(
                "InputXpathInterfaceTypeParameterNameOnlySingleLetterLowercase.java"
                ));

        final String format = "^[a-z]$";

        final DefaultConfiguration configuration =
                createModuleConfig(InterfaceTypeParameterNameCheck.class);
        configuration.addProperty("format", format);

        final String[] expectedViolations = {
            "4:28: " + getCheckMessage(InterfaceTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN,
                     "T", format),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameOnlySingleLetterLowercase']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='FirstInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT"
                + "[@text='T']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"

                + "[@text='InputXpathInterfaceTypeParameterNameOnlySingleLetterLowercase']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='FirstInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='T']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testOnlySingleLetter() throws Exception {
        final File fileToCheck =
                new File(getPath(
                "InputXpathInterfaceTypeParameterNameOnlySingleLetter.java"
                ));

        final String format = "^[a-zA-Z]$";

        final DefaultConfiguration configuration =
                createModuleConfig(InterfaceTypeParameterNameCheck.class);
        configuration.addProperty("format", format);

        final String[] expectedViolations = {
            "6:28: " + getCheckMessage(InterfaceTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN,
                     "Type", format),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameOnlySingleLetter']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='ThirdInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT"
                + "[@text='Type']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathInterfaceTypeParameterNameOnlySingleLetter']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='ThirdInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='Type']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testOneLetterStartsWithCapital() throws Exception {
        final File fileToCheck =
                new File(getPath(
                "InputXpathInterfaceTypeParameterNameMoreThanOneLetterStartsWithCapital.java"
                ));

        final String format = "^[A-Z][a-z]+$";

        final DefaultConfiguration configuration =
                createModuleConfig(InterfaceTypeParameterNameCheck.class);
        configuration.addProperty("format", format);

        final String[] expectedViolations = {
            "5:29: " + getCheckMessage(InterfaceTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN,
                    "type", format),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text="
                + "'InputXpathInterfaceTypeParameterNameMoreThanOneLetterStartsWithCapital']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='SecondInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER[./IDENT"
                + "[@text='type']]",

                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text="
                + "'InputXpathInterfaceTypeParameterNameMoreThanOneLetterStartsWithCapital']]"
                + "/OBJBLOCK/INTERFACE_DEF[./IDENT"
                + "[@text='SecondInterface']]"
                + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='type']"
        );

        runVerifications(configuration, fileToCheck, expectedViolations, expectedXpathQueries);
    }
}
