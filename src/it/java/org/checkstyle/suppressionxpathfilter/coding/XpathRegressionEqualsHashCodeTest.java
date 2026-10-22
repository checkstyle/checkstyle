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

import java.util.Arrays;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.coding.EqualsHashCodeCheck;

public class XpathRegressionEqualsHashCodeTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {
        return EqualsHashCodeCheck.class.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/equalshashcode";
    }

    @Test
    public void testEqualsOnly() throws Exception {
        final String[] expectedViolation = {
            "10:5: " + getCheckMessage(EqualsHashCodeCheck.class,
                    EqualsHashCodeCheck.MSG_KEY_HASHCODE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeEqualsOnly']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeEqualsOnly']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeEqualsOnly']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEqualsHashCodeEqualsOnly.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testHashCodeOnly() throws Exception {
        final String[] expectedViolation = {
            "10:5: " + getCheckMessage(EqualsHashCodeCheck.class,
                    EqualsHashCodeCheck.MSG_KEY_EQUALS),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeHashCodeOnly']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='hashCode']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeHashCodeOnly']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='hashCode']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeHashCodeOnly']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='hashCode']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEqualsHashCodeHashCodeOnly.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testNestedCase() throws Exception {
        final String[] expectedViolation = {
            "11:9: " + getCheckMessage(EqualsHashCodeCheck.class,
                    EqualsHashCodeCheck.MSG_KEY_HASHCODE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeNestedCase']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeNestedCase']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathEqualsHashCodeNestedCase']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='innerClass']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='equals']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathEqualsHashCodeNestedCase.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
