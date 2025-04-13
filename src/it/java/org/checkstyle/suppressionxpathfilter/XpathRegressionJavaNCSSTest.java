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
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.metrics.JavaNCSSCheck;

// -@cs[AbbreviationAsWordInName] Test should be named as its main class.
public class XpathRegressionJavaNCSSTest extends AbstractXpathTestSupport {

    private final String checkName = JavaNCSSCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Test
    public void testOne() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathJavaNCSSOne.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavaNCSSCheck.class);

        final String[] expectedViolation = {
            "5:5: " + getCheckMessage(JavaNCSSCheck.class,
                    JavaNCSSCheck.MSG_METHOD, 51, 50),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSOne']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testTwo() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathJavaNCSSTwo.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavaNCSSCheck.class);

        moduleConfig.addProperty("classMaximum", "50");

        final String[] expectedViolation = {
            "3:1: " + getCheckMessage(JavaNCSSCheck.class,
                    JavaNCSSCheck.MSG_CLASS, 51, 50),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSTwo']]",

                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSTwo']]/MODIFIERS",

                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathJavaNCSSTwo']]"
                + "/MODIFIERS/LITERAL_PUBLIC"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testThree() throws Exception {
        final File fileToProcess =
                new File(getPath("InputXpathJavaNCSSThree.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(JavaNCSSCheck.class);

        moduleConfig.addProperty("fileMaximum", "50");

        final String[] expectedViolation = {
            "1:1: " + getCheckMessage(JavaNCSSCheck.class,
                    JavaNCSSCheck.MSG_FILE, 51, 50),
        };

        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT",
                "/COMPILATION_UNIT/PACKAGE_DEF"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }
}
