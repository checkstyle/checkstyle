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

package com.puppycrawl.tools.checkstyle;

import static com.puppycrawl.tools.checkstyle.internal.TestUtils.assertUtilsClassHasPrivateConstructor;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Assert;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;

public class AstTreeStringPrinterTest {

    private static String getNonCompilablePath(String filename) {
        return "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/" + filename;
    }

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/astprinter/" + filename;
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(AstTreeStringPrinter.class);
    }

    @Test(expected = CheckstyleException.class)
    public void testParseFileThrowable() throws Exception {
        AstTreeStringPrinter.printFileAst(
            new File(getNonCompilablePath("InputAstTreeStringPrinter.java")), false);
    }

    @Test
    public void testParseFile() throws Exception {
        final String actual = AstTreeStringPrinter.printFileAst(
            new File(getPath("InputAstTreeStringPrinterComments.java")), false);
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("expectedInputAstTreeStringPrinter.txt"))), StandardCharsets.UTF_8);
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseFileWithComments() throws Exception {
        final String actual = AstTreeStringPrinter.printFileAst(
            new File(getPath("InputAstTreeStringPrinterComments.java")), true)
                .replaceAll("\\\\r\\\\n", "\\\\n");
        final String expected = new String(Files.readAllBytes(Paths.get(
                getPath("expectedInputAstTreeStringPrinterComments.txt"))), StandardCharsets.UTF_8)
                .replaceAll("\\\\r\\\\n", "\\\\n");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseFileWithJavadoc() throws Exception {
        final String actual = AstTreeStringPrinter.printJavaAndJavadocTree(
                new File(getPath("InputAstTreeStringPrinterJavadoc.java")))
                .replaceAll("\\\\r\\\\n", "\\\\n");
        final String expected = new String(Files.readAllBytes(Paths.get(
                getPath("expectedInputAstTreeStringPrinterJavadoc.txt"))), StandardCharsets.UTF_8)
                .replaceAll("\\\\r\\\\n", "\\\\n");
        Assert.assertEquals(expected, actual);
    }
}
