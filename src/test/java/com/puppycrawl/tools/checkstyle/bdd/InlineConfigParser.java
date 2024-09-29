///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2024 the original author or authors.
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

package com.puppycrawl.tools.checkstyle.bdd;

import java.io.File;
import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.xml.sax.InputSource;

import com.puppycrawl.tools.checkstyle.ConfigurationLoader;
import com.puppycrawl.tools.checkstyle.PropertiesExpander;
import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.api.Configuration;

public final class InlineConfigParser {

    /** A pattern matching the symbol: "\" or "/". */
    private static final Pattern SLASH_PATTERN = Pattern.compile("[\\\\/]");

    /**
     * Pattern for lines under
     * {@link InlineConfigParser#VIOLATIONS_SOME_LINES_ABOVE_PATTERN}.
     */
    private static final Pattern VIOLATION_MESSAGE_PATTERN = Pattern
            .compile(".*//\\s*(?:['\"](.*)['\"])?$");
    /**
     * A pattern that matches the following comments formats.
     * <ol>
     *     <li> // violation </li>
     *     <li> // violation, 'violation message' </li>
     *     <li> // violation 'violation messages' </li>
     *     <li> // violation, "violation messages" </li>
     * </ol>
     *
     * <p>
     * This pattern will not match the following formats.
     * <ol>
     *     <li> // violation, explanation </li>
     *     <li> // violation, explanation, 'violation message' </li>
     * </ol>
     *
     * These are matched by
     * {@link InlineConfigParser#VIOLATION_WITH_EXPLANATION_PATTERN}.
     * </p>
     */
    private static final Pattern VIOLATION_PATTERN = Pattern
            .compile(".*//\\s*violation,?\\s*(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// violation above". */
    private static final Pattern VIOLATION_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*violation above,?\\s*(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// violation below". */
    private static final Pattern VIOLATION_BELOW_PATTERN = Pattern
            .compile(".*//\\s*violation below,?\\s*(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// violation above, explanation". */
    private static final Pattern VIOLATION_ABOVE_WITH_EXPLANATION_PATTERN = Pattern
            .compile(".*//\\s*violation above,\\s.+\\s(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// violation below, explanation". */
    private static final Pattern VIOLATION_BELOW_WITH_EXPLANATION_PATTERN = Pattern
            .compile(".*//\\s*violation below,\\s.+\\s(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// violation, explanation". */
    private static final Pattern VIOLATION_WITH_EXPLANATION_PATTERN = Pattern
            .compile(".*//\\s*violation,\\s+(?:.*)?$");

    /** A pattern to find the string: "// X violations". */
    private static final Pattern MULTIPLE_VIOLATIONS_PATTERN = Pattern
            .compile(".*//\\s*(\\d+) violations$");

    /** A pattern to find the string: "// X violations above". */
    private static final Pattern MULTIPLE_VIOLATIONS_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*(\\d+) violations above$");

    /** A pattern to find the string: "// X violations below". */
    private static final Pattern MULTIPLE_VIOLATIONS_BELOW_PATTERN = Pattern
            .compile(".*//\\s*(\\d+) violations below$");

    /** A pattern to find the string: "// filtered violation". */
    private static final Pattern FILTERED_VIOLATION_PATTERN = Pattern
            .compile(".*//\\s*filtered violation\\s*(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// filtered violation above". */
    private static final Pattern FILTERED_VIOLATION_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*filtered violation above\\s*(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// filtered violation below". */
    private static final Pattern FILTERED_VIOLATION_BELOW_PATTERN = Pattern
            .compile(".*//\\s*filtered violation below\\s*(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// violation X lines above". */
    private static final Pattern VIOLATION_SOME_LINES_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*violation (\\d+) lines above\\s*(?:['\"](.*)['\"])?$");

    /** A pattern to find the string: "// violation X lines below". */
    private static final Pattern VIOLATION_SOME_LINES_BELOW_PATTERN = Pattern
            .compile(".*//\\s*violation (\\d+) lines below\\s*(?:['\"](.*)['\"])?$");

    /**
     * <p>
     * Multiple violations for above line. Messages are X lines below.
     * {@code
     *   // X violations above:
     *   //                    'violation message1'
     *   //                    'violation messageX'
     * }
     *
     * Messages are matched by {@link InlineConfigParser#VIOLATION_MESSAGE_PATTERN}
     * </p>
     */
    private static final Pattern VIOLATIONS_ABOVE_PATTERN_WITH_MESSAGES = Pattern
            .compile(".*//\\s*(\\d+) violations above:$");

    /**
     * <p>
     * Multiple violations for line. Violations are Y lines above, messages are X lines below.
     * {@code
     *   // X violations Y lines above:
     *   //                            'violation message1'
     *   //                            'violation messageX'
     * }
     *
     * Messages are matched by {@link InlineConfigParser#VIOLATION_MESSAGE_PATTERN}
     * </p>
     */
    private static final Pattern VIOLATIONS_SOME_LINES_ABOVE_PATTERN = Pattern
            .compile(".*//\\s*(\\d+) violations (\\d+) lines above:$");

    /**
     * <p>
     * Multiple violations for line. Violations are Y lines below, messages are X lines below.
     * {@code
     *   // X violations Y lines below:
     *   //                            'violation message1'
     *   //                            'violation messageX'
     * }
     *
     * Messages are matched by {@link InlineConfigParser#VIOLATION_MESSAGE_PATTERN}
     * </p>
     */
    private static final Pattern VIOLATIONS_SOME_LINES_BELOW_PATTERN = Pattern
            .compile(".*//\\s*(\\d+) violations (\\d+) lines below:$");

    /** A pattern that matches any comment by default. */
    private static final Pattern VIOLATION_DEFAULT = Pattern
            .compile("//.*violation.*");

    /** The String "(null)". */
    private static final String NULL_STRING = "(null)";

    private static final String LATEST_DTD = String.format(Locale.ROOT,
            "<!DOCTYPE module PUBLIC \"%s\" \"%s\">%n",
            ConfigurationLoader.DTD_PUBLIC_CS_ID_1_3,
            ConfigurationLoader.DTD_PUBLIC_CS_ID_1_3);

    /**
     *  Inlined configs can not be used in non-java checks, as Inlined config is java style
     *  multiline comment.
     *  Such check files needs to be permanently suppressed.
     */
    private static final Set<String> PERMANENT_SUPPRESSED_CHECKS = Set.of(
            // Inlined config is not supported for non java files.
            "com.puppycrawl.tools.checkstyle.checks.OrderedPropertiesCheck",
            "com.puppycrawl.tools.checkstyle.checks.UniquePropertiesCheck",
            "com.puppycrawl.tools.checkstyle.checks.TranslationCheck"
    );

    /**
     *  Checks in which violation message is not specified in input files.
     *  Until <a href="https://github.com/checkstyle/checkstyle/issues/15456">#15456</a>.
     */
    private static final Set<String> SUPPRESSED_CHECKS = Set.of(
            "com.puppycrawl.tools.checkstyle.checks.annotation.PackageAnnotationCheck",
            "com.puppycrawl.tools.checkstyle.checks.annotation.SuppressWarningsCheck",
            "com.puppycrawl.tools.checkstyle.checks.ArrayTypeStyleCheck",
            "com.puppycrawl.tools.checkstyle.checks.AvoidEscapedUnicodeCharactersCheck",
            "com.puppycrawl.tools.checkstyle.checks.blocks.EmptyCatchBlockCheck",
            "com.puppycrawl.tools.checkstyle.checks.blocks.NeedBracesCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.ArrayTrailingCommaCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.AvoidInlineConditionalsCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding"
                    + ".AvoidNoArgumentSuperConstructorCallCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.CovariantEqualsCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.ExplicitInitializationCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.FinalLocalVariableCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.HiddenFieldCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.IllegalCatchCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.IllegalInstantiationCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.IllegalThrowsCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.IllegalTokenTextCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.IllegalTypeCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.InnerAssignmentCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.MagicNumberCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.MatchXpathCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.MissingCtorCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.MissingSwitchDefaultCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.ModifiedControlVariableCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.MultipleStringLiteralsCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.NestedForDepthCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.NestedIfDepthCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.NestedTryDepthCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.NoArrayTrailingCommaCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.NoCloneCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.NoEnumTrailingCommaCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.NoFinalizerCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.OneStatementPerLineCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.OverloadMethodsDeclarationOrderCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.ParameterAssignmentCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanExpressionCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.SimplifyBooleanReturnCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.StringLiteralEqualityCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.SuperCloneCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding.SuperFinalizeCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding"
                    + ".UnnecessarySemicolonAfterOuterTypeDeclarationCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding"
                    + ".UnnecessarySemicolonAfterTypeMemberDeclarationCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding"
                    + ".UnnecessarySemicolonInEnumerationCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding"
                    + ".UnnecessarySemicolonInTryWithResourcesCheck",
            "com.puppycrawl.tools.checkstyle.checks.coding"
                    + ".UnusedCatchParameterShouldBeUnnamedCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.DesignForExtensionCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.HideUtilityClassConstructorCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.InnerTypeLastCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.InterfaceIsTypeCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.MutableExceptionCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.OneTopLevelClassCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.SealedShouldHavePermitsListCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.ThrowsCountCheck",
            "com.puppycrawl.tools.checkstyle.checks.design.VisibilityModifierCheck",
            "com.puppycrawl.tools.checkstyle.checks.FinalParametersCheck",
            "com.puppycrawl.tools.checkstyle.checks.imports.AvoidStarImportCheck",
            "com.puppycrawl.tools.checkstyle.checks.imports.AvoidStaticImportCheck",
            "com.puppycrawl.tools.checkstyle.checks.imports.IllegalImportCheck",
            "com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc."
                    + "AbstractJavadocCheckTest$TokenIsNotInAcceptablesCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.AtclauseOrderCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.InvalidJavadocPositionCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocBlockTagLocationCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocMissingLeadingAsteriskCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc"
                    + ".JavadocMissingWhitespaceAfterAsteriskCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc"
                    + ".JavadocTagContinuationIndentationCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.JavadocVariableCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocMethodCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocPackageCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.MissingJavadocTypeCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.NonEmptyAtclauseDescriptionCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc"
                    + ".RequireEmptyLineBeforeBlockTagGroupCheck",
            "com.puppycrawl.tools.checkstyle.checks.javadoc.SingleLineJavadocCheck",
            "com.puppycrawl.tools.checkstyle.checks.metrics.BooleanExpressionComplexityCheck",
            "com.puppycrawl.tools.checkstyle.checks.metrics.ClassDataAbstractionCouplingCheck",
            "com.puppycrawl.tools.checkstyle.checks.metrics.ClassFanOutComplexityCheck",
            "com.puppycrawl.tools.checkstyle.checks.metrics.CyclomaticComplexityCheck",
            "com.puppycrawl.tools.checkstyle.checks.metrics.NPathComplexityCheck",
            "com.puppycrawl.tools.checkstyle.checks.modifier.ClassMemberImpliedModifierCheck",
            "com.puppycrawl.tools.checkstyle.checks.modifier.InterfaceMemberImpliedModifierCheck",
            "com.puppycrawl.tools.checkstyle.checks.modifier.RedundantModifierCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.AbbreviationAsWordInNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.CatchParameterNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.ClassTypeParameterNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.IllegalIdentifierNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.InterfaceTypeParameterNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.LambdaParameterNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.LocalFinalVariableNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.LocalVariableNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.MethodTypeParameterNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.PackageNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.PatternVariableNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.RecordComponentNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.RecordTypeParameterNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.StaticVariableNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.naming.TypeNameCheck",
            "com.puppycrawl.tools.checkstyle.checks.NoCodeInFileCheck",
            "com.puppycrawl.tools.checkstyle.checks.OuterTypeFilenameCheck",
            "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpMultilineCheck",
            "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineCheck",
            "com.puppycrawl.tools.checkstyle.checks.regexp.RegexpSinglelineJavaCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.AnonInnerLengthCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.ExecutableStatementCountCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.FileLengthCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.LambdaBodyLengthCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.MethodLengthCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.OuterTypeNumberCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck",
            "com.puppycrawl.tools.checkstyle.checks.sizes.RecordComponentNumberCheck",
            "com.puppycrawl.tools.checkstyle.checks.TodoCommentCheck",
            "com.puppycrawl.tools.checkstyle.checks.TrailingCommentCheck",
            "com.puppycrawl.tools.checkstyle.checks.UncommentedMainCheck",
            "com.puppycrawl.tools.checkstyle.checks.UpperEllCheck",
            "com.puppycrawl.tools.checkstyle.checks.whitespace.NoLineWrapCheck",
            "com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck",
            "com.puppycrawl.tools.checkstyle.checks.whitespace."
                    + "NoWhitespaceBeforeCaseDefaultColonCheck",
            "com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceBeforeCheck",
            "com.puppycrawl.tools.checkstyle.checks.whitespace.ParenPadCheck",
            "com.puppycrawl.tools.checkstyle.checks.whitespace.SingleSpaceSeparatorCheck",
            "com.puppycrawl.tools.checkstyle.meta.JavadocMetadataScraper",
            "com.puppycrawl.tools.checkstyle.api.AbstractCheckTest$ViolationAstCheck",
            "com.puppycrawl.tools.checkstyle.CheckerTest$VerifyPositionAfterTabFileSet"
    );

    /** Stop instances being created. **/
    private InlineConfigParser() {
    }

    public static TestInputConfiguration parse(String inputFilePath) throws Exception {
        return parse(inputFilePath, false);
    }

    /**
     * Parses the input file provided.
     *
     * @param inputFilePath the input file path.
     * @param setFilteredViolations flag to set filtered violations.
     * @throws Exception if unable to read file or file not formatted properly.
     */
    private static TestInputConfiguration parse(String inputFilePath,
                                                boolean setFilteredViolations) throws Exception {
        final TestInputConfiguration.Builder testInputConfigBuilder =
                new TestInputConfiguration.Builder();
        final Path filePath = Paths.get(inputFilePath);
        final List<String> lines = readFile(filePath);
        try {
            setModules(testInputConfigBuilder, inputFilePath, lines);
        }
        catch (Exception ex) {
            throw new CheckstyleException("Config comment not specified properly in "
                    + inputFilePath, ex);
        }
        try {
            setViolations(testInputConfigBuilder, lines, setFilteredViolations);
        }
        catch (CheckstyleException ex) {
            throw new CheckstyleException(ex.getMessage() + " in " + inputFilePath, ex);
        }
        return testInputConfigBuilder.build();
    }

    public static List<TestInputViolation> getViolationsFromInputFile(String inputFilePath)
            throws Exception {
        final TestInputConfiguration.Builder testInputConfigBuilder =
                new TestInputConfiguration.Builder();
        final Path filePath = Paths.get(inputFilePath);
        final List<String> lines = readFile(filePath);

        try {
            for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
                setViolations(testInputConfigBuilder, lines, false, lineNo, true);
            }
        }
        catch (CheckstyleException ex) {
            throw new CheckstyleException(ex.getMessage() + " in " + inputFilePath, ex);
        }

        return testInputConfigBuilder.build().getViolations();
    }

    public static TestInputConfiguration parseWithFilteredViolations(String inputFilePath)
            throws Exception {
        return parse(inputFilePath, true);
    }

    /**
     * Parse the input file with configuration in xml header.
     *
     * @param inputFilePath the input file path.
     * @throws Exception if unable to parse the xml header
     */
    public static TestInputConfiguration parseWithXmlHeader(String inputFilePath)
            throws Exception {

        final Path filePath = Paths.get(inputFilePath);
        final List<String> lines = readFile(filePath);
        if (!checkIsXmlConfig(lines)) {
            throw new CheckstyleException("Config cannot be parsed as xml.");
        }

        final List<String> inlineConfig = getInlineConfig(lines);
        final String stringXmlConfig = LATEST_DTD + String.join("", inlineConfig);
        final InputSource inputSource = new InputSource(new StringReader(stringXmlConfig));
        final Configuration xmlConfig = ConfigurationLoader.loadConfiguration(
                inputSource, new PropertiesExpander(System.getProperties()),
                ConfigurationLoader.IgnoredModulesOptions.EXECUTE
        );
        final String configName = xmlConfig.getName();
        if (!"Checker".equals(configName)) {
            throw new CheckstyleException(
                    "First module should be Checker, but was " + configName);
        }

        final TestInputConfiguration.Builder testInputConfigBuilder =
                new TestInputConfiguration.Builder();
        testInputConfigBuilder.setXmlConfiguration(xmlConfig);
        try {
            setViolations(testInputConfigBuilder, lines, false);
        }
        catch (CheckstyleException ex) {
            throw new CheckstyleException(ex.getMessage() + " in " + inputFilePath, ex);
        }
        return testInputConfigBuilder.buildWithXmlConfiguration();
    }

    /**
     * Check whether a file provides xml configuration.
     *
     * @param lines lines of the file
     * @return true if a file provides xml configuration, otherwise false.
     */
    private static boolean checkIsXmlConfig(List<String> lines) {
        return "/*xml".equals(lines.get(0));
    }

    private static void setModules(TestInputConfiguration.Builder testInputConfigBuilder,
                                   String inputFilePath, List<String> lines)
            throws Exception {
        if (!lines.get(0).startsWith("/*")) {
            throw new CheckstyleException("Config not specified on top."
                + "Please see other inputs for examples of what is required.");
        }

        final List<String> inlineConfig = getInlineConfig(lines);

        if (checkIsXmlConfig(lines)) {
            final String stringXmlConfig = LATEST_DTD + String.join("", inlineConfig);
            final InputSource inputSource = new InputSource(new StringReader(stringXmlConfig));
            final Configuration xmlConfig = ConfigurationLoader.loadConfiguration(
                inputSource, new PropertiesExpander(System.getProperties()),
                    ConfigurationLoader.IgnoredModulesOptions.EXECUTE
            );
            final String configName = xmlConfig.getName();
            if (!"Checker".equals(configName)) {
                throw new CheckstyleException(
                        "First module should be Checker, but was " + configName);
            }
            handleXmlConfig(testInputConfigBuilder, inputFilePath, xmlConfig.getChildren());
        }
        else {
            handleKeyValueConfig(testInputConfigBuilder, inputFilePath, inlineConfig);
        }
    }

    private static List<String> getInlineConfig(List<String> lines) {
        return lines.stream()
                .skip(1)
                .takeWhile(line -> !line.startsWith("*/"))
                .collect(Collectors.toUnmodifiableList());
    }

    private static void handleXmlConfig(TestInputConfiguration.Builder testInputConfigBuilder,
                                        String inputFilePath,
                                        Configuration... modules)
            throws CheckstyleException {

        for (Configuration module: modules) {
            final String moduleName = module.getName();
            if ("TreeWalker".equals(moduleName)) {
                handleXmlConfig(testInputConfigBuilder, inputFilePath, module.getChildren());
            }
            else {
                final ModuleInputConfiguration.Builder moduleInputConfigBuilder =
                        new ModuleInputConfiguration.Builder();
                setModuleName(moduleInputConfigBuilder, inputFilePath, moduleName);
                setProperties(inputFilePath, module, moduleInputConfigBuilder);
                testInputConfigBuilder.addChildModule(moduleInputConfigBuilder.build());
            }
        }
    }

    private static void handleKeyValueConfig(TestInputConfiguration.Builder testInputConfigBuilder,
                                             String inputFilePath, List<String> lines)
            throws CheckstyleException, IOException {
        int lineNo = 0;
        while (lineNo < lines.size()) {
            final ModuleInputConfiguration.Builder moduleInputConfigBuilder =
                    new ModuleInputConfiguration.Builder();
            setModuleName(moduleInputConfigBuilder, inputFilePath, lines.get(lineNo));
            setProperties(moduleInputConfigBuilder, inputFilePath, lines, lineNo + 1);
            testInputConfigBuilder.addChildModule(moduleInputConfigBuilder.build());
            do {
                lineNo++;
            } while (lineNo < lines.size()
                    && lines.get(lineNo).isEmpty()
                    || !lines.get(lineNo - 1).isEmpty());
        }
    }

    private static String getFullyQualifiedClassName(String filePath, String moduleName)
            throws CheckstyleException {
        // This is a hack until https://github.com/checkstyle/checkstyle/issues/13845
        final Map<String, String> moduleMappings = new HashMap<>();
        moduleMappings.put("ParameterNumber",
                "com.puppycrawl.tools.checkstyle.checks.sizes.ParameterNumberCheck");
        moduleMappings.put("SuppressWarningsHolder",
                "com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder");
        moduleMappings.put("SuppressWarningsFilter",
                "com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter");
        moduleMappings.put("MemberName",
                "com.puppycrawl.tools.checkstyle.checks.naming.MemberNameCheck");
        moduleMappings.put("ConstantName",
                "com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck");
        moduleMappings.put("NoWhitespaceAfter",
                "com.puppycrawl.tools.checkstyle.checks.whitespace.NoWhitespaceAfterCheck");
        moduleMappings.put("SummaryJavadoc",
                "com.puppycrawl.tools.checkstyle.checks.javadoc.SummaryJavadocCheck");
        moduleMappings.put("LineLength",
                "com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck");
        moduleMappings.put("ParameterName",
                "com.puppycrawl.tools.checkstyle.checks.naming.ParameterNameCheck");
        moduleMappings.put("MethodName",
                "com.puppycrawl.tools.checkstyle.checks.naming.MethodNameCheck");
        moduleMappings.put("SuppressionXpathSingleFilter",
                "com.puppycrawl.tools.checkstyle.filters.SuppressionXpathSingleFilter");

        String fullyQualifiedClassName;
        if (moduleMappings.containsKey(moduleName)) {
            fullyQualifiedClassName = moduleMappings.get(moduleName);
        }
        else if (moduleName.startsWith("com.")) {
            fullyQualifiedClassName = moduleName;
        }
        else {
            final String path = SLASH_PATTERN.matcher(filePath).replaceAll(".");
            final int endIndex = path.lastIndexOf(moduleName.toLowerCase(Locale.ROOT));
            if (endIndex == -1) {
                throw new CheckstyleException("Unable to resolve module name: " + moduleName
                + ". Please check for spelling errors or specify fully qualified class name.");
            }
            final int beginIndex = path.indexOf("com.puppycrawl");
            fullyQualifiedClassName = path.substring(beginIndex, endIndex) + moduleName;
            if (!fullyQualifiedClassName.endsWith("Filter")) {
                fullyQualifiedClassName += "Check";
            }
        }
        return fullyQualifiedClassName;
    }

    private static String getFilePath(String fileName, String inputFilePath) {
        final int lastSlashIndex = Math.max(inputFilePath.lastIndexOf('\\'),
                inputFilePath.lastIndexOf('/'));
        final String root = inputFilePath.substring(0, lastSlashIndex + 1);
        return root + fileName;
    }

    private static String getResourcePath(String fileName, String inputFilePath) {
        final String filePath = getUriPath(fileName, inputFilePath);
        final int lastSlashIndex = filePath.lastIndexOf('/');
        final String root = filePath.substring(filePath.indexOf("puppycrawl") - 5,
                lastSlashIndex + 1);
        return root + fileName;
    }

    private static String getUriPath(String fileName, String inputFilePath) {
        return new File(getFilePath(fileName, inputFilePath)).toURI().toString();
    }

    private static String getResolvedPath(String fileValue, String inputFilePath) {
        final String resolvedFilePath;
        if (fileValue.startsWith("(resource)")) {
            resolvedFilePath =
                    getResourcePath(fileValue.substring(fileValue.indexOf(')') + 1),
                            inputFilePath);
        }
        else if (fileValue.startsWith("(uri)")) {
            resolvedFilePath =
                    getUriPath(fileValue.substring(fileValue.indexOf(')') + 1), inputFilePath);
        }
        else {
            resolvedFilePath = getFilePath(fileValue, inputFilePath);
        }
        return resolvedFilePath;
    }

    private static List<String> readFile(Path filePath) throws CheckstyleException {
        try {
            return Files.readAllLines(filePath);
        }
        catch (IOException ex) {
            throw new CheckstyleException("Failed to read " + filePath, ex);
        }
    }

    private static void setModuleName(ModuleInputConfiguration.Builder moduleInputConfigBuilder,
                                      String filePath, String moduleName)
            throws CheckstyleException {
        final String fullyQualifiedClassName = getFullyQualifiedClassName(filePath, moduleName);
        moduleInputConfigBuilder.setModuleName(fullyQualifiedClassName);
    }

    private static void setProperties(ModuleInputConfiguration.Builder inputConfigBuilder,
                                      String inputFilePath,
                                      List<String> lines,
                                      int beginLineNo)
                    throws IOException {
        final StringBuilder stringBuilder = new StringBuilder(128);
        int lineNo = beginLineNo;
        for (String line = lines.get(lineNo); !line.isEmpty() && !"*/".equals(line);
                ++lineNo, line = lines.get(lineNo)) {
            stringBuilder.append(line).append('\n');
        }
        final Properties properties = new Properties();
        properties.load(new StringReader(stringBuilder.toString()));
        for (final Map.Entry<Object, Object> entry : properties.entrySet()) {
            final String key = entry.getKey().toString();
            final String value = entry.getValue().toString();
            if (key.startsWith("message.")) {
                inputConfigBuilder.addModuleMessage(key.substring(8), value);
            }
            else if (value.startsWith("(file)")) {
                final String fileName = value.substring(value.indexOf(')') + 1);
                final String filePath = getResolvedPath(fileName, inputFilePath);
                inputConfigBuilder.addNonDefaultProperty(key, filePath);
            }
            else if (value.startsWith("(default)")) {
                final String defaultValue = value.substring(value.indexOf(')') + 1);
                if (NULL_STRING.equals(defaultValue)) {
                    inputConfigBuilder.addDefaultProperty(key, null);
                }
                else {
                    inputConfigBuilder.addDefaultProperty(key, defaultValue);
                }
            }
            else {
                if (NULL_STRING.equals(value)) {
                    inputConfigBuilder.addNonDefaultProperty(key, null);
                }
                else {
                    inputConfigBuilder.addNonDefaultProperty(key, value);
                }
            }
        }
    }

    private static void setProperties(String inputFilePath, Configuration module,
                                      ModuleInputConfiguration.Builder moduleInputConfigBuilder)
            throws CheckstyleException {
        final String[] getPropertyNames = module.getPropertyNames();
        for (final String propertyName : getPropertyNames) {
            final String propertyValue = module.getProperty(propertyName);

            if ("file".equals(propertyName)) {
                final String filePath = getResolvedPath(propertyValue, inputFilePath);
                moduleInputConfigBuilder.addNonDefaultProperty(propertyName, filePath);
            }
            else {
                if (NULL_STRING.equals(propertyValue)) {
                    moduleInputConfigBuilder.addNonDefaultProperty(propertyName, null);
                }
                else {
                    moduleInputConfigBuilder.addNonDefaultProperty(propertyName, propertyValue);
                }
            }
        }

        final Map<String, String> messages = module.getMessages();
        for (final Map.Entry<String, String> entry : messages.entrySet()) {
            final String key = entry.getKey();
            final String value = entry.getValue();
            moduleInputConfigBuilder.addModuleMessage(key, value);
        }
    }

    private static void setViolations(TestInputConfiguration.Builder inputConfigBuilder,
                                      List<String> lines, boolean useFilteredViolations)
            throws CheckstyleException {
        final List<ModuleInputConfiguration> moduleLists = inputConfigBuilder.getChildrenModules();
        final boolean specifyViolationMessage = moduleLists.size() == 1
                && !PERMANENT_SUPPRESSED_CHECKS.contains(moduleLists.get(0).getModuleName())
                && !SUPPRESSED_CHECKS.contains(moduleLists.get(0).getModuleName());
        for (int lineNo = 0; lineNo < lines.size(); lineNo++) {
            setViolations(inputConfigBuilder, lines,
                    useFilteredViolations, lineNo, specifyViolationMessage);
        }
    }

    /**
     * Sets the violations.
     *
     * @param inputConfigBuilder the input file path.
     * @param lines all the lines in the file.
     * @param useFilteredViolations flag to set filtered violations.
     * @param lineNo current line.
     * @noinspection IfStatementWithTooManyBranches
     * @noinspectionreason IfStatementWithTooManyBranches - complex logic of violation
     *      parser requires giant if/else
     * @throws CheckstyleException if violation message is not specified
     */
    // -@cs[ExecutableStatementCount] splitting this method is not reasonable.
    // -@cs[JavaNCSS] splitting this method is not reasonable.
    // -@cs[CyclomaticComplexity] splitting this method is not reasonable.
    private static void setViolations(TestInputConfiguration.Builder inputConfigBuilder,
                                      List<String> lines, boolean useFilteredViolations,
                                      int lineNo, boolean specifyViolationMessage)
            throws CheckstyleException {
        final Matcher violationMatcher =
                VIOLATION_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationAboveMatcher =
                VIOLATION_ABOVE_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationBelowMatcher =
                VIOLATION_BELOW_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationAboveWithExplanationMatcher =
                VIOLATION_ABOVE_WITH_EXPLANATION_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationBelowWithExplanationMatcher =
                VIOLATION_BELOW_WITH_EXPLANATION_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationWithExplanationMatcher =
                VIOLATION_WITH_EXPLANATION_PATTERN.matcher(lines.get(lineNo));
        final Matcher multipleViolationsMatcher =
                MULTIPLE_VIOLATIONS_PATTERN.matcher(lines.get(lineNo));
        final Matcher multipleViolationsAboveMatcher =
                MULTIPLE_VIOLATIONS_ABOVE_PATTERN.matcher(lines.get(lineNo));
        final Matcher multipleViolationsBelowMatcher =
                MULTIPLE_VIOLATIONS_BELOW_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationSomeLinesAboveMatcher =
                VIOLATION_SOME_LINES_ABOVE_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationSomeLinesBelowMatcher =
                VIOLATION_SOME_LINES_BELOW_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationsAboveMatcherWithMessages =
                VIOLATIONS_ABOVE_PATTERN_WITH_MESSAGES.matcher(lines.get(lineNo));
        final Matcher violationsSomeLinesAboveMatcher =
                VIOLATIONS_SOME_LINES_ABOVE_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationsSomeLinesBelowMatcher =
                VIOLATIONS_SOME_LINES_BELOW_PATTERN.matcher(lines.get(lineNo));
        final Matcher violationsDefault =
                VIOLATION_DEFAULT.matcher(lines.get(lineNo));
        if (violationMatcher.matches()) {
            final String violationMessage = violationMatcher.group(1);
            final int violationLineNum = lineNo + 1;
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage,
                    violationLineNum);
            inputConfigBuilder.addViolation(violationLineNum, violationMessage);
        }
        else if (violationAboveMatcher.matches()) {
            final String violationMessage = violationAboveMatcher.group(1);
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage, lineNo);
            inputConfigBuilder.addViolation(lineNo, violationMessage);
        }
        else if (violationBelowMatcher.matches()) {
            final String violationMessage = violationBelowMatcher.group(1);
            final int violationLineNum = lineNo + 2;
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage,
                    violationLineNum);
            inputConfigBuilder.addViolation(violationLineNum, violationMessage);
        }
        else if (violationAboveWithExplanationMatcher.matches()) {
            final String violationMessage = violationAboveWithExplanationMatcher.group(1);
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage, lineNo);
            inputConfigBuilder.addViolation(lineNo, violationMessage);
        }
        else if (violationBelowWithExplanationMatcher.matches()) {
            final String violationMessage = violationBelowWithExplanationMatcher.group(1);
            final int violationLineNum = lineNo + 2;
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage,
                    violationLineNum);
            inputConfigBuilder.addViolation(violationLineNum, violationMessage);
        }
        else if (violationWithExplanationMatcher.matches()) {
            final int violationLineNum = lineNo + 1;
            inputConfigBuilder.addViolation(violationLineNum, null);
        }
        else if (violationSomeLinesAboveMatcher.matches()) {
            final String violationMessage = violationSomeLinesAboveMatcher.group(2);
            final int linesAbove = Integer.parseInt(violationSomeLinesAboveMatcher.group(1)) - 1;
            final int violationLineNum = lineNo - linesAbove;
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage,
                    violationLineNum);
            inputConfigBuilder.addViolation(violationLineNum, violationMessage);
        }
        else if (violationSomeLinesBelowMatcher.matches()) {
            final String violationMessage = violationSomeLinesBelowMatcher.group(2);
            final int linesBelow = Integer.parseInt(violationSomeLinesBelowMatcher.group(1)) + 1;
            final int violationLineNum = lineNo + linesBelow;
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage,
                    violationLineNum);
            inputConfigBuilder.addViolation(violationLineNum, violationMessage);
        }
        else if (violationsAboveMatcherWithMessages.matches()) {
            inputConfigBuilder.addViolations(
                getExpectedViolationsForSpecificLine(
                    lines, lineNo, lineNo, violationsAboveMatcherWithMessages));
        }
        else if (violationsSomeLinesAboveMatcher.matches()) {
            inputConfigBuilder.addViolations(
                getExpectedViolations(
                    lines, lineNo, violationsSomeLinesAboveMatcher, true));
        }
        else if (violationsSomeLinesBelowMatcher.matches()) {
            inputConfigBuilder.addViolations(
                    getExpectedViolations(
                            lines, lineNo, violationsSomeLinesBelowMatcher, false));
        }
        else if (multipleViolationsMatcher.matches()) {
            Collections
                    .nCopies(Integer.parseInt(multipleViolationsMatcher.group(1)), lineNo + 1)
                    .forEach(actualLineNumber -> {
                        inputConfigBuilder.addViolation(actualLineNumber, null);
                    });
        }
        else if (multipleViolationsAboveMatcher.matches()) {
            Collections
                    .nCopies(Integer.parseInt(multipleViolationsAboveMatcher.group(1)), lineNo)
                    .forEach(actualLineNumber -> {
                        inputConfigBuilder.addViolation(actualLineNumber, null);
                    });
        }
        else if (multipleViolationsBelowMatcher.matches()) {
            Collections
                    .nCopies(Integer.parseInt(multipleViolationsBelowMatcher.group(1)),
                            lineNo + 2)
                    .forEach(actualLineNumber -> {
                        inputConfigBuilder.addViolation(actualLineNumber, null);
                    });
        }
        else if (useFilteredViolations) {
            setFilteredViolation(inputConfigBuilder, lineNo + 1,
                    lines.get(lineNo), specifyViolationMessage);
        }
        else if (violationsDefault.matches()) {
            final int violationLineNum = lineNo + 1;
            inputConfigBuilder.addViolation(violationLineNum, null);
        }
    }

    private static List<TestInputViolation> getExpectedViolationsForSpecificLine(
                                              List<String> lines, int lineNo, int violationLineNum,
                                              Matcher matcher) {
        final List<TestInputViolation> results = new ArrayList<>();

        final int expectedMessageCount =
            Integer.parseInt(matcher.group(1));
        for (int index = 1; index <= expectedMessageCount; index++) {
            final String lineWithMessage = lines.get(lineNo + index);
            final Matcher messageMatcher = VIOLATION_MESSAGE_PATTERN.matcher(lineWithMessage);
            if (messageMatcher.find()) {
                final String violationMessage = messageMatcher.group(1);
                results.add(new TestInputViolation(violationLineNum, violationMessage));
            }
        }
        if (results.size() != expectedMessageCount) {
            final String message = String.format(Locale.ROOT,
                "Declared amount of violation messages at line %s is %s but found %s",
                lineNo + 1, expectedMessageCount, results.size());
            throw new IllegalStateException(message);
        }
        return results;
    }

    private static List<TestInputViolation> getExpectedViolations(
                                              List<String> lines, int lineNo,
                                              Matcher matcher, boolean isAbove) {
        final int violationLine =
            Integer.parseInt(matcher.group(2));
        final int violationLineNum;
        if (isAbove) {
            violationLineNum = lineNo - violationLine + 1;
        }
        else {
            violationLineNum = lineNo + violationLine + 1;
        }
        return getExpectedViolationsForSpecificLine(lines,
            lineNo, violationLineNum, matcher);
    }

    private static void setFilteredViolation(TestInputConfiguration.Builder inputConfigBuilder,
                                             int lineNo, String line,
                                             boolean specifyViolationMessage)
            throws CheckstyleException {
        final Matcher violationMatcher =
                FILTERED_VIOLATION_PATTERN.matcher(line);
        final Matcher violationAboveMatcher =
                FILTERED_VIOLATION_ABOVE_PATTERN.matcher(line);
        final Matcher violationBelowMatcher =
                FILTERED_VIOLATION_BELOW_PATTERN.matcher(line);
        if (violationMatcher.matches()) {
            final String violationMessage = violationMatcher.group(1);
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage, lineNo);
            inputConfigBuilder.addFilteredViolation(lineNo, violationMessage);
        }
        else if (violationAboveMatcher.matches()) {
            final String violationMessage = violationAboveMatcher.group(1);
            final int violationLineNum = lineNo - 1;
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage,
                    violationLineNum);
            inputConfigBuilder.addFilteredViolation(violationLineNum, violationMessage);
        }
        else if (violationBelowMatcher.matches()) {
            final String violationMessage = violationBelowMatcher.group(1);
            final int violationLineNum = lineNo + 1;
            checkWhetherViolationSpecified(specifyViolationMessage, violationMessage,
                    violationLineNum);
            inputConfigBuilder.addFilteredViolation(violationLineNum, violationMessage);
        }
    }

    /**
     * Check whether violation is specified along with {@code // violation} comment.
     *
     * @param shouldViolationMsgBeSpecified should violation messages be specified.
     * @param violationMessage violation message
     * @param lineNum line number
     * @throws CheckstyleException if violation message is not specified
     */
    private static void checkWhetherViolationSpecified(boolean shouldViolationMsgBeSpecified,
            String violationMessage, int lineNum) throws CheckstyleException {
        if (shouldViolationMsgBeSpecified && violationMessage == null) {
            throw new CheckstyleException(
                    "Violation message should be specified on line " + lineNum);
        }
    }
}
