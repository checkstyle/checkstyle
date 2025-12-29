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

package com.puppycrawl.tools.checkstyle.grammar;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractTreeTestSupport;
import com.puppycrawl.tools.checkstyle.AstTreeStringPrinter;
import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.FileText;

public class AstRegressionTest extends AbstractTreeTestSupport {

    @Override
    public String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar";
    }

    @Test
    public void classAstTree1() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaClass1Ast.txt"),
                getPath("InputRegressionJavaClass1.java"));
    }

    @Test
    public void classAstTree2() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaClass2Ast.txt"),
                getPath("InputRegressionJavaClass2.java"));
    }

    @Test
    public void java8ClassAstTree1() throws Exception {
        verifyAst(getPath("ExpectedRegressionJava8Class1Ast.txt"),
                getPath("InputRegressionJava8Class1.java"));
    }

    @Test
    public void java8ClassAstTree2() throws Exception {
        verifyAst(getPath("ExpectedRegressionJava8Class2Ast.txt"),
                getPath("InputRegressionJava8Class2.java"));
    }

    @Test
    public void java9TryWithResourcesAstTree() throws Exception {
        verifyAst(getPath("ExpectedJava9TryWithResources.txt"),
                getPath("/java9/InputJava9TryWithResources.java"));
    }

    @Test
    public void advanceJava9TryWithResourcesAstTree() throws Exception {
        verifyAst(getPath("ExpectedAdvanceJava9TryWithResources.txt"),
                getPath("/java9/InputAdvanceJava9TryWithResources.java"));
    }

    @Test
    public void inputSemicolonBetweenImports() throws Exception {
        verifyAst(getPath("ExpectedSemicolonBetweenImportsAst.txt"),
                getNonCompilablePath("InputSemicolonBetweenImports.java"));
    }

    @Test
    public void interfaceAstTree1() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaInterface1Ast.txt"),
                getPath("InputRegressionJavaInterface1.java"));
    }

    @Test
    public void interfaceAstTree2() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaInterface2Ast.txt"),
                getPath("InputRegressionJavaInterface2.java"));
    }

    @Test
    public void java8InterfaceAstTree1() throws Exception {
        verifyAst(getPath("ExpectedRegressionJava8Interface1Ast.txt"),
                getPath("InputRegressionJava8Interface1.java"));
    }

    @Test
    public void enumAstTree1() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaEnum1Ast.txt"),
                getPath("InputRegressionJavaEnum1.java"));
    }

    @Test
    public void enumAstTree2() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaEnum2Ast.txt"),
                getPath("InputRegressionJavaEnum2.java"));
    }

    @Test
    public void annotationAstTree1() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaAnnotation1Ast.txt"),
                getPath("InputRegressionJavaAnnotation1.java"));
    }

    @Test
    public void typecast() throws Exception {
        verifyAst(getPath("ExpectedRegressionJavaTypecastAst.txt"),
                getPath("InputRegressionJavaTypecast.java"));
    }

    @Test
    public void java14InstanceofWithPatternMatching() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14InstanceofWithPatternMatchingAST.txt"),
                getNonCompilablePath("java14/InputJava14InstanceofWithPatternMatching.java"));
    }

    @Test
    public void charLiteralSurrogatePair() throws Exception {
        verifyAst(getPath("ExpectedCharLiteralSurrogatePair.txt"),
                getPath("InputCharLiteralSurrogatePair.java"));
    }

    @Test
    public void customAstTree() throws Exception {
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "\t");
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "\r\n");
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "\n");
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "\r\r");
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "\r");
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "\u000c\f");
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "// \n",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "// \r",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "// \r\n",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "/* \n */",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "/* \r\n */",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("ExpectedRegressionEmptyAst.txt"), "/* \r" + "\u0000\u0000" + " */",
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void newlineCr() throws Exception {
        verifyAst(getPath("ExpectedNewlineCrAtEndOfFileAst.txt"),
                getPath("InputAstRegressionNewlineCrAtEndOfFile.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void java14Records() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14Records.txt"),
                getPath("java14/InputJava14Records.java"));
    }

    @Test
    public void java14RecordsTopLevel() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14RecordsTopLevel.txt"),
                getPath("java14/InputJava14RecordsTopLevel.java"));
    }

    @Test
    public void java14LocalRecordAnnotation() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14LocalRecordAnnotation.txt"),
            getPath("java14/InputJava14LocalRecordAnnotation.java"));
    }

    @Test
    public void java14TextBlocks() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14TextBlocks.txt"),
                getPath("java14/InputJava14TextBlocks.java"));
    }

    @Test
    public void java14TextBlocksEscapes() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14TextBlocksEscapesAreOneChar.txt"),
                getPath("java14/InputJava14TextBlocksEscapesAreOneChar.java"));
    }

    @Test
    public void java14SwitchExpression() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14SwitchExpression.txt"),
                getNonCompilablePath("java14/InputJava14SwitchExpression.java"));
    }

    @Test
    public void inputJava14TextBlocksTabSize() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14TextBlocksTabSize.txt"),
            getPath("java14/InputJava14TextBlocksTabSize.java"));
    }

    @Test
    public void inputEscapedS() throws Exception {
        verifyAst(getPath("java14/ExpectedJava14EscapedS.txt"),
                getPath("java14/InputJava14EscapedS.java"));
    }

    @Test
    public void inputSealedAndPermits() throws Exception {
        verifyAst(getPath("java15/ExpectedAstRegressionSealedAndPermits.txt"),
            getPath("java15/InputAstRegressionSealedAndPermits.java"));
    }

    @Test
    public void inputTopLevelNonSealed() throws Exception {
        verifyAst(getPath("java15/ExpectedTopLevelNonSealed.txt"),
            getPath("java15/InputTopLevelNonSealed.java"));
    }

    @Test
    public void patternVariableWithModifiers() throws Exception {
        verifyAst(getPath("java16/ExpectedPatternVariableWithModifiers.txt"),
                getPath("java16/InputPatternVariableWithModifiers.java"));
    }

    @Test
    public void inputMethodDefArrayDeclarator() throws Exception {
        verifyAst(getPath("ExpectedAstRegressionMethodDefArrayDeclarator.txt"),
                getPath("InputAstRegressionMethodDefArrayDeclarator.java"));
    }

    @Test
    public void inputCstyleArrayDefinition() throws Exception {
        verifyAst(getPath("ExpectedAstRegressionCStyleArrayDefinition.txt"),
                getPath("InputAstRegressionCStyleArrayDefinition.java"));
    }

    @Test
    public void inputAnnotatedMethodVariableArityParam() throws Exception {
        verifyAst(getPath("ExpectedAstRegressionAnnotatedMethodVariableArityParam.txt"),
                getNonCompilablePath("InputAstRegressionAnnotatedMethodVariableArityParam.java"));
    }

    @Test
    public void inputManyAlternativesInMultiCatch() throws Exception {
        verifyAst(getPath("ExpectedAstRegressionManyAlternativesInMultiCatch.txt"),
                getPath("InputAstRegressionManyAlternativesInMultiCatch.java"));
    }

    @Test
    public void tryWithResourcesOnAutoCloseable() throws Exception {
        verifyAst(getPath("ExpectedAstRegressionTryWithResourcesOnAutoCloseable.txt"),
                getPath("InputAstRegressionTryWithResourcesOnAutoCloseable.java"));
    }

    @Test
    public void recordPatterns() throws Exception {
        verifyAst(getPath("ExpectedRecordPatterns.txt"),
                getPath("InputRecordPatterns.java"));
    }

    @Test
    public void trickyWhenUsage() throws Exception {
        verifyAst(getPath("ExpectedPatternsTrickyWhenUsage.txt"),
                getPath("InputPatternsTrickyWhenUsage.java"));
    }

    @Test
    public void patternsInSwitch() throws Exception {
        verifyAst(getPath("ExpectedPatternsInSwitchLabels.txt"),
                getPath("InputPatternsInSwitchLabels.java"));
    }

    @Test
    public void recordPatternsWithNestedDecomposition() throws Exception {
        verifyAst(getPath("ExpectedRecordPatternsNestedDecomposition.txt"),
                getPath("InputRecordPatternsNestedDecomposition.java"));
    }

    @Test
    public void annotationsOnBinding() throws Exception {
        verifyAst(getPath("ExpectedPatternsAnnotationsOnBinding.txt"),
                getPath("InputPatternsAnnotationsOnBinding.java"));
    }

    private static void verifyAstRaw(String expectedTextPrintFileName, String actualJava)
            throws Exception {
        verifyAstRaw(expectedTextPrintFileName, actualJava, JavaParser.Options.WITHOUT_COMMENTS);
    }

    private static void verifyAstRaw(String expectedTextPrintFileName, String actualJava,
            JavaParser.Options withComments) throws Exception {
        final File expectedFile = new File(expectedTextPrintFileName);
        final String expectedContents = new FileText(expectedFile, System.getProperty(
                "file.encoding", StandardCharsets.UTF_8.name()))
                .getFullText().toString().replace("\r", "");

        final FileText actualFileContents = new FileText(new File(""),
                Arrays.asList(actualJava.split("\\n|\\r\\n?")));
        final String actualContents = AstTreeStringPrinter.printAst(actualFileContents,
                withComments);

        assertWithMessage("Generated AST from Java code should match pre-defined AST")
            .that(actualContents)
            .isEqualTo(expectedContents);
    }
}
