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

import com.puppycrawl.tools.checkstyle.checks.coding.ModifiedControlVariableCheck;

public class XpathRegressionModifiedControlVariableTest extends AbstractXpathTestSupport {

    private static final Class<ModifiedControlVariableCheck> CLAZZ =
            ModifiedControlVariableCheck.class;

    @Override
    protected String getCheckName() {
        return CLAZZ.getSimpleName();
    }

    @Override
    public String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter/coding/modifiedcontrolvariable";
    }

    @Test
    public void testDefaultForLoop() throws Exception {
        final String fileToProcess =
                getPath("InputXpathModifiedControlVariableWithFor.java");
        final String[] expectedViolation = {
            "13:14: " + getCheckMessage(CLAZZ,
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

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testDefaultForeach() throws Exception {
        final String fileToProcess =
                getPath("InputXpathModifiedControlVariableWithForeach.java");
        final String[] expectedViolation = {
            "14:15: " + getCheckMessage(CLAZZ,
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
        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testSkipEnhancedForLoop() throws Exception {
        final String fileToProcess =
                getPath("InputXpathModifiedControlVariableSkipEnhancedForLoop.java");
        final String[] expectedViolation = {
            "17:14: " + getCheckMessage(CLAZZ,
                        ModifiedControlVariableCheck.MSG_KEY, "i"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableSkipEnhancedForLoop']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableSkipEnhancedForLoop']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]"
                + "/SLIST/LITERAL_FOR/SLIST/EXPR/POST_INC[./IDENT[@text='i']]");

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testNestedWithFor() throws Exception {
        final String fileToProcess =
                getPath("InputXpathModifiedControlVariableNestedWithFor.java");
        final String[] expectedViolation = {
            "14:19: " + getCheckMessage(CLAZZ,
                    ModifiedControlVariableCheck.MSG_KEY, "j"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedWithFor']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/LITERAL_FOR"
                + "/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedWithFor']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/LITERAL_FOR"
                + "/SLIST/EXPR/STAR_ASSIGN[./IDENT[@text='j']]");

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testNestedWithForeach() throws Exception {
        final String fileToProcess =
                getPath("InputXpathModifiedControlVariableNestedWithForeach.java");
        final String[] expectedViolation = {
            "15:19: " + getCheckMessage(CLAZZ,
                    ModifiedControlVariableCheck.MSG_KEY, "s"),
        };

        final List<String> expectedXpathQueries = Arrays.asList(
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedWithForeach']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/LITERAL_FOR"
                + "/SLIST/EXPR",
                "/COMPILATION_UNIT/CLASS_DEF"
                + "[./IDENT[@text='InputXpathModifiedControlVariableNestedWithForeach']]"
                + "/OBJBLOCK/METHOD_DEF[./IDENT[@text='test']]/SLIST/LITERAL_FOR/SLIST/LITERAL_FOR"
                + "/SLIST/EXPR/PLUS_ASSIGN[./IDENT[@text='s']]");

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

    @Test
    public void testNestedSkipEnhancedForLoop() throws Exception {
        final String fileToProcess =
                getPath("InputXpathModifiedControlVariableNestedSkipEnhancedForLoop.java");
        final String[] expectedViolation = {
            "17:15: " + getCheckMessage(CLAZZ,
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

        verifyXpathWithInlineConfigParser(fileToProcess, expectedXpathQueries, expectedViolation);
    }

}
