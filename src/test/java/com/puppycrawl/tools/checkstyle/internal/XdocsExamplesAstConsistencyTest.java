///////////////////////////////////////////////////////////////////////////////////////////////
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
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;

/**
 * Ensures xdocs Java examples for the same check differ only by comments.
 *
 * <p>This test validates that all Example1.java, Example2.java, etc. files within
 * the same directory have identical AST structure .
 * When differences are found, it provides readable AST dumps to help
 * identify where the examples diverge.
 *
 */
public class XdocsExamplesAstConsistencyTest {

    private static final Path XDOCS_ROOT = Path.of(
            "src/xdocs-examples/resources/com/puppycrawl/tools/checkstyle"
    );

    private static final String COMMON_PATH =
            "src/xdocs-examples/resources/com/puppycrawl/tools/checkstyle/";

    // This list is temporarily suppressed.
    // Until: https://github.com/checkstyle/checkstyle/issues/XXXXX
    private static final Set<String> SUPPRESSED_DIRECTORIES = Set.of(
            "checks/annotation/annotationlocation",
            "checks/annotation/annotationonsameline",
            "checks/annotation/annotationusestyle",
            "checks/annotation/missingoverride",
            "checks/annotation/suppresswarningsholder",
            "checks/blocks/emptyblock",
            "checks/blocks/emptycatchblock",
            "checks/blocks/needbraces",
            "checks/blocks/rightcurly",
            "checks/coding/arraytrailingcomma",
            "checks/coding/constructorsdeclarationgrouping",
            "checks/coding/covariantequals",
            "checks/coding/hiddenfield",
            "checks/coding/illegaltoken",
            "checks/coding/illegaltokentext",
            "checks/coding/innerassignment",
            "checks/coding/matchxpath",
            "checks/coding/missingswitchdefault",
            "checks/coding/packagedeclaration",
            "checks/coding/requirethis",
            "checks/coding/textblockgooglestyleformatting",
            "checks/coding/unnecessaryparentheses",
            "checks/coding/unnecessarysemicoloninenumeration",
            "checks/coding/variabledeclarationusagedistance",
            "checks/descendanttoken",
            "checks/design/hideutilityclassconstructor",
            "checks/design/onetoplevelclass",
            "checks/design/visibilitymodifier",
            "checks/finalparameters",
            "checks/imports/avoidstaticimport",
            "checks/imports/customimportorder",
            "checks/imports/importcontrol",
            "checks/imports/importcontrol/filters",
            "checks/imports/importcontrol/someImports",
            "checks/imports/importorder",
            "checks/indentation/commentsindentation",
            "checks/javadoc/javadocblocktaglocation",
            "checks/javadoc/javadocleadingasteriskalign",
            "checks/javadoc/javadocmethod",
            "checks/javadoc/javadoctagcontinuationindentation",
            "checks/javadoc/javadocvariable",
            "checks/metrics/classdataabstractioncoupling",
            "checks/metrics/classdataabstractioncoupling/ignore",
            "checks/metrics/classdataabstractioncoupling/ignore/deeper",
            "checks/metrics/npathcomplexity",
            "checks/naming/abbreviationaswordinname",
            "checks/naming/interfacetypeparametername",
            "checks/naming/lambdaparametername",
            "checks/naming/localfinalvariablename",
            "checks/naming/localvariablename",
            "checks/naming/membername",
            "checks/naming/methodname",
            "checks/naming/parametername",
            "checks/naming/patternvariablename",
            "checks/naming/typename",
            "checks/nocodeinfile",
            "checks/outertypefilename",
            "checks/regexp/regexp",
            "checks/regexp/regexpsingleline",
            "checks/sizes/lambdabodylength",
            "checks/sizes/linelength",
            "checks/sizes/parameternumber",
            "checks/trailingcomment",
            "checks/whitespace/emptyforinitializerpad",
            "checks/whitespace/nolinewrap",
            "checks/whitespace/nowhitespacebefore",
            "checks/whitespace/operatorwrap",
            "checks/whitespace/parenpad",
            "checks/whitespace/separatorwrap",
            "checks/whitespace/singlespaceseparator",
            "checks/whitespace/typecastparenpad",
            "checks/whitespace/whitespaceafter",
            "checks/whitespace/whitespacearound",
            "filters/suppressionfilter",
            "filters/suppressionsinglefilter",
            "filters/suppressionxpathfilter",
            "filters/suppressionxpathsinglefilter",
            "filters/suppresswarningsfilter",
            "filters/suppresswithnearbycommentfilter",
            "filters/suppresswithnearbytextfilter",
            "filters/suppresswithplaintextcommentfilter"
    );

