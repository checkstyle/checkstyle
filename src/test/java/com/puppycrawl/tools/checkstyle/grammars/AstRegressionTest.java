////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2016 the original author or authors.
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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.BaseCheckTestSupport;

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
        verifyAst(getNonCompilablePath("InputRegressionJava8Class1Ast.txt"),
                getNonCompilablePath("InputRegressionJava8Class1.java"));
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
        verifyAst(getNonCompilablePath("InputRegressionJava8Interface1Ast.txt"),
                getNonCompilablePath("InputRegressionJava8Interface1.java"));
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
    public void testUnusedMethods() throws Exception {
        final Class<?> clss = GeneratedJavaLexer.class;
        final Constructor<?> constructor = clss.getDeclaredConstructor(InputStream.class);

        constructor.newInstance((InputStream) null);
    }
}
