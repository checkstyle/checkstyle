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

package org.checkstyle.suppressionxpathfilter.whitespace;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.whitespace.SeparatorWrapCheck;

public class XpathRegressionSeparatorWrapTest extends AbstractXpathTestSupport {

    private final String checkName = SeparatorWrapCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/separatorwrap";
    }

    @Test
    public void testClass() throws Exception {
        final String[] expectedViolation = {
            "18:17: " + getCheckMessage(SeparatorWrapCheck.class,
                        SeparatorWrapCheck.MSG_LINE_PREVIOUS, ","),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathSeparatorWrapClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod']]"
                        + "/PARAMETERS/COMMA"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSeparatorWrapClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expectedViolation = {
            "17:13: " + getCheckMessage(SeparatorWrapCheck.class,
                        SeparatorWrapCheck.MSG_LINE_PREVIOUS, "..."),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text='InputXpathSeparatorWrapInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='testMethod2']]"
                        + "/PARAMETERS/PARAMETER_DEF"
                        + "[./IDENT[@text='parameters']]/ELLIPSIS"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSeparatorWrapInterface.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMethod() throws Exception {
        final String[] expectedViolation = {
            "17:13: " + getCheckMessage(SeparatorWrapCheck.class,
                    SeparatorWrapCheck.MSG_LINE_PREVIOUS, "."),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathSeparatorWrapMethod']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method1']]/SLIST"
                        + "/VARIABLE_DEF[./IDENT[@text='stringLength']]"
                        + "/ASSIGN/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathSeparatorWrapMethod']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method1']]/SLIST"
                        + "/VARIABLE_DEF[./IDENT[@text='stringLength']]/ASSIGN"
                        + "/EXPR/DOT[./IDENT[@text='stringArray']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSeparatorWrapMethod.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
