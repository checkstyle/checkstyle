///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package org.checkstyle.suppressionxpathfilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.checks.coding.ModifiedControlVariableCheck;

public class XpathRegressionModifiedControlVariableTest extends AbstractXpathTestSupport {
    private static Class<ModifiedControlVariableCheck> checkClass =
            ModifiedControlVariableCheck.class;

    @Override
    protected String getCheckName() {
        return checkClass.getSimpleName();
    }

    @Test
    public void testDefaultForLoop() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathModifiedControlVariableWithFor.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(checkClass);
        final String[] expectedViolation = {
            "6:14: " + getCheckMessage(checkClass,
                    ModifiedControlVariableCheck.MSG_KEY, "i"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableWithFor']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableWithFor']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_FOR/SLIST/EXPR/POST_INC[./IDENT[@text='i']]");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testDefaultForeach() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathModifiedControlVariableWithForeach.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(checkClass);
        final String[] expectedViolation = {
            "7:15: " + getCheckMessage(checkClass,
                        ModifiedControlVariableCheck.MSG_KEY, "s"),
        };
        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableWithForeach']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableWithForeach']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_FOR/SLIST/EXPR/PLUS_ASSIGN[./IDENT[@text='s']]"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testSkipEnhancedForLoop() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathModifiedControlVariableSkipEnhancedForLoop.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(checkClass);
        moduleConfig.addProperty("skipEnhancedForLoopVariable", "true");
        final String[] expectedViolation = {
            "10:14: " + getCheckMessage(checkClass,
                        ModifiedControlVariableCheck.MSG_KEY, "i"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableSkipEnhancedForLoop']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableSkipEnhancedForLoop']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_FOR/SLIST/EXPR/POST_INC[./IDENT[@text='i']]"
        );
        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testDefaultNestedForLoop() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathModifiedControlVariableNestedWithFor.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(checkClass);
        final String[] expectedViolation = {
            "7:19: " + getCheckMessage(checkClass,
                    ModifiedControlVariableCheck.MSG_KEY, "j"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedWithFor']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_FOR/SLIST/LITERAL_FOR/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedWithFor']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_FOR/SLIST"
                + "/LITERAL_FOR/SLIST/EXPR/STAR_ASSIGN[./IDENT[@text='j']]");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testForeachNested() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathModifiedControlVariableNestedWithForeach.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(checkClass);
        final String[] expectedViolation = {
            "8:19: " + getCheckMessage(checkClass,
                    ModifiedControlVariableCheck.MSG_KEY, "s"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedWithForeach']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_FOR/SLIST/LITERAL_FOR/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedWithForeach']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_FOR/SLIST/LITERAL_FOR/SLIST/EXPR"
                + "/PLUS_ASSIGN[./IDENT[@text='s']]");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

    @Test
    public void testSkipEnhancedForLoopNested() throws Exception {
        final File fileToProcess = new File(
                getPath("InputXpathModifiedControlVariableNestedSkipEnhancedForLoop.java"));
        final DefaultConfiguration moduleConfig = createModuleConfig(checkClass);
        moduleConfig.addProperty("skipEnhancedForLoopVariable", "true");
        final String[] expectedViolation = {
            "10:15: " + getCheckMessage(checkClass,
                    ModifiedControlVariableCheck.MSG_KEY, "i"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedSkipEnhancedForLoop']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedSkipEnhancedForLoop']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/EXPR"
                + "/PLUS_ASSIGN[./IDENT[@text='i']]");

        runVerifications(moduleConfig, fileToProcess, expectedViolation, expectedXpathQueries);
    }

}
