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

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import com.puppycrawl.tools.checkstyle.AbstractPathTestSupport;
import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.TestInputViolation;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XmlUtil;

public class XdocsExampleFileTest {

    private static final Set<String> COMMON_PROPERTIES = Set.of(
        "severity",
        "id",
        "fileExtensions",
        "tabWidth",
        "fileContents",
        "tokens",
        "javadocTokens",
        "violateExecutionOnNonTightHtml"
    );

    /**
     * Modules using the external-XML-config verification path
     * (verifyWithExternalXmlConfig) instead of InlineConfigParser's inline-comment
     * config format. Their config lives in a separate external XML file rather
     * than embedded as a comment block in the resource file itself, so
     * InlineConfigParser cannot parse them at all - this is a scope limit, not a
     * pending duplicate-violation finding. Remove an entry here only once a
     * parser for that format is added to this test.
     *
     * <p>See <a href="https://github.com/checkstyle/checkstyle/issues/18809">...</a>
     * for background on why these checks use a separate config mechanism (their
     * "config" often overlaps with literal file-header content being validated).
     */
    private static final Set<String> UNSUPPORTED_CONFIG_FORMAT_MODULES = Set.of(
        "checks/header/header/",
        "checks/header/multifileregexpheader/",
        "checks/header/regexpheader/",
        "checks/imports/importcontrol/"
    );

    /**
     * Modules with confirmed or as-yet-unreviewed duplicate-behavior examples,
     * temporarily suppressed until each is reviewed and either fixed (the examples
     * are made behaviorally distinct) or confirmed intentional and documented.
     *
     * <p>Remove an entry here once its examples are fixed or the duplication is
     * confirmed acceptable and reflected in xdocs commentary. Do not add new
     * entries without reviewing the flagged examples first - see
     * <a href="https://github.com/checkstyle/checkstyle/issues/21072">...</a>
     */
    private static final Set<String> SUPPRESSED_UNIQUENESS_CHECK_MODULES = Set.of(
        "checks/coding/magicnumber/",
        "checks/design/onetoplevelclass/",
        "checks/javadoc/missingjavadocmethod/",
        "checks/naming/constantname/",
        "checks/naming/methodname/",
        "checks/naming/typename/",
        "checks/nocodeinfile/",
        "checks/outertypefilename/",
        "checks/coding/finallocalvariable/"
    );

    @Test
    public void testAllCheckPropertiesAreUsedInXdocsExamples() throws Exception {
        final Map<String, Set<String>> usedPropertiesByCheck =
            XdocUtil.extractUsedPropertiesFromXdocsExamples();
        final List<String> failures = new ArrayList<>();

        for (Class<?> checkClass : CheckUtil.getCheckstyleChecks()) {
            final String checkSimpleName = checkClass.getSimpleName();

            final Set<String> definedProperties = Arrays.stream(
                    PropertyUtils.getPropertyDescriptors(checkClass))
                .filter(descriptor -> descriptor.getWriteMethod() != null)
                .map(PropertyDescriptor::getName)
                .filter(property -> !COMMON_PROPERTIES.contains(property))
                .collect(Collectors.toUnmodifiableSet());

            final Set<String> usedProperties =
                usedPropertiesByCheck.getOrDefault(checkSimpleName, Collections.emptySet());

            for (String property : definedProperties) {
                if (!usedProperties.contains(property)) {
                    failures.add("Missing property in xdoc: '"
                            + property + "' of " + checkSimpleName);
                }
            }
        }
        if (!failures.isEmpty()) {
            assertWithMessage("Xdocs are missing properties:\n" + String.join("\n", failures))
                    .fail();
        }
    }

    @Test
    public void testAllExampleFilesHaveCorrespondingTestMethods() throws Exception {
        final Path examplesResources = Path.of("src/xdocs-examples/resources");
        final Path examplesNonCompilable = Path.of("src/xdocs-examples/resources-noncompilable");
        final Path examplesTestRoot = Path.of(
            "src/xdocs-examples/java/com/puppycrawl/tools/checkstyle/checks");
        final List<String> failures = new ArrayList<>();

        try (Stream<Path> testFiles = Files.walk(examplesTestRoot)) {
            testFiles
                .filter(path -> path.toString().endsWith("ExamplesTest.java"))
                .forEach(testFile -> {
                    try {
                        scanFile(testFile, examplesResources, examplesNonCompilable, failures);
                    }
                    catch (IOException exception) {
                        throw new IllegalStateException("Error processing: "
                                     + testFile, exception);
                    }
                });
        }
        if (!failures.isEmpty()) {
            assertWithMessage("Example files are missing corresponding test methods:\n"
                    + String.join("\n", failures))
                    .fail();
        }
    }

