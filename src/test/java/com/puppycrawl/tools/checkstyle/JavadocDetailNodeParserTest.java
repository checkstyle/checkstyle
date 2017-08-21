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

package com.puppycrawl.tools.checkstyle;

import static java.util.Locale.ENGLISH;
import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.internal.TestUtils;

public class JavadocDetailNodeParserTest extends AbstractModuleTestSupport {

    private static final String OS_NAME = System.getProperty("os.name").toLowerCase(ENGLISH);

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle";
    }

    @Test
    public void testParseJavadocAsDetailNode() throws Exception {
        final DetailAST ast = TestUtils.parseFile(new File("src/test/resources/"
                + getPackageLocation() + "/InputJavadocDetailNodeParser.java"))
                .getNextSibling().getFirstChild().getFirstChild();
        final JavadocDetailNodeParser parser = new JavadocDetailNodeParser();
        final JavadocDetailNodeParser.ParseStatus status = parser.parseJavadocAsDetailNode(ast);
        final String actual = DetailNodeTreeStringPrinter.printTree(status.getTree(), "", "");
        final String expected;

        // line separators in the input file while running this test on Windows are different,
        // so when we try to print tree, output also will have different line separators on windows
        // and linux.
        if (OS_NAME.startsWith("windows")) {
            expected = new String(Files.readAllBytes(Paths.get(
                    getPath("OutputWindowsJavadocDetailedNodeParser.txt"))),
                    StandardCharsets.UTF_8);
        }
        else {
            expected = new String(Files.readAllBytes(Paths.get(
                    getPath("OutputJavadocDetailedNodeParser.txt"))),
                    StandardCharsets.UTF_8);
        }

        assertEquals("Invalid parse result", expected, actual);
    }

}
