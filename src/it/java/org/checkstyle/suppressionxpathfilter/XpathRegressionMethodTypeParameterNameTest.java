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

import static com.puppycrawl.tools.checkstyle.checks.naming.MethodTypeParameterNameCheck.MSG_INVALID_PATTERN;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.MethodTypeParameterNameCheck;

public class XpathRegressionMethodTypeParameterNameTest extends AbstractXpathTestSupport {

    private final String checkName = MethodTypeParameterNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void test1() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMethodTypeParameterNameDefault.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MethodTypeParameterNameCheck.class);

        final String[] expectedViolation = {
            "4:11: " + getCheckMessage(MethodTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN, "TT", "^[A-Z]$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./"
                  + "IDENT[@text='InputXpathMethodTypeParameterNameDefault']]"
                  + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/TYPE_PARAMETERS"
                  + "/TYPE_PARAMETER[./IDENT[@text='TT']]", "/COMPILATION_UNIT"
                  + "/CLASS_DEF[./IDENT["
                  + "@text='InputXpathMethodTypeParameterNameDefault']]"
                  + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                  + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='TT']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void test2() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMethodTypeParameterNameInner.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MethodTypeParameterNameCheck.class);
        moduleConfig.addProperty("format", "^foo$");

        final String[] expectedViolation = {
            "6:10: " + getCheckMessage(MethodTypeParameterNameCheck.class,
                    MSG_INVALID_PATTERN, "fo_", "^foo$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                  + "@text='InputXpathMethodTypeParameterNameInner']]"
                  + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Junk']]/OBJBLOCK"
                  + "/METHOD_DEF[./IDENT[@text='foo']]/TYPE_PARAMETERS"
                  + "/TYPE_PARAMETER[./IDENT[@text='fo_']]", "/COMPILATION_UNIT"
                  + "/CLASS_DEF[./IDENT[@text="
                  + "'InputXpathMethodTypeParameterNameInner']]"
                  + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Junk']]/OBJBLOCK"
                  + "/METHOD_DEF[./IDENT[@text='foo']]/TYPE_PARAMETERS"
                  + "/TYPE_PARAMETER/IDENT[@text='fo_']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void test3() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathMethodTypeParameterNameLowercase.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MethodTypeParameterNameCheck.class);
        moduleConfig.addProperty("format", "^[a-z]$");

        final String[] expectedViolation = {
            "7:6: " + getCheckMessage(MethodTypeParameterNameCheck.class,
                        MSG_INVALID_PATTERN, "a_a", "^[a-z]$"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                  + "[@text='InputXpathMethodTypeParameterNameLowercase']]"
                  + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod']]/TYPE_PARAMETERS"
                  + "/TYPE_PARAMETER[./IDENT[@text='a_a']]", "/COMPILATION_UNIT"
                  + "/CLASS_DEF[./IDENT[@text="
                  + "'InputXpathMethodTypeParameterNameLowercase']]"
                  + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myMethod']]"
                  + "/TYPE_PARAMETERS/TYPE_PARAMETER/IDENT[@text='a_a']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