    @Test
    public void testAllExampleFilesAreReferencedInXdocs() throws Exception {
        final Set<String> referencedPaths = collectReferencedExamplePaths();
        final Path xdocsExamplesBase = Path.of("src/xdocs-examples");
        final List<Path> exampleRoots = List.of(
            xdocsExamplesBase.resolve("resources"),
            xdocsExamplesBase.resolve("resources-noncompilable")
        );
        final List<String> failures = new ArrayList<>();

        for (Path root : exampleRoots) {
            if (Files.exists(root)) {
                try (Stream<Path> paths = Files.walk(root)) {
                    paths
                        .filter(path -> {
                            final String fileName = path.getFileName().toString();
                            return Files.isRegularFile(path)
                                && (fileName.startsWith("Example")
                                    || fileName.startsWith("UseCase"));
                        })
                        .forEach(exampleFile -> {
                            final String relative = xdocsExamplesBase
                                .relativize(exampleFile)
                                .toString()
                                .replace(File.separatorChar, '/');
                            if (!referencedPaths.contains(relative)) {
                                failures.add(relative);
                            }
                        });
                }
            }
        }

        if (!failures.isEmpty()) {
            assertWithMessage(
                "The following example files are not referenced in any xml.template file:\n"
                    + String.join("\n", failures))
                .fail();
        }
    }

    @Test
    public void testAllModuleExamplesAreBehaviorallyUnique() throws Exception {
        final Path examplesTestRoot = Path.of(
                "src/xdocs-examples/java/com/puppycrawl/tools/checkstyle/checks");
        final Path examplesResources = Path.of("src/xdocs-examples/resources");
        final Path examplesNonCompilable = Path.of("src/xdocs-examples/resources-noncompilable");
        final List<String> failures = new ArrayList<>();

        try (Stream<Path> testFiles = Files.walk(examplesTestRoot)) {
            testFiles
                    .filter(path -> path.toString().endsWith("ExamplesTest.java"))
                    .forEach(testFile -> {
                        try {
                            checkUniquenessForModule(testFile, examplesResources,
                                    examplesNonCompilable, failures);
                        }
                        catch (IOException exception) {
                            throw new IllegalStateException("Error processing: "
                                    + testFile, exception);
                        }
                    });
        }

        if (!failures.isEmpty()) {
            assertWithMessage("Found examples with duplicate behavior:\n"
                    + String.join("\n", failures))
                    .fail();
        }
    }

    private static Set<String> collectReferencedExamplePaths() throws Exception {
        final Set<String> referenced = new HashSet<>();

        for (Path template : XdocUtil.getXdocsTemplatesFilePaths()) {
            final String input = Files.readString(template);
            final Document document = XmlUtil.getRawXml(template.toString(), input, input);
            final NodeList macros = document.getElementsByTagName("macro");

            for (int idx = 0; idx < macros.getLength(); idx++) {
                final Element macro = (Element) macros.item(idx);
                if ("example".equals(macro.getAttribute("name"))) {
                    final String path = getMacroParamValue(macro, "path");
                    if (path != null && !path.isEmpty()) {
                        referenced.add(normalizePath(path));
                    }
                }
            }
        }
        return referenced;
    }

    private static String getMacroParamValue(Element macro, String paramName) {
        String result = null;
        final NodeList params = macro.getElementsByTagName("param");

        for (int idx = 0; idx < params.getLength(); idx++) {
            final Element param = (Element) params.item(idx);
            if (paramName.equals(param.getAttribute("name"))) {
                result = param.getAttribute("value");
                break;
            }
        }
        return result;
    }

    private static String normalizePath(String path) {
        String result = path;
        if (result.startsWith("/")) {
            result = result.substring(1);
        }
        return result;
    }

