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
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
 * <p>Line numbers within the extracted xdoc section are also compared, ensuring
 * that structurally identical statements appear on the same relative lines across
 * examples. Because {@code parseContent} re-parses only the extracted section
 * (starting at line 1), line numbers are always section-relative and are safe
 * to compare directly between examples.
 *
 * <p>Single-line comments starting with {@code ok}, {@code violation}, or
 * {@code xdoc section} are excluded from comparison as they are documentation
 * markers. All other single-line comments, as well as Javadoc and block comments,
 * are included in the structural comparison.
 *
 * <p>Block comments used as {@code ok} or {@code violation} markers
 * (e.g. {@code /* ok, allowMissingReturnTag is true *}{@code /}) are forbidden;
 * use single-line {@code //} comments instead. The
 * {@link #testNoBlockCommentMarkers()} test enforces this.
 *
 */
public class XdocsExamplesAstConsistencyTest {

    private static final Path XDOCS_ROOT = Path.of(
            "src/xdocs-examples/resources/com/puppycrawl/tools/checkstyle"
    );
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
            "checks/coding/explicitinitialization/Example2",
            "checks/coding/illegalsymbol/Example4",
            "checks/coding/nestedfordepth/Example2",
            "checks/coding/nestedifdepth/Example2",
            "checks/coding/onestatementperline/Example2",
            "checks/coding/packagedeclaration/Example2",
            "checks/coding/unnecessarysemicolonafteroutertypedeclaration/Example2",
            "checks/imports/importcontrol/filters/Example9",
            "checks/indentation/indentation/Example7",
            "checks/javadoc/javadoccontentlocation/Example2",
            "checks/javadoc/javadocleadingasteriskalign/Example2",
            "checks/javadoc/javadocleadingasteriskalign/Example3",
            "checks/javadoc/javadocpackage/legacywithboth/Example3",
            "checks/javadoc/javadocpackage/nonlegacy/Example1",
            "checks/javadoc/javadoctagcontinuationindentation/Example3",
            "checks/javadoc/javadocvariable/Example5",
            "checks/metrics/booleanexpressioncomplexity/Example2",
            "checks/metrics/booleanexpressioncomplexity/Example3",
            "checks/metrics/classdataabstractioncoupling/Example2",
            "checks/metrics/classdataabstractioncoupling/Example3",
            "checks/metrics/classdataabstractioncoupling/ignore/Example7",
            "checks/metrics/classdataabstractioncoupling/ignore/Example8",
            "checks/metrics/classdataabstractioncoupling/ignore/Example9",
            "checks/metrics/classdataabstractioncoupling/ignore/deeper/Example5",
            "checks/metrics/classdataabstractioncoupling/ignore/deeper/Example6",
            "checks/metrics/cyclomaticcomplexity/Example2",
            "checks/metrics/cyclomaticcomplexity/Example3",
            "checks/modifier/interfacememberimpliedmodifier/Example2",
            "checks/modifier/interfacememberimpliedmodifier/Example3",
            "checks/modifier/interfacememberimpliedmodifier/Example4",
            "checks/modifier/redundantmodifier/Example2",
            "checks/naming/abstractclassname/Example3",
            "checks/naming/abstractclassname/Example4",
            "checks/naming/catchparametername/Example2",
            "checks/naming/methodname/Example4",
            "checks/naming/packagename/Example2",
            "checks/naming/patternvariablename/Example2",
            "checks/naming/patternvariablename/Example3",
            "checks/naming/patternvariablename/Example4",
            "checks/regexp/regexp/Example7",
            "checks/regexp/regexpmultiline/Example5",
            "checks/regexp/regexpsinglelinejava/Example2",
            "checks/regexp/regexpsinglelinejava/Example3",
            "checks/regexp/regexpsinglelinejava/Example4",
            "checks/regexp/regexpsinglelinejava/Example5",
            "checks/sizes/lambdabodylength/Example2",
            "checks/sizes/methodlength/Example3",
            "checks/sizes/outertypenumber/Example2",
            "checks/whitespace/singlespaceseparator/Example2",
            "checks/whitespace/typecastparenpad/Example2",
            "checks/whitespace/whitespaceafter/Example2",
            "checks/todocomment/Example2",
            "checks/todocomment/Example3",
            "checks/uncommentedmain/Example2",
            "checks/uniqueproperties/Example2",
            "checks/whitespace/emptyforiteratorpad/Example2",
            "checks/whitespace/parenpad/Example2",
            "filters/suppressioncommentfilter/Example2",
            "filters/suppressioncommentfilter/Example3",
            "filters/suppressioncommentfilter/Example4",
            "filters/suppressioncommentfilter/Example5",
            "filters/suppressioncommentfilter/Example6",
            "filters/suppressioncommentfilter/Example7",
            "filters/suppressioncommentfilter/Example8",
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
            "filters/suppressionxpathsinglefilter/Example3",
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
            // Note: customImport/ImportOrder changes import group ORDER affecting AST structure
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
            // until https://github.com/checkstyle/checkstyle/issues/19891
            "checks/blocks/leftcurly/Example3",
            "checks/whitespace/parenpad/Example3",
            "checks/javadoc/missingjavadoctype/Example5",
            "checks/indentation/commentsindentation/Example3",
            "checks/indentation/commentsindentation/Example4",
            "checks/indentation/commentsindentation/Example5",
            "checks/indentation/commentsindentation/Example6",
            "checks/indentation/commentsindentation/Example7",
            "checks/indentation/commentsindentation/Example8",
            "checks/coding/equalsavoidnull/Example2",
            "checks/blocks/needbraces/Example4",
            "checks/blocks/needbraces/Example6",
            "checks/metrics/classfanoutcomplexity/Example6",
            "checks/whitespace/separatorwrap/Example2",
            "checks/regexp/regexp/Example2",
            "checks/regexp/regexp/Example3",
            "checks/regexp/regexp/Example4",
            "checks/regexp/regexp/Example5",
            "checks/regexp/regexp/Example8",
            "checks/blocks/rightcurly/Example3",
            "checks/blocks/rightcurly/Example5",
            "checks/regexp/regexp/Example9",
            "checks/regexp/regexp/Example11",
            "checks/regexp/regexp/Example10",
            "checks/regexp/regexp/Example6",
            "checks/regexp/regexpsingleline/Example3",
            "checks/regexp/regexpsingleline/Example5",
            "checks/javadoc/writetag/Example5",
            "checks/coding/illegalinstantiation/Example3",
            "checks/annotation/suppresswarningsholder/Example3",
            "checks/annotation/suppresswarningsholder/Example4",
            "checks/coding/matchxpath/Example2",
            "checks/coding/matchxpath/Example3",
            "checks/coding/matchxpath/Example4",
            "checks/coding/matchxpath/Example5",
            "checks/coding/matchxpath/Example6",
            "filters/suppresswithnearbytextfilter/Example2",
            "filters/suppresswithnearbytextfilter/Example6",
            "filters/suppresswithnearbytextfilter/Example7",
            "filters/suppresswithnearbytextfilter/Example8",
            "checks/coding/illegaltokentext/Example3",
            "checks/coding/illegaltokentext/Example4",
            "filters/suppresswithplaintextcommentfilter/Example9",
            "checks/design/visibilitymodifier/Example7",
            "checks/design/visibilitymodifier/Example9",
            "checks/design/visibilitymodifier/Example10",
            "checks/design/visibilitymodifier/Example11",
            "checks/coding/variabledeclarationusagedistance/Example2",
            "checks/indentation/indentation/Example4",
            "filters/suppressionfilter/Example2",
            "filters/suppressionfilter/Example3",
            "filters/suppressionfilter/Example4",
            "checks/trailingcomment/Example4",
            "checks/trailingcomment/Example5",
            "checks/trailingcomment/Example6",
            "checks/imports/importcontrol/filters/Example3.java",
            "checks/imports/importcontrol/newdomain/dao/Example4.java",
            "checks/imports/importcontrol/Example5",
            "checks/imports/importcontrol/someImports/Example6.java",
            "checks/imports/importcontrol/someImports/Example7.java",
            "checks/imports/importcontrol/gui/Example8.java",
            "checks/imports/importcontrol/filters/Example9.java",
            "checks/imports/importcontrol/someImports/Example10.java",
            "checks/imports/importcontrol/someImports/Example11.java",
            "checks/imports/importcontrol/Example12",
            "checks/coding/requirethis/Example5",
            "checks/coding/requirethis/Example6",
            "checks/whitespace/nolinewrap/Example3",
            "checks/naming/abbreviationaswordinname/Example4",
            "checks/naming/abbreviationaswordinname/Example6",
            "checks/naming/abbreviationaswordinname/Example7",
            "checks/naming/localvariablename/Example3",
            "checks/naming/localvariablename/Example5",
            "checks/coding/unnecessaryparentheses/Example3",
            "checks/whitespace/nowhitespacebefore/Example4",
            "checks/whitespace/whitespacearound/Example2",
            "checks/naming/localfinalvariablename/Example2",
            "checks/blocks/emptycatchblock/Example4",
            "checks/blocks/emptycatchblock/Example5",
            "checks/naming/parametername/Example4",
            "checks/coding/illegalsymbol/Example3",
            "checks/coding/illegalsymbol/Example5",
            "checks/descendanttoken/Example5",
            "checks/descendanttoken/Example8",
            "checks/descendanttoken/Example9",
            "checks/descendanttoken/Example10",
            "checks/descendanttoken/Example11",
            "checks/descendanttoken/Example13",
            "checks/descendanttoken/Example15",
            "checks/descendanttoken/Example16"
            );

    /**
     * Tests that examples with the same code structure maintain consistency.
     * Examples not marked as independent must have identical AST structure,
     * including the line numbers of each node within the xdoc section.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testExamplesDifferOnlyByComments() throws IOException {
        final List<Violation> violations = new ArrayList<>();

        try (Stream<Path> pathStream = Files.walk(XDOCS_ROOT)) {
            final List<Path> exampleDirs = pathStream
                    .filter(Files::isDirectory)
                    .filter(XdocsExamplesAstConsistencyTest::containsMultipleExamples)
                    .toList();

            for (Path dir : exampleDirs) {
                final List<Violation> dirViolations = checkExamplesInDirectory(dir);
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

            for (Violation violation : violations) {
                builder.append(violation)
                        .append("\n\n");
            }

            builder.append("If these examples have different code intent, "
                    + "add them to SUPPRESSED_EXAMPLES:\n");

            for (Violation violation : violations) {
                final String pattern = violation.getSuppressionPattern();
                builder.append('"').append(pattern).append("\",\n");
            }

            message = builder.toString();
        }

        assertWithMessage(message)
                .that(violations)
                .isEmpty();
    }

    /**
     * Tests that no example file uses block comments as {@code ok} or
     * {@code violation} markers. All such markers must use single-line
     * comments instead. For example:
     * <pre>
     *   BAD:  &#47;* ok, allowMissingReturnTag is true *&#47;
     *   GOOD: // ok, allowMissingReturnTag is true
     * </pre>
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testNoBlockCommentMarkers() throws IOException {
        final List<String> violations = new ArrayList<>();

        try (Stream<Path> pathStream = Files.walk(XDOCS_ROOT)) {
            pathStream
                    .filter(path -> path.getFileName().toString().matches("Example\\d+\\.java"))
                    .filter(path -> {
                        final String relativePath = getRelativePath(path.getParent());
                        final String fileName = path.getFileName().toString();
                        return !isExampleIndependent(relativePath, fileName);
                    })
                    .sorted()
                    .forEach(path -> {
                        try {
                            violations.addAll(checkForBlockCommentMarkers(path));
                        }
                        catch (IOException exception) {
                            throw new IllegalStateException(
                                    "Failed to read file: " + path, exception);
                        }
                    });
        }

        final String message;
        if (violations.isEmpty()) {
            message = "";
        }
        else {
            message = formatBlockCommentMarkerViolationsMessage(violations);
        }

        assertWithMessage(message)
                .that(violations)
                .isEmpty();
    }

    /**
     * Formats the violation message for block comment markers.
     *
     * @param violations the list of violations
     * @return formatted message
     */
    private static String formatBlockCommentMarkerViolationsMessage(List<String> violations) {
        final StringBuilder builder = new StringBuilder(1024);
        builder.append("Found ")
                .append(violations.size())
                .append(
                        """
                         example file(s) using block comments as ok/violation markers.
                        Convert them to single-line comments, e.g.:
                          BAD:  /* ok, allowMissingReturnTag is true */
                          GOOD: // ok, allowMissingReturnTag is true

                        """);

        for (String violation : violations) {
            builder.append(violation).append('\n');
        }

        return builder.toString();
    }

    /**
     * Checks a single example file for block comments used as ok/violation markers.
     *
     * @param file the example file to check
     * @return the list of violation messages
     * @throws IOException if an I/O error occurs
     */
    private static List<String> checkForBlockCommentMarkers(Path file)
            throws IOException {
        final List<String> fileViolations = new ArrayList<>();
        final String content = Files.readString(file);
        final Pattern blockCommentPattern = Pattern.compile("(?s)/\\*.*?\\*/");
        final Matcher matcher = blockCommentPattern.matcher(content);

        while (matcher.find()) {
            final String block = matcher.group();
            final String inner = block
                    .replaceAll("^/\\*+", "")
                    .replaceAll("\\*/$", "")
                    .replace("*", "")
                    .strip();

            if (inner.startsWith("ok") || inner.startsWith("violation")) {
                int lineNo = 1;
                for (int index = 0; index < matcher.start(); index++) {
                    if (content.charAt(index) == '\n') {
                        lineNo++;
                    }
                }
                fileViolations.add(file + ":" + lineNo
                        + " - use single-line comment instead: // "
                        + inner);
            }
        }
        return fileViolations;
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
     * Checks whether none of the examples in this directory define any module properties.
     * When a module has no configurable properties, its examples may intentionally use
     * very different code to demonstrate different behaviours, so consistency checking
     * is not meaningful.
     *
     * @param examples the list of example files in the directory
     * @return true if no example file contains a {@code <property} element in its XML config
     * @throws IOException if an I/O error occurs reading an example file
     */
    private static boolean isModuleWithNoProperties(List<Path> examples) throws IOException {
        boolean result = true;
        for (Path example : examples) {
            final String content = Files.readString(example);
            if (content.contains("<property ")) {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Checks examples in a directory. Non-independent examples must match.
     *
     * @param dir the directory containing example files
     * @return list of violation messages for mismatches
     * @throws IOException if an I/O error occurs
     */
    private static List<Violation> checkExamplesInDirectory(Path dir) throws IOException {
        final List<Violation> violations = new ArrayList<>();
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
        return XDOCS_ROOT.relativize(dir).toString().replace('\\', '/');
    }

    /**
     * Compares examples: groups by AST, validates groups, reports mismatches.
     *
     * @param dir the directory containing the examples
     * @param examples the list of example files
     * @return list of violation messages for mismatches
     */
    private static List<Violation> compareExamples(Path dir, List<Path> examples)
            throws IOException {
        final List<Violation> violations = new ArrayList<>();

        if (!isModuleWithNoProperties(examples)) {
            final String relativePath = getRelativePath(dir);

            final List<Path> regularExamples = new ArrayList<>();

            for (Path example : examples) {
                final String fileName = example.getFileName().toString();
                if (!isExampleIndependent(relativePath, fileName)) {
                    regularExamples.add(example);
                }
            }

            if (regularExamples.size() > 1) {
                violations.addAll(validateExamplesByConstructorPresence(dir, regularExamples));
            }
        }

        return violations;
    }

    /**
     * Validates examples by comparing files with and without constructors separately.
     *
     * @param dir the directory containing examples
     * @param examples the list of examples that must be validated
     * @return list of violation messages for mismatches
     * @throws IOException if an I/O error occurs
     */
    private static List<Violation> validateExamplesByConstructorPresence(Path dir,
                                                                         List<Path> examples)
            throws IOException {
        final List<Violation> violations = new ArrayList<>();
        final List<Path> constructorExamples = new ArrayList<>();
        final List<Path> nonConstructorExamples = new ArrayList<>();

        for (Path example : examples) {
            if (containsConstructorDefinition(example)) {
                constructorExamples.add(example);
            }
            else {
                nonConstructorExamples.add(example);
            }
        }

        if (nonConstructorExamples.size() > 1) {
            violations.addAll(validateAllMatch(dir, nonConstructorExamples));
        }
        if (constructorExamples.size() > 1) {
            violations.addAll(validateAllMatch(dir, constructorExamples));
        }

        return violations;
    }

    /**
     * Checks whether an example contains at least one constructor definition.
     *
     * @param example the example file path
     * @return true if the parsed xdoc section contains a constructor definition
     * @throws IOException if an I/O error occurs
     */
    private static boolean containsConstructorDefinition(Path example) throws IOException {
        final String xdocSection = extractXdocSection(example);
        boolean result;
        try {
            final DetailAST ast = parseContent(xdocSection);
            result = ast != null && hasDescendantOfType(ast, TokenTypes.CTOR_DEF);
        }
        catch (CheckstyleException exception) {
            result = false;
        }

        return result;
    }

    /**
     * Checks whether an AST contains a descendant of the given token type.
     *
     * @param ast the AST root to inspect
     * @param tokenType the token type to find
     * @return true if a matching node is found
     */
    private static boolean hasDescendantOfType(DetailAST ast, int tokenType) {
        boolean result = false;
        if (ast.getType() == tokenType) {
            result = true;
        }
        else {
            for (DetailAST child = ast.getFirstChild(); child != null;
                 child = child.getNextSibling()) {
                if (hasDescendantOfType(child, tokenType)) {
                    result = true;
                    break;
                }
            }
        }

        return result;
    }

    /**
     * Validates that all examples in the list have identical AST structure,
     * including identical line numbers for each node within the xdoc section.
     *
     * @param dir the directory containing the examples
     * @param examples the list of examples that must all match
     * @return list of violation messages for mismatches
     * @throws IOException if an I/O error occurs
     */
    private static List<Violation> validateAllMatch(Path dir, List<Path> examples)
            throws IOException {
        final List<Violation> violations = new ArrayList<>();
        final Path reference = examples.getFirst();
        final String referenceXdocSection = extractXdocSection(reference);

        try {
            final DetailAST referenceDetailAst = parseContent(referenceXdocSection);
            if (referenceDetailAst != null) {
                final StructuralAstNode referenceAst = toStructuralAst(referenceDetailAst);
                final List<String> referenceComments =
                        extractComments(referenceXdocSection);
                for (int index = 1; index < examples.size(); index++) {
                    final Path example = examples.get(index);
                    final Violation violation = compareSingleExample(
                            dir, example, reference, referenceAst, referenceComments
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
     * <p>The extracted lines are re-joined and parsed fresh by {@link #parseContent}, so
     * AST line numbers are always relative to the start of the extracted section (line 1).
     * This makes line-number comparisons between examples independent of any difference in
     * header length (license block, imports, etc.) above the marker.
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
        if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
            result = String.join("\n", lines);
        }
        else {
            result = String.join("\n", lines.subList(startIndex, endIndex));
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
    private static Violation compareSingleExample(Path dir, Path example,
                                                  Path reference,
                                                  StructuralAstNode referenceAst,
                                                  List<String> referenceComments)
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

        final Violation result;
        if (exampleDetailAst == null) {
            result = null;
        }
        else {
            final StructuralAstNode ast = toStructuralAst(exampleDetailAst);

            final List<String> exampleComments =
                    extractComments(exampleXdocSection);

            if (referenceAst.equals(ast) && referenceComments.equals(exampleComments)) {
                result = null;
            }
            else if (referenceAst.equals(ast)) {
                result = new Violation(relativePath, reference.getFileName().toString(),
                        example.getFileName().toString(), "Comments mismatch");
            }
            else {
                result = new Violation(relativePath, reference.getFileName().toString(),
                        example.getFileName().toString(), "AST structure mismatch");
            }
        }

        return result;
    }

    /**
     * Converts a DetailAST into a structural representation that excludes only
     * {@code ok}, {@code violation}, and {@code xdoc section} single-line comments.
     * All other single-line comments, as well as Javadoc and block comments, are
     * included in the comparison.
     *
     * @param ast the AST to convert
     * @return structural representation of the AST, or null if the node is a
     *         skippable comment
     */
    private static StructuralAstNode toStructuralAst(DetailAST ast) {
        final boolean ignoreName = isClassOrConstructorName(ast)
                || isExtendsAnExampleClass(ast);
        final StructuralAstNode node = new StructuralAstNode(
                ast.getType(), ast.getText(), ignoreName, ast.getLineNo(), ignoreName
        );

        for (DetailAST child = ast.getFirstChild();
             child != null;
             child = child.getNextSibling()) {
            final StructuralAstNode structuralChild = toStructuralAst(child);
            if (structuralChild != null) {
                node.addChild(structuralChild);
            }
        }
        return node;
    }

    /**
     * Checks if an AST node is an identifier representing class or constructor name.
     *
     * @param ast the AST node to check
     * @return true if the node is a class or constructor name identifier
     */
    private static boolean isClassOrConstructorName(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        return parent != null
                && ast.getType() == TokenTypes.IDENT
                && (parent.getType() == TokenTypes.CLASS_DEF
                    || parent.getType() == TokenTypes.CTOR_DEF);
    }

    /**
     * Checks if an AST node is an identifier in an extends clause of an example class.
     *
     * @param ast the AST node to check
     * @return true if the node is an identifier in an extends clause of an example class
     */
    private static boolean isExtendsAnExampleClass(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        boolean result = false;
        if (parent != null
                && ast.getType() == TokenTypes.IDENT
                && parent.getType() == TokenTypes.EXTENDS_CLAUSE) {
            final DetailAST classDef = parent.getParent();
            if (classDef != null && classDef.getType() == TokenTypes.CLASS_DEF) {
                final DetailAST className = classDef.findFirstToken(TokenTypes.IDENT);
                result = className != null
                        && className.getText().matches("Example\\d+");
            }
        }
        return result;
    }

    /**
     * Checks whether a comment is a documentation marker that should be
     * excluded from structural comparison.
     *
     * <p>Skipped prefixes:
     * <ul>
     *   <li>{@code ok} - suppressed-violation marker</li>
     *   <li>{@code violation} - violation marker (including {@code filtered violation})</li>
     *   <li>{@code xdoc section} - section boundary marker</li>
     *   <li>{@code N violation(s)} - count-style marker, e.g. {@code 3 violations}</li>
     *   <li>A single-quoted string starting and ending with a single-quote character
     *       continuation line, e.g. {@code //    'Expected }&#64;{@code param tag for p1.'}.
     *       These lines appear below a count-style or
     *       {@code violation above} marker and may be separated from it by a blank line,
     *       so they cannot be reliably caught by the continuation-chain logic alone.</li>
     * </ul>
     *
     * @param comment the stripped comment text (everything after {@code //})
     * @return true if the comment is a marker that should be ignored
     */
    private static boolean isIgnoredComment(String comment) {
        return comment.startsWith("ok")
                || comment.startsWith("violation")
                || comment.startsWith("filtered violation")
                || comment.startsWith("xdoc section")
                || comment.contains("// ok")
                || comment.contains("// violation")
                || comment.matches("\\d+\\s+violations?.*")
                || comment.matches("'.*'");
    }

    /**
     * Extracts comments that participate in comparison.
     *
     * <p>The following comments are ignored:
     * <ul>
     *   <li>{@code ok}</li>
     *   <li>{@code violation} (including {@code filtered violation}
     *       and count-style {@code N violations})</li>
     *   <li>xdoc section markers</li>
     *   <li>standalone continuation lines immediately following a skipped marker —
     *       e.g. <code>// no space after '{'</code> after {@code // 3 violations}</li>
     * </ul>
     *
     * <p>A standalone continuation line is one where there is no code before the
     * double-slash on that line, and the previous comment line was a skipped marker.
     * Inline trailing comments on code lines are always evaluated independently.
     *
     * <p>All other comments are included in comparison.
     *
     * @param content example content
     * @return comments participating in comparison
     */
    private static List<String> extractComments(String content) {
        final List<String> comments = new ArrayList<>();
        boolean prevLineWasMarker = false;

        for (String line : content.lines().toList()) {
            final int commentIndex = findCommentStart(line);

            if (commentIndex < 0) {
                prevLineWasMarker = false;
                continue;
            }

            final String comment =
                    line.substring(commentIndex + 2).strip();
            final boolean isCodeBefore =
                    !line.substring(0, commentIndex).isBlank();

            if (isIgnoredComment(comment)) {
                prevLineWasMarker = true;
            }
            else if (!prevLineWasMarker || isCodeBefore) {
                comments.add(comment);
                prevLineWasMarker = false;
            }
        }

        return comments;
    }

    /**
     * Finds the starting index of a single-line comment ({@code //}) in a given line,
     * ignoring comments within string literals or character literals.
     *
     * @param line the string line to search
     * @return the index of the first {@code //}, or -1 if not found or within a literal
     */
    private static int findCommentStart(String line) {
        int result = -1;
        boolean inString = false;
        boolean inChar = false;
        boolean escaped = false;

        for (int index = 0; index < line.length() - 1; index++) {
            final char current = line.charAt(index);

            if (escaped) {
                escaped = false;
            }
            else if (current == '\\') {
                escaped = true;
            }
            else if (!inChar && current == '"') {
                inString = !inString;
            }
            else if (!inString && current == '\'') {
                inChar = !inChar;
            }
            else if (isCommentAt(line, index, inString, inChar)) {
                result = index;
                break;
            }
        }

        return result;
    }

    /**
     * Checks if a single-line comment starts at the given index.
     *
     * @param line current line
     * @param index current index
     * @param inString whether currently inside a string literal
     * @param inChar whether currently inside a character literal
     * @return true if comment starts at index
     */
    private static boolean isCommentAt(String line, int index, boolean inString, boolean inChar) {
        return !inString && !inChar
                && line.charAt(index) == '/'
                && line.charAt(index + 1) == '/';
    }

    /**
     * Represents a structural AST node without skippable comments.
     * This allows for structural comparison between example files.
     * Includes literal text values and identifier names for semantic comparison,
     * and line numbers for positional consistency validation.
     *
     * <p>Line numbers are section-relative (line 1 = first line of the extracted
     * xdoc section) because {@link #parseContent} re-parses only the extracted
     * section string. Class and constructor name nodes have their line number
     * set to {@code null} so that intentional name differences do not cause
     * false positives.
     */
    private static final class StructuralAstNode {
        private final int type;
        private final String text;
        /** Section-relative line number; null when position is intentionally ignored. */
        private final Integer lineNo;
        private final List<StructuralAstNode> children = new ArrayList<>();

        /**
         * Constructs a structural AST node.
         *
         * @param type           the token type
         * @param text           the token text from the source
         * @param ignoreText     if true, the text field is not stored (class/ctor names)
         * @param lineNo         the line number of this node within the parsed section
         * @param ignorePosition if true, the line number is not stored (class/ctor names)
         */
        private StructuralAstNode(int type, String text, boolean ignoreText,
                                  int lineNo, boolean ignorePosition) {
            this.type = type;
            if (ignoreText) {
                this.text = null;
            }
            else if (isLiteralToken(type)) {
                this.text = text;
            }
            else {
                this.text = null;
            }
            if (ignorePosition) {
                this.lineNo = null;
            }
            else {
                this.lineNo = lineNo;
            }
        }

        /**
         * Checks if a token type represents a value whose text should be compared.
         * This includes numeric, string, boolean, null literals, and identifiers.
         * Identifiers are included so that differences in variable names, parameter
         * names, annotation names, etc. are detected as mismatches.
         * Class and constructor name identifiers are excluded via the
         * {@code ignoreText} flag set in {@link #toStructuralAst}.
         *
         * @param tokenType the token type
         * @return true if the token text carries semantic value
         */
        private static boolean isLiteralToken(int tokenType) {
            return switch (tokenType) {
                case TokenTypes.NUM_INT, TokenTypes.NUM_LONG, TokenTypes.NUM_FLOAT,
                     TokenTypes.NUM_DOUBLE, TokenTypes.STRING_LITERAL,
                     TokenTypes.CHAR_LITERAL, TokenTypes.LITERAL_TRUE,
                     TokenTypes.LITERAL_FALSE, TokenTypes.LITERAL_NULL,
                     TokenTypes.IDENT -> true;
                default -> false;
            };
        }

        private void addChild(StructuralAstNode child) {
            children.add(child);
        }

        @Override
        public boolean equals(Object obj) {
            if (!(obj instanceof StructuralAstNode other)) {
                return false;
            }
            final boolean typeMatch = type == other.type;
            final boolean textMatch = Objects.equals(text, other.text);
            final boolean lineNoMatch = Objects.equals(lineNo, other.lineNo);
            final boolean childrenMatch = children.equals(other.children);
            return typeMatch && textMatch && lineNoMatch && childrenMatch;
        }

        @Override
        public int hashCode() {
            return Objects.hash(type, text, lineNo, children);
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
            if (lineNo != null) {
                sb.append(", line=").append(lineNo);
            }
            if (!children.isEmpty()) {
                sb.append(", children=").append(children.size());
            }
            sb.append('}');
            return sb.toString();
        }
    }

    /**
     * Represents a violation found during consistency check.
     *
     * @param relativePath      relative directory path
     * @param referenceFileName reference file name
     * @param mismatchFileName  mismatch file name
     * @param reason            mismatch reason
     */
    private record Violation(String relativePath, String referenceFileName,
                             String mismatchFileName, String reason) {
        @Override
        public String toString() {
            return "Directory: " + relativePath + "\n"
                    + "Reference: " + referenceFileName + "\n"
                    + "Mismatch:  " + mismatchFileName + "\n"
                    + "Reason:    " + reason;
        }

        /**
         * Gets the pattern to use for suppression.
         *
         * @return the suppression pattern
         */
        /* package */ String getSuppressionPattern() {
            return relativePath + "/" + mismatchFileName.replace(".java", "");
        }
    }

}