    /**
     * Tests that all examples in each check directory have the same AST structure,
     * differing only in comments. This ensures consistency across documentation examples.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testExamplesDifferOnlyByComments() throws IOException {
        final List<String> violations = new ArrayList<>();

        try (Stream<Path> pathStream = Files.walk(XDOCS_ROOT)) {
            final List<Path> exampleDirs = pathStream
                    .filter(Files::isDirectory)
                    .filter(XdocsExamplesAstConsistencyTest::containsMultipleExamples)
                    .toList();

            for (Path dir : exampleDirs) {
                final String relativePath = getRelativePath(dir);

                // Skip suppressed directories
                if (SUPPRESSED_DIRECTORIES.contains(relativePath)) {
                    continue;
                }

                final String violation = checkExamplesInDirectory(dir);
                if (violation != null) {
                    violations.add(violation);
                }
            }
        }

        final String message;

        if (violations.isEmpty()) {
            message = "";
        }
        else {
            final StringBuilder builder = new StringBuilder(1024);

            builder.append("Found ")
                    .append(violations.size())
                    .append(
                            """
                             director(ies) with AST mismatches.
                            Examples should have identical code structure, \
                            differing only in comments.
                            """
                );

            for (String violation : violations) {
                builder.append(violation)
                        .append("\n\n");
            }

            for (String violation : violations) {
                final String dirPath = extractDirectoryPath(violation);
                if (dirPath != null) {
                    builder.append('"').append(dirPath).append("\",\n");
                }
            }

            message = builder.toString();
        }

        assertWithMessage(message)
                .that(violations)
                .isEmpty();
    }

    /**
     * Gets the relative path from the common base path.
     *
     * @param dir the directory path
     * @return the relative path string
     */
    private static String getRelativePath(Path dir) {
        final String fullPath = dir.toString().replace('\\', '/');
        final String result;
        if (fullPath.startsWith(COMMON_PATH)) {
            result = fullPath.substring(COMMON_PATH.length());
        }
        else {
            result = fullPath;
        }
        return result;
    }

    /**
     * Extracts the directory path from a violation message.
     *
     * @param violation the violation message
     * @return the directory path, or null if not found
     */
    private static String extractDirectoryPath(String violation) {
        final String prefix = "Directory: ";
        final int startIndex = violation.indexOf(prefix);
        final String result;

        if (startIndex == -1) {
            result = null;
        }
        else {
            final int endIndex = violation.indexOf('\n', startIndex);
            if (endIndex == -1) {
                result = null;
            }
            else {
                result = violation.substring(startIndex + prefix.length(), endIndex).trim();
            }
        }

        return result;
    }

    /**
     * Checks if a directory contains multiple example files (Example1.java, Example2.java, etc.).
     *
     * @param dir the directory to check
     * @return true if the directory contains 2 or more Example*.java files
     */
    private static boolean containsMultipleExamples(Path dir) {
        boolean result = false;

        try (Stream<Path> pathStream = Files.list(dir)) {
            result = pathStream
                    .filter(path -> path.getFileName().toString().matches("Example\\d+\\.java"))
                    .count() > 1;
        }
        catch (IOException ignored) {
            // result already set to false
        }

        return result;
    }

    /**
     * Checks if all Example*.java files in the given directory have identical AST structure.
     * Differences in comments are ignored.
     *
     * @param dir the directory containing example files
     * @return violation message if AST mismatch is detected, null otherwise
     */
    private static String checkExamplesInDirectory(Path dir) {
        String result = null;

        try {
            final List<Path> examples = getExampleFiles(dir);

            if (!examples.isEmpty()) {
                result = compareExamples(dir, examples);
            }
        }
        catch (IOException | CheckstyleException exception) {
            final String relativePath = getRelativePath(dir);
            result = "Directory: " + relativePath + "\n"
                    + "Error: " + exception.getMessage();
        }

        return result;
    }

    /**
     * Gets all Example*.java files from a directory.
     *
     * @param dir the directory to search
     * @return list of example file paths
     * @throws IOException if an I/O error occurs
     */
    private static List<Path> getExampleFiles(Path dir) throws IOException {
        final List<Path> examples;
        try (Stream<Path> pathStream = Files.list(dir)) {
            examples = pathStream
                    .filter(path -> path.getFileName().toString().matches("Example\\d+\\.java"))
                    .sorted(Comparator.comparing(Path::toString))
                    .toList();
        }
        return examples;
    }

