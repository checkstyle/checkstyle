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

package com.puppycrawl.tools.checkstyle.grammar.java19;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;

public class Java19AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java19";
    }

    @Test
    public void testPatternsInNullSwitch1() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19PatternsInNullSwitch1.txt"),
                getNonCompilablePath("InputJava19PatternsInNullSwitch1.java"));
    }

    @Test
    public void testPatternsInNullSwitch2() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19PatternsInNullSwitch2.txt"),
                getNonCompilablePath("InputJava19PatternsInNullSwitch2.java"));
    }

    @Test
    public void testLotsOfOperators() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19BindingsWithLotsOfOperators.txt"),
                getNonCompilablePath("InputJava19BindingsWithLotsOfOperators.java"));
    }

    @Test
    public void testGenericRecordDecomposition() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedGenericRecordDeconstructionPattern.txt"),
                getNonCompilablePath("InputGenericRecordDeconstructionPattern.java"));
    }

    @Test
    public void testGuardsWithExtraParenthesis() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19GuardsWithExtraParenthesis.txt"),
                getNonCompilablePath("InputJava19GuardsWithExtraParenthesis.java"));
    }

    @Test
    public void testBindingWithModifiers() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19BindingWithModifiers.txt"),
                getNonCompilablePath("InputJava19BindingWithModifiers.java"));
    }

    @Test
    public void test() throws Exception {
        verifyAst(getNonCompilablePath(
                "ExpectedJava19RecordDecompositionWithConditionInLoops.txt"),
                getNonCompilablePath(
                        "InputJava19RecordDecompositionWithConditionInLoops.java"
                ));
    }
}
