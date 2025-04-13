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
import java.io.IOException;

public abstract class AbstractTreeTestSupport extends AbstractPathTestSupport {

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the non-compilable resources location.
     * This implementation uses 'src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/'
     * as a non-compilable resource location.
     *
     * @param filename file name.
     * @return canonical path for the file with the given file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected final String getNonCompilablePath(String filename) throws IOException {
        return new File("src/test/resources-noncompilable/" + getPackageLocation() + "/"
                + filename).getCanonicalPath();
    }

    /**
     * Performs verification of the given text ast tree representation.
     *
     * @param expectedTextPrintFileName expected text ast tree representation.
     * @param actualJavaFileName actual text ast tree representation.
     * @param withComments whether to perform verification of comment nodes in tree.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyAst(String expectedTextPrintFileName, String actualJavaFileName,
                                    JavaParser.Options withComments)
            throws Exception {
        final String expectedContents = readFile(expectedTextPrintFileName);

        final String actualContents = toLfLineEnding(AstTreeStringPrinter.printFileAst(
                new File(actualJavaFileName), withComments));

        assertWithMessage("Generated AST from Java file should match pre-defined AST")
                .that(actualContents)
                .isEqualTo(expectedContents);
    }

    /**
     * Performs verification of the given text ast tree representation.
     * This implementation uses
     * {@link AbstractTreeTestSupport#verifyAst(String, String, JavaParser.Options)}
     * method inside.
     *
     * @param expectedTextPrintFileName expected text ast tree representation.
     * @param actualJavaFileName actual text ast tree representation.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyAst(String expectedTextPrintFileName, String actualJavaFileName)
            throws Exception {
        verifyAst(expectedTextPrintFileName, actualJavaFileName,
                JavaParser.Options.WITHOUT_COMMENTS);
    }

    /**
     * Verifies the java and javadoc AST generated for the supplied java file against
     * the expected AST in supplied text file.
     *
     * @param expectedTextPrintFilename name of the file having the expected ast.
     * @param actualJavaFilename name of the java file.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyJavaAndJavadocAst(String expectedTextPrintFilename,
                                                  String actualJavaFilename) throws Exception {
        final String expectedContents = readFile(expectedTextPrintFilename);

        final String actualContents = toLfLineEnding(AstTreeStringPrinter.printJavaAndJavadocTree(
                new File(actualJavaFilename)));

        assertWithMessage("Generated AST from the java file should match the pre-defined AST")
                .that(actualContents)
                .isEqualTo(expectedContents);
    }

    /**
     * Verifies the javadoc tree generated for the supplied javadoc file against the expected tree
     * in the supplied text file.
     *
     * @param expectedTextPrintFilename name of the text file having the expected tree.
     * @param actualJavadocFilename name of the file containing the javadoc.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyJavadocTree(String expectedTextPrintFilename,
                                            String actualJavadocFilename) throws Exception {
        final String expectedContents = readFile(expectedTextPrintFilename);

        final String actualContents = toLfLineEnding(DetailNodeTreeStringPrinter.printFileAst(
                new File(actualJavadocFilename)));

        assertWithMessage("Generated tree from the javadoc file should match the pre-defined tree")
                .that(actualContents)
                .isEqualTo(expectedContents);
    }

}
