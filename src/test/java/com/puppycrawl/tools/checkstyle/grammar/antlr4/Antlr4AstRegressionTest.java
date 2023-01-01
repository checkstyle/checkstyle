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
import com.puppycrawl.tools.checkstyle.JavaParser;

public class Antlr4AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/antlr4";
    }

    @Test
    public void testPackage() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionPackage.txt"),
                getPath("InputAntlr4AstRegressionPackage.java"));
    }

    @Test
    public void testSimple() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSimple.txt"),
                getPath("InputAntlr4AstRegressionSimple.java"));
    }

    @Test
    public void testAnno() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSimpleWithAnno.txt"),
                getPath("InputAntlr4AstRegressionSimpleWithAnno.java"));
    }

    @Test
    public void testImports() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionImports.txt"),
                getPath("InputAntlr4AstRegressionImports.java"));
    }

    @Test
    public void testConstructorCall() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionConstructorCall.txt"),
                getPath("InputAntlr4AstRegressionConstructorCall.java"));
    }

    @Test
    public void testMethodCall() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionMethodCall.txt"),
                getPath("InputAntlr4AstRegressionMethodCall.java"));
    }

    @Test
    public void testRegressionJavaClass1() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaClass1Ast.txt"),
                getPath("InputRegressionJavaClass1.java"));
    }

    @Test
    public void testRegressionAnnotationLocation() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationLocation.txt"),
                getPath("InputAntlr4AstRegressionAnnotationLocation.java"));
    }

    @Test
    public void testRegressionKeywordsAndOperators() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionKeywordsAndOperators.txt"),
                getPath("InputAntlr4AstRegressionKeywordsAndOperators.java"));
    }

    @Test
    public void testRegressionDiamondOperators() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionKeywordsAndOperators.txt"),
                getPath("InputAntlr4AstRegressionKeywordsAndOperators.java"));
    }

    @Test
    public void testSingleLineBlocks() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSingleLineBlocks.txt"),
                getPath("InputAntlr4AstRegressionSingleLineBlocks.java"));
    }

    @Test
    public void testExpressionList() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionExpressionList.txt"),
                getPath("InputAntlr4AstRegressionExpressionList.java"));
    }

    @Test
    public void testNewTypeTree() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionNewTypeTree.txt"),
                getPath("InputAntlr4AstRegressionNewTypeTree.java"));
    }

    @Test
    public void testFallThroughDefault() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionFallThroughDefault.txt"),
                getPath("InputAntlr4AstRegressionFallThroughDefault.java"));
    }

    @Test
    public void testPackageAnnotation() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionPackageAnnotation.txt"),
                getPath("package-info.java"));
    }

    @Test
    public void testAnnotationOnSameLine1() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationOnSameLine.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnSameLine.java"));
    }

    @Test
    public void testAnnotationOnSameLine2() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationOnSameLine2.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnSameLine2.java"));
    }

    @Test
    public void testSuppressWarnings() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSuppressWarnings.txt"),
                getPath("InputAntlr4AstRegressionSuppressWarnings.java"));
    }

    @Test
    public void testBadOverride() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionBadOverride.txt"),
                getPath("InputAntlr4AstRegressionBadOverride.java"));
    }

    @Test
    public void testTrickySwitch() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionTrickySwitch.txt"),
                getPath("InputAntlr4AstRegressionTrickySwitch.java"));
    }

    @Test
    public void testExplicitInitialization() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionExplicitInitialization.txt"),
                getPath("InputAntlr4AstRegressionExplicitInitialization.java"));
    }

    @Test
    public void testTypeParams() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionTypeParams.txt"),
                getPath("InputAntlr4AstRegressionTypeParams.java"));
    }

    @Test
    public void testForLoops() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionForLoops.txt"),
                getPath("InputAntlr4AstRegressionForLoops.java"));
    }

    @Test
    public void testIllegalCatch() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionIllegalCatch.txt"),
                getPath("InputAntlr4AstRegressionIllegalCatch.java"));
    }

    @Test
    public void testNestedTypeParametersAndArrayDeclarators() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionNestedTypeParametersAndArrayDeclarators.txt"),
                getPath("InputAntlr4AstRegressionNestedTypeParametersAndArrayDeclarators.java"));
    }

    @Test
    public void testNewLineAtEndOfFileCr() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionNewLineAtEndOfFileCr.txt"),
                getPath("InputAntlr4AstRegressionNewLineAtEndOfFileCr.java"));
    }

    @Test
    public void testWeirdCtor() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionWeirdCtor.txt"),
                getPath("InputAntlr4AstRegressionWeirdCtor.java"));
    }

    @Test
    public void testAnnotationOnQualifiedTypes() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationOnQualifiedTypes.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnQualifiedTypes.java"));
    }

    @Test
    public void testAnnotationOnArrays() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationOnArrays.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnArrays.java"));
    }

    @Test
    public void testMethodRefs() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionMethodRefs.txt"),
                getPath("InputAntlr4AstRegressionMethodRefs.java"));
    }

    @Test
    public void testEmbeddedBlockComments() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionEmbeddedBlockComments.txt"),
                getPath("InputAntlr4AstRegressionEmbeddedBlockComments.java"));
    }

    @Test
    public void testJava16LocalEnumAndInterface() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionJava16LocalEnumAndInterface.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionJava16LocalEnumAndInterface.java"));
    }

    @Test
    public void testTrickySwitchWithComments() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionTrickySwitchWithComments.txt"),
                getPath("InputAntlr4AstRegressionTrickySwitchWithComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testCassandraInputWithComments() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionCassandraInputWithComments.txt"),
                getPath("InputAntlr4AstRegressionCassandraInputWithComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testCommentsOnAnnotationsAndEnums() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionCommentsOnAnnotationsAndEnums.txt"),
                getPath("InputAntlr4AstRegressionCommentsOnAnnotationsAndEnums.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testUncommon() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUncommon.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionUncommon.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testUncommon2() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUncommon2.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionUncommon2.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testTrickyYield() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionTrickyYield.txt"),
                getPath("InputAntlr4AstRegressionTrickyYield.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testSingleCommaInArrayInit() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSingleCommaInArrayInit.txt"),
                getPath("InputAntlr4AstRegressionSingleCommaInArrayInit.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testUncommon3() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUncommon3.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionUncommon3.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testUncommon4() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUncommon4.txt"),
                getPath("InputAntlr4AstRegressionUncommon4.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testQualifiedConstructorParameter() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionQualifiedConstructorParameter.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionQualifiedConstructorParameter.java"));
    }

    @Test
    public void testJava15FinalLocalRecord() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionJava15FinalLocalRecord.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionJava15FinalLocalRecord.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testUnusualAnnotation() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUnusualAnnotation.txt"),
                getPath("InputAntlr4AstRegressionUnusualAnnotation.java"));
    }

    @Test
    public void testInterfaceMemberAlternativePrecedence() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionInterfaceRecordDef.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionInterfaceRecordDef.java"));
    }
}
