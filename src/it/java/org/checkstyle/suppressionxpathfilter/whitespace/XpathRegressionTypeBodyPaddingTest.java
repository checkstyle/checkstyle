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

import com.puppycrawl.tools.checkstyle.checks.whitespace.TypeBodyPaddingCheck;

public class XpathRegressionTypeBodyPaddingTest extends AbstractXpathTestSupport {

    private final String checkName = TypeBodyPaddingCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/whitespace/typebodypadding";
    }

    @Test
    public void testAfterLcurly() throws Exception {
        final String[] expectedViolation = {
            "16:51: " + getCheckMessage(TypeBodyPaddingCheck.class,
                    TypeBodyPaddingCheck.MSG_AFTER_LCURLY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathTypeBodyPaddingAfterLcurly']]/OBJBLOCK",
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathTypeBodyPaddingAfterLcurly']]/OBJBLOCK/LCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTypeBodyPaddingAfterLcurly.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testBeforeRcurly() throws Exception {
        final String[] expectedViolation = {
            "18:1: " + getCheckMessage(TypeBodyPaddingCheck.class,
                    TypeBodyPaddingCheck.MSG_BEFORE_RCURLY),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathTypeBodyPaddingBeforeRcurly']]/OBJBLOCK/RCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTypeBodyPaddingBeforeRcurly.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testEnum() throws Exception {
        final String[] expectedViolation = {
            "16:43: " + getCheckMessage(TypeBodyPaddingCheck.class,
                    TypeBodyPaddingCheck.MSG_AFTER_LCURLY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/ENUM_DEF"
                + "[./IDENT[@text='InputXpathTypeBodyPaddingEnum']]/OBJBLOCK",
            "/COMPILATION_UNIT/ENUM_DEF"
                + "[./IDENT[@text='InputXpathTypeBodyPaddingEnum']]/OBJBLOCK/LCURLY"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathTypeBodyPaddingEnum.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
