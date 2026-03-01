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

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.UnnecessaryTypeArgumentsWithRecordPatternCheck;

public class XpathRegressionUnnecessaryTypeArgumentsWithRecordPatternTest
        extends AbstractXpathTestSupport {

    private final String checkName =
            UnnecessaryTypeArgumentsWithRecordPatternCheck.class.getSimpleName();

    @Override
    protected String getCheckName() {
        return checkName;
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/"
                + "coding/unnecessarytypeargumentswithrecordpattern";
    }

    @Test
    public void testInstanceOf() throws Exception {
        final File fileToProcess =
            new File(getPath(
                "InputXpathUnnecessaryTypeArgumentsWithRecordPatternInstanceOf.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryTypeArgumentsWithRecordPatternCheck.class);

        final String[] expected = {
            "8:31: " + getCheckMessage(UnnecessaryTypeArgumentsWithRecordPatternCheck.class,
                    UnnecessaryTypeArgumentsWithRecordPatternCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT"
                + "[@text='InputXpathUnnecessaryTypeArgumentsWithRecordPatternInstanceOf']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_IF/EXPR"
                + "/LITERAL_INSTANCEOF[./IDENT[@text='box']]"
                + "/RECORD_PATTERN_DEF"
                + "/TYPE[./IDENT[@text='Box']]"
                + "/TYPE_ARGUMENTS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT"
                + "[@text='InputXpathUnnecessaryTypeArgumentsWithRecordPatternInstanceOf']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_IF/EXPR"
                + "/LITERAL_INSTANCEOF[./IDENT[@text='box']]"
                + "/RECORD_PATTERN_DEF"
                + "/TYPE[./IDENT[@text='Box']]"
                + "/TYPE_ARGUMENTS/GENERIC_START"
        );

        runVerifications(moduleConfig, fileToProcess,
                expected, expectedXpathQueries);
    }

    @Test
    public void testSwitch() throws Exception {
        final File fileToProcess =
            new File(getPath(
                "InputXpathUnnecessaryTypeArgumentsWithRecordPatternSwitch.java"));

        final DefaultConfiguration moduleConfig =
            createModuleConfig(UnnecessaryTypeArgumentsWithRecordPatternCheck.class);

        final String[] expected = {
            "9:21: " + getCheckMessage(UnnecessaryTypeArgumentsWithRecordPatternCheck.class,
                    UnnecessaryTypeArgumentsWithRecordPatternCheck.MSG_KEY),
        };

        final List<String> expectedXpathQueries = List.of(
            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryTypeArgumentsWithRecordPatternSwitch']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_SWITCH"
                + "/SWITCH_RULE/LITERAL_CASE"
                + "/RECORD_PATTERN_DEF"
                + "/TYPE[./IDENT[@text='Box']]"
                + "/TYPE_ARGUMENTS",

            "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathUnnecessaryTypeArgumentsWithRecordPatternSwitch']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_SWITCH"
                + "/SWITCH_RULE/LITERAL_CASE"
                + "/RECORD_PATTERN_DEF"
                + "/TYPE[./IDENT[@text='Box']]"
                + "/TYPE_ARGUMENTS/GENERIC_START"
        );

        runVerifications(moduleConfig, fileToProcess,
            expected, expectedXpathQueries);
    }

}
