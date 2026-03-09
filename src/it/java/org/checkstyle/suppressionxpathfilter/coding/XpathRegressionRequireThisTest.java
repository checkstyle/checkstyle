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

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.RequireThisCheck;

public class XpathRegressionRequireThisTest extends AbstractXpathTestSupport {

    private final String checkName = RequireThisCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/requirethis";
    }

    @Test
    public void testSimple() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRequireThisSimple.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RequireThisCheck.class);
        moduleConfig.addProperty("validateOnlyOverlapping", "false");

        final String[] expectedViolation = {
            "7:9: " + getCheckMessage(RequireThisCheck.class,
                RequireThisCheck.MSG_VARIABLE, "age", ""),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRequireThisSimple']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='changeAge']]/SLIST/EXPR/ASSIGN"
                + "/IDENT[@text='age']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testAnonymousClass() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRequireThisAnonymousClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RequireThisCheck.class);
        moduleConfig.addProperty("validateOnlyOverlapping", "false");

        final String[] expectedViolation = {
            "9:13: " + getCheckMessage(RequireThisCheck.class,
                RequireThisCheck.MSG_VARIABLE, "age", ""),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRequireThisAnonymousClass']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='runnable']]/ASSIGN/EXPR/"
                + "LITERAL_NEW[./IDENT[@text='Runnable']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='run']]/SLIST/EXPR/ASSIGN/IDENT[@text='age']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testMethodInnerClass() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRequireThisInnerClass.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RequireThisCheck.class);
        moduleConfig.addProperty("validateOnlyOverlapping", "false");

        final String[] expectedViolation = {
            "8:13: " + getCheckMessage(RequireThisCheck.class,
                RequireThisCheck.MSG_VARIABLE, "age", ""),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRequireThisInnerClass']]/OBJBLOCK"
                + "/CLASS_DEF[./IDENT[@text='Inner']]/OBJBLOCK/METHOD_DEF"
                + "[./IDENT[@text='changeAge']]/SLIST/EXPR/ASSIGN"
                + "/IDENT[@text='age']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
