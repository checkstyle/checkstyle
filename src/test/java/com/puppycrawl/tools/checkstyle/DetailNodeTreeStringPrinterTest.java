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

import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Charsets;
import com.google.common.io.Files;

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
        final String expected = Files.toString(new File(
                getPath("expectedInputJavadocComment.txt")), Charsets.UTF_8)
                .replaceAll("\\\\r\\\\n", "\\\\n");
        Assert.assertEquals(expected, actual);
    }

}
