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

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

public abstract class AbstractTreeTestSupport extends AbstractPathTestSupport {

    protected static final String LF_REGEX = "\\\\n";

    protected static final String CLRF_REGEX = "\\\\r\\\\n";

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the non-compilable resources location.
     * This implementation uses 'src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/'
     * as a non-compilable resource location.
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
     * @param expectedTextPrintFileName expected text ast tree representation.
     * @param actualJavaFileName actual text ast tree representation.
     * @param withComments whether to perform verification of comment nodes in tree.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyAst(String expectedTextPrintFileName, String actualJavaFileName,
                                    AstTreeStringPrinter.PrintOptions withComments)
            throws Exception {
        final String expectedContents = readFile(expectedTextPrintFileName);

        final String actualContents = AstTreeStringPrinter.printFileAst(
                new File(actualJavaFileName), withComments).replaceAll(CLRF_REGEX, LF_REGEX);

        assertEquals("Generated AST from Java file should match pre-defined AST", expectedContents,
                actualContents);
    }

    /**
     * Performs verification of the given text ast tree representation.
     * This implementation uses
     * {@link BaseCheckTestSupport#verifyAst(String, String, AstTreeStringPrinter.PrintOptions)}
     * method inside.
     * @param expectedTextPrintFileName expected text ast tree representation.
     * @param actualJavaFileName actual text ast tree representation.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyAst(String expectedTextPrintFileName, String actualJavaFileName)
            throws Exception {
        verifyAst(expectedTextPrintFileName, actualJavaFileName,
                AstTreeStringPrinter.PrintOptions.WITHOUT_COMMENTS);
    }

    /**
     * Verifies the java and javadoc AST generated for the supplied java file against
     * the expected AST in supplied text file.
     * @param expectedTextPrintFilename name of the file having the expected ast.
     * @param actualJavaFilename name of the java file.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyJavaAndJavadocAst(String expectedTextPrintFilename,
                                                  String actualJavaFilename) throws Exception {

        final String expectedContents = readFile(expectedTextPrintFilename);

        final String actualContents = AstTreeStringPrinter.printJavaAndJavadocTree(
                new File(actualJavaFilename)).replaceAll(CLRF_REGEX, LF_REGEX);

        assertEquals("Generated AST from the java file should match the pre-defined AST",
                expectedContents, actualContents);
    }

    /**
     * Verifies the javadoc tree generated for the supplied javadoc file against the expected tree
     * in the supplied text file.
     * @param expectedTextPrintFilename name of the text file having the expected tree.
     * @param actualJavadocFilename name of the file containing the javadoc.
     * @throws Exception if exception occurs during verification.
     */
    protected static void verifyJavadocTree(String expectedTextPrintFilename,
                                            String actualJavadocFilename) throws Exception {

        final String expectedContents = readFile(expectedTextPrintFilename);

        final String actualContents = DetailNodeTreeStringPrinter.printFileAst(
                new File(actualJavadocFilename)).replaceAll(CLRF_REGEX, LF_REGEX);

        assertEquals("Generated tree from the javadoc file should match the pre-defined tree",
                expectedContents, actualContents);
    }

    /** Reads the contents of a file.
     * @param filename the name of the file whose contents are to be read
     * @return contents of the file with all {@code \r\n} replaced by {@code \n}
     * @throws IOException if I/O exception occurs while reading
     */
    protected static String readFile(String filename) throws IOException {
        return new String(Files.readAllBytes(
                Paths.get(filename)), StandardCharsets.UTF_8)
                .replaceAll(CLRF_REGEX, LF_REGEX);
    }
}
