///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.naming.AbstractNameCheck;
import com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck;

public class XpathRegressionMemberNameTest extends AbstractXpathTestSupport {

    private final String checkName = MemberNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void test1() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMemberName1.java"));

        final String pattern = "^[a-z][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
                createModuleConfig(MemberNameCheck.class);

        final String[] expectedViolation = {
            "5:17: " + getCheckMessage(MemberNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "NUM2", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='SuppressionXpathRegressionMemberName1']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='NUM2']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void test2() throws Exception {
        final File fileToProcess =
                new File(getPath("SuppressionXpathRegressionMemberName2.java"));

        final String pattern = "^m[A-Z][a-zA-Z0-9]*$";
        final DefaultConfiguration moduleConfig =
                createModuleConfig(MemberNameCheck.class);
        moduleConfig.addProperty("format", "^m[A-Z][a-zA-Z0-9]*$");
        moduleConfig.addProperty("applyToProtected", "false");
        moduleConfig.addProperty("applyToPackage", "false");

        final String[] expectedViolation = {
            "6:20: " + getCheckMessage(MemberNameCheck.class,
                        AbstractNameCheck.MSG_INVALID_PATTERN, "NUM1", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='SuppressionXpathRegressionMemberName2']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                        + "/OBJBLOCK/VARIABLE_DEF/IDENT[@text='NUM1']"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

}
