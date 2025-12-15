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

import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;
import com.puppycrawl.tools.checkstyle.internal.utils.XdocUtil;

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

    // This list is temporarily suppressed.
    // Until: https://github.com/checkstyle/checkstyle/issues/17449
    private static final Map<String, Set<String>> SUPPRESSED_PROPERTIES_BY_CHECK = Map.ofEntries(
            Map.entry("MissingJavadocTypeCheck", Set.of("skipAnnotations")),
            Map.entry("JavadocStyleCheck", Set.of("endOfSentenceFormat", "checkEmptyJavadoc")),
            Map.entry("ConstantNameCheck", Set.of("applyToPackage", "applyToPrivate")),
            Map.entry("WhitespaceAroundCheck", Set.of("allowEmptySwitchBlockStatements")),
            Map.entry("FinalLocalVariableCheck", Set.of("validateUnnamedVariables")),
            Map.entry("SuppressWarningsHolder", Set.of("aliasList")),
            Map.entry("IllegalTokenTextCheck", Set.of("message")),
            Map.entry("IndentationCheck", Set.of(
                    "basicOffset",
                    "lineWrappingIndentation",
                    "throwsIndent",
                    "arrayInitIndent",
                    "braceAdjustment"
            )),
            Map.entry("MethodCountCheck", Set.of("maxPrivate", "maxPackage", "maxProtected")),
            Map.entry("ClassMemberImpliedModifierCheck", Set.of(
                    "violateImpliedStaticOnNestedEnum",
                    "violateImpliedStaticOnNestedRecord",
                    "violateImpliedStaticOnNestedInterface"
            )),
            Map.entry("DescendantTokenCheck", Set.of("minimumMessage")),
            Map.entry("InterfaceMemberImpliedModifierCheck", Set.of(
                    "violateImpliedFinalField",
                    "violateImpliedPublicField",
                    "violateImpliedStaticField",
                    "violateImpliedPublicMethod",
                    "violateImpliedAbstractMethod"
            ))
    );

    private static final Pattern TEST_PACKAGE_LOCATION_PATTERN =
        Pattern.compile("getPackageLocation\\(\\)\\s*\\{\\s*return\\s*\"([^\"]+)\"");
    private static final String EXAMPLE_FILE_PATTERN = "Example\\d+\\.java";

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

            final Set<String> suppressedProps =
                SUPPRESSED_PROPERTIES_BY_CHECK.getOrDefault(
                    checkSimpleName, Collections.emptySet());

            for (String property : definedProperties) {
                if (!usedProperties.contains(property)
                        && !suppressedProps.contains(property)) {
                    failures.add("Missing property in xdoc: '"
                            + property + "' of " + checkSimpleName);
                }
            }
        }
        if (!failures.isEmpty()) {
            assertWithMessage("Xdocs are missing properties:\n %s", String.join("\n", failures))
                    .fail();
        }
    }

    @Test
    public void verifyAllExampleFilesAreTested() throws IOException {
        final Path exampleResourcesRoot = Path.of("src/xdocs-examples/resources");
        final Path exampleTestsRoot = Path.of(
            "src/xdocs-examples/java/com/puppycrawl/tools/checkstyle/checks");

        final List<String> untestedExamples = new ArrayList<>();

        processAllTestFiles(exampleTestsRoot, exampleResourcesRoot, untestedExamples);

        if (!untestedExamples.isEmpty()) {
            assertWithMessage("Example files without corresponding test methods:\n%s",
                    String.join("\n", untestedExamples))
                .fail();
        }
    }

    private static void processAllTestFiles(
            Path testsDirectory,
            Path resourcesRoot,
            List<String> untestedExamples) throws IOException {

        try (Stream<Path> testFilesStream = Files.walk(testsDirectory)) {
            testFilesStream
                .filter(Files::isRegularFile)
                .filter(path -> path.getFileName().toString().endsWith("ExamplesTest.java"))
                .forEach(testFile ->
                    findUntestedExamplesInTestFile(testFile, resourcesRoot, untestedExamples)
                );
        }
    }

    private static void findUntestedExamplesInTestFile(
            Path testFilePath,
            Path resourcesRoot,
            List<String> untestedExamples) {

        try {
            final String testFileContent = Files.readString(testFilePath);
            final Matcher matcher = TEST_PACKAGE_LOCATION_PATTERN.matcher(testFileContent);

            if (matcher.find()) {
                final String exampleDirectoryPath = matcher.group(1);
                final Path exampleDirectory = resourcesRoot.resolve(exampleDirectoryPath);

                if (Files.isDirectory(exampleDirectory)) {
                    checkExampleCoverage(testFilePath, testFileContent,
                                       exampleDirectory, untestedExamples);
                }
            }
        } catch (IOException exception) {
            throw new IllegalStateException(
                "Failed to process test file: " + testFilePath, exception);
        }
    }

    private static void checkExampleCoverage(
            Path testFilePath,
            String testFileContent,
            Path exampleDirectory,
            List<String> untestedExamples) throws IOException {

        try (Stream<Path> exampleFiles = Files.list(exampleDirectory)) {
            exampleFiles
                .filter(Files::isRegularFile)
                .filter(XdocsExampleFileTest::isValidExampleFile)
                .filter(exampleFile -> !isExampleReferencedInTest(exampleFile, testFileContent))
                .forEach(exampleFile ->
                    untestedExamples.add(formatMissingTestMessage(exampleFile, testFilePath))
                );
        }
    }

    private static boolean isValidExampleFile(Path file) {
        return file.getFileName().toString().matches(EXAMPLE_FILE_PATTERN);
    }

    private static boolean isExampleReferencedInTest(Path exampleFile, String testFileContent) {
        final String fileName = exampleFile.getFileName().toString();
        return testFileContent.contains("\"" + fileName + "\"");
    }

    private static String formatMissingTestMessage(Path exampleFile, Path testFilePath) {
        return String.format("Missing test for '%s' in %s",
            exampleFile.getFileName(), testFilePath.getFileName());
    }
}
