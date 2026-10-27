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

import com.puppycrawl.tools.checkstyle.checks.coding.UnusedLocalVariableCheck;

public class XpathRegressionUnusedLocalVariableTest extends AbstractXpathTestSupport {

    private final String checkName = UnusedLocalVariableCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/unusedlocalvariable";
    }

    @Test
    public void testOne() throws Exception {
        final String[] expectedViolation = {
            "14:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                    UnusedLocalVariableCheck.MSG_UNUSED_NAMED_LOCAL_VARIABLE, "a"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableOne']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[./IDENT[@text='a']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableOne']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='a']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableOne']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='a']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableOne']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='a']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathUnusedLocalVariableOne.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testTwo() throws Exception {
        final String[] expectedViolation = {
            "18:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                    UnusedLocalVariableCheck.MSG_UNUSED_LOCAL_VARIABLE, "b"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableTwo']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF[./IDENT[@text='b']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableTwo']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='b']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableTwo']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='b']]/TYPE", "/COMPILATION_UNIT/CLASS_DEF["
                        + "./IDENT[@text='InputXpathUnusedLocalVariableTwo']]/"
                        + "OBJBLOCK/METHOD_DEF[./IDENT[@text='foo']]/SLIST/VARIABLE_DEF["
                        + "./IDENT[@text='b']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathUnusedLocalVariableTwo.java"),
                expectedXpathQueries,
                expectedViolation);
    }

    @Test
    public void testThree() throws Exception {
        final String[] expectedViolation = {
            "16:9: " + getCheckMessage(UnusedLocalVariableCheck.class,
                   UnusedLocalVariableCheck.MSG_UNUSED_NAMED_LOCAL_VARIABLE, "c"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableThree']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_DO/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='c']]",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableThree']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_DO/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='c']]/MODIFIERS",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableThree']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_DO/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='c']]/TYPE",
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT["
                        + "@text='InputXpathUnusedLocalVariableThree']]/OBJBLOCK/"
                        + "METHOD_DEF[./IDENT[@text='foo']]/SLIST/LITERAL_DO/SLIST/"
                        + "VARIABLE_DEF[./IDENT[@text='c']]/TYPE/LITERAL_INT"
        );

        verifyXpathWithInlineConfigParser(
                getPath("InputXpathUnusedLocalVariableThree.java"),
                expectedXpathQueries,
                expectedViolation);
    }

}
