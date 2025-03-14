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
    private static final Map<String, String> ALLOWED_DIRECTORY_AND_CHECKS =
        getAllowedDirectoryAndChecks();

    private static final Set<String> INTERNAL_MODULES = getInternalModules();

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
        final Set<String> compatibleChecks = new HashSet<>();
        final Pattern pattern = Pattern.compile("^XpathRegression(.+)Test\\.java$");
        try (DirectoryStream<Path> javaPaths = Files.newDirectoryStream(javaDir)) {
            for (Path path : javaPaths) {
                assertWithMessage(path + " is not a regular file")
                        .that(Files.isRegularFile(path))
                        .isTrue();
                final String filename = path.toFile().getName();
                if (filename.startsWith("Abstract")) {
                    continue;
                }

                final Matcher matcher = pattern.matcher(filename);
                assertWithMessage(
                            "Invalid test file: " + filename + ", expected pattern: " + pattern)
                        .that(matcher.matches())
                        .isTrue();

                final String check = matcher.group(1);
                assertWithMessage("Unknown check '" + check + "' in test file: " + filename)
                        .that(SIMPLE_CHECK_NAMES)
                        .contains(check);

                assertWithMessage(
                            "Check '" + check + "' is now compatible with SuppressionXpathFilter."
                                + " Please update the todo list in"
                                + " XpathRegressionTest.INCOMPATIBLE_CHECK_NAMES")
                        .that(INCOMPATIBLE_CHECK_NAMES.contains(check))
                        .isFalse();
                compatibleChecks.add(check);
            }
        }

        // Ensure that all lists are up-to-date
        final Set<String> allChecks = new HashSet<>(SIMPLE_CHECK_NAMES);
        allChecks.removeAll(INCOMPATIBLE_JAVADOC_CHECK_NAMES);
        allChecks.removeAll(INCOMPATIBLE_CHECK_NAMES);
        allChecks.removeAll(Set.of("Regexp", "RegexpSinglelineJava", "NoCodeInFile"));
        allChecks.removeAll(NO_VIOLATION_MODULES);
        allChecks.removeAll(compatibleChecks);
        allChecks.removeAll(INTERNAL_MODULES);

        assertWithMessage("XpathRegressionTest is missing for [" + String.join(", ", allChecks)
                + "]. Please add them to src/it/java/org/checkstyle/suppressionxpathfilter")
                        .that(allChecks)
                        .isEmpty();
    }

    @Test
    public void validateInputFiles() throws Exception {
        try (DirectoryStream<Path> dirs = Files.newDirectoryStream(inputDir, path -> {
            return path.toFile().isDirectory();
        })) {
            for (Path dir : dirs) {
                final String dirName = dir.toFile().getName();
                assertWithMessage("Invalid dir name: " + dirName)
                    .that(ALLOWED_DIRECTORY_AND_CHECKS)
                    .containsKey(dirName);
                // input dir must be connected to an existing test
                final Path test = this.javaDir.resolve("XpathRegression"
                    + ALLOWED_DIRECTORY_AND_CHECKS.get(dirName) + "Test.java");
                assertWithMessage("Input dir '" + dir + "' is not connected to Java test case: "
                        + test)
                    .that(Files.exists(test))
                    .isTrue();
                // input files should be named correctly
                validateInputDirectory(dir);
            }
        }
    }

    private static void validateInputDirectory(Path checkDir) throws IOException {
        final Pattern pattern = Pattern.compile("^InputXpath(.+)\\.java$");
        final String check = ALLOWED_DIRECTORY_AND_CHECKS.get(checkDir.toFile().getName());

        try (DirectoryStream<Path> inputPaths = Files.newDirectoryStream(checkDir)) {
            for (Path inputPath : inputPaths) {
                final String filename = inputPath.toFile().getName();
                if (filename.endsWith("java")) {
                    final Matcher matcher = pattern.matcher(filename);
                    assertWithMessage(
                              "Invalid input file '" + inputPath
                              + "', expected pattern:" + pattern)
                            .that(matcher.matches())
                            .isTrue();

                    final String remaining = matcher.group(1);
                    assertWithMessage("Check name '" + check
                                + "' should be included in input file: " + inputPath)
                            .that(remaining)
                            .startsWith(check);
                }
            }
        }
    }
}
