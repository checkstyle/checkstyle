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

package org.checkstyle.suppressionxpathfilter.design;

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.design.SealedShouldHavePermitsListCheck;

public class XpathRegressionSealedShouldHavePermitsListTest extends AbstractXpathTestSupport {

    private final String checkName = SealedShouldHavePermitsListCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/design/sealedshouldhavepermitslist";
    }

    @Test
    public void testInner() throws Exception {
        final String[] expectedViolation = {
            "11:4: " + getCheckMessage(SealedShouldHavePermitsListCheck.class,
                SealedShouldHavePermitsListCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathSealedShouldHavePermitsListInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='A']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathSealedShouldHavePermitsListInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='A']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathSealedShouldHavePermitsListInner']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='A']]/MODIFIERS/LITERAL_SEALED"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSealedShouldHavePermitsListInner.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testTopLevel() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(SealedShouldHavePermitsListCheck.class,
                SealedShouldHavePermitsListCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListTopLevel']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListTopLevel']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListTopLevel']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSealedShouldHavePermitsListTopLevel.java"),
                expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testInterface() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(SealedShouldHavePermitsListCheck.class,
                SealedShouldHavePermitsListCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListInterface']]",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListInterface']]"
                        + "/MODIFIERS",
                "/COMPILATION_UNIT/INTERFACE_DEF[./IDENT"
                + "[@text='InputXpathSealedShouldHavePermitsListInterface']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathSealedShouldHavePermitsListInterface.java"),
                expectedXpathQueries, expectedViolation);
    }

}
