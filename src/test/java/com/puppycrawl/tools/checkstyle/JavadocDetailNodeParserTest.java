///
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
///

package com.puppycrawl.tools.checkstyle;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.api.DetailAST;

public class JavadocDetailNodeParserTest extends AbstractModuleTestSupport {

    @Override
    protected String getPackageLocation() {
        return "com/puppycrawl/tools/checkstyle/javadocdetailnodeparser";
    }

    @Test
    public void testParseJavadocAsDetailNode() throws Exception {
        final DetailAST ast = JavaParser.parseFile(
            new File(getPath("InputJavadocDetailNodeParser.java")),
            JavaParser.Options.WITH_COMMENTS)
                .getFirstChild().getNextSibling().getFirstChild().getFirstChild();
        final JavadocDetailNodeParser parser = new JavadocDetailNodeParser();
        final JavadocDetailNodeParser.ParseStatus status = parser.parseJavadocAsDetailNode(ast);
        final String actual = toLfLineEnding(DetailNodeTreeStringPrinter.printTree(status.getTree(),
                "", ""));
        final String expected = toLfLineEnding(Files.readString(Path.of(
                getPath("ExpectedJavadocDetailNodeParser.txt"))));
        assertWithMessage("Invalid parse result")
                .that(actual)
                .isEqualTo(expected);
    }

}
