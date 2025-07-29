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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryNullCheckWithInstanceOfCheck;

public class XpathRegressionUnnecessaryNullCheckWithInstanceOfTest
    extends AbstractXpathTestSupport {

    private final String checkName = UnnecessaryNullCheckWithInstanceOfCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/unnecessarynullcheckwithinstanceof";
    }

    @Test
    public void test1() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathUnnecessaryNullCheckWithInstanceOf.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnnecessaryNullCheckWithInstanceOfCheck.class);

        final String[] expected = {
            "5:13: " + getCheckMessage(UnnecessaryNullCheckWithInstanceOfCheck.class,
                    UnnecessaryNullCheckWithInstanceOfCheck.MSG_UNNECESSARY_NULLCHECK),
        };
        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                + "'InputXpathUnnecessaryNullCheckWithInstanceOf']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='methodWithUnnecessaryNullCheck1']]"
                + "/SLIST/LITERAL_IF/EXPR/LAND/NOT_EQUAL/IDENT[@text='obj']"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }

    @Test
    public void test2() throws Exception {
        final File fileToProcess =
                new File(getPath(
                    "InputXpathUnnecessaryNullCheckWithInstanceOfAnonymous.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnnecessaryNullCheckWithInstanceOfCheck.class);

        final String[] expected = {
            "8:17: " + getCheckMessage(UnnecessaryNullCheckWithInstanceOfCheck.class,
                    UnnecessaryNullCheckWithInstanceOfCheck.MSG_UNNECESSARY_NULLCHECK),
        };
        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathUnnecessaryNullCheckWithInstanceOfAnonymous']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='runnable']]/ASSIGN/EXPR/LITERAL_NEW"
                + "[./IDENT[@text='Runnable']]/OBJBLOCK/METHOD_DEF[./IDENT[@text='run']]"
                + "/SLIST/LITERAL_IF/EXPR/LAND/NOT_EQUAL/IDENT[@text='obj']"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }

    @Test
    public void test3() throws Exception {
        final File fileToProcess =
                new File(getPath(
                    "InputXpathUnnecessaryNullCheckWithInstanceOfInterface.java"));
        final DefaultConfiguration moduleConfig =
                createModuleConfig(UnnecessaryNullCheckWithInstanceOfCheck.class);

        final String[] expected = {
            "6:16: " + getCheckMessage(UnnecessaryNullCheckWithInstanceOfCheck.class,
                    UnnecessaryNullCheckWithInstanceOfCheck.MSG_UNNECESSARY_NULLCHECK),
        };
        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathUnnecessaryNullCheckWithInstanceOfInterface']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='validateString']]/SLIST/LITERAL_RETURN"
                + "/EXPR/LAND/NOT_EQUAL/IDENT[@text='obj']"
        );

        runVerifications(moduleConfig, fileToProcess, expected, expectedXpathQueries);
    }
}

