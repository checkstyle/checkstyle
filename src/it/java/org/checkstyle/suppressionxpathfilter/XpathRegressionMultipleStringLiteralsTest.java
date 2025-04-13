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

import static com.puppycrawl.tools.checkstyle.checks.coding.MultipleStringLiteralsCheck.MSG_KEY;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.MultipleStringLiteralsCheck;

public class XpathRegressionMultipleStringLiteralsTest extends AbstractXpathTestSupport {

    @Override
    protected String getCheckName() {

        return MultipleStringLiteralsCheck.class.getSimpleName();
    }

    @Test
    public void testDefault() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathMultipleStringLiteralsDefault.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MultipleStringLiteralsCheck.class);

        final String[] expectedViolations = {
            "4:16: " + getCheckMessage(MultipleStringLiteralsCheck.class,
                    MSG_KEY, "\"StringContents\"", 2),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                    + "[./IDENT[@text='InputXpathMultipleStringLiteralsDefault']]"
                    + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]"
                    + "/ASSIGN/EXPR[./STRING_LITERAL[@text='StringContents']]",
                 "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathMultipleStringLiteralsDefault']]"
                + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a']]"
                + "/ASSIGN/EXPR/STRING_LITERAL[@text='StringContents']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testAllowDuplicates() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathMultipleStringLiteralsAllowDuplicates.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MultipleStringLiteralsCheck.class);
        moduleConfig.addProperty("allowedDuplicates", "2");

        final String[] expectedViolations = {
            "8:19: " + getCheckMessage(MultipleStringLiteralsCheck.class,
                    MSG_KEY, "\", \"", 3),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMultipleStringLiteralsAllowDuplicates']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myTest']]/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='a5']]/ASSIGN/EXPR/PLUS[./STRING_LITERAL[@text=', ']]"
                + "/PLUS/STRING_LITERAL[@text=', ']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testIgnoreRegexp() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathMultipleStringLiteralsIgnoreRegexp.java"));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MultipleStringLiteralsCheck.class);
        moduleConfig.addProperty("ignoreStringsRegexp", "((\"\")|(\", \"))$");

        final String[] expectedViolations = {
            "7:19: " + getCheckMessage(MultipleStringLiteralsCheck.class,
                    MSG_KEY, "\"DoubleString\"", 2),
        };

        final List<String> expectedXpathQueries = Collections.singletonList(
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT"
                + "[@text='InputXpathMultipleStringLiteralsIgnoreRegexp']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='myTest']]/SLIST/VARIABLE_DEF"
                + "[./IDENT[@text='a3']]/ASSIGN/EXPR/PLUS/STRING_LITERAL[@text='DoubleString']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

    @Test
    public void testIgnoreOccurrenceContext() throws Exception {
        final String filePath =
                "InputXpathMultipleStringLiteralsIgnoreOccurrenceContext.java";
        final File fileToProcess = new File(getPath(filePath));

        final DefaultConfiguration moduleConfig =
                createModuleConfig(MultipleStringLiteralsCheck.class);
        moduleConfig.addProperty("ignoreOccurrenceContext", "");

        final String[] expectedViolations = {
            "5:17: " + getCheckMessage(MultipleStringLiteralsCheck.class,
                    MSG_KEY, "\"unchecked\"", 3),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
               "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
               + "'InputXpathMultipleStringLiteralsIgnoreOccurrenceContext']]"
               + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a1']]"
               + "/ASSIGN/EXPR[./STRING_LITERAL[@text='unchecked']]",
               "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text="
               + "'InputXpathMultipleStringLiteralsIgnoreOccurrenceContext']]"
               + "/OBJBLOCK/VARIABLE_DEF[./IDENT[@text='a1']]"
               + "/ASSIGN/EXPR/STRING_LITERAL[@text='unchecked']"
        );

        runVerifications(moduleConfig, fileToProcess, expectedViolations, expectedXpathQueries);
    }

}
