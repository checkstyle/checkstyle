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

package org.checkstyle.suppressionxpathfilter.blocks;

import java.io.File;
import java.util.Collections;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.blocks.RightCurlyAloneOrEmptyCheck;

public class XpathRegressionRightCurlyAloneOrEmptyTest extends AbstractXpathTestSupport {

    private final String checkName = RightCurlyAloneOrEmptyCheck.class.getSimpleName();

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/blocks/rightcurlyaloneorempty";
    }

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testRightCurlyAloneOrEmptyOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRightCurlyAloneOrEmptyOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RightCurlyAloneOrEmptyCheck.class);

        final String[] expectedViolation = {
            "7:9: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.class,
                RightCurlyAloneOrEmptyCheck.MSG_KEY_ALONE_OR_EMPTY, "}", 9),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyAloneOrEmptyOne']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/SLIST/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testRightCurlyAloneOrEmptyTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRightCurlyAloneOrEmptyTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RightCurlyAloneOrEmptyCheck.class);
        moduleConfig.addProperty("tokens", "LITERAL_CASE");

        final String[] expectedViolation = {
            "9:13: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.class,
                RightCurlyAloneOrEmptyCheck.MSG_KEY_ALONE_OR_EMPTY, "}", 13),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyAloneOrEmptyTwo']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_SWITCH"
                + "/CASE_GROUP/SLIST/SLIST/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }

    @Test
    public void testRightCurlyAloneOrEmptyThree() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathRightCurlyAloneOrEmptyThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(RightCurlyAloneOrEmptyCheck.class);
        moduleConfig.addProperty("tokens", "METHOD_DEF");

        final String[] expectedViolation = {
            "6:36: " + getCheckMessage(RightCurlyAloneOrEmptyCheck.class,
                RightCurlyAloneOrEmptyCheck.MSG_KEY_ALONE_OR_EMPTY, "}", 36),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathRightCurlyAloneOrEmptyThree']]/OBJBLOCK"
                + "/CLASS_DEF[./IDENT[@text='Demo']]/OBJBLOCK"
                + "/METHOD_DEF[./IDENT[@text='method2']]/SLIST/RCURLY"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation,
                expectedXpathQueries);
    }
}
