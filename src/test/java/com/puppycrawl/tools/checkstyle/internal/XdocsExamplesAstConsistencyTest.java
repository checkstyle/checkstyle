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

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.apache.maven.doxia.macro.MacroExecutionException;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.puppycrawl.tools.checkstyle.JavaParser;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.DetailAST;
import com.puppycrawl.tools.checkstyle.api.FileContents;
import com.puppycrawl.tools.checkstyle.api.FileText;
import com.puppycrawl.tools.checkstyle.api.TokenTypes;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.site.SiteUtil;
import com.puppycrawl.tools.checkstyle.utils.TokenUtil;

/**
 * Ensures xdocs Java examples for the same check differ only by comments, and that
 * the number of AST-consistent examples matches the number of documented properties.
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
 * <p>Modules may organize their examples into subdirectories (e.g.
 * {@code classdataabstractioncoupling/ignore/deeper}) to group related
 * use-cases. Example discovery for a module directory walks recursively
 * into its own subtree so such examples are still counted as belonging to
 * that module, while those subdirectories themselves are never treated as
 * separate modules (see {@link #isModuleDirectory(Path)}).
 *
 */
public class XdocsExamplesAstConsistencyTest {

    private static final Path XDOCS_ROOT = Path.of(
            "src/xdocs-examples/resources/com/puppycrawl/tools/checkstyle"
    );

    private static final Path XDOCS_NONCOMPILABLE_ROOT = Path.of(
            "src/xdocs-examples/resources-noncompilable/com/puppycrawl/tools/checkstyle"
    );
    private static final String XDOC_START_MARKER = "// xdoc section -- start";
    private static final String XDOC_END_MARKER = "// xdoc section -- end";
    private static final Pattern BLOCK_COMMENT_PATTERN = Pattern.compile("(?s)/\\*.*?\\*/");

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
     * Properties that are intentionally never demonstrated in an example, e.g.
     * because they are purely technical/internal and not related to the check's
     * main purpose.
     */
    private static final Set<String> IGNORED_PROPERTIES_FOR_COVERAGE = Set.of(
        "violateExecutionOnNonTightHtml"
    );

    /**
     * Cache for module property counts to avoid repeated, expensive reflective lookups
     * during test execution.
     */
    private static final ConcurrentMap<String, Integer> PROPERTY_COUNT_CACHE =
        new ConcurrentHashMap<>();

    /**
     * Cache mapping a lower-cased xdocs directory name (e.g. {@code declarationorder}) to
     * the check's simple class name (e.g. {@code DeclarationOrderCheck}), built once from
     * the full list of checkstyle module classes. Avoids repeated classpath scans.
     */
    private static final ConcurrentMap<String, String> MODULE_SIMPLE_NAME_CACHE =
        buildModuleSimpleNameIndex();

    /**
     * Modules where the example-count-matches-property-count validation should be skipped,
     * e.g. because the directory intentionally demonstrates use-cases rather than a strict
     * one-example-per-property mapping.
     */
    private static final Set<String> EXAMPLE_COUNT_SUPPRESSED_MODULES = Set.of(
            // until https://github.com/checkstyle/checkstyle/issues/20625
            "checks/annotation/annotationlocation",
            "checks/annotation/suppresswarnings",
            "checks/blocks/leftcurly",
            "checks/coding/illegaltokentext",
            "checks/descendanttoken",
            "checks/javadoc/javadocblocktaglocation",
            "checks/javadoc/writetag",
            "checks/metrics/cyclomaticcomplexity",
            "checks/modifier/interfacememberimpliedmodifier",
            "checks/naming/abbreviationaswordinname",
            "checks/naming/constantname",
            "checks/naming/localfinalvariablename",
            "checks/naming/membername",
            "checks/naming/methodname",
            "checks/naming/staticvariablename",
            "checks/naming/typename",
            "checks/regexp/regexp",
            "checks/regexp/regexpmultiline",
            "checks/regexp/regexponfilename",
            "checks/regexp/regexpsingleline",
            "checks/regexp/regexpsinglelinejava",
            "checks/sizes/methodlength",
            "checks/translation",
            "checks/whitespace/methodparampad",
            "checks/whitespace/nowhitespaceafter",
            "checks/whitespace/operatorwrap",
            "checks/whitespace/parenpad",
            "checks/whitespace/separatorwrap",
            "filters/suppressioncommentfilter",
            "filters/suppressionsinglefilter",
            "filters/suppressionxpathfilter",
            "filters/suppressionxpathsinglefilter",
            "filters/suppresswithnearbycommentfilter",
            "filters/suppresswithnearbytextfilter"
    );

