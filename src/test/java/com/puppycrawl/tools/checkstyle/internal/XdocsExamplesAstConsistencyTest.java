///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
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
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Ensures xdocs Java examples for the same check differ only by comments.
 *
 * <p>This test validates that examples with the same code structure maintain
 * consistency. Examples are grouped explicitly - either all examples must match,
 * or specific examples can be marked as independent.
 *
 * <p>Only code between {@code // xdoc section -- start} and
 * {@code // xdoc section -- end} markers is compared. Helper code outside
 * these markers (like interface definitions) can differ between examples.
 *
 */
public class XdocsExamplesAstConsistencyTest {

    private static final Path XDOCS_ROOT = Path.of(
            "src/xdocs-examples/resources/com/puppycrawl/tools/checkstyle"
    );

    private static final String COMMON_PATH =
            "src/xdocs-examples/resources/com/puppycrawl/tools/checkstyle/";

    private static final String XDOC_START_MARKER = "// xdoc section -- start";
    private static final String XDOC_END_MARKER = "// xdoc section -- end";

    /**
     * Examples that cannot be parsed as valid Java.
     * These files are intentionally non-compilable for documentation purposes.
     *
     */
    private static final Set<String> UNPARSEABLE_EXAMPLES = Set.of(
            "checks/regexp/regexponfilename/Example1",
            "checks/translation/Example1",
            "filters/suppressionxpathsinglefilter/Example14"
    );

    /**
     * Examples that have independent code structure and should not be compared.
     * These represent different use cases or configurations with different code.
     *
     * <p>Format: "directory/ExampleN" where the example has unique code.
     *
     * <p>Until: <a href="https://github.com/checkstyle/checkstyle/issues/18435">...</a>
     */
    private static final Set<String> SUPPRESSED_EXAMPLES = Set.of(
            "checks/annotation/annotationonsameline/Example2",
            "checks/annotation/missingoverride/Example2",
            "checks/annotation/suppresswarningsholder/Example1",
            "checks/blocks/emptyblock/Example3",
            "checks/blocks/emptycatchblock/Example4",
            "checks/blocks/emptycatchblock/Example5",
            "checks/blocks/needbraces/Example6",
            "checks/blocks/rightcurly/Example2",
            "checks/blocks/rightcurly/Example3",
            "checks/blocks/rightcurly/Example4",
            "checks/blocks/rightcurly/Example5",
            "checks/coding/constructorsdeclarationgrouping/Example2",
            "checks/coding/covariantequals/Example2",
            "checks/coding/hiddenfield/Example7",
            "checks/coding/illegaltoken/Example2",
            "checks/coding/illegaltokentext/Example3",
            "checks/coding/illegaltokentext/Example4",
            "checks/coding/innerassignment/Example2",
            "checks/coding/matchxpath/Example2",
            "checks/coding/matchxpath/Example3",
            "checks/coding/matchxpath/Example4",
            "checks/coding/matchxpath/Example5",
            "checks/coding/missingswitchdefault/Example2",
            "checks/coding/missingswitchdefault/Example3",
            "checks/coding/packagedeclaration/Example2",
            "checks/coding/requirethis/Example5",
            "checks/coding/requirethis/Example6",
            "checks/coding/textblockgooglestyleformatting/Example2",
            "checks/coding/textblockgooglestyleformatting/Example3",
            "checks/coding/textblockgooglestyleformatting/Example4",
            "checks/coding/unnecessaryparentheses/Example2",
            "checks/coding/unnecessaryparentheses/Example3",
            "checks/coding/unnecessarysemicoloninenumeration/Example2",
            "checks/coding/variabledeclarationusagedistance/Example2",
            "checks/descendanttoken/Example10",
            "checks/descendanttoken/Example11",
            "checks/descendanttoken/Example12",
            "checks/descendanttoken/Example13",
            "checks/descendanttoken/Example14",
            "checks/descendanttoken/Example15",
            "checks/descendanttoken/Example16",
            "checks/descendanttoken/Example4",
            "checks/descendanttoken/Example5",
            "checks/descendanttoken/Example6",
            "checks/descendanttoken/Example7",
            "checks/descendanttoken/Example8",
            "checks/descendanttoken/Example9",
            "checks/design/onetoplevelclass/Example2",
            "checks/design/onetoplevelclass/Example3",
            "checks/design/visibilitymodifier/Example11",
            "checks/design/visibilitymodifier/Example12",
            "checks/finalparameters/Example4",
            "checks/imports/avoidstaticimport/Example2",
            "checks/imports/customimportorder/Example10",
            "checks/imports/customimportorder/Example11",
            "checks/imports/customimportorder/Example12",
            "checks/imports/customimportorder/Example13",
            "checks/imports/customimportorder/Example14",
            "checks/imports/customimportorder/Example15",
            "checks/imports/customimportorder/Example2",
            "checks/imports/customimportorder/Example3",
            "checks/imports/customimportorder/Example4",
            "checks/imports/customimportorder/Example5",
            "checks/imports/customimportorder/Example6",
            "checks/imports/customimportorder/Example7",
            "checks/imports/customimportorder/Example8",
            "checks/imports/customimportorder/Example9",
            "checks/imports/importcontrol/Example12",
            "checks/imports/importcontrol/Example5",
            "checks/imports/importcontrol/filters/Example9",
            "checks/imports/importcontrol/someImports/Example11",
            "checks/imports/importcontrol/someImports/Example6",
            "checks/imports/importcontrol/someImports/Example7",
            "checks/imports/importorder/Example10",
            "checks/imports/importorder/Example11",
            "checks/imports/importorder/Example12",
            "checks/imports/importorder/Example2",
            "checks/imports/importorder/Example3",
            "checks/imports/importorder/Example4",
            "checks/imports/importorder/Example5",
            "checks/imports/importorder/Example6",
            "checks/imports/importorder/Example7",
            "checks/imports/importorder/Example8",
            "checks/imports/importorder/Example9",
            "checks/indentation/commentsindentation/Example3",
            "checks/indentation/commentsindentation/Example4",
            "checks/indentation/commentsindentation/Example5",
            "checks/indentation/commentsindentation/Example6",
            "checks/indentation/commentsindentation/Example7",
            "checks/indentation/commentsindentation/Example8",
            "checks/javadoc/javadocleadingasteriskalign/Example2",
            "checks/javadoc/javadocleadingasteriskalign/Example3",
            "checks/javadoc/javadocmethod/Example7",
            "checks/javadoc/javadocmethod/Example8",
            "checks/javadoc/javadoctagcontinuationindentation/Example4",
            "checks/javadoc/javadocvariable/Example5",
            "checks/metrics/classdataabstractioncoupling/Example11",
            "checks/metrics/classdataabstractioncoupling/Example2",
            "checks/metrics/classdataabstractioncoupling/Example3",
            "checks/metrics/classdataabstractioncoupling/ignore/Example7",
            "checks/metrics/classdataabstractioncoupling/ignore/Example8",
            "checks/metrics/classdataabstractioncoupling/ignore/Example9",
            "checks/metrics/classdataabstractioncoupling/ignore/deeper/Example5",
            "checks/metrics/classdataabstractioncoupling/ignore/deeper/Example6",
            "checks/metrics/javancss/Example5",
            "checks/metrics/npathcomplexity/Example2",
            "checks/naming/abbreviationaswordinname/Example3",
            "checks/naming/abbreviationaswordinname/Example4",
            "checks/naming/abbreviationaswordinname/Example5",
            "checks/naming/abbreviationaswordinname/Example6",
            "checks/naming/abbreviationaswordinname/Example7",
            "checks/naming/interfacetypeparametername/Example2",
            "checks/naming/lambdaparametername/Example2",
            "checks/naming/localfinalvariablename/Example3",
            "checks/naming/localvariablename/Example3",
            "checks/naming/localvariablename/Example4",
            "checks/naming/localvariablename/Example5",
            "checks/naming/membername/Example2",
            "checks/naming/membername/Example3",
            "checks/naming/methodname/Example2",
            "checks/naming/methodname/Example3",
            "checks/naming/methodname/Example4",
            "checks/naming/methodname/Example5",
            "checks/naming/methodname/Example6",
            "checks/naming/methodname/Example7",
            "checks/naming/parametername/Example2",
            "checks/naming/parametername/Example3",
            "checks/naming/parametername/Example4",
            "checks/naming/parametername/Example5",
            "checks/naming/patternvariablename/Example4",
            "checks/naming/typename/Example2",
            "checks/naming/typename/Example3",
            "checks/naming/typename/Example4",
            "checks/outertypefilename/Example2",
            "checks/outertypefilename/Example3",
            "checks/outertypefilename/Example4",
            "checks/outertypefilename/Example5",
            "checks/regexp/regexp/Example1",
            "checks/regexp/regexp/Example10",
            "checks/regexp/regexp/Example11",
            "checks/regexp/regexp/Example2",
            "checks/regexp/regexp/Example3",
            "checks/regexp/regexp/Example4",
            "checks/regexp/regexp/Example5",
            "checks/regexp/regexp/Example6",
            "checks/regexp/regexp/Example7",
            "checks/regexp/regexp/Example8",
            "checks/regexp/regexp/Example9",
            "checks/regexp/regexpsingleline/Example2",
            "checks/regexp/regexpsingleline/Example3",
            "checks/regexp/regexpsingleline/Example4",
            "checks/regexp/regexpmultiline/Example5",
            "checks/sizes/lambdabodylength/Example2",
            "checks/sizes/linelength/Example6",
            "checks/trailingcomment/Example2",
            "checks/trailingcomment/Example3",
            "checks/trailingcomment/Example4",
            "checks/trailingcomment/Example5",
            "checks/trailingcomment/Example6",
            "checks/whitespace/arraybracketnowhitespace/Example2",
            "checks/whitespace/nolinewrap/Example2",
            "checks/whitespace/nolinewrap/Example3",
            "checks/whitespace/nolinewrap/Example4",
            "checks/whitespace/nolinewrap/Example5",
            "checks/whitespace/nowhitespacebefore/Example2",
            "checks/whitespace/nowhitespacebefore/Example3",
            "checks/whitespace/nowhitespacebefore/Example4",
            "checks/whitespace/operatorwrap/Example2",
            "checks/whitespace/parenpad/Example2",
            "checks/whitespace/separatorwrap/Example2",
            "checks/whitespace/separatorwrap/Example3",
            "checks/whitespace/singlespaceseparator/Example2",
            "checks/whitespace/typecastparenpad/Example2",
            "checks/whitespace/whitespaceafter/Example2",
            "checks/whitespace/whitespacearound/Example10",
            "checks/whitespace/whitespacearound/Example2",
            "checks/whitespace/whitespacearound/Example3",
            "checks/whitespace/whitespacearound/Example4",
            "checks/whitespace/whitespacearound/Example5",
            "checks/whitespace/whitespacearound/Example6",
            "checks/whitespace/whitespacearound/Example7",
            "checks/whitespace/whitespacearound/Example8",
            "checks/whitespace/whitespacearound/Example9",
            "filters/suppressionfilter/Example2",
            "filters/suppressionfilter/Example3",
            "filters/suppressionfilter/Example4",
            "filters/suppressionsinglefilter/Example2",
            "filters/suppressionsinglefilter/Example3",
            "filters/suppressionsinglefilter/Example4",
            "filters/suppressionxpathfilter/Example10",
            "filters/suppressionxpathfilter/Example11",
            "filters/suppressionxpathfilter/Example12",
            "filters/suppressionxpathfilter/Example13",
            "filters/suppressionxpathfilter/Example14",
            "filters/suppressionxpathfilter/Example2",
            "filters/suppressionxpathfilter/Example3",
            "filters/suppressionxpathfilter/Example4",
            "filters/suppressionxpathfilter/Example5",
            "filters/suppressionxpathfilter/Example6",
            "filters/suppressionxpathfilter/Example7",
            "filters/suppressionxpathfilter/Example8",
            "filters/suppressionxpathfilter/Example9",
            "filters/suppressionxpathsinglefilter/Example10",
            "filters/suppressionxpathsinglefilter/Example11",
            "filters/suppressionxpathsinglefilter/Example12",
            "filters/suppressionxpathsinglefilter/Example13",
            "filters/suppressionxpathsinglefilter/Example2",
            "filters/suppressionxpathsinglefilter/Example4",
            "filters/suppressionxpathsinglefilter/Example5",
            "filters/suppressionxpathsinglefilter/Example6",
            "filters/suppressionxpathsinglefilter/Example7",
            "filters/suppressionxpathsinglefilter/Example8",
            "filters/suppressionxpathsinglefilter/Example9",
            "filters/suppresswarningsfilter/Example2",
            "filters/suppresswithnearbycommentfilter/Example2",
            "filters/suppresswithnearbycommentfilter/Example3",
            "filters/suppresswithnearbycommentfilter/Example4",
            "filters/suppresswithnearbycommentfilter/Example5",
            "filters/suppresswithnearbycommentfilter/Example6",
            "filters/suppresswithnearbycommentfilter/Example7",
            "filters/suppresswithnearbycommentfilter/Example8",
            "filters/suppresswithnearbytextfilter/Example2",
            "filters/suppresswithnearbytextfilter/Example3",
            "filters/suppresswithnearbytextfilter/Example4",
            "filters/suppresswithnearbytextfilter/Example5",
            "filters/suppresswithnearbytextfilter/Example6",
            "filters/suppresswithnearbytextfilter/Example7",
            "filters/suppresswithnearbytextfilter/Example8",
            "filters/suppresswithnearbytextfilter/Example9",
            "filters/suppresswithplaintextcommentfilter/Example5",
            "filters/suppresswithplaintextcommentfilter/Example9"
    );

    /**
     * Tests that examples with the same code structure maintain consistency.
     * Examples not marked as independent must have identical AST structure.
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
                final List<String> dirViolations = checkExamplesInDirectory(dir);
                violations.addAll(dirViolations);
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
                    .append(" example files with AST mismatches.\n\n");

            for (String violation : violations) {
                builder.append(violation)
                        .append("\n\n");
            }

            builder.append("If these examples have different code intent, "
                    + "add them to SUPPRESSED_EXAMPLES:\n");

            for (String violation : violations) {
                final String pattern = extractIndependentPattern(violation);
                if (pattern != null) {
                    builder.append('"').append(pattern).append("\",\n");
                }
            }

            message = builder.toString();
        }

        assertWithMessage(message)
                .that(violations)
                .isEmpty();
    }

    /**
     * Extracts an independent example pattern from a violation message.
     *
     * @param violation the violation message
     * @return the pattern, or null if not found
     */
    private static String extractIndependentPattern(String violation) {
        final String dirPath = extractDirectoryPath(violation);
        final String fileName = extractMismatchFileName(violation);
        final String result;

        if (dirPath != null && fileName != null) {
            result = dirPath + "/" + fileName.replace(".java", "");
        }
        else {
            result = null;
        }

        return result;
    }

    /**
     * Extracts the mismatched filename from a violation message.
     *
     * @param violation the violation message
     * @return the filename, or null if not found
     */
    private static String extractMismatchFileName(String violation) {
        final String prefix = "Mismatch:  ";
        final int startIndex = violation.indexOf(prefix);
        final String result;

        if (startIndex == -1) {
            result = null;
        }
        else {
            final int endIndex = violation.indexOf('\n', startIndex);
            if (endIndex == -1) {
                result = violation.substring(startIndex + prefix.length()).trim();
            }
            else {
                result = violation.substring(startIndex + prefix.length(), endIndex).trim();
            }
        }

        return result;
    }

    /**
     * Checks if a specific example is marked as unparseable.
     *
     * @param relativePath the relative directory path
     * @param exampleFileName the example filename (e.g., "Example1.java")
     * @return true if this example cannot be parsed
     */
    private static boolean isExampleUnparseable(String relativePath, String exampleFileName) {
        final String exampleName = exampleFileName.replace(".java", "");
        final String fullPath = relativePath + "/" + exampleName;
        return UNPARSEABLE_EXAMPLES.contains(fullPath);
    }

    /**
     * Checks if a specific example is marked as independent.
     *
     * @param relativePath the relative directory path
     * @param exampleFileName the example filename (e.g., "Example1.java")
     * @return true if this example is independent
     */
    private static boolean isExampleIndependent(String relativePath, String exampleFileName) {
        final String exampleName = exampleFileName.replace(".java", "");
        final String fullPath = relativePath + "/" + exampleName;
        return SUPPRESSED_EXAMPLES.contains(fullPath);
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
     * Checks if a directory contains multiple example files.
     *
     * @param dir the directory to check
     * @return true if the directory contains 2 or more Example*.java files
     */
    private static boolean containsMultipleExamples(Path dir) {
        try (Stream<Path> pathStream = Files.list(dir)) {
            return pathStream
                    .filter(path -> path.getFileName().toString().matches("Example\\d+\\.java"))
                    .count() > 1;
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed to list files in directory: " + dir,
                    exception);
        }
    }

    /**
     * Checks examples in a directory. Non-independent examples must match.
     *
     * @param dir the directory containing example files
     * @return list of violation messages for mismatches
     * @throws IOException if an I/O error occurs
     */
    private static List<String> checkExamplesInDirectory(Path dir) throws IOException {
        final List<String> violations = new ArrayList<>();
        final List<Path> examples = getExampleFiles(dir);

        if (!examples.isEmpty()) {
            violations.addAll(compareExamples(dir, examples));
        }

        return violations;
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
     * Compares examples: groups by AST, validates groups, reports mismatches.
     *
     * @param dir the directory containing the examples
     * @param examples the list of example files
     * @return list of violation messages for mismatches
     * @throws IOException if an I/O error occurs
     */
    private static List<String> compareExamples(Path dir, List<Path> examples)
            throws IOException {
        final List<String> violations = new ArrayList<>();
        final String relativePath = getRelativePath(dir);

        final List<Path> regularExamples = new ArrayList<>();

        for (Path example : examples) {
            final String fileName = example.getFileName().toString();
            if (!isExampleIndependent(relativePath, fileName)) {
                regularExamples.add(example);
            }
        }

        if (regularExamples.size() > 1) {
            violations.addAll(validateAllMatch(dir, regularExamples));
        }

        return violations;
    }

    /**
     * Validates that all examples in the list have identical AST structure.
     *
     * @param dir the directory containing the examples
     * @param examples the list of examples that must all match
     * @return list of violation messages for mismatches
     * @throws IOException if an I/O error occurs
     */
    private static List<String> validateAllMatch(Path dir, List<Path> examples)
            throws IOException {
        final List<String> violations = new ArrayList<>();
        final Path reference = examples.getFirst();
        final String referenceXdocSection = extractXdocSection(reference);

        try {
            final DetailAST referenceDetailAst = parseContent(referenceXdocSection);
            if (referenceDetailAst != null) {
                final StructuralAstNode referenceAst = toStructuralAst(referenceDetailAst);

                for (int index = 1; index < examples.size(); index++) {
                    final Path example = examples.get(index);
                    final String violation = compareSingleExample(
                            dir, example, reference, referenceAst
                    );
                    if (violation != null) {
                        violations.add(violation);
                    }
                }
            }
        }
        catch (CheckstyleException exception) {
            // Skip examples that can't be parsed as Java
        }

        return violations;
    }

    /**
     * Extracts content between xdoc section markers from a file.
     *
     * @param file the file to read
     * @return the content between markers, or entire file if no markers
     * @throws IOException if an I/O error occurs
     */
    private static String extractXdocSection(Path file) throws IOException {
        final List<String> lines = Files.readAllLines(file, StandardCharsets.UTF_8);
        int startIndex = -1;
        int endIndex = -1;

        for (int index = 0; index < lines.size(); index++) {
            final String line = lines.get(index);
            if (line.contains(XDOC_START_MARKER)) {
                startIndex = index + 1;
            }
            else if (line.contains(XDOC_END_MARKER)) {
                endIndex = index;
                break;
            }
        }

        final String result;
        if (startIndex != -1 && endIndex != -1 && startIndex < endIndex) {
            result = String.join("\n", lines.subList(startIndex, endIndex));
        }
        else {
            result = String.join("\n", lines);
        }

        return result;
    }

    /**
     * Parses Java content string into a DetailAST.
     *
     * @param content the Java code content to parse
     * @return the parsed AST, or null if parsing fails
     * @throws CheckstyleException if parsing fails
     */
    private static DetailAST parseContent(String content) throws CheckstyleException {
        final FileText text = new FileText(
                new File("Example.java").getAbsoluteFile(),
                content.lines().toList()
        );
        final FileContents contents = new FileContents(text);
        return JavaParser.parse(contents);
    }

    /**
     * Compares a single example file against the reference.
     *
     * @param dir          the directory containing the examples
     * @param example      the example file to compare
     * @param reference    the reference file
     * @param referenceAst the reference AST
     * @return violation message if mismatch found, null otherwise
     * @throws IOException if an I/O error occurs
     */
    private static String compareSingleExample(Path dir, Path example,
                                               Path reference,
                                               StructuralAstNode referenceAst)
            throws IOException {
        final String exampleXdocSection = extractXdocSection(example);
        final String relativePath = getRelativePath(dir);
        final String exampleFileName = example.getFileName().toString();

        DetailAST exampleDetailAst = null;
        try {
            exampleDetailAst = parseContent(exampleXdocSection);
        }
        catch (CheckstyleException exception) {
            if (!isExampleUnparseable(relativePath, exampleFileName)) {
                throw new IllegalStateException(
                        "Failed to parse example: " + example, exception);
            }
        }

        final String result;
        if (exampleDetailAst == null) {
            result = null;
        }
        else {
            final StructuralAstNode ast = toStructuralAst(exampleDetailAst);

            if (referenceAst.equals(ast)) {
                result = null;
            }
            else {
                result = "Directory: " + relativePath + "\n"
                        + "Reference: " + reference.getFileName() + "\n"
                        + "Mismatch:  " + example.getFileName();
            }
        }

        return result;
    }

    /**
     * Converts a DetailAST into a structural representation that excludes comments.
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
            final StructuralAstNode node = new StructuralAstNode(ast.getType(), ast.getText());

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
     * Now includes literal text values for semantic comparison.
     */
    private static final class StructuralAstNode {
        private final int type;
        private final String text;
        private final List<StructuralAstNode> children = new ArrayList<>();

        private StructuralAstNode(int type, String text) {
            this.type = type;
            if (isLiteralToken(type)) {
                this.text = text;
            }
            else {
                this.text = null;
            }
        }

        /**
         * Checks if a token type represents a literal that should have its text compared.
         *
         * @param tokenType the token type
         * @return true if the token represents a literal with semantic value
         */
        private static boolean isLiteralToken(int tokenType) {
            return switch (tokenType) {
                case TokenTypes.NUM_INT, TokenTypes.NUM_LONG, TokenTypes.NUM_FLOAT,
                     TokenTypes.NUM_DOUBLE, TokenTypes.STRING_LITERAL,
                     TokenTypes.CHAR_LITERAL, TokenTypes.LITERAL_TRUE,
                     TokenTypes.LITERAL_FALSE, TokenTypes.LITERAL_NULL -> true;
                default -> false;
            };
        }

        private void addChild(StructuralAstNode child) {
            children.add(child);
        }

        @Override
        public boolean equals(Object obj) {
            final boolean result;
            if (obj instanceof StructuralAstNode other) {
                final boolean typeMatch = type == other.type;
                final boolean textMatch = Objects.equals(text, other.text);
                final boolean childrenMatch = children.equals(other.children);
                result = typeMatch && textMatch && childrenMatch;
            }
            else {
                result = false;
            }
            return result;
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, text, children);
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder(128);
            sb.append("StructuralAstNode{type=");
            try {
                sb.append(TokenUtil.getTokenName(type));
            }
            catch (IllegalArgumentException exception) {
                sb.append(type);
            }
            if (text != null) {
                sb.append(", text='").append(text).append('\'');
            }
            if (!children.isEmpty()) {
                sb.append(", children=").append(children.size());
            }
            sb.append('}');
            return sb.toString();
        }
    }
}
