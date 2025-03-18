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

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMethodCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocStyleCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocTypeCheck;
import com.puppycrawl.tools.checkstyle.checks.javadoc.WriteTagCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class XpathRegressionTest extends AbstractModuleTestSupport {

    // Checks that not compatible with SuppressionXpathFilter
    public static final Set<String> INCOMPATIBLE_CHECK_NAMES = Set.of(
            "NoCodeInFile (reason is that AST is not generated for a file not containing code)",
            "Regexp (reason is at  #7759)",
            "RegexpSinglelineJava (reason is at  #7759)"
    );

    // Javadoc checks are not compatible with SuppressionXpathFilter
    // till https://github.com/checkstyle/checkstyle/issues/5770
    // then all of them should be added to #INCOMPATIBLE_CHECK_NAMES
    // and this field should be removed
    public static final Set<String> INCOMPATIBLE_JAVADOC_CHECK_NAMES = Set.of(
                    "AtclauseOrder",
                    "JavadocBlockTagLocation",
                    "JavadocMethod",
                    "JavadocMissingLeadingAsterisk",
                    "JavadocLeadingAsteriskAlign",
                    "JavadocMissingWhitespaceAfterAsterisk",
                    "JavadocParagraph",
                    "JavadocStyle",
                    "JavadocTagContinuationIndentation",
                    "JavadocType",
                    "MissingDeprecated",
                    "NonEmptyAtclauseDescription",
                    "RequireEmptyLineBeforeBlockTagGroup",
                    "SingleLineJavadoc",
                    "SummaryJavadoc",
                    "WriteTag"
    );

    // Older regex-based checks that are under INCOMPATIBLE_JAVADOC_CHECK_NAMES
    // but not subclasses of AbstractJavadocCheck.
    private static final Set<Class<?>> REGEXP_JAVADOC_CHECKS = Set.of(
                    JavadocStyleCheck.class,
                    JavadocMethodCheck.class,
                    JavadocTypeCheck.class,
                    WriteTagCheck.class
    );

    // Modules that will never have xpath support ever because they not report violations
    private static final Set<String> NO_VIOLATION_MODULES = Set.of(
            "SuppressWarningsHolder"
    );

    private static final Set<String> SIMPLE_CHECK_NAMES = getSimpleCheckNames();

    /**
     * Directory containing the corresponding test file.
     */
    private static final Map<String, String> DIR_AND_TEST = getAllowedDirectoryAndChecks();
    private static final Set<String> INTERNAL_MODULES = getInternalModules();
    private static final String JAVA = ".java";

    private Path javaDir;
    private Path inputDir;

    private static Set<String> getSimpleCheckNames() {
        try {
            return CheckUtil.getSimpleNames(CheckUtil.getCheckstyleChecks());
        }
        catch (IOException ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    private static Map<String, String> getAllowedDirectoryAndChecks() {
        return SIMPLE_CHECK_NAMES
            .stream()
            .collect(Collectors.toUnmodifiableMap(
                id -> id.toLowerCase(Locale.ENGLISH), Function.identity()));
    }

    private static Set<String> getInternalModules() {
        return Definitions.INTERNAL_MODULES.stream()
            .map(moduleName -> {
                final String[] packageTokens = moduleName.split("\\.");
                return packageTokens[packageTokens.length - 1];
            })
            .collect(Collectors.toUnmodifiableSet());
    }

    @BeforeEach
    public void setUp() throws Exception {
        javaDir = Path.of("src/it/java/" + getPackageLocation());
        inputDir = Path.of(getPath(""));
    }

    @Override
    protected String getPackageLocation() {
        return "org/checkstyle/suppressionxpathfilter";
    }

    @Override
    protected String getResourceLocation() {
        return "it";
    }

    @Test
    public void validateIncompatibleJavadocCheckNames() throws IOException {
        // subclasses of AbstractJavadocCheck
        final Set<Class<?>> abstractJavadocCheckNames = CheckUtil.getCheckstyleChecks()
                .stream()
                .filter(AbstractJavadocCheck.class::isAssignableFrom)
                .collect(Collectors.toCollection(HashSet::new));
        // add the extra checks
        abstractJavadocCheckNames.addAll(REGEXP_JAVADOC_CHECKS);
        final Set<String> abstractJavadocCheckSimpleNames =
                CheckUtil.getSimpleNames(abstractJavadocCheckNames);
        abstractJavadocCheckSimpleNames.removeAll(INTERNAL_MODULES);
        assertWithMessage("INCOMPATIBLE_JAVADOC_CHECK_NAMES should contains all descendants "
                    + "of AbstractJavadocCheck")
            .that(abstractJavadocCheckSimpleNames)
            .isEqualTo(INCOMPATIBLE_JAVADOC_CHECK_NAMES);
    }

    @Test
    public void validateIntegrationTestClassNames() throws Exception {
        // Ensure that all lists are up-to-date
        final Set<String> allChecks = new HashSet<>(SIMPLE_CHECK_NAMES);
        allChecks.removeAll(INCOMPATIBLE_JAVADOC_CHECK_NAMES);
        allChecks.removeAll(INCOMPATIBLE_CHECK_NAMES);
        allChecks.removeAll(Set.of("Regexp", "RegexpSinglelineJava", "NoCodeInFile"));
        allChecks.removeAll(NO_VIOLATION_MODULES);
        allChecks.removeAll(compatibleChecks(Pattern.compile("^XpathRegression(.+)Test\\.java$")));
        allChecks.removeAll(INTERNAL_MODULES);

        assertWithMessage("XpathRegressionTest is missing for [" + String.join(", ", allChecks)
                + "]. Please add them to src/it/java/org/checkstyle/suppressionxpathfilter")
                .that(allChecks)
                .isEmpty();
    }

    private Set<String> compatibleChecks(Pattern pattern) throws IOException {
        try (Stream<Path> javaPaths = Files.list(javaDir)) {
            return javaPaths
                    // Ensure it's a regular file
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    // Skip Abstract classes
                    .filter(filename -> !filename.startsWith("Abstract"))
                    .flatMap(filename -> Stream.of(pattern.matcher(filename).group(1)))
                    .peek(test -> {
                        if (!SIMPLE_CHECK_NAMES.contains(test)) {
                            throw new IllegalStateException("Unknown test '"
                                    + test + "' in test file: " + test);
                        }
                        if (INCOMPATIBLE_CHECK_NAMES.contains(test)) {
                            throw new IllegalStateException("Test '"
                                    + test + "' is not compatible with SuppressionXpathFilter.");
                        }
                    })
                    .collect(Collectors.toSet());
        }
    }

    /**
     * Validates the input directory by iterating through all directories within the input directory
     * and validating each one against its corresponding test case.
     *
     * @throws Exception If an I/O error occurs or if validation fails.
     */
    @Test
    public void validateInputDir() throws Exception {
        try (DirectoryStream<Path> dirs = Files.newDirectoryStream(inputDir, dirFilter())) {
            for (Path dir : dirs) {
                assertWithMessage("Invalid directory name: " + dir.getFileName())
                        .that(DIR_AND_TEST)
                        .containsKey(dir.getFileName().toString());
                assertTestDirectoryIsConnectedToTestCase(
                        dir,
                        javaDir.resolve("XpathRegression"
                                + DIR_AND_TEST.get(dir.getFileName().toString()) + "Test" + JAVA)
                );
                try (DirectoryStream<Path> files = Files.newDirectoryStream(dir)) {
                    for (Path file : files) {
                        validateFilesIfJava(
                                file,
                                Pattern.compile("^InputXpath(.+)\\" + JAVA + "$")
                        );
                    }
                }
            }
        }
    }

    /**
     * Validates an input file if it has a ".java" extension.
     * Ensures the file name matches the expected pattern and contains the specified test name.
     *
     * @param file     The file to validate.
     * @param pattern  The expected regex pattern that the file name should match.
     * @throws AssertionError If the test file does not exist, or does not match the pattern.
     */
    private static void validateFilesIfJava(Path file, Pattern pattern) {
        if (file.endsWith(JAVA)) {
            assertInputFileMatchesPatternAndContainsCheck(
                    file,
                    pattern,
                    DIR_AND_TEST.get(file.getFileName().toString()),
                    pattern.matcher(file.getFileName().toString())
            );
        }
    }

    /**
     * Asserts that the input file matches the expected pattern and that the test name is included.
     *
     * @param path    The path to the input file being validated.
     * @param pattern The expected regex pattern that the file should match.
     * @param test    The name of the test that should be included in the file.
     * @param matcher The matcher used to validate the file against the pattern.
     * @throws AssertionError If the test file does not exist, or does not match the pattern.
     */
    private static void assertInputFileMatchesPatternAndContainsCheck(
            Path path, Pattern pattern, String test, Matcher matcher) {
        assertWithMessage("Invalid input file '" + path + "', expected pattern: " + pattern)
                .that(matcher.matches())
                .isTrue();
        assertWithMessage("Test name '" + test + "' should be included in input file: " + path)
                .that(matcher.group(1))
                .startsWith(test);
    }

    /**
     * Asserts that the given directory is connected to an existing Java test case file.
     *
     * @param dir  The directory that should be connected to a Java test case.
     * @param test The path to the Java test case file that should exist.
     * @throws AssertionError If the test file does not exist.
     */
    private static void assertTestDirectoryIsConnectedToTestCase(Path dir, Path test) {
        assertWithMessage("Input dir '" + dir + "' is not connected to Java test case: " + test)
                .that(Files.exists(test))
                .isTrue();
    }

    /**
     * Creates a filter to ensure only directories are processed.
     *
     * @return A {@link DirectoryStream.Filter} that accepts only directories.
     */
    private static DirectoryStream.Filter<Path> dirFilter() {
        return path -> path.toFile().isDirectory();
    }

}
