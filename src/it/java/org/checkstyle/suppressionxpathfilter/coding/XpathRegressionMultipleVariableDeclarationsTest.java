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

import com.puppycrawl.tools.checkstyle.checks.coding.MultipleVariableDeclarationsCheck;

public class XpathRegressionMultipleVariableDeclarationsTest extends AbstractXpathTestSupport {

    private final String checkName = MultipleVariableDeclarationsCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/multiplevariabledeclarations";
    }

    @Test
    public void testCommaSeparator() throws Exception {
        final String[] expectedViolation = {
            "10:5: " + getCheckMessage(MultipleVariableDeclarationsCheck.class,
                MultipleVariableDeclarationsCheck.MSG_MULTIPLE_COMMA),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i']]/TYPE/LITERAL_INT",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarationsCommaSeparator']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='j']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMultipleVariableDeclarationsCommaSeparator.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMultipleVariableDeclarations() throws Exception {
        final String[] expectedViolation = {
            "10:5: " + getCheckMessage(MultipleVariableDeclarationsCheck.class,
                MultipleVariableDeclarationsCheck.MSG_MULTIPLE),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarations']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i1']]",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarations']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i1']]/MODIFIERS",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarations']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i1']]/TYPE",
            "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                + "@text='InputXpathMultipleVariableDeclarations']]/OBJBLOCK"
                + "/VARIABLE_DEF[./IDENT[@text='i1']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMultipleVariableDeclarations.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testMultipleVariableDeclarationsInMethod() throws Exception {
        final String[] expectedViolation = {
            "11:9: " + getCheckMessage(MultipleVariableDeclarationsCheck.class,
                MultipleVariableDeclarationsCheck.MSG_MULTIPLE_COMMA),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathMultipleVariableDeclarationsInMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='a']]",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathMultipleVariableDeclarationsInMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='a']]/MODIFIERS",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathMultipleVariableDeclarationsInMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='a']]/TYPE",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathMultipleVariableDeclarationsInMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='a']]/TYPE/LITERAL_INT",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathMultipleVariableDeclarationsInMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='b']]",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathMultipleVariableDeclarationsInMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='b']]/MODIFIERS",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathMultipleVariableDeclarationsInMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='b']]/TYPE",
            "/COMPILATION_UNIT"
                + "/CLASS_DEF[./IDENT[@text='InputXpathMultipleVariableDeclarationsInMethod']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]"
                + "/SLIST/VARIABLE_DEF[./IDENT[@text='b']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathMultipleVariableDeclarationsInMethod.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
