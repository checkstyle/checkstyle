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
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import org.junit.jupiter.api.Test;

import antlr.NoViableAltForCharException;
import antlr.ParserSharedInputState;
import antlr.SemanticException;
import antlr.TokenBuffer;
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
    public void testUnusedConstructors1() throws Exception {
        final Class<?> clss = GeneratedJavaLexer.class;
        final Constructor<?> constructor = clss.getDeclaredConstructor(InputStream.class);

        assertNotNull(constructor.newInstance(new Object[] {null}),
                "InputStream should not be null");
    }

    @Test
    public void testUnusedConstructors2() throws Exception {
        final Class<?> clss = GeneratedJavaRecognizer.class;
        final Constructor<?> constructor = clss
                .getDeclaredConstructor(ParserSharedInputState.class);

        assertNotNull(constructor.newInstance(new Object[] {null}),
                "ParserSharedInputState should not be null");
    }

    @Test
    public void testUnusedConstructors3() throws Exception {
        final Class<?> clss = GeneratedJavaRecognizer.class;
        final Constructor<?> constructor = clss.getDeclaredConstructor(TokenBuffer.class);

        assertNotNull(constructor.newInstance(new Object[] {null}),
                "TokenBuffer should not be null");
    }

    @Test
    public void testUnusedConstructors4() throws Exception {
        final Class<?> clss = GeneratedTextBlockLexer.class;
        final Constructor<?> constructor = clss.getDeclaredConstructor(Reader.class);

        assertNotNull(constructor.newInstance(new Object[] {null}),
                "Reader should not be null");
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
    public void testImpossibleExceptions() throws Exception {
        AssertGeneratedJavaLexer.verifyFail("mSTD_ESC", 'a');
        AssertGeneratedJavaLexer.verifyFail("mSTD_ESC", '0', (char) 0xFFFF);
        AssertGeneratedJavaLexer.verifyFail("mSTD_ESC", '4', (char) 0xFFFF);
        AssertGeneratedJavaLexer.verifyFail("mCHAR_LITERAL", '\'', '\'');
        AssertGeneratedJavaLexer.verifyFail("mHEX_DIGIT", ';');
        AssertGeneratedJavaLexer.verifyFail("mEXPONENT", ';');
        AssertGeneratedJavaLexer.verifyFail("mBINARY_DIGIT", '2');
        AssertGeneratedJavaLexer.verifyFail("mSIGNED_INTEGER", 'a');
        AssertGeneratedJavaLexer.verifyFail("mID_START", '%');
        AssertGeneratedJavaLexer.verifyFail("mID_START", (char) 0xBF);
        AssertGeneratedJavaLexer.verifyFailNoGuessing("mID_START", (char) 0xBF);
        AssertGeneratedJavaLexer.verifyFail("mID_PART", '%');
        AssertGeneratedJavaLexer.verifyFail("mID_PART", (char) 0xBF);
        AssertGeneratedJavaLexer.verifyFailNoGuessing("mID_PART", (char) 0xBF);
        AssertGeneratedJavaLexer.verifyFail("mESC", '\\', 'a');
        AssertGeneratedJavaLexer.verifyFail("mLONG_LITERAL", '0', ';');
        AssertGeneratedJavaLexer.verifyFail("mLONG_LITERAL", '1', ';');
        AssertGeneratedJavaLexer.verifyFail("mLONG_LITERAL", ';');
        AssertGeneratedJavaLexer.verifyFail("mINT_LITERAL", ';');
        AssertGeneratedJavaLexer.verifyFail("mHEX_DOUBLE_LITERAL", '0', 'a');
        AssertGeneratedJavaLexer.verifyFail("mHEX_FLOAT_LITERAL", '0', 'a');
    }

    @Test
    public void testImpossibleValid() throws Exception {
        AssertGeneratedJavaLexer.verifyPass("mSTD_ESC", 'n');
        AssertGeneratedJavaLexer.verifyPass("mELLIPSIS", '.', '.', '.');
        AssertGeneratedJavaLexer.verifyPass("mDOT", '.');
        AssertGeneratedJavaLexer.verifyPass("mBINARY_EXPONENT", 'p', '0', ';');
        AssertGeneratedJavaLexer.verifyPass("mHEX_DIGIT", '0');
        AssertGeneratedJavaLexer.verifyPass("mEXPONENT", 'e', '0', ';');
        AssertGeneratedJavaLexer.verifyPass("mBINARY_DIGIT", '0');
        AssertGeneratedJavaLexer.verifyPass("mSIGNED_INTEGER", '0', ';');
        AssertGeneratedJavaLexer.verifyPass("mWS", ' ', ';');
        AssertGeneratedJavaLexer.verifyPass("mID_START", '$');
        AssertGeneratedJavaLexer.verifyPass("mID_PART", '$');
        AssertGeneratedJavaLexer.verifyPass("mESC", '\\', '\\');
        AssertGeneratedJavaLexer.verifyPass("mLONG_LITERAL", '1', 'L');
        AssertGeneratedJavaLexer.verifyPass("mINT_LITERAL", '0', ';');
        AssertGeneratedJavaLexer.verifyPass("mFLOAT_LITERAL", '0', 'f');
        AssertGeneratedJavaLexer.verifyPass("mDOUBLE_LITERAL", '0', 'd');
        AssertGeneratedJavaLexer.verifyPass("mHEX_FLOAT_LITERAL", '0', 'x', '2', '_', '4', '.',
                '4', '4', '.', '4', 'P', '4', ';');
        AssertGeneratedJavaLexer.verifyPass("mHEX_DOUBLE_LITERAL", '0', 'x', '2', '_', '4', '.',
                '4', '4', '.', '4', 'P', '4', 'D', ';');
    }

    @Test
    public void testImpossibleExceptionsTextBlockLexer() throws Exception {
        AssertGenTextBlockLexer.verifyFail("mSTD_ESC", '\\', '*', (char) 0xFFFF);
        AssertGenTextBlockLexer.verifyFail("mONE_DOUBLE_QUOTE", '"', '"');
        AssertGenTextBlockLexer.verifyFail("mNEWLINE", '*');
        AssertGenTextBlockLexer.verifyFail("mTEXT_BLOCK_CONTENT", (char) 0xFFFF);
    }

    @Test
    public void testImpossibleValidTextBlockLexer() throws Exception {
        AssertGenTextBlockLexer.verifyPass("mSTD_ESC", '\\', '\\', 'n');
        AssertGenTextBlockLexer.verifyPass("mNEWLINE", '\r', '\n');
        AssertGenTextBlockLexer.verifyPass("mNEWLINE", '\r', '1');
        AssertGenTextBlockLexer.verifyPass("mTEXT_BLOCK_CONTENT", '\\', (char) 0xFFFF);
        AssertGenTextBlockLexer.verifyPass("mTEXT_BLOCK_CONTENT", '\r', (char) 0xFFFF);
        AssertGenTextBlockLexer.verifyPass("mONE_DOUBLE_QUOTE", '"', 'a');
        AssertGenTextBlockLexer.verifyPass("mTWO_DOUBLE_QUOTES", '"', '"', 'a');
        AssertGenTextBlockLexer.verifyPass("mONE_DOUBLE_QUOTE", '"', '\r', '\r');
        AssertGenTextBlockLexer
                .verifyPass("mTWO_DOUBLE_QUOTES", '"', '"', '\r', '\r');
        AssertGenTextBlockLexer
                .verifyPass("mTEXT_BLOCK_LITERAL_END", '"', '"', '"', (char) 0xFFFF);
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

    private static final class AssertGenTextBlockLexer
            extends GeneratedTextBlockLexer {

        private int laPosition;
        private char[] laResults;

        private AssertGenTextBlockLexer() {
            super((InputStream) null);
        }

        public static void verifyPass(String methodName, char... laResults) throws Exception {
            verify(methodName, true, 1, laResults);
        }

        public static void verifyFail(String methodName, char... laResults) throws Exception {
            verify(methodName, false, 1, laResults);
        }

        private static void verify(String methodName, boolean expectPass, int guessing,
                                   char... laResults) throws Exception {
            final AssertGenTextBlockLexer instance =
                    new AssertGenTextBlockLexer();
            instance.laPosition = 0;
            instance.laResults = laResults.clone();
            instance.inputState.guessing = guessing;

            final Method method = GeneratedTextBlockLexer.class.getDeclaredMethod(methodName,
                    boolean.class);
            boolean exception;

            try {
                method.invoke(instance, true);
                exception = false;
            }
            catch (InvocationTargetException ex) {
                if (expectPass) {
                    throw ex;
                }

                final Class<?> clss = ex.getTargetException().getClass();
                if (clss != NoViableAltForCharException.class
                        && clss != SemanticException.class) {
                    throw ex;
                }
                exception = true;
            }

            if (expectPass) {
                assertFalse(exception, "Call to GeneratedTextBlockLexer." + methodName
                        + " resulted in an exception");
            }
            else {
                assertTrue(exception, "Call to GeneratedTextBlockLexer." + methodName
                        + " did not result in an exception");
            }
        }

        @Override
        public char LA(int i) {
            return laResults[laPosition + i - 1];
        }

        @Override
        public void consume() {
            laPosition++;
        }

        @Override
        public int mark() {
            return 1;
        }

    }

    private static final class AssertGeneratedJavaLexer extends GeneratedJavaLexer {

        private int laPosition;
        private char[] laResults;

        private AssertGeneratedJavaLexer() {
            super((InputStream) null);
        }

        public static void verifyFailNoGuessing(String methodName, char... laResults)
                throws Exception {
            verify(methodName, false, 0, laResults);
        }

        public static void verifyPass(String methodName, char... laResults) throws Exception {
            verify(methodName, true, 1, laResults);
        }

        public static void verifyFail(String methodName, char... laResults) throws Exception {
            verify(methodName, false, 1, laResults);
        }

        private static void verify(String methodName, boolean expectPass, int guessing,
                char... laResults) throws Exception {
            final AssertGeneratedJavaLexer instance = new AssertGeneratedJavaLexer();
            instance.laPosition = 0;
            instance.laResults = laResults.clone();
            instance.inputState.guessing = guessing;

            final Method method = GeneratedJavaLexer.class.getDeclaredMethod(methodName,
                    boolean.class);
            boolean exception;

            try {
                method.invoke(instance, true);
                exception = false;
            }
            catch (InvocationTargetException ex) {
                if (expectPass) {
                    throw ex;
                }

                final Class<?> clss = ex.getTargetException().getClass();
                if (clss != NoViableAltForCharException.class
                        && clss != SemanticException.class) {
                    throw ex;
                }
                exception = true;
            }

            if (expectPass) {
                assertFalse(exception, "Call to GeneratedJavaLexer." + methodName
                        + " resulted in an exception");
            }
            else {
                assertTrue(exception, "Call to GeneratedJavaLexer." + methodName
                        + " did not result in an exception");
            }
        }

        @Override
        public char LA(int i) {
            return laResults[laPosition + i - 1];
        }

        @Override
        public void consume() {
            laPosition++;
        }

        @Override
        public int mark() {
            return 1;
        }

    }

}
