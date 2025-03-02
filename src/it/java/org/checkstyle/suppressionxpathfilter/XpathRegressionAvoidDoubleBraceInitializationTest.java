///////////////////////////////////////////////////////////////////////////////////////////////
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

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.AvoidDoubleBraceInitializationCheck;

public class XpathRegressionAvoidDoubleBraceInitializationTest extends AbstractXpathTestSupport {

    private static Class<AvoidDoubleBraceInitializationCheck> clazz =
        AvoidDoubleBraceInitializationCheck.class;

    @Override
    protected String getCheckName() {
        return clazz.getSimpleName();
    }

    @Test
    public void testClassFields() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathAvoidDoubleBraceInitializationClassFields.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);

        final String[] expectedViolation = {
            "6:41: " + getCheckMessage(clazz, AvoidDoubleBraceInitializationCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAvoidDoubleBraceInitializationClassFields']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='list']]/ASSIGN/EXPR/"
                + "LITERAL_NEW[./IDENT[@text='ArrayList']]/OBJBLOCK",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathAvoidDoubleBraceInitializationClassFields']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='list']]/ASSIGN/EXPR/"
                + "LITERAL_NEW[./IDENT[@text='ArrayList']]/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testMethodDef() throws Exception {
        final File fileToProcess = new File(
            getPath("InputXpathAvoidDoubleBraceInitializationMethodDef.java"));

        final DefaultConfiguration moduleConfig = createModuleConfig(clazz);

        final String[] expectedViolation = {
            "7:31: " + getCheckMessage(clazz, AvoidDoubleBraceInitializationCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'InputXpathAvoidDoubleBraceInitializationMethodDef']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/EXPR/LITERAL_NEW[./IDENT[@text='HashSet']]"
                + "/OBJBLOCK",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                    + "'InputXpathAvoidDoubleBraceInitializationMethodDef']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/EXPR/LITERAL_NEW[./IDENT[@text='HashSet']]"
                + "/OBJBLOCK/LCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
