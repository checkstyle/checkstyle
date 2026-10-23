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
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.whitespace.EmptyForInitializerPadCheck;

public class XpathRegressionEmptyForInitializerPadTest extends AbstractXpathTestSupport {

    private final String checkName = EmptyForInitializerPadCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/emptyforinitializerpad";
    }

    @Test
    public void testPreceded() throws Exception {
        final String[] expectedViolation = {
            "12:15: " + getCheckMessage(EmptyForInitializerPadCheck.class,
                    EmptyForInitializerPadCheck.MSG_PRECEDED, ";"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathEmptyForInitializerPadPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_FOR/FOR_INIT",
            "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathEmptyForInitializerPadPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_FOR/SEMI[1]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEmptyForInitializerPadPreceded.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testNotPreceded() throws Exception {
        final String[] expectedViolation = {
            "12:14: " + getCheckMessage(EmptyForInitializerPadCheck.class,
                    EmptyForInitializerPadCheck.MSG_NOT_PRECEDED, ";"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathEmptyForInitializerPadNotPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_FOR/FOR_INIT",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathEmptyForInitializerPadNotPreceded']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_FOR/SEMI[1]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEmptyForInitializerPadNotPreceded.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testPrecededStaticInit() throws Exception {
        final String[] expectedViolation = {
            "13:15: " + getCheckMessage(EmptyForInitializerPadCheck.class,
                    EmptyForInitializerPadCheck.MSG_PRECEDED, ";"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputXpathEmptyForInitializerPadPrecededStaticInit']]"
                    + "/OBJBLOCK/STATIC_INIT/SLIST/LITERAL_FOR/FOR_INIT",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                    + "@text='InputXpathEmptyForInitializerPadPrecededStaticInit']]"
                    + "/OBJBLOCK/STATIC_INIT/SLIST/LITERAL_FOR/SEMI[1]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEmptyForInitializerPadPrecededStaticInit.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
