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

import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.checks.design.FinalClassCheck;

public class XpathRegressionFinalClassTest extends AbstractXpathTestSupport {

    private final String checkName = FinalClassCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/design/finalclass";
    }

    @Test
    public void testDefault() throws Exception {
        final String[] expectedViolation = {
            "10:1: " + getCheckMessage(FinalClassCheck.class,
                    FinalClassCheck.MSG_KEY, "InputXpathFinalClassDefault"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassDefault']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassDefault']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassDefault']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathFinalClassDefault.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String[] expectedViolation = {
            "11:5: " + getCheckMessage(FinalClassCheck.class,
                    FinalClassCheck.MSG_KEY, "Test"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassInnerClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassInnerClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassInnerClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='Test']]/LITERAL_CLASS"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathFinalClassInnerClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testStaticNestedClass() throws Exception {
        final String[] expectedViolation = {
            "12:5: " + getCheckMessage(FinalClassCheck.class,
                    FinalClassCheck.MSG_KEY, "StaticNestedClass"),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassStaticNestedClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='StaticNestedClass']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassStaticNestedClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='StaticNestedClass']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathFinalClassStaticNestedClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='StaticNestedClass']]"
                        + "/MODIFIERS/LITERAL_STATIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathFinalClassStaticNestedClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
