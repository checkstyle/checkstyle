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

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck;

public class XpathRegressionMethodNameTest extends AbstractXpathTestSupport {

    private final String checkName = MethodNameCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/naming/methodname";
    }

    @Test
    public void testDefault() throws Exception {
        final String pattern = "^[a-z][a-zA-Z0-9]*$";

        final String[] expectedViolation = {
            "19:16: " + getCheckMessage(MethodNameCheck.class,
                        MethodNameCheck.MSG_INVALID_PATTERN, "SecondMethod", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathMethodNameDefault']]"
                        + "/OBJBLOCK/METHOD_DEF/IDENT[@text='SecondMethod']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMethodNameDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String pattern = "^[a-z](_?[a-zA-Z0-9]+)*$";

        final String[] expectedViolation = {
            "20:21: " + getCheckMessage(MethodNameCheck.class,
                        MethodNameCheck.MSG_INVALID_PATTERN, "MyMethod2", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/CLASS_DEF[./IDENT[@text"
                        + "='InputXpathMethodNameInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Inner']]"
                        + "/OBJBLOCK/METHOD_DEF/IDENT[@text='MyMethod2']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMethodNameInner.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testCustomProperties() throws Exception {
        final String pattern = "^[a-z](_?[a-zA-Z0-9]+)*$";

        final String[] expectedViolation = {
            "20:19: " + getCheckMessage(MethodNameCheck.class,
                        MethodNameCheck.MSG_INVALID_PATTERN,
                        "ThirdMethod", pattern),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT"
                        + "/INTERFACE_DEF[./IDENT[@text='Check']]"
                        + "/OBJBLOCK/METHOD_DEF/IDENT[@text='ThirdMethod']"
        );
        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMethodNameCustomProperties.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
