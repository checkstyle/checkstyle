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

import com.puppycrawl.tools.checkstyle.checks.coding.DeclarationOrderCheck;

public class XpathRegressionDeclarationOrderTest extends AbstractXpathTestSupport {

    private final String checkName = DeclarationOrderCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/declarationorder";
    }

    @Test
    public void testNonStatic() throws Exception {
        final String[] expectedViolation = {
            "14:5: " + getCheckMessage(DeclarationOrderCheck.class,
                DeclarationOrderCheck.MSG_ACCESS),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderNonStatic']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='name']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderNonStatic']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='name']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderNonStatic']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='name']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathDeclarationOrderNonStatic.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testStatic() throws Exception {
        final String[] expectedViolation = {
            "14:5: " + getCheckMessage(DeclarationOrderCheck.class,
                DeclarationOrderCheck.MSG_STATIC),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderStatic']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='MAX']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderStatic']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='MAX']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderStatic']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='MAX']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathDeclarationOrderStatic.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testInnerClass() throws Exception {
        final String[] expectedViolation = {
            "15:9: " + getCheckMessage(DeclarationOrderCheck.class,
                DeclarationOrderCheck.MSG_ACCESS),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderInnerClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderInnerClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathDeclarationOrderInnerClass']]"
                        + "/OBJBLOCK/CLASS_DEF[./IDENT[@text='InnerClass']]"
                        + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]/MODIFIERS/LITERAL_PUBLIC"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathDeclarationOrderInnerClass.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
