////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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
////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;

public class Antlr4AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/antlr4";
    }

    @Test
    public void testPackage() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionPackage.txt"),
                getPath("InputAntlr4AstRegressionPackage.java"));
    }

    @Test
    public void testSimple() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionSimple.txt"),
                getPath("InputAntlr4AstRegressionSimple.java"));
    }

    @Test
    public void testAnno() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionSimpleWithAnno.txt"),
                getPath("InputAntlr4AstRegressionSimpleWithAnno.java"));
    }

    @Test
    public void testImports() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionImports.txt"),
                getPath("InputAntlr4AstRegressionImports.java"));
    }

    @Test
    public void testConstructorCall() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionConstructorCall.txt"),
                getPath("InputAntlr4AstRegressionConstructorCall.java"));
    }

    @Test
    public void testMethodCall() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionMethodCall.txt"),
                getPath("InputAntlr4AstRegressionMethodCall.java"));
    }

    @Test
    public void testRegressionJavaClass1() throws Exception {
        verifyAst(getPath("InputRegressionJavaClass1Ast.txt"),
                getPath("InputRegressionJavaClass1.java"));
    }

    @Test
    public void testRegressionAnnotationLocation() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionAnnotationLocation.txt"),
                getPath("InputAntlr4AstRegressionAnnotationLocation.java"));
    }

    @Test
    public void testRegressionKeywordsAndOperators() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionKeywordsAndOperators.txt"),
                getPath("InputAntlr4AstRegressionKeywordsAndOperators.java"));
    }

    @Test
    public void testRegressionDiamondOperators() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionKeywordsAndOperators.txt"),
                getPath("InputAntlr4AstRegressionKeywordsAndOperators.java"));
    }

    @Test
    public void testSingleLineBlocks() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionSingleLineBlocks.txt"),
                getPath("InputAntlr4AstRegressionSingleLineBlocks.java"));
    }

    @Test
    public void testExpressionList() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionExpressionList.txt"),
                getPath("InputAntlr4AstRegressionExpressionList.java"));
    }

    @Test
    public void testNewTypeTree() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionNewTypeTree.txt"),
                getPath("InputAntlr4AstRegressionNewTypeTree.java"));
    }

    @Test
    public void testFallThroughDefault() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionFallThroughDefault.txt"),
                getPath("InputAntlr4AstRegressionFallThroughDefault.java"));
    }

    @Test
    public void testPackageAnnotation() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionPackageAnnotation.txt"),
                getPath("package-info.java"));
    }

    @Test
    public void testAnnotationOnSameLine1() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionAnnotationOnSameLine.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnSameLine.java"));
    }

    @Test
    public void testAnnotationOnSameLine2() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionAnnotationOnSameLine2.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnSameLine2.java"));
    }

    @Test
    public void testSuppressWarnings() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionSuppressWarnings.txt"),
                getPath("InputAntlr4AstRegressionSuppressWarnings.java"));
    }

    @Test
    public void testBadOverride() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionBadOverride.txt"),
                getPath("InputAntlr4AstRegressionBadOverride.java"));
    }

    @Test
    public void testTrickySwitch() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionTrickySwitch.txt"),
                getPath("InputAntlr4AstRegressionTrickySwitch.java"));
    }

    @Test
    public void testExplicitInitialization() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionExplicitInitialization.txt"),
                getPath("InputAntlr4AstRegressionExplicitInitialization.java"));
    }

    @Test
    public void testTypeParams() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionTypeParams.txt"),
                getPath("InputAntlr4AstRegressionTypeParams.java"));
    }

    @Test
    public void testForLoops() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionForLoops.txt"),
                getPath("InputAntlr4AstRegressionForLoops.java"));
    }

    @Test
    public void testIllegalCatch() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionIllegalCatch.txt"),
                getPath("InputAntlr4AstRegressionIllegalCatch.java"));
    }

    @Test
    public void testNewLineAtEndOfFileCr() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionNewLineAtEndOfFileCr.txt"),
                getPath("InputAntlr4AstRegressionNewLineAtEndOfFileCr.java"));
    }

    @Test
    public void testWeirdCtor() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionWeirdCtor.txt"),
                getPath("InputAntlr4AstRegressionWeirdCtor.java"));
    }

    @Test
    public void testAnnotationOnQualifiedTypes() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionAnnotationOnQualifiedTypes.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnQualifiedTypes.java"));
    }

    @Test
    public void testAnnotationOnArrays() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionAnnotationOnArrays.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnArrays.java"));
    }

    @Test
    public void testMethodRefs() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionMethodRefs.txt"),
                getPath("InputAntlr4AstRegressionMethodRefs.java"));
    }

    @Test
    public void testEmbeddedBlockComments() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionEmbeddedBlockComments.txt"),
                getPath("InputAntlr4AstRegressionEmbeddedBlockComments.java"));
    }

    @Test
    public void testTrickySwitchWithComments() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionTrickySwitchWithComments.txt"),
                getPath("InputAntlr4AstRegressionTrickySwitchWithComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testCassandraInputWithComments() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionCassandraInputWithComments.txt"),
                getPath("InputAntlr4AstRegressionCassandraInputWithComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testCommentsOnAnnotationsAndEnums() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionCommentsOnAnnotationsAndEnums.txt"),
                getPath("InputAntlr4AstRegressionCommentsOnAnnotationsAndEnums.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testUncommon() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionUncommon.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionUncommon.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testUncommon2() throws Exception {
        verifyAst(getPath("InputAntlr4AstRegressionUncommon2.txt"),
                getPath("InputAntlr4AstRegressionUncommon2.java"),
                JavaParser.Options.WITH_COMMENTS);
    }
}
