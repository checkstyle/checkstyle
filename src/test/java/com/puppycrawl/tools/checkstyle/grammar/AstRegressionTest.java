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

package com.puppycrawl.tools.checkstyle.grammar;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/grammar";
    }

    @Test
    public void testClassAstTree1() throws Exception {
        verifyAst(getPath("InputRegressionJavaClass1Ast.txt"),
                getPath("InputRegressionJavaClass1.java"));
    }

    @Test
    public void testClassAstTree2() throws Exception {
        verifyAst(getPath("InputRegressionJavaClass2Ast.txt"),
                getPath("InputRegressionJavaClass2.java"));
    }

    @Test
    public void testJava8ClassAstTree1() throws Exception {
        verifyAst(getPath("InputRegressionJava8Class1Ast.txt"),
                getPath("InputRegressionJava8Class1.java"));
    }

    @Test
    public void testJava8ClassAstTree2() throws Exception {
        verifyAst(getPath("InputRegressionJava8Class2Ast.txt"),
                getPath("InputRegressionJava8Class2.java"));
    }

    @Test
    public void testJava9TryWithResourcesAstTree() throws Exception {
        verifyAst(getPath("InputJava9TryWithResources.txt"),
                getNonCompilablePath("/java9/InputJava9TryWithResources.java"));
    }

    @Test
    public void testAdvanceJava9TryWithResourcesAstTree() throws Exception {
        verifyAst(getPath("InputAdvanceJava9TryWithResources.txt"),
                getNonCompilablePath("/java9/InputAdvanceJava9TryWithResources.java"));
    }

    @Test
    public void testInputSemicolonBetweenImports() throws Exception {
        verifyAst(getPath("InputSemicolonBetweenImportsAst.txt"),
                getNonCompilablePath("InputSemicolonBetweenImports.java"));
    }

    @Test
    public void testInterfaceAstTree1() throws Exception {
        verifyAst(getPath("InputRegressionJavaInterface1Ast.txt"),
                getPath("InputRegressionJavaInterface1.java"));
    }

    @Test
    public void testInterfaceAstTree2() throws Exception {
        verifyAst(getPath("InputRegressionJavaInterface2Ast.txt"),
                getPath("InputRegressionJavaInterface2.java"));
    }

    @Test
    public void testJava8InterfaceAstTree1() throws Exception {
        verifyAst(getPath("InputRegressionJava8Interface1Ast.txt"),
                getPath("InputRegressionJava8Interface1.java"));
    }

    @Test
    public void testEnumAstTree1() throws Exception {
        verifyAst(getPath("InputRegressionJavaEnum1Ast.txt"),
                getPath("InputRegressionJavaEnum1.java"));
    }

    @Test
    public void testEnumAstTree2() throws Exception {
        verifyAst(getPath("InputRegressionJavaEnum2Ast.txt"),
                getPath("InputRegressionJavaEnum2.java"));
    }

    @Test
    public void testAnnotationAstTree1() throws Exception {
        verifyAst(getPath("InputRegressionJavaAnnotation1Ast.txt"),
                getPath("InputRegressionJavaAnnotation1.java"));
    }

    @Test
    public void testTypecast() throws Exception {
        verifyAst(getPath("InputRegressionJavaTypecastAst.txt"),
                getPath("InputRegressionJavaTypecast.java"));
    }

    @Test
    public void testJava14InstanceofWithPatternMatching() throws Exception {
        verifyAst(getPath("java14/InputJava14InstanceofWithPatternMatchingAST.txt"),
                getNonCompilablePath("java14/InputJava14InstanceofWithPatternMatching.java"));
    }

    @Test
    public void testCharLiteralSurrogatePair() throws Exception {
        verifyAst(getPath("InputCharLiteralSurrogatePair.txt"),
                getPath("InputCharLiteralSurrogatePair.java"));
    }

    @Test
    public void testCustomAstTree() throws Exception {
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\t");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\r\n");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\n");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\r\r");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\r");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\u000c\f");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "// \n",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "// \r",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "// \r\n",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "/* \n */",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "/* \r\n */",
                JavaParser.Options.WITH_COMMENTS);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "/* \r" + "\u0000\u0000" + " */",
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testNewlineCr() throws Exception {
        verifyAst(getPath("InputNewlineCrAtEndOfFileAst.txt"),
                getPath("InputAstRegressionNewlineCrAtEndOfFile.java"),
                JavaParser.Options.WITH_COMMENTS);
    }

    @Test
    public void testJava14Records() throws Exception {
        verifyAst(getPath("java14/InputJava14Records.txt"),
                getNonCompilablePath("java14/InputJava14Records.java"));
    }

    @Test
    public void testJava14RecordsTopLevel() throws Exception {
        verifyAst(getPath("java14/InputJava14RecordsTopLevel.txt"),
                getNonCompilablePath("java14/InputJava14RecordsTopLevel.java"));
    }

    @Test
    public void testJava14LocalRecordAnnotation() throws Exception {
        verifyAst(getPath("java14/InputJava14LocalRecordAnnotation.txt"),
            getNonCompilablePath("java14/InputJava14LocalRecordAnnotation.java"));
    }

    @Test
    public void testJava14TextBlocks() throws Exception {
        verifyAst(getPath("java14/InputJava14TextBlocks.txt"),
                getNonCompilablePath("java14/InputJava14TextBlocks.java"));
    }

    @Test
    public void testJava14TextBlocksEscapes() throws Exception {
        verifyAst(getPath("java14/InputJava14TextBlocksEscapesAreOneChar.txt"),
                getNonCompilablePath("java14/InputJava14TextBlocksEscapesAreOneChar.java"));
    }

    @Test
    public void testJava14SwitchExpression() throws Exception {
        verifyAst(getPath("java14/InputJava14SwitchExpression.txt"),
                getNonCompilablePath("java14/InputJava14SwitchExpression.java"));
    }

    @Test
    public void testInputJava14TextBlocksTabSize() throws Exception {
        verifyAst(getPath("java14/InputJava14TextBlocksTabSize.txt"),
            getNonCompilablePath("java14/InputJava14TextBlocksTabSize.java"));
    }

    @Test
    public void testInputEscapedS() throws Exception {
        verifyAst(getPath("java14/InputJava14EscapedS.txt"),
                getNonCompilablePath("java14/InputJava14EscapedS.java"));
    }

    @Test
    public void testInputSealedAndPermits() throws Exception {
        verifyAst(getPath("java15/InputAstRegressionSealedAndPermits.txt"),
            getNonCompilablePath("java15/InputAstRegressionSealedAndPermits.java"));
    }

    @Test
    public void testInputTopLevelNonSealed() throws Exception {
        verifyAst(getPath("java15/InputTopLevelNonSealed.txt"),
            getNonCompilablePath("java15/InputTopLevelNonSealed.java"));
    }

    @Test
    public void testPatternVariableWithModifiers() throws Exception {
        verifyAst(getPath("java16/InputPatternVariableWithModifiers.txt"),
                getNonCompilablePath("java16/InputPatternVariableWithModifiers.java"));
    }

    @Test
    public void testInputMethodDefArrayDeclarator() throws Exception {
        verifyAst(getPath("InputAstRegressionMethodDefArrayDeclarator.txt"),
                getPath("InputAstRegressionMethodDefArrayDeclarator.java"));
    }

    @Test
    public void testInputCstyleArrayDefinition() throws Exception {
        verifyAst(getPath("InputAstRegressionCStyleArrayDefinition.txt"),
                getPath("InputAstRegressionCStyleArrayDefinition.java"));
    }

    @Test
    public void testInputAnnotatedMethodVariableArityParam() throws Exception {
        verifyAst(getPath("InputAstRegressionAnnotatedMethodVariableArityParam.txt"),
                getPath("InputAstRegressionAnnotatedMethodVariableArityParam.java"));
    }

    @Test
    public void testInputManyAlternativesInMultiCatch() throws Exception {
        verifyAst(getPath("InputAstRegressionManyAlternativesInMultiCatch.txt"),
                getPath("InputAstRegressionManyAlternativesInMultiCatch.java"));
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

        assertEquals(expectedContents, actualContents,
                "Generated AST from Java code should match pre-defined AST");
    }
}