    private static void scanFile(Path testFile, Path examplesResources, Path examplesNonCompilable,
            List<String> failures)
            throws IOException {
        final String testContent = Files.readString(testFile);

        final String className = Path.of("src/xdocs-examples/java").toAbsolutePath()
                .relativize(testFile.toAbsolutePath()).toString()
                .replace(File.separator, ".")
                .replaceFirst("\\.java$", "");

        try {
            final Class<?> testClass = Class.forName(className);
            final AbstractPathTestSupport instance = (AbstractPathTestSupport) testClass
                    .getDeclaredConstructor().newInstance();
            final String packageLocation = instance.getPackageLocation();

            scanExampleDirectory(examplesResources.resolve(packageLocation),
                    testContent, testFile, failures);
            scanExampleDirectory(examplesNonCompilable.resolve(packageLocation),
                    testContent, testFile, failures);
        }
        catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to instantiate " + className, exception);
        }
    }

    private static void scanExampleDirectory(Path exampleDir, String testContent,
            Path testFile, List<String> failures) throws IOException {
        if (Files.exists(exampleDir) && Files.isDirectory(exampleDir)) {
            try (Stream<Path> exampleFiles = Files.list(exampleDir)) {
                exampleFiles
                    .filter(path -> {
                        final String fileName = path.getFileName()
                                .toString();
                        return fileName.matches("Example\\d+\\.java");
                    })
                    .forEach(exampleFile -> {
                        final String fileName = exampleFile.getFileName()
                                .toString();
                        if (!testContent.contains("\"" + fileName + "\"")) {
                            failures.add("Missing test for " + fileName + " in "
                                        + testFile.getFileName());
                        }
                    });
            }
        }
    }

    private static void checkUniquenessForModule(Path testFile, Path examplesResources,
             Path examplesNonCompilable, List<String> failures) throws IOException {
        final String className = Path.of("src/xdocs-examples/java").toAbsolutePath()
                .relativize(testFile.toAbsolutePath()).toString()
                .replace(File.separator, ".")
                .replaceFirst("\\.java$", "");

        try {
            final Class<?> testClass = Class.forName(className);
            final AbstractPathTestSupport instance = (AbstractPathTestSupport) testClass
                    .getDeclaredConstructor().newInstance();
            final String packageLocation = instance.getPackageLocation();

            checkUniquenessInDirectory(examplesResources.resolve(packageLocation), failures);
            checkUniquenessInDirectory(examplesNonCompilable.resolve(packageLocation), failures);
        }
        catch (ReflectiveOperationException exception) {
            throw new IllegalStateException("Failed to instantiate " + className, exception);
        }
    }

    private static void checkUniquenessInDirectory(Path exampleDir, List<String> failures)
            throws IOException {
        if (Files.exists(exampleDir) && Files.isDirectory(exampleDir)) {
            final String normalizedDirPath = exampleDir.toString()
                    .replace(File.separatorChar, '/') + "/";

            final boolean unsupportedFormat = UNSUPPORTED_CONFIG_FORMAT_MODULES.stream()
                    .anyMatch(normalizedDirPath::endsWith);

            if (!unsupportedFormat) {
                final String moduleName = exampleDir.getFileName().toString();
                final boolean suppressed = SUPPRESSED_UNIQUENESS_CHECK_MODULES.stream()
                        .anyMatch(normalizedDirPath::endsWith);
                final Map<String, List<String>> signatureToExamples = collectSignatures(
                        exampleDir, suppressed, failures);

                reportDuplicates(moduleName, suppressed, signatureToExamples, failures);
            }
        }
    }

    private static Map<String, List<String>> collectSignatures(Path exampleDir,
               boolean suppressed, List<String> failures) throws IOException {
        final Map<String, List<String>> signatureToExamples = new HashMap<>();

        try (Stream<Path> exampleFiles = Files.list(exampleDir)) {
            final List<Path> examples = exampleFiles
                    .filter(path -> {
                        return path.getFileName().toString()
                                .matches("Example\\d+\\.java");
                    })
                    .sorted()
                    .toList();

            if (examples.size() >= 2) {
                for (Path exampleFile : examples) {
                    final String signature = buildSignature(exampleFile, suppressed, failures);
                    if (signature != null) {
                        signatureToExamples
                                .computeIfAbsent(signature, key -> new ArrayList<>())
                                .add(exampleFile.getFileName().toString());
                    }
                }
            }
        }

        return signatureToExamples;
    }

    private static void reportDuplicates(String moduleName, boolean suppressed,
             Map<String, List<String>> signatureToExamples, List<String> failures) {
        if (!suppressed) {
            signatureToExamples.forEach((signature, examples) -> {
                if (examples.size() > 1) {
                    failures.add(String.format(Locale.ROOT,
                            "Module '%s': examples %s produce identical violations (%s).",
                            moduleName, examples, signature));
                }
            });
        }
    }

    /**
     * Builds a signature from an example's expected violations: line number plus
     * message, joined per violation. Line number is included (not stripped)
     * because all examples of a check are guaranteed structurally identical by
     * AST (enforced separately by XdocsExamplesAstConsistencyTest) - only
     * comments/config differ - so a given line number means the same structural
     * position across every example of that module, making it a meaningful part
     * of the comparison rather than noise.
     *
     * <p>Returns null if any violation message in the file is unspecified, or if
     * the file has zero violations, since neither case is a reliable duplicate
     * signal - see InlineConfigParser.SUPPRESSED_VALIDATE_MESSAGE_FILES /
     * SUPPRESSED_CHECKS.
     *
     * @param suppressed whether this module is in SUPPRESSED_UNIQUENESS_CHECK_MODULES;
     *     when true, parse failures are silently skipped instead of recorded, so
     *     unrelated pre-existing parsing issues don't block review of the
     *     duplicate-violation finding itself.
     */
    private static String buildSignature(Path exampleFile, boolean suppressed,
             List<String> failures) {
        String signature;
        try {
            final TestInputConfiguration parsed =
                    InlineConfigParser.parse(exampleFile.toString());
            final List<TestInputViolation> violations = parsed.getViolations();

            final boolean hasUnspecifiedMessage = violations.stream()
                    .anyMatch(violation -> violation.message() == null);

            if (hasUnspecifiedMessage || violations.isEmpty()) {
                signature = null;
            }
            else {
                signature = violations.stream()
                        .sorted()
                        .map(violation -> violation.lineNo() + ":" + violation.message())
                        .collect(Collectors.joining("|"));
            }
        }
        // -@cs[IllegalCatch] InlineConfigParser.parse declares "throws Exception";
        catch (Exception exception) {
            if (!suppressed) {
                failures.add("Failed to parse " + exampleFile + ": " + exception.getMessage());
            }
            signature = null;
        }
        return signature;
    }

}
