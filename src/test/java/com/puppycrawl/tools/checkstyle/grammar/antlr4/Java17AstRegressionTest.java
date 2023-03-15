///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2023 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;

public class Java17AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/antlr4";
    }

    @Test
    public void testPatternsInSwitch() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedAntlr4AstRegressionPatternsInSwitch.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionPatternsInSwitch.java"));
    }

    @Test
    public void testPatternsInIfStatement() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedAntlr4AstRegressionPatternsInIfStatement.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionPatternsInIfStatement.java"));
    }

    @Test
    public void testPatternsInWhile() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedAntlr4AstRegressionPatternsInWhile.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionPatternsInWhile.java"));
    }

    @Test
    public void testPatternsInTernary() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedAntlr4AstRegressionPatternsInTernary.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionPatternsInTernary.java"));
    }

    @Test
    public void testPatternsInFor() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedAntlr4AstRegressionPatternsInFor.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionPatternsInFor.java"));
    }

    @Test
    public void testPatternMatchingInSwitch() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedAntlr4AstRegressionPatternMatchingInSwitch.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionPatternMatchingInSwitch.java"));
    }

    @Test
    public void testCaseDefault() throws Exception {
        verifyAst(getNonCompilablePath("ExpectedAntlr4AstRegressionCaseDefault.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionCaseDefault.java"));
    }
}
