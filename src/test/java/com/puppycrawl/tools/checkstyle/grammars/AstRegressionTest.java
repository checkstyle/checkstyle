////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2017 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.grammars;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

import org.junit.Test;

import antlr.NoViableAltForCharException;
import antlr.ParserSharedInputState;
import antlr.SemanticException;
import antlr.TokenBuffer;
import com.puppycrawl.tools.checkstyle.AstTreeStringPrinter;
import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;
import com.puppycrawl.tools.checkstyle.api.FileText;

public class AstRegressionTest extends BaseCheckTestSupport {
    @Override
    protected String getPath(String filename) throws IOException {
        return super.getPath("grammars" + File.separator + filename);
    }

    @Override
    protected String getNonCompilablePath(String filename) throws IOException {
        return super.getNonCompilablePath("grammars" + File.separator + filename);
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
    public void testUnusedConstructors1() throws Exception {
        final Class<?> clss = GeneratedJavaLexer.class;
        final Constructor<?> constructor = clss.getDeclaredConstructor(InputStream.class);

        assertNotNull(constructor.newInstance((InputStream) null));
    }

    @Test
    public void testUnusedConstructors2() throws Exception {
        final Class<?> clss = GeneratedJavaRecognizer.class;
        final Constructor<?> constructor = clss
                .getDeclaredConstructor(ParserSharedInputState.class);

        assertNotNull(constructor.newInstance((ParserSharedInputState) null));
    }

    @Test
    public void testUnusedConstructors3() throws Exception {
        final Class<?> clss = GeneratedJavaRecognizer.class;
        final Constructor<?> constructor = clss.getDeclaredConstructor(TokenBuffer.class);

        assertNotNull(constructor.newInstance((TokenBuffer) null));
    }

    @Test
    public void testCustomAstTree() throws Exception {
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\t");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\r\n");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\n");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\r\r");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\r");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "\u000c\f");
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "// \n", true);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "// \r", true);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "// \r\n", true);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "/* \n */", true);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "/* \r\n */", true);
        verifyAstRaw(getPath("InputRegressionEmptyAst.txt"), "/* \r" + "\u0000\u0000" + " */",
            true);
    }

    @Test
    public void testNewlineCr() throws Exception {
        verifyAst(super.getPath("/checks/InputNewlineCrAtEndOfFileAst.txt"),
                super.getPath("/checks/InputNewlineCrAtEndOfFile.java"), true);
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

    private static void verifyAstRaw(String expectedTextPrintFileName, String actualJava)
            throws Exception {
        verifyAstRaw(expectedTextPrintFileName, actualJava, false);
    }

    private static void verifyAstRaw(String expectedTextPrintFileName, String actualJava,
            boolean withComments) throws Exception {
        final File expectedFile = new File(expectedTextPrintFileName);
        final String expectedContents = new FileText(expectedFile, System.getProperty(
                "file.encoding", "UTF-8")).getFullText().toString().replace("\r", "");

        final FileText actualFileContents = new FileText(new File(""),
                Arrays.asList(actualJava.split("\\n|\\r\\n?")));
        final String actualContents = AstTreeStringPrinter.printAst(actualFileContents,
                withComments);

        assertEquals("Generated AST from Java code should match pre-defined AST", expectedContents,
                actualContents);
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

        public static void verify(String methodName, boolean expectPass, int guessing,
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
                assertFalse("Call to GeneratedJavaLexer." + methodName
                        + " resulted in an exception", exception);
            }
            else {
                assertTrue("Call to GeneratedJavaLexer." + methodName
                        + " did not result in an exception", exception);
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