    /**
     * Compares all example files in a list against the first one.
     *
     * @param dir the directory containing the examples
     * @param examples the list of example files
     * @return violation message if mismatch found, null otherwise
     * @throws IOException if an I/O error occurs
     * @throws CheckstyleException if parsing fails
     */
    private static String compareExamples(Path dir, List<Path> examples)
            throws IOException, CheckstyleException {
        final Path reference = examples.get(0);
        final DetailAST referenceDetailAst = parse(reference);
        final String result;

        if (referenceDetailAst == null) {
            final String relativePath = getRelativePath(dir);
            result = "Directory: " + relativePath + "\n"
                    + "Error: Failed to parse " + reference.getFileName();
        }
        else {
            final StructuralAstNode referenceAst = toStructuralAst(referenceDetailAst);
            result = compareAgainstReference(dir, examples, reference, referenceAst);
        }

        return result;
    }

    /**
     * Compares remaining examples against the reference.
     *
     * @param dir the directory containing the examples
     * @param examples all example files
     * @param reference the reference file
     * @param referenceAst the reference AST
     * @return violation message if mismatch found, null otherwise
     * @throws IOException if an I/O error occurs
     * @throws CheckstyleException if parsing fails
     */
    private static String compareAgainstReference(Path dir, List<Path> examples,
                                                  Path reference,
                                                  StructuralAstNode referenceAst)
            throws IOException, CheckstyleException {
        String result = null;

        for (Path example : examples.subList(1, examples.size())) {
            result = compareSingleExample(dir, example, reference, referenceAst);
            if (result != null) {
                break;
            }
        }

        return result;
    }

    /**
     * Compares a single example file against the reference.
     *
     * @param dir the directory containing the examples
     * @param example the example file to compare
     * @param reference the reference file
     * @param referenceAst the reference AST
     * @return violation message if mismatch found, null otherwise
     * @throws IOException if an I/O error occurs
     * @throws CheckstyleException if parsing fails
     */
    private static String compareSingleExample(Path dir, Path example,
                                               Path reference,
                                               StructuralAstNode referenceAst)
            throws IOException, CheckstyleException {
        final String result;
        final DetailAST exampleDetailAst = parse(example);

        if (exampleDetailAst == null) {
            final String relativePath = getRelativePath(dir);
            result = "Directory: " + relativePath + "\n"
                    + "Error: Failed to parse " + example.getFileName();
        }
        else {
            final StructuralAstNode ast = toStructuralAst(exampleDetailAst);

            if (referenceAst.equals(ast)) {
                result = null;
            }
            else {
                final String relativePath = getRelativePath(dir);
                result = "Directory: " + relativePath + "\n"
                        + "Reference: " + reference.getFileName() + "\n"
                        + "Mismatch:  " + example.getFileName();
            }
        }

        return result;
    }

    /**
     * Parses a Java file into a DetailAST.
     *
     * @param file the file to parse
     * @return the parsed AST, or null if parsing fails
     * @throws IOException if an I/O error occurs
     * @throws CheckstyleException if parsing fails
     */
    private static DetailAST parse(Path file) throws IOException, CheckstyleException {
        final FileText text =
                new FileText(new File(file.toString()), StandardCharsets.UTF_8.name());
        final FileContents contents = new FileContents(text);
        return JavaParser.parse(contents);
    }

    /**
     * Converts a DetailAST into a structural representation that excludes comments
     * and focuses only on the code structure.
     *
     * @param ast the AST to convert
     * @return structural representation of the AST, or null if the node is a comment
     */
    private static StructuralAstNode toStructuralAst(DetailAST ast) {
        final StructuralAstNode result;

        if (isCommentNode(ast)) {
            result = null;
        }
        else {
            final StructuralAstNode node = new StructuralAstNode(ast.getType());

            for (DetailAST child = ast.getFirstChild();
                 child != null;
                 child = child.getNextSibling()) {
                final StructuralAstNode structuralChild = toStructuralAst(child);
                if (structuralChild != null) {
                    node.addChild(structuralChild);
                }
            }
            result = node;
        }

        return result;
    }

    /**
     * Checks if an AST node represents a comment.
     *
     * @param ast the AST node to check
     * @return true if the node is a comment
     */
    private static boolean isCommentNode(DetailAST ast) {
        final int type = ast.getType();
        return type == TokenTypes.SINGLE_LINE_COMMENT
                || type == TokenTypes.BLOCK_COMMENT_BEGIN
                || type == TokenTypes.COMMENT_CONTENT;
    }

    /**
     * Represents a structural AST node without comments or source positions.
     * This allows for pure structural comparison between example files.
     */
    private static final class StructuralAstNode {
        private final int type;
        private final List<StructuralAstNode> children = new ArrayList<>();

        private StructuralAstNode(int type) {
            this.type = type;
        }

        private void addChild(StructuralAstNode child) {
            children.add(child);
        }

        @Override
        public boolean equals(Object obj) {
            return obj instanceof StructuralAstNode other
                    && type == other.type
                    && children.equals(other.children);
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, children);
        }
    }
}
