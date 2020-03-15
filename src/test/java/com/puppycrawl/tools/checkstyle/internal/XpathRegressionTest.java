////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2020 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.internal;

import static com.google.common.truth.Truth.assertWithMessage;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.checks.javadoc.AbstractJavadocCheck;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class XpathRegressionTest extends AbstractModuleTestSupport {

    // Checks that not compatible with SuppressionXpathFilter
    // till https://github.com/checkstyle/checkstyle/issues/5777
    public static final Set<String> INCOMPATIBLE_CHECK_NAMES =
        Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
            "AnnotationLocation",
            "AnnotationOnSameLine",
            "AnnotationUseStyle",
            "ArrayTrailingComma",
            "AvoidEscapedUnicodeCharacters",
            "AvoidStarImport",
            "AvoidStaticImport",
            "CommentsIndentation",
            "CustomImportOrder",
            "EmptyLineSeparator",
            "FinalClass",
            "IllegalCatch",
            "ImportOrder",
            "Indentation",
            "InterfaceIsType",
            "InterfaceMemberImpliedModifier",
            "InvalidJavadocPosition",
            "JavadocContentLocation",
            "JavadocMethod",
            "JavadocStyle",
            "JavadocType",
            "LambdaParameterName",
            "MethodCount",
            "MissingCtor",
            "MissingJavadocMethod",
            "MissingJavadocPackage",
            "MissingJavadocType",
            "MissingOverride",
            "MissingSwitchDefault",
            "NeedBraces",
            "NoClone",
            "NoFinalizer",
            "NoLineWrap",
            "OneTopLevelClass",
            "OuterTypeFilename",
            "OverloadMethodsDeclarationOrder",
            "PackageAnnotation",
            "PackageDeclaration",
            "Regexp",
            "RegexpSinglelineJava",
            "SuppressWarningsHolder",
            "TodoComment",
            "TrailingComment",
            "UncommentedMain",
            "UnnecessaryParentheses",
            "VariableDeclarationUsageDistance",
            "WriteTag"
    )));

    // Javadoc checks are not compatible with SuppressionXpathFilter
    // till https://github.com/checkstyle/checkstyle/issues/5770
    // then all of them should be added to the list of incompatible checks
    // and this field should be removed
    public static final Set<String> INCOMPATIBLE_JAVADOC_CHECK_NAMES =
            Collections.unmodifiableSet(new HashSet<>(Arrays.asList(
                    "AtclauseOrder",
                    "JavadocBlockTagLocation",
                    "JavadocParagraph",
                    "JavadocTagContinuationIndentation",
                    "MissingDeprecated",
                    "NonEmptyAtclauseDescription",
                    "SingleLineJavadoc",
                    "SummaryJavadoc"
    )));

    // Checks that allowed to have no XPath IT Regression Testing
    // till https://github.com/checkstyle/checkstyle/issues/6207
    private static final Set<String> MISSING_CHECK_NAMES = new HashSet<>(Arrays.asList(
            "BooleanExpressionComplexity",
            "CatchParameterName",
            "ClassDataAbstractionCoupling",
            "ClassFanOutComplexity",
            "ClassTypeParameterName",
            "ConstantName",
            "CovariantEquals",
            "DescendantToken",
            "DesignForExtension",
            "EmptyBlock",
            "EmptyStatement",
            "EqualsAvoidNull",
            "EqualsHashCode",
            "ExecutableStatementCount",
            "FinalLocalVariable",
            "FinalParameters",
            "HideUtilityClassConstructor",
            "IllegalInstantiation",
            "IllegalToken",
            "IllegalTokenText",
            "IllegalType",
            "InnerAssignment",
            "InnerTypeLast",
            "InterfaceTypeParameterName",
            "JavaNCSS",
            "IllegalImport",
            "LocalFinalVariableName",
            "LocalVariableName",
            "MagicNumber",
            "MemberName",
            "MethodLength",
            "MethodName",
            "MethodTypeParameterName",
            "ModifiedControlVariable",
            "ModifierOrder",
            "MultipleStringLiterals",
            "MutableException",
            "OperatorWrap",
            "PackageName",
            "ParameterAssignment",
            "ParameterName",
            "ParameterNumber",
            "RedundantImport",
            "RedundantModifier",
            "ReturnCount",
            "SeparatorWrap",
            "SimplifyBooleanExpression",
            "SimplifyBooleanReturn",
            "StaticVariableName",
            "StringLiteralEquality",
            "SuperClone",
            "SuperFinalize",
            "SuppressWarnings",
            "ThrowsCount",
            "TypeName",
            "VisibilityModifier"
    ));

    private static Set<String> simpleCheckNames;
    private static Map<String, String> allowedDirectoryAndChecks;

    private Path javaDir;
    private Path inputDir;

    @BeforeAll
    public static void setUpBeforeClass() throws IOException {
        simpleCheckNames = CheckUtil.getSimpleNames(CheckUtil.getCheckstyleChecks());

        allowedDirectoryAndChecks = simpleCheckNames
                .stream()
                .collect(Collectors.toMap(id -> id.toLowerCase(Locale.ENGLISH), id -> id));
    }

    @BeforeEach
    public void setUp() throws Exception {
        javaDir = Paths.get("src/it/java/" + getPackageLocation());
        inputDir = Paths.get(getPath(""));
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
        final Set<Class<?>> abstractJavadocCheckNames = CheckUtil.getCheckstyleChecks()
                .stream()
                .filter(AbstractJavadocCheck.class::isAssignableFrom)
                .collect(Collectors.toSet());
        assertWithMessage("INCOMPATIBLE_JAVADOC_CHECK_NAMES should contains all descendants "
                    + "of AbstractJavadocCheck")
            .that(CheckUtil.getSimpleNames(abstractJavadocCheckNames))
            .isEqualTo(INCOMPATIBLE_JAVADOC_CHECK_NAMES);
    }

    @Test
    public void validateIntegrationTestClassNames() throws Exception {
        final Set<String> compatibleChecks = new HashSet<>();
        final Pattern pattern = Pattern.compile("^XpathRegression(.+)Test\\.java$");
        try (DirectoryStream<Path> javaPaths = Files.newDirectoryStream(javaDir)) {
            for (Path path : javaPaths) {
                assertTrue(Files.isRegularFile(path), path + " is not a regular file");
                final String filename = path.toFile().getName();
                if (filename.startsWith("Abstract")) {
                    continue;
                }

                final Matcher matcher = pattern.matcher(filename);
                assertTrue(matcher.matches(),
                        "Invalid test file: " + filename + ", expected pattern: " + pattern);

                final String check = matcher.group(1);
                assertTrue(simpleCheckNames.contains(check),
                        "Unknown check '" + check + "' in test file: " + filename);

                assertFalse(MISSING_CHECK_NAMES.contains(check),
                        "Check '" + check + "' is now tested. Please update the todo list in"
                                + " XpathRegressionTest.MISSING_CHECK_NAMES");
                assertFalse(INCOMPATIBLE_CHECK_NAMES.contains(check),
                        "Check '" + check + "' is now compatible with SuppressionXpathFilter."
                                + " Please update the todo list in"
                                + " XpathRegressionTest.INCOMPATIBLE_CHECK_NAMES");
                compatibleChecks.add(check);
            }
        }

        // Ensure that all lists are up to date
        final Set<String> allChecks = new HashSet<>(simpleCheckNames);
        allChecks.removeAll(INCOMPATIBLE_JAVADOC_CHECK_NAMES);
        allChecks.removeAll(INCOMPATIBLE_CHECK_NAMES);
        allChecks.removeAll(MISSING_CHECK_NAMES);
        allChecks.removeAll(compatibleChecks);

        assertTrue(allChecks.isEmpty(), "XpathRegressionTest is missing for ["
                + String.join(", ", allChecks)
                + "]. Please add them to src/it/java/org/checkstyle/suppressionxpathfilter");
    }

    @Test
    public void validateInputFiles() throws Exception {
        try (DirectoryStream<Path> dirs = Files.newDirectoryStream(inputDir)) {
            for (Path dir : dirs) {
                // input directory must be named in lower case
                assertTrue(Files.isDirectory(dir), dir + " is not a directory");
                final String dirName = dir.toFile().getName();
                assertTrue(allowedDirectoryAndChecks.containsKey(dirName),
                        "Invalid directory name: " + dirName);

                // input directory must be connected to an existing test
                final String check = allowedDirectoryAndChecks.get(dirName);
                final Path javaPath = javaDir.resolve("XpathRegression" + check + "Test.java");
                assertTrue(Files.exists(javaPath),
                        "Input directory '" + dir + "' is not connected to Java test case: "
                                + javaPath);

                // input files should named correctly
                validateInputDirectory(dir);
            }
        }
    }

    private static void validateInputDirectory(Path checkDir) throws IOException {
        final Pattern pattern = Pattern.compile("^SuppressionXpathRegression(.+)\\.java$");
        final String check = allowedDirectoryAndChecks.get(checkDir.toFile().getName());

        try (DirectoryStream<Path> inputPaths = Files.newDirectoryStream(checkDir)) {
            for (Path inputPath : inputPaths) {
                final String filename = inputPath.toFile().getName();
                if (filename.endsWith("java")) {
                    final Matcher matcher = pattern.matcher(filename);
                    assertTrue(matcher.matches(),
                            "Invalid input file '" + inputPath + "', expected pattern:" + pattern);

                    final String remaining = matcher.group(1);
                    assertTrue(remaining.startsWith(check),
                            "Check name '" + check + "' should be included in input file: "
                            + inputPath);
                }
            }
        }
    }
}
