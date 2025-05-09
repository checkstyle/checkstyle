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

package com.puppycrawl.tools.checkstyle.grammar.java19;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;

class Java19AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/java19";
    }

    @Test
    void patternsInSwitch() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19PatternsInSwitchLabels.txt"),
                getNonCompilablePath("InputJava19PatternsInSwitchLabels.java"));
    }

    @Test
    void patternsInNullSwitch1() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19PatternsInNullSwitch1.txt"),
                getNonCompilablePath("InputJava19PatternsInNullSwitch1.java"));
    }

    @Test
    void patternsInNullSwitch2() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19PatternsInNullSwitch2.txt"),
                getNonCompilablePath("InputJava19PatternsInNullSwitch2.java"));
    }

    @Test
    void annotationsOnBinding() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19PatternsAnnotationsOnBinding.txt"),
                getNonCompilablePath("InputJava19PatternsAnnotationsOnBinding.java"));
    }

    @Test
    void trickyWhenUsage() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19PatternsTrickyWhenUsage.txt"),
                getNonCompilablePath("InputJava19PatternsTrickyWhenUsage.java"));
    }

    @Test
    void lotsOfOperators() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19BindingsWithLotsOfOperators.txt"),
                getNonCompilablePath("InputJava19BindingsWithLotsOfOperators.java"));
    }

    @Test
    void recordPatternsWithNestedDecomposition() throws Exception {
        verifyAst(getPath("ExpectedRecordPatternsPreviewNestedDecomposition.txt"),
                getNonCompilablePath("InputRecordPatternsPreviewNestedDecomposition.java"));
    }

    @Test
    void recordPatternsPreview() throws Exception {
        verifyAst(getPath("ExpectedRecordPatternsPreview.txt"),
                getNonCompilablePath("InputRecordPatternsPreview.java"));
    }

    @Test
    void genericRecordDecomposition() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedGenericRecordDeconstructionPattern.txt"),
                getNonCompilablePath("InputGenericRecordDeconstructionPattern.java"));
    }

    @Test
    void guardsWithExtraParenthesis() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19GuardsWithExtraParenthesis.txt"),
                getNonCompilablePath("InputJava19GuardsWithExtraParenthesis.java"));
    }

    @Test
    void bindingWithModifiers() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedJava19BindingWithModifiers.txt"),
                getNonCompilablePath("InputJava19BindingWithModifiers.java"));
    }

    @Test
    void test() throws Exception {
        verifyAst(getNonCompilablePath(
                "ExpectedJava19RecordDecompositionWithConditionInLoops.txt"),
                getNonCompilablePath(
                        "InputJava19RecordDecompositionWithConditionInLoops.java"
                ));
    }
}
