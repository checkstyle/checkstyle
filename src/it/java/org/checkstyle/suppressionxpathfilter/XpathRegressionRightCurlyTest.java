///
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2025 the original author or authors.
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
///

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyCheck;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyOption;

public class XpathRegressionRightCurlyTest extends AbstractXpathTestSupport {

    private final String checkName = RightCurlyCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathRightCurlyOne.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(RightCurlyCheck.class);

        final String[] expectedViolation = {
            "8:9: " + getCheckMessage(RightCurlyCheck.class,
                RightCurlyCheck.MSG_KEY_LINE_SAME, "}", 9),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyOne']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_IF/SLIST/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathRightCurlyTwo.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(RightCurlyCheck.class);
        moduleConfig.addProperty("option", RightCurlyOption.ALONE.toString());

        final String[] expectedViolation = {
            "9:15: " + getCheckMessage(RightCurlyCheck.class,
                RightCurlyCheck.MSG_KEY_LINE_ALONE, "}", 15),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyTwo']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='fooMethod']]/SLIST/LITERAL_TRY/SLIST/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathRightCurlyThree.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(RightCurlyCheck.class);
        moduleConfig.addProperty("option", RightCurlyOption.ALONE.toString());

        final String[] expectedViolation = {
            "5:72: " + getCheckMessage(RightCurlyCheck.class,
                RightCurlyCheck.MSG_KEY_LINE_ALONE, "}", 72),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyThree']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='sample']]/SLIST/LITERAL_IF/SLIST/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }

    @Test
    public void testFour() throws Exception {
        final File fileToProcess =
            new File(getPath("InputXpathRightCurlyFour.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(RightCurlyCheck.class);
        moduleConfig.addProperty("option", RightCurlyOption.SAME.toString());

        final String[] expectedViolation = {
            "7:27: " + getCheckMessage(RightCurlyCheck.class,
                RightCurlyCheck.MSG_KEY_LINE_BREAK_BEFORE, "}", 27),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyFour']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='sample']]/SLIST/LITERAL_IF/SLIST/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
            expectedXpathQueries);
    }
}