    /**
     * Examples that have independent code structure and should not be compared.
     * These represent different use cases or configurations with different code.
     *
     * <p>Format: "directory/ExampleN" where the example has unique code.
     *
     * <p>Until: <a href="https://github.com/checkstyle/checkstyle/issues/19891">...</a>
     */
    private static final Set<String> SUPPRESSED_EXAMPLES = Set.of(
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
            "checks/imports/importorder/Example9"
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
                    .filter(XdocsExamplesAstConsistencyTest::isModuleDirectory)
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

            builder.append(
                    """
                    Note: a mismatch reason of "line numbers differ only" usually means \
                    an example has an extra/missing blank line or shifted code relative to its \
                    reference - fix the line alignment before considering suppression.
                    """);

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
     * Tests that the number of AST-consistent examples matches the number of
     * documented properties plus one (one example per property + one baseline
     * example with no properties set). Unlike a raw file count, this groups
     * examples by actual structural AST equality, so a directory that merely
     * has the "right number" of files but where one of them is structurally
     * different will correctly fail.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testExampleCountMatchesPropertyCount() throws IOException {
        final List<String> violations = Collections.synchronizedList(new ArrayList<>());

        try (Stream<Path> pathStream = Files.walk(XDOCS_ROOT)) {
            pathStream
                .filter(Files::isDirectory)
                .filter(XdocsExamplesAstConsistencyTest::isModuleDirectory)
                .parallel()
                .forEach(dir -> processDirectory(dir, violations));
        }

        final String message = formatViolationsMessage(violations);

        assertWithMessage(message)
            .that(violations)
            .isEmpty();
    }

    /**
     * Tests that every documented property of a module is actually configured
     * by at least one of its AST-matching examples. Unlike
     * {@link #testExampleCountMatchesPropertyCount}, which only checks the count
     * of matching examples, this test checks semantic coverage: it is possible
     * for the count to be correct while one property is demonstrated twice and
     * another isn't demonstrated at all. This test catches that gap.
     *
     * <p>Unlike the AST-comparison tests, this test does not parse example files
     * as Java. It only looks for an embedded {@code /*xml ... *}{@code /}
     * configuration block, so example files are discovered by name pattern and
     * the actual presence of that block, regardless of file extension (or lack
     * of one). This lets a check's examples span mixed file types (e.g.
     * {@code Example1.java}, {@code Example2.cpp}, {@code Example3.txt}) while
     * still being counted toward property coverage - as long as each file
     * carries a real config block. Files without one (e.g. a stray
     * {@code .properties} companion file) are silently excluded rather than
     * causing a false gap or a parse failure.
     *
     * @throws IOException if an I/O error occurs
     */
    @Test
    public void testEveryPropertyHasAnExample() throws IOException {
        final List<String> violations = Collections.synchronizedList(new ArrayList<>());

        try (Stream<Path> pathStream = Files.walk(XDOCS_ROOT)) {
            pathStream
                .filter(Files::isDirectory)
                .filter(XdocsExamplesAstConsistencyTest::isModuleDirectory)
                .parallel()
                .forEach(dir -> processDirectoryForPropertyCoverage(dir, violations));
        }

        final String message = formatPropertyCoverageViolationsMessage(violations);

        assertWithMessage(message)
            .that(violations)
            .isEmpty();
    }

    /**
     * Collects files from {@code dir} and its subdirectories, but never descends
     * into a subdirectory that is itself a resolvable module directory (per
     * {@link #isModuleDirectory}).
     *
     * <p>This is what keeps recursion properly scoped: (e.g.
     * {@code classdataabstractioncoupling/ignore/deeper}), and those should be
     * walked into. But some modules are nested as filesystem subdirectories of
     * another, unrelated module (e.g. {@code checks/regexp/regexpmultiline} is
     * its own module living under the {@code checks/regexp} module's directory);
     * such subdirectories must be treated as separate module roots, not folded
     * into the parent's examples.
     *
     * @param dir the directory to search
     * @param fileFilter predicate selecting which regular files to collect
     * @return the collected files, in no particular order
     * @throws IOException if an I/O error occurs
     */
    private static List<Path> collectFilesWithinModule(Path dir,
                   Predicate<Path> fileFilter) throws IOException {
        final List<Path> result = new ArrayList<>();

        try (Stream<Path> pathStream = Files.list(dir)) {
            for (Path entry : pathStream.toList()) {
                if (Files.isDirectory(entry)) {
                    if (!isModuleDirectory(entry)) {
                        result.addAll(collectFilesWithinModule(entry, fileFilter));
                    }
                }
                else if (fileFilter.test(entry)) {
                    result.add(entry);
                }
            }
        }

        return result;
    }

    /**
     * Checks whether the given directory resolves to an actual checkstyle module
     * (i.e. its name maps to a check/filter class via {@link #toModuleClassSimpleName}).
     *
     * <p>Used to keep example discovery scoped correctly: a module directory's
     * own subdirectories (e.g. {@code classdataabstractioncoupling/ignore},
     * {@code classdataabstractioncoupling/ignore/deeper}) do not themselves
     * resolve to a module, so they are excluded from being processed as
     * separate, independent modules - while {@link #getExampleFiles} and
     * {@link #getExamplePropertyCoverageFiles} still walk into them when
     * collecting examples that belong to the enclosing module.
     *
     * @param dir the directory to check
     * @return true if the directory name resolves to a known module
     */
    private static boolean isModuleDirectory(Path dir) {
        return toModuleClassSimpleName(dir.getFileName().toString()) != null;
    }

    private static void processDirectoryForPropertyCoverage(Path dir, List<String> violations) {
        try {
            final List<Path> examples = new ArrayList<>(getExamplePropertyCoverageFiles(dir));
            examples.addAll(getNonCompilableExamplePropertyCoverageFiles(dir));

            if (examples.size() > 1) {
                final String violation = checkPropertyCoverage(dir, examples);
                if (violation != null) {
                    violations.add(violation);
                }
            }
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed processing directory: " + dir, exception);
        }
    }

    /**
     * Gets Example* files with an embedded XML config block from the
     * non-compilable sibling directory of the given compilable xdocs directory,
     * if one exists. Mirrors {@link #getExamplePropertyCoverageFiles} but reads
     * from {@link #XDOCS_NONCOMPILABLE_ROOT} instead.
     *
     * @param dir the compilable xdocs directory
     * @return list of example file paths from the non-compilable sibling directory,
     *         or an empty list if no such directory exists
     * @throws IOException if an I/O error occurs
     */
    private static List<Path> getNonCompilableExamplePropertyCoverageFiles(Path dir)
            throws IOException {
        final String relativePath = getRelativePath(dir);
        final Path nonCompilableDir = XDOCS_NONCOMPILABLE_ROOT.resolve(relativePath);

        List<Path> examples = List.of();
        if (Files.isDirectory(nonCompilableDir)) {
            examples = getExamplePropertyCoverageFiles(nonCompilableDir);
        }
        return examples;
    }

    /**
     * Formats property-coverage violations into a single, readable error message.
     *
     * @param violations the list of violation strings
     * @return a formatted string detailing all found gaps
     */
    private static String formatPropertyCoverageViolationsMessage(List<String> violations) {
        final StringBuilder builder = new StringBuilder(1024);
        if (!violations.isEmpty()) {
            builder.append("Found ").append(violations.size())
                .append(" module(s) with a documented property not covered by any example.\n\n");

            violations.stream()
                .sorted()
                .forEach(violation -> builder.append(violation).append("\n\n"));

            builder.append("If intentional add to EXAMPLE_PROPERTY_COVERAGE_SUPPRESSED_MODULES.\n");
        }
        return builder.toString();
    }

    /**
     * Checks a single module directory: unions the properties configured across
     * all of its examples (each of which is guaranteed by
     * {@link #getExamplePropertyCoverageFiles} to carry an embedded XML config
     * block) and compares that against the full set of documented properties
     * for the module, reporting any that aren't covered.
     *
     * @param dir the directory to check
     * @param examples the list of pre-fetched example files, each known to
     *                 contain an embedded XML config block
     * @return a violation message, or null if every property is covered / not applicable
     * @throws IOException if an I/O error occurs
     */
    private static String checkPropertyCoverage(Path dir, List<Path> examples)
            throws IOException {
        String result = null;

        if (!isModuleWithNoProperties(examples)) {

            final String moduleName = toModuleClassSimpleName(dir.getFileName().toString());

            if (moduleName != null) {
                final Set<String> documentedProperties = resolveDocumentedPropertyNames(dir);

                if (!documentedProperties.isEmpty()) {
                    final String xmlModuleName = stripCheckSuffix(moduleName);

                    final Set<String> configuredProperties = new HashSet<>();
                    for (Path example : examples) {
                        configuredProperties.addAll(
                                extractConfiguredPropertyNames(example, xmlModuleName));
                    }

                    final Set<String> uncoveredProperties = new HashSet<>(documentedProperties);
                    uncoveredProperties.removeAll(configuredProperties);
                    uncoveredProperties.removeAll(IGNORED_PROPERTIES_FOR_COVERAGE);

                    if (!uncoveredProperties.isEmpty()) {
                        final String relativePath = getRelativePath(dir);
                        result = "Directory: " + relativePath
                                + "\nDocumented properties: " + documentedProperties
                                + "\nProperties with no covering example: " + uncoveredProperties;
                    }
                }
            }
        }

        return result;
    }

    /**
     * Resolves the full set of documented property names for the module associated
     * with the given xdocs directory.
     *
     * @param dir the example directory
     * @return the set of documented property names, or an empty set if the module
     *         could not be resolved
     */
    private static Set<String> resolveDocumentedPropertyNames(Path dir) {
        final String moduleName = toModuleClassSimpleName(dir.getFileName().toString());
        Set<String> result = Set.of();

        if (moduleName != null) {
            try {
                final Object instance = SiteUtil.getModuleInstance(moduleName);
                result = SiteUtil.getPropertiesForDocumentation(instance.getClass(), instance);
            }
            catch (MacroExecutionException exception) {
                // Failure to resolve is expected for some non-check modules
            }
        }

        return result;
    }

    /**
     * Extracts the set of property names actually configured for the given module
     * within an example's embedded {@code /*xml ... *}{@code /} config block.
     *
     * <p>Uses a plain, non-validating DOM parse rather than
     * {@code ConfigurationLoader}, since the latter is designed to fully execute
     * a configuration (DTD validation, {@code ${...}} property substitution such
     * as {@code config.folder}) which several example configs intentionally use
     * and which isn't relevant here — this method only needs the property
     * <em>names</em>, not a runnable configuration.
     *
     * @param example the example file
     * @param moduleName the module's simple name as it appears in the embedded XML
     * @return the set of property names configured for that module in this example,
     *         or an empty set if the module doesn't appear in the example's config
     * @throws IOException if reading the file fails
     */
    private static Set<String> extractConfiguredPropertyNames(Path example, String moduleName)
            throws IOException {
        Set<String> result = Set.of();
        final String xmlBlock = extractXmlConfigBlock(example);

        if (xmlBlock != null) {
            try {
                final Element moduleElement = parseConfigModuleElement(xmlBlock, moduleName);
                if (moduleElement != null) {
                    result = collectPropertyNames(moduleElement);
                }
            }
            catch (ParserConfigurationException | SAXException exception) {
                throw new IllegalStateException(
                    "Failed to parse example config XML: " + example, exception);
            }
        }

        return result;
    }

    /**
     * Parses the given XML config fragment (non-validating, no external DTD or
     * entity resolution) and finds the {@code <module>} element with the given
     * simple name anywhere in the tree.
     *
     * @param xmlBlock the raw XML content, rooted at {@code <module name="Checker">}
     * @param moduleName the module simple name to find
     * @return the matching module {@link Element}, or null if not found
     * @throws ParserConfigurationException if a document builder cannot be created
     * @throws IOException if an I/O error occurs during parsing
     * @throws SAXException if the XML content is malformed
     */
    private static Element parseConfigModuleElement(String xmlBlock, String moduleName)
            throws ParserConfigurationException, IOException, SAXException {
        final DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(false);
        factory.setFeature(
            "http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
        factory.setFeature(
            "http://xml.org/sax/features/external-general-entities", false);
        factory.setFeature(
            "http://xml.org/sax/features/external-parameter-entities", false);

        final DocumentBuilder builder = factory.newDocumentBuilder();
        final Document document = builder.parse(
            new ByteArrayInputStream(xmlBlock.getBytes(StandardCharsets.UTF_8)));

        return findModuleElement(document.getDocumentElement(), moduleName);
    }

    /**
     * Recursively searches an XML {@link Element} tree for a {@code <module>}
     * element with the given {@code name} attribute.
     *
     * @param element the element to search from
     * @param moduleName the module simple name to find
     * @return the matching element, or null if not found
     */
    private static Element findModuleElement(Element element, String moduleName) {
        Element result = null;

        if (moduleName.equals(element.getAttribute("name"))) {
            result = element;
        }
        else {
            final NodeList children = element.getChildNodes();
            for (int index = 0; result == null && index < children.getLength(); index++) {
                final Node node = children.item(index);
                if (node instanceof Element childElement
                    && "module".equals(node.getNodeName())) {
                    result = findModuleElement(childElement, moduleName);
                }
            }
        }

        return result;
    }

    /**
     * Collects the {@code name} attribute of every direct {@code <property>}
     * child of the given module element.
     *
     * @param moduleElement the module element to read properties from
     * @return the set of configured property names
     */
    private static Set<String> collectPropertyNames(Element moduleElement) {
        final Set<String> names = new HashSet<>();
        final NodeList children = moduleElement.getChildNodes();

        for (int index = 0; index < children.getLength(); index++) {
            final Node node = children.item(index);
            if (node instanceof Element childElement
                && "property".equals(node.getNodeName())) {
                names.add(childElement.getAttribute("name"));
            }
        }

        return names;
    }

    /**
     * Extracts the embedded {@code /*xml ... *}{@code /} configuration block from the
     * top of an example file, if present.
     *
     * @param file the example file to read
     * @return the XML content between the {@code /*xml} and closing {@code *}{@code /}
     *         markers, or null if no such block is present
     * @throws IOException if an I/O error occurs
     */
    private static String extractXmlConfigBlock(Path file) throws IOException {
        final String content = Files.readString(file);
        String result = null;

        final int startMarker = content.indexOf("/*xml");
        if (startMarker >= 0) {
            final int contentStart = startMarker + "/*xml".length();
            final int endMarker = content.indexOf("*/", contentStart);
            if (endMarker >= 0) {
                result = content.substring(contentStart, endMarker).strip();
            }
        }

        return result;
    }

    /**
     * Processes a directory to identify example-count-vs-property-count violations.
     *
     * @param dir the directory containing example files
     * @param violations a thread-safe list to collect any discovered violations
     */
    private static void processDirectory(Path dir, List<String> violations) {
        try {
            final List<Path> examples = getExampleFiles(dir);
            if (examples.size() > 1) {
                final String violation = checkExampleCount(dir, examples);
                if (violation != null) {
                    violations.add(violation);
                }
            }
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed processing directory: " + dir, exception);
        }
    }

    /**
     * Formats the collection of violations into a single, readable error message.
     *
     * @param violations the list of violation strings
     * @return a formatted string detailing all found inconsistencies
     */
    private static String formatViolationsMessage(List<String> violations) {
        final StringBuilder builder = new StringBuilder(1024);
        if (!violations.isEmpty()) {
            builder.append("Found ").append(violations.size())
                .append(" module(s) whose example count does not match property count + 1.\n\n");

            violations.stream()
                .sorted()
                .forEach(violation -> builder.append(violation).append("\n\n"));

            builder.append("If intentional, add to EXAMPLE_COUNT_SUPPRESSED_MODULES.\n");
        }
        return builder.toString();
    }

    /**
     * Checks a single module directory: resolves the check class, counts its
     * documented properties, and compares that against the size of the *actual*
     * AST-matching example group (not just the raw file count).
     *
     * @param dir the directory to check
     * @param examples the list of pre-fetched example files
     * @return a violation message, or null if consistent / not applicable
     * @throws IOException if an I/O error occurs
     */
    private static String checkExampleCount(Path dir, List<Path> examples) throws IOException {
        final String relativePath = getRelativePath(dir);
        String result = null;

        if (!EXAMPLE_COUNT_SUPPRESSED_MODULES.contains(relativePath)
            && !isModuleWithNoProperties(examples)) {

            final List<Path> regularExamples = examples.stream()
                .filter(example -> {
                    return !isExampleIndependent(
                        relativePath, example.getFileName().toString());
                })
                .filter(example -> {
                    return !isExampleUnparseable(
                        relativePath, example.getFileName().toString());
                })
                .toList();

            final int propertyCount = resolvePropertyCount(dir);

            if (propertyCount >= 0 && regularExamples.size() > 1) {
                final int largestAstGroupSize = findLargestAstMatchingGroupSize(regularExamples);
                final int expected = propertyCount + 1;

                // only flag when there are too few matching examples.
                if (largestAstGroupSize < expected) {
                    result = "Directory: " + relativePath
                        + "\nProperties: " + propertyCount
                        + "\nExpected AST-matching examples (at least): " + expected
                        + "\nActual largest AST-matching group: " + largestAstGroupSize
                        + " (of " + regularExamples.size() + " total example files)";
                }
            }
        }
        return result;
    }

    /**
     * Groups examples by structural AST equality and returns the size of the
     * largest group found. Constructor-presence is used to pre-split the
     * examples, mirroring {@link #validateExamplesByConstructorPresence}, since
     * that split represents an intentionally separate AST family, not a mismatch.
     * Examples that fail to parse are skipped from grouping (they are validated
     * elsewhere by the unparseable-examples allowance).
     *
     * @param examples candidate example files (already filtered for suppression)
     * @return size of the largest AST-identical group, or 0 if none parse
     * @throws IOException if reading a file fails
     */
    private static int findLargestAstMatchingGroupSize(List<Path> examples) throws IOException {
        final List<Path> ctorExamples = new ArrayList<>();
        final List<Path> nonCtorExamples = new ArrayList<>();

        for (Path example : examples) {
            if (containsConstructorDefinition(example)) {
                ctorExamples.add(example);
            }
            else {
                nonCtorExamples.add(example);
            }
        }

        return Math.max(
            largestGroupWithinSubset(nonCtorExamples),
            largestGroupWithinSubset(ctorExamples)
        );
    }

    /**
     * Finds the size of the largest group of structurally-identical ASTs within
     * a single subset of examples (already split by constructor presence).
     *
     * @param examples the subset of examples to group
     * @return size of the largest AST-identical group, or 0 if none parse
     * @throws IOException if reading a file fails
     */
    private static int largestGroupWithinSubset(List<Path> examples) throws IOException {
        final List<StructuralAstNode> asts = new ArrayList<>();

        for (Path example : examples) {
            try {
                final String xdocSection = extractXdocSection(example);
                final DetailAST detailAst = parseContent(xdocSection);
                if (detailAst != null) {
                    asts.add(toStructuralAst(detailAst));
                }
            }
            catch (CheckstyleException exception) {
                // unparseable excluded from grouping, handled by UNPARSEABLE_EXAMPLES elsewhere
            }
        }

        int best = 0;
        for (StructuralAstNode candidate : asts) {
            int count = 0;
            for (StructuralAstNode other : asts) {
                if (candidate.equals(other)) {
                    count++;
                }
            }
            best = Math.max(best, count);
        }
        return best;
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
        boolean noProperties = true;

        for (Path example : examples) {
            final String content = Files.readString(example);

            if (content.contains("<property ")) {
                noProperties = false;
                break;
            }
        }

        return noProperties;
    }

    /**
     * Retrieves the documented property count for a module using a cache to optimize performance.
     *
     * @param dir the directory path associated with the module
     * @return the number of properties, or -1 if the module cannot be resolved
     */
    private static int resolvePropertyCount(Path dir) {
        final String moduleName = toModuleClassSimpleName(dir.getFileName().toString());
        int result = -1;

        if (moduleName != null) {
            result = PROPERTY_COUNT_CACHE.computeIfAbsent(moduleName,
                XdocsExamplesAstConsistencyTest::loadPropertyCount);
        }

        return result;
    }

    /**
     * Helper method to load property count via reflection.
     *
     * @param moduleName the simple class name of the check
     * @return the property count, or -1 on failure
     */
    private static int loadPropertyCount(String moduleName) {
        int count = -1;
        try {
            final Object instance = SiteUtil.getModuleInstance(moduleName);
            final Set<String> properties = new HashSet<>(SiteUtil.getPropertiesForDocumentation(
                    instance.getClass(), instance));
            properties.removeAll(IGNORED_PROPERTIES_FOR_COVERAGE);
            count = properties.size();
        }
        catch (MacroExecutionException exception) {
            // Failure to resolve is expected for some non-check modules
        }
        return count;
    }

    /**
     * Converts a lower-cased directory name (e.g. {@code declarationorder}) into
     * the check's simple class name (e.g. {@code DeclarationOrderCheck}) as expected by
     * {@link SiteUtil#getModuleInstance}, using an index built once from all known
     * checkstyle module classes.
     *
     * @param dirName the last path segment of the example directory
     * @return the resolved module simple name, or null if no matching module class was found
     */
    private static String toModuleClassSimpleName(String dirName) {
        return MODULE_SIMPLE_NAME_CACHE.get(dirName.toLowerCase(Locale.ROOT));
    }

    /**
     * Builds a one-time index mapping lower-cased simple class name stem
     * (i.e. class simple name with any trailing {@code Check} removed, lower-cased)
     * to the actual module simple name expected by {@link SiteUtil#getModuleInstance}.
     * Built once to avoid repeating an expensive classpath scan per directory.
     *
     * @return the populated index
     */
    private static ConcurrentMap<String, String> buildModuleSimpleNameIndex() {
        final ConcurrentMap<String, String> index = new ConcurrentHashMap<>();

        try {
            for (Class<?> moduleClass : CheckUtil.getCheckstyleModules()) {
                final String simpleName = moduleClass.getSimpleName();
                final String stem = stripCheckSuffix(simpleName);
                index.putIfAbsent(stem.toLowerCase(Locale.ROOT), simpleName);
            }
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed to build module simple name index",
                exception);
        }

        return index;
    }

    /**
     * Removes a trailing {@code Check} suffix from a class simple name, if present.
     *
     * @param simpleName the class simple name
     * @return the name with any trailing {@code Check} removed
     */
    private static String stripCheckSuffix(String simpleName) {
        String result = simpleName;
        if (simpleName.endsWith("Check")) {
            result = simpleName.substring(0, simpleName.length() - "Check".length());
        }
        return result;
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
        final Matcher matcher = BLOCK_COMMENT_PATTERN.matcher(content);

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
     * Checks if a directory contains multiple example files, including any
     * contained in its own non-module subdirectories (see
     * {@link #collectFilesWithinModule}).
     *
     * @param dir the directory to check
     * @return true if the directory (recursively, stopping at nested module
     *         boundaries) contains 2 or more Example*.java files
     */
    private static boolean containsMultipleExamples(Path dir) {
        try {
            return collectFilesWithinModule(dir,
                    path -> path.getFileName().toString().matches("Example\\d+\\.java"))
                    .size() > 1;
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
    private static List<Violation> checkExamplesInDirectory(Path dir) throws IOException {
        final List<Violation> violations = new ArrayList<>();
        final List<Path> examples = getExampleFiles(dir);

        if (!examples.isEmpty()) {
            violations.addAll(compareExamples(dir, examples));
        }

        return violations;
    }

    /**
     * Gets all Example*.java files from a directory, including those found in
     * its own subdirectories (e.g. modules that group related use-cases into
     * folders such as {@code ignore} or {@code ignore/deeper}).
     *
     * <p>This walk is always rooted at a single module directory (callers only
     * ever pass directories for which {@link #isModuleDirectory} is true), and
     * via {@link #collectFilesWithinModule} it stops descending as soon as it
     * hits a subdirectory that is itself a resolvable module directory - so it
     * never crosses into sibling/nested module directories (e.g.
     * {@code checks/regexp/regexpmultiline} is excluded from
     * {@code checks/regexp}'s examples).
     *
     * @param dir the module directory to search
     * @return list of example file paths
     * @throws IOException if an I/O error occurs
     */
    private static List<Path> getExampleFiles(Path dir) throws IOException {
        final List<Path> examples = collectFilesWithinModule(dir,
                path -> path.getFileName().toString().matches("Example\\d+\\.java"));
        return examples.stream()
                .sorted(Comparator.comparing(Path::toString))
                .toList();
    }

    /**
     * Gets all Example* files from a directory (and its own subdirectories)
     * that contain an embedded {@code /*xml ... *}{@code /} configuration
     * block, regardless of file extension (or the lack of one).
     *
     * <p>Used only for property-coverage checking ({@link #testEveryPropertyHasAnExample}),
     * which inspects that embedded block via plain text/DOM extraction rather
     * than parsing the file as Java. This lets a check's examples span mixed
     * file types (e.g. {@code Example1.java}, {@code Example2.cpp},
     * {@code Example3.txt}) while still contributing to property coverage.
     * Files that match the {@code Example<N>} naming pattern but don't actually
     * carry a config block (e.g. a stray {@code .properties} companion file)
     * are excluded, since they have nothing to contribute and aren't reliably
     * identifiable by extension alone.
     *
     * <p>As with {@link #getExampleFiles}, this walk is rooted at a single
     * module directory and, via {@link #collectFilesWithinModule}, stops
     * descending at any nested module directory boundary.
     *
     * @param dir the module directory to search
     * @return list of example file paths containing an XML config block
     * @throws IOException if an I/O error occurs
     */
    private static List<Path> getExamplePropertyCoverageFiles(Path dir) throws IOException {
        final List<Path> examples = collectFilesWithinModule(dir, path -> {
            return path.getFileName().toString().matches("Example\\d+(\\..+)?")
                    && hasXmlConfigBlock(path);
        });
        return examples.stream()
                .sorted(Comparator.comparing(Path::toString))
                .toList();
    }

    /**
     * Checks whether a file contains an embedded {@code /*xml ... *}{@code /}
     * configuration block.
     *
     * @param file the file to check
     * @return true if an XML config block is present
     */
    private static boolean hasXmlConfigBlock(Path file) {
        final boolean result;
        try {
            result = extractXmlConfigBlock(file) != null;
        }
        catch (IOException exception) {
            throw new IllegalStateException("Failed to read file: " + file, exception);
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
        final List<String> lines = Files.readAllLines(file);
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
            else if (referenceAst.equalsIgnoringLineNumbers(ast)) {
                result = new Violation(relativePath, reference.getFileName().toString(),
                    example.getFileName().toString(),
                    "AST structure mismatch (line numbers differ only - "
                        + "check for added/removed blank lines or shifted code)");
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
        final boolean ignoreName = isTypeName(ast)
                || isConstructorName(ast)
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
     * Checks if an AST node is an identifier representing a type name.
     *
     * @param ast the AST node to check
     * @return true if the node is a type name identifier
     */
    private static boolean isTypeName(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        return parent != null
                && ast.getType() == TokenTypes.IDENT
                && (parent.getType() == TokenTypes.CLASS_DEF
                    || parent.getType() == TokenTypes.INTERFACE_DEF
                    || parent.getType() == TokenTypes.ENUM_DEF
                    || parent.getType() == TokenTypes.RECORD_DEF
                    || parent.getType() == TokenTypes.ANNOTATION_DEF);
    }

    /**
     * Checks if an AST node is an identifier representing a constructor name.
     *
     * @param ast the AST node to check
     * @return true if the node is a constructor name identifier
     */
    private static boolean isConstructorName(DetailAST ast) {
        final DetailAST parent = ast.getParent();
        return parent != null
                && ast.getType() == TokenTypes.IDENT
                && parent.getType() == TokenTypes.CTOR_DEF;
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
     * <p>All other comments, including javadoc comments, are included in comparison.
     * Inline {@code // violation}, {@code // ok}, and other marker comments within
     * javadoc are excluded, per {@link #isIgnoredComment}.
     *
     * @param content example content
     * @return comments participating in comparison
     */
    private static List<String> extractComments(String content) {
        final List<String> comments = new ArrayList<>();
        comments.addAll(extractJavadocComments(content));
        comments.addAll(extractSingleLineComments(content));
        return comments;
    }

    /**
     * Extracts javadoc comments, stripping any trailing inline marker comment
     * (ok/violation/etc., per {@link #isIgnoredComment}) from each line rather
     * than dropping the whole line. Javadocs that become empty after stripping
     * are excluded entirely.
     *
     * @param content example content
     * @return filtered javadoc comments, excluding any that become empty
     */
    private static List<String> extractJavadocComments(String content) {
        final List<String> javadocComments = new ArrayList<>();
        final Matcher javadocMatcher = Pattern.compile("/\\*\\*[\\s\\S]*?\\*/").matcher(content);

        while (javadocMatcher.find()) {
            final String originalJavadoc = javadocMatcher.group().strip();
            final StringBuilder filteredJavadoc = new StringBuilder(256);

            for (String line : originalJavadoc.lines().toList()) {
                filteredJavadoc.append(stripInlineMarkers(line)).append('\n');
            }

            final String filteredJavadocStr = filteredJavadoc.toString().strip();
            if (!filteredJavadocStr.isEmpty()) {
                javadocComments.add(filteredJavadocStr);
            }
        }

        return javadocComments;
    }

    /**
     * Removes a trailing inline marker comment (ok/violation/filtered violation/
     * N violations/etc., per {@link #isIgnoredComment}) from a single line,
     * keeping the code (or whitespace) that precedes it. Lines whose trailing
     * {@code //} comment is not a recognized marker are returned unchanged.
     *
     * @param line the line to strip
     * @return the line with any trailing marker comment removed
     */
    private static String stripInlineMarkers(String line) {
        String result = line;
        final int commentIndex = findCommentStart(line);

        if (commentIndex >= 0) {
            final String comment = line.substring(commentIndex + 2).strip();
            if (isIgnoredComment(comment)) {
                result = line.substring(0, commentIndex).stripTrailing();
            }
        }

        return result;
    }

    /**
     * Extracts single-line ({@code //}) comments that participate in comparison,
     * skipping ok/violation/xdoc-section markers and standalone continuation
     * lines that immediately follow such a marker.
     *
     * @param content example content
     * @return single-line comments participating in comparison
     */
    private static List<String> extractSingleLineComments(String content) {
        final List<String> comments = new ArrayList<>();
        boolean prevLineWasMarker = false;

        for (String line : content.lines().toList()) {
            final int commentIndex = findCommentStart(line);

            if (commentIndex < 0) {
                prevLineWasMarker = false;
                continue;
            }

            final String comment = line.substring(commentIndex + 2).strip();
            final boolean isCodeBefore = !line.substring(0, commentIndex).isBlank();

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

        /**
         * Compares this node against another, ignoring line-number differences.
         * Used to distinguish a genuine structural mismatch from one caused purely
         * by a shift in line numbers (e.g. an added or removed blank line), so the
         * violation message can point reviewers toward the right kind of fix.
         *
         * @param other the node to compare against
         * @return true if the two nodes (and their children) are structurally
         *         identical except possibly for line numbers
         */
        private boolean equalsIgnoringLineNumbers(StructuralAstNode other) {
            final boolean typeMatch = type == other.type;
            final boolean textMatch = Objects.equals(text, other.text);
            boolean childrenMatch = children.size() == other.children.size();
            if (childrenMatch) {
                for (int index = 0; index < children.size(); index++) {
                    if (!children.get(index)
                        .equalsIgnoringLineNumbers(other.children.get(index))) {
                        childrenMatch = false;
                        break;
                    }
                }
            }
            return typeMatch && textMatch && childrenMatch;
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
