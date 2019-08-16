////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2019 the original author or authors.
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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.puppycrawl.tools.checkstyle.AbstractModuleTestSupport;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

public class XpathRegressionTest extends AbstractModuleTestSupport {

    // Temporal Checks that allowed to have no XPath IT Regression Testing
    // https://github.com/checkstyle/checkstyle/issues/6207
    private static final Set<String> MISSING_CHECK_NAMES = new HashSet<>(Arrays.asList(
            "AvoidInlineConditionals",
            "AvoidNestedBlocks",
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

    @BeforeClass
    public static void setUpBeforeClass() throws IOException {
        simpleCheckNames = CheckUtil.getSimpleNames(CheckUtil.getCheckstyleChecks());

        allowedDirectoryAndChecks = simpleCheckNames
                .stream()
                .collect(Collectors.toMap(id -> id.toLowerCase(Locale.ENGLISH), id -> id));
    }

    @Before
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
    public void validateIntegrationTestClassNames() throws Exception {
        final Pattern pattern = Pattern.compile("^XpathRegression(.+)Test\\.java$");
        try (DirectoryStream<Path> javaPaths = Files.newDirectoryStream(javaDir)) {
            for (Path path : javaPaths) {
                assertTrue(path + " is not a regular file", Files.isRegularFile(path));
                final String filename = path.toFile().getName();
                if (filename.startsWith("Abstract")) {
                    continue;
                }

                final Matcher matcher = pattern.matcher(filename);
                assertTrue("Invalid test file: " + filename + ", expected pattern: " + pattern,
                        matcher.matches());

                final String check = matcher.group(1);
                assertTrue("Unknown check '" + check + "' in test file: " + filename,
                        simpleCheckNames.contains(check));

                assertFalse("Check '" + check + "' is now tested. Please update the todo list in"
                        + " XpathRegressionTest.MISSING_CHECK_NAMES",
                        MISSING_CHECK_NAMES.contains(check));
            }
        }
    }

    @Test
    public void validateInputFiles() throws Exception {
        try (DirectoryStream<Path> dirs = Files.newDirectoryStream(inputDir)) {
            for (Path dir : dirs) {
                // input directory must be named in lower case
                assertTrue(dir + " is not a directory", Files.isDirectory(dir));
                final String dirName = dir.toFile().getName();
                assertTrue("Invalid directory name: " + dirName,
                        allowedDirectoryAndChecks.containsKey(dirName));

                // input directory must be connected to an existing test
                final String check = allowedDirectoryAndChecks.get(dirName);
                final Path javaPath = javaDir.resolve("XpathRegression" + check + "Test.java");
                assertTrue("Input directory '" + dir + "' is not connected to Java test case: "
                        + javaPath, Files.exists(javaPath));

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
                    assertTrue("Invalid input file '" + inputPath + "', expected pattern:"
                            + pattern, matcher.matches());

                    final String remaining = matcher.group(1);
                    assertTrue("Check name '" + check + "' should be included in input file: "
                            + inputPath, remaining.startsWith(check));
                }
            }
        }
    }
}
