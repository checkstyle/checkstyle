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

import com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck;

public class XpathRegressionOverloadMethodsDeclarationOrderTest extends AbstractXpathTestSupport {

    private static final Class<OverloadMethodsDeclarationOrderCheck> CLAZZ =
            OverloadMethodsDeclarationOrderCheck.class;

    @Override
    protected String getCheckName() {
        return CLAZZ.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/overloadmethodsdeclarationorder";
    }

    @Test
    public void testDefault() throws Exception {
        final String fileToProcess =
                getPath("InputXpathOverloadMethodsDeclarationOrderDefault.java");

        final String[] expectedViolation = {
            "22:5: " + getCheckMessage(CLAZZ,
                        OverloadMethodsDeclarationOrderCheck.MSG_KEY, "12"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathOverloadMethodsDeclarationOrderDefault']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathOverloadMethodsDeclarationOrderDefault']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                        + "[@text='InputXpathOverloadMethodsDeclarationOrderDefault']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testAnonymous() throws Exception {
        final String fileToProcess =
                getPath("InputXpathOverloadMethodsDeclarationOrderAnonymous.java");

        final String[] expectedViolation = {
            "38:9: " + getCheckMessage(CLAZZ,
                    OverloadMethodsDeclarationOrderCheck.MSG_KEY, "28"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathOverloadMethodsDeclarationOrderAnonymous']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text="
                        + "'MyInputXpathOverloadMethodsDeclarationOrderAnonymous']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathOverloadMethodsDeclarationOrderAnonymous']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text="
                        + "'MyInputXpathOverloadMethodsDeclarationOrderAnonymous']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
                        + "'InputXpathOverloadMethodsDeclarationOrderAnonymous']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text="
                        + "'MyInputXpathOverloadMethodsDeclarationOrderAnonymous']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testEnum() throws Exception {
        final String fileToProcess =
                getPath("InputXpathOverloadMethodsDeclarationOrderEnum.java");

        final String[] expectedViolation = {
            "24:5: " + getCheckMessage(CLAZZ,
                        OverloadMethodsDeclarationOrderCheck.MSG_KEY, "14"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                        + "[@text='InputXpathOverloadMethodsDeclarationOrderEnum']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]",
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                        + "[@text='InputXpathOverloadMethodsDeclarationOrderEnum']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]/MODIFIERS",
                "/COMPILATION_UNIT/ENUM_DEF[./IDENT"
                        + "[@text='InputXpathOverloadMethodsDeclarationOrderEnum']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='overloadMethod']]"
                        + "/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

}
