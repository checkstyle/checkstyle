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

public class DetailNodeTreeStringPrinterTest {

    private static String getPath(String filename) {
        return "src/test/resources/com/puppycrawl/tools/checkstyle/astprinter/" + filename;
    }

    @Test
    public void testIsProperUtilsClass() throws ReflectiveOperationException {
        assertUtilsClassHasPrivateConstructor(DetailNodeTreeStringPrinter.class);
    }

    @Test
    public void testParseFile() throws Exception {
        final String actual = DetailNodeTreeStringPrinter.printFileAst(
            new File(getPath("InputJavadocComment.javadoc")))
                .replaceAll("\\\\r\\\\n", "\\\\n");
        final String expected = new String(Files.readAllBytes(Paths.get(
            getPath("expectedInputJavadocComment.txt"))), StandardCharsets.UTF_8)
            .replaceAll("\\\\r\\\\n", "\\\\n");
        Assert.assertEquals(expected, actual);
    }

    @Test
    public void testParseFileWithError() throws Exception {
        try {
            DetailNodeTreeStringPrinter.printFileAst(
                    new File(getPath("InputJavadocWithError.javadoc")));
            Assert.fail("Javadoc parser didn't failed on missing end tag");
        }
        catch (IllegalArgumentException ex) {
            final String expected = "[ERROR:0] Javadoc comment at column 1 has parse error. "
                    + "Missed HTML close tag 'qwe'. Sometimes it means that close tag missed "
                    + "for one of previous tags.";
            Assert.assertEquals(expected, ex.getMessage());
        }
    }

}
