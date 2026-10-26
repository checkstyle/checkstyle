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

package org.checkstyle.suppressionxpathfilter.indentation;

import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.indentation.OpenjdkMethodThrowsAlignmentCheck;

public class XpathRegressionOpenjdkMethodThrowsAlignmentTest
        extends AbstractXpathTestSupport {

    private static final Class<OpenjdkMethodThrowsAlignmentCheck> CLASS =
            OpenjdkMethodThrowsAlignmentCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/indentation/"
                + "openjdkmethodthrowsalignment";
    }

    @Test
    public void testClassMethod() throws Exception {
        final String[] expectedViolation = {
            "11:36: " + getCheckMessage(CLASS,
                    OpenjdkMethodThrowsAlignmentCheck.MSG_KEY_NOT_ON_NEW_LINE),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkMethodThrowsAlignmentClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                        + "/LITERAL_THROWS[./IDENT[@text='Exception']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOpenjdkMethodThrowsAlignmentClass.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testInterfaceMethod() throws Exception {
        final String[] expectedViolation = {
            "11:29: " + getCheckMessage(CLASS,
                    OpenjdkMethodThrowsAlignmentCheck.MSG_KEY_NOT_ON_NEW_LINE),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/INTERFACE_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkMethodThrowsAlignmentInterface']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                        + "/LITERAL_THROWS[./IDENT[@text='Exception']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOpenjdkMethodThrowsAlignmentInterface.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testEnumMethod() throws Exception {
        final String[] expectedViolation = {
            "13:36: " + getCheckMessage(CLASS,
                    OpenjdkMethodThrowsAlignmentCheck.MSG_KEY_NOT_ON_NEW_LINE),
        };
        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/ENUM_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkMethodThrowsAlignmentEnum']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]"
                        + "/LITERAL_THROWS[./IDENT[@text='Exception']]"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathOpenjdkMethodThrowsAlignmentEnum.java"),
                expectedXpathQueries, expectedViolation);
    }

}
