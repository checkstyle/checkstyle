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

package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import com.puppycrawl.tools.checkstyle.JavaParser;

class Antlr4AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar/antlr4";
    }

    @Test
    void testPackage() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionPackage.txt"),
                getPath("InputAntlr4AstRegressionPackage.java"));
    }

    @Test
    void simple() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSimple.txt"),
                getPath("InputAntlr4AstRegressionSimple.java"));
    }

    @Test
    void anno() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSimpleWithAnno.txt"),
                getPath("InputAntlr4AstRegressionSimpleWithAnno.java"));
    }

    @Test
    void imports() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionImports.txt"),
                getPath("InputAntlr4AstRegressionImports.java"));
    }

    @Test
    void constructorCall() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionConstructorCall.txt"),
                getPath("InputAntlr4AstRegressionConstructorCall.java"));
    }

    @Test
    void methodCall() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionMethodCall.txt"),
                getPath("InputAntlr4AstRegressionMethodCall.java"));
    }

    @Test
    void regressionJavaClass1() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaClass1Ast.txt"),
                getPath("InputRegressionJavaClass1.java"));
    }

    @Test
    void regressionAnnotationLocation() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationLocation.txt"),
                getPath("InputAntlr4AstRegressionAnnotationLocation.java"));
    }

    @Test
    void regressionKeywordsAndOperators() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionKeywordsAndOperators.txt"),
                getPath("InputAntlr4AstRegressionKeywordsAndOperators.java"));
    }

    @Test
    void regressionDiamondOperators() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionKeywordsAndOperators.txt"),
                getPath("InputAntlr4AstRegressionKeywordsAndOperators.java"));
    }

    @Test
    void singleLineBlocks() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSingleLineBlocks.txt"),
                getPath("InputAntlr4AstRegressionSingleLineBlocks.java"));
    }

    @Test
    void expressionList() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionExpressionList.txt"),
                getPath("InputAntlr4AstRegressionExpressionList.java"));
    }

    @Test
    void newTypeTree() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionNewTypeTree.txt"),
                getPath("InputAntlr4AstRegressionNewTypeTree.java"));
    }

    @Test
    void fallThroughDefault() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionFallThroughDefault.txt"),
                getPath("InputAntlr4AstRegressionFallThroughDefault.java"));
    }

    @Test
    void packageAnnotation() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionPackageAnnotation.txt"),
                getPath("package-info.java"));
    }

    @Test
    void annotationOnSameLine1() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationOnSameLine.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnSameLine.java"));
    }

    @Test
    void annotationOnSameLine2() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationOnSameLine2.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnSameLine2.java"));
    }

    @Test
    void suppressWarnings() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSuppressWarnings.txt"),
                getPath("InputAntlr4AstRegressionSuppressWarnings.java"));
    }

    @Test
    void badOverride() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionBadOverride.txt"),
                getPath("InputAntlr4AstRegressionBadOverride.java"));
    }

    @Test
    void trickySwitch() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionTrickySwitch.txt"),
                getPath("InputAntlr4AstRegressionTrickySwitch.java"));
    }

    @Test
    void explicitInitialization() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionExplicitInitialization.txt"),
                getPath("InputAntlr4AstRegressionExplicitInitialization.java"));
    }

    @Test
    void typeParams() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionTypeParams.txt"),
                getPath("InputAntlr4AstRegressionTypeParams.java"));
    }

    @Test
    void forLoops() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionForLoops.txt"),
                getPath("InputAntlr4AstRegressionForLoops.java"));
    }

    @Test
    void illegalCatch() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionIllegalCatch.txt"),
                getPath("InputAntlr4AstRegressionIllegalCatch.java"));
    }

    @Test
    void nestedTypeParametersAndArrayDeclarators() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionNestedTypeParametersAndArrayDeclarators.txt"),
                getPath("InputAntlr4AstRegressionNestedTypeParametersAndArrayDeclarators.java"));
    }

    @Test
    void newLineAtEndOfFileCr() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionNewLineAtEndOfFileCr.txt"),
                getPath("InputAntlr4AstRegressionNewLineAtEndOfFileCr.java"));
    }

    @Test
    void weirdCtor() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionWeirdCtor.txt"),
                getPath("InputAntlr4AstRegressionWeirdCtor.java"));
    }

    @Test
    void annotationOnQualifiedTypes() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationOnQualifiedTypes.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnQualifiedTypes.java"));
    }

    @Test
    void annotationOnArrays() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionAnnotationOnArrays.txt"),
                getPath("InputAntlr4AstRegressionAnnotationOnArrays.java"));
    }

    @Test
    void methodRefs() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionMethodRefs.txt"),
                getPath("InputAntlr4AstRegressionMethodRefs.java"));
    }

    @Test
    void embeddedBlockComments() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionEmbeddedBlockComments.txt"),
                getPath("InputAntlr4AstRegressionEmbeddedBlockComments.java"));
    }

    @Test
    void java16LocalEnumAndInterface() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionJava16LocalEnumAndInterface.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionJava16LocalEnumAndInterface.java"));
    }

    @Test
    void trickySwitchWithComments() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionTrickySwitchWithComments.txt"),
                getPath("InputAntlr4AstRegressionTrickySwitchWithComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void cassandraInputWithComments() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionCassandraInputWithComments.txt"),
                getPath("InputAntlr4AstRegressionCassandraInputWithComments.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void commentsOnAnnotationsAndEnums() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionCommentsOnAnnotationsAndEnums.txt"),
                getPath("InputAntlr4AstRegressionCommentsOnAnnotationsAndEnums.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void uncommon() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUncommon.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionUncommon.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void uncommon2() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUncommon2.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionUncommon2.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void trickyYield() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionTrickyYield.txt"),
                getPath("InputAntlr4AstRegressionTrickyYield.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void singleCommaInArrayInit() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionSingleCommaInArrayInit.txt"),
                getPath("InputAntlr4AstRegressionSingleCommaInArrayInit.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void uncommon3() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUncommon3.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionUncommon3.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void uncommon4() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUncommon4.txt"),
                getPath("InputAntlr4AstRegressionUncommon4.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void qualifiedConstructorParameter() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionQualifiedConstructorParameter.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionQualifiedConstructorParameter.java"));
    }

    @Test
    void java15FinalLocalRecord() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionJava15FinalLocalRecord.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionJava15FinalLocalRecord.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    void unusualAnnotation() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionUnusualAnnotation.txt"),
                getPath("InputAntlr4AstRegressionUnusualAnnotation.java"));
    }

    @Test
    void lambda() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionLambda.txt"),
                getPath("InputAntlr4AstRegressionLambda.java"));
    }

    @Test
    void expressions() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionExpressions.txt"),
                getPath("InputAntlr4AstRegressionExpressions.java"));
    }

    @Test
    void interfaceMemberAlternativePrecedence() throws Exception {
        verifyAst(getPath("ExpectedAntlr4AstRegressionInterfaceRecordDef.txt"),
                getNonCompilablePath("InputAntlr4AstRegressionInterfaceRecordDef.java"));
    }
}
