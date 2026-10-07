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

package org.checkstyle.suppressionxpathfilter.indentation;

import java.io.File;
import java.util.List;

import org.checkstyle.suppressionxpathfilter.AbstractXpathTestSupport;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.indentation.OpenjdkLineWrappingCheck;

public class XpathRegressionOpenjdkLineWrappingTest extends AbstractXpathTestSupport {

    private static final Class<OpenjdkLineWrappingCheck> CLASS = OpenjdkLineWrappingCheck.class;

    @Override
    protected String getCheckName() {
        return CLASS.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/indentation/openjdklinewrapping";
    }

    @Test
    public void testExpression() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathOpenjdkLineWrapping.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "6:10: " + getCheckMessage(CLASS, OpenjdkLineWrappingCheck.MSG_ERROR_MULTI,
                    "+", 9, "16, 18, 26"),
        };
        final String expressionXpath =
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathOpenjdkLineWrapping']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST"
                        + "/VARIABLE_DEF[./IDENT[@text='sum']]/ASSIGN/EXPR";
        final List<String> expectedXpathQueries = List.of(
                expressionXpath, expressionXpath + "/PLUS[./NUM_INT[@text='1']]");
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testCondition() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathOpenjdkLineWrappingCondition.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "6:10: " + getCheckMessage(CLASS, OpenjdkLineWrappingCheck.MSG_ERROR_MULTI,
                    "second", 9, "16, 20"),
        };
        final List<String> expectedXpathQueries = List.of(
                "/COMPILATION_UNIT/CLASS_DEF"
                        + "[./IDENT[@text='InputXpathOpenjdkLineWrappingCondition']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_IF/EXPR"
                        + "/LOR[./IDENT[@text='first']]/IDENT[@text='second']");
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testReturn() throws Exception {
        final File fileToProcess = new File(getPath("InputXpathOpenjdkLineWrappingReturn.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(CLASS);
        final String[] expectedViolation = {
            "6:10: " + getCheckMessage(CLASS, OpenjdkLineWrappingCheck.MSG_ERROR_MULTI,
                    "+", 9, "15, 16, 23"),
        };
        final String expressionXpath =
                "/COMPILATION_UNIT/CLASS_DEF[./IDENT[@text='InputXpathOpenjdkLineWrappingReturn']]"
                        + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='method']]/SLIST/LITERAL_RETURN/EXPR";
        final List<String> expectedXpathQueries = List.of(
                expressionXpath, expressionXpath + "/PLUS[./IDENT[@text='value']]");
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
