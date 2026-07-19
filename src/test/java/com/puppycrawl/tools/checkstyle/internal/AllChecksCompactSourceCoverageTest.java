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
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.beanutils.PropertyUtils;
import org.junit.jupiter.api.Test;

import com.puppycrawl.tools.checkstyle.bdd.InlineConfigParser;
import com.puppycrawl.tools.checkstyle.bdd.ModuleInputConfiguration;
import com.puppycrawl.tools.checkstyle.bdd.TestInputConfiguration;
import com.puppycrawl.tools.checkstyle.internal.utils.CheckUtil;

/**
 * Enforces that every TreeWalker check has compact source file (JEP 512) test coverage.
 * A check is covered when a {@code compact/} input folder exists and its inline configs both
 * run the check once with all properties at their default values and, together, exercise every
 * settable property at a non-default value at least once. Not-yet-covered checks are listed in
 * {@link #SUPPRESSED_CHECKS} and skipped; removing a check from that set arms this test for it.
 */
public class AllChecksCompactSourceCoverageTest {

    /** Root of the non-compilable check test resources; compact inputs live under it. */
    private static final Path NONCOMPILABLE_CHECKS_ROOT = Path.of(
        "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks");

    /** Package prefix common to all check classes, used to derive their resource folders. */
    private static final String CHECKS_PACKAGE = "com.puppycrawl.tools.checkstyle.checks";

    /** Name of the folder holding a check's compact source inputs. */
    private static final String COMPACT_FOLDER = "compact";

    /** Suffix stripped from a check's simple name to derive its resource folder name. */
    private static final String CHECK_SUFFIX = "Check";

    /**
     * Properties that are not specific to a single check and therefore do not need to be
     * exercised by compact source inputs.
     */
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
     * Checks not yet covered by compact source inputs; skipped until a contributor adds their
     * {@code compact/} folder and removes the entry. This set should shrink to empty and then
     * be removed.
     */
    // until https://github.com/checkstyle/checkstyle/issues/20590
    private static final Set<String> SUPPRESSED_CHECKS = Set.of(
        "AbbreviationAsWordInNameCheck",
        "AbstractClassNameCheck",
        "AnnotationLocationCheck",
        "AnnotationOnSameLineCheck",
        "AnnotationUseStyleCheck",
        "AnonInnerLengthCheck",
        "ArrayTrailingCommaCheck",
        "ArrayTypeStyleCheck",
        "AtclauseOrderCheck",
        "AvoidDoubleBraceInitializationCheck",
        "AvoidEscapedUnicodeCharactersCheck",
        "AvoidInlineConditionalsCheck",
        "AvoidNestedBlocksCheck",
        "AvoidNoArgumentSuperConstructorCallCheck",
        "AvoidStarImportCheck",
        "AvoidStaticImportCheck",
        "BooleanExpressionComplexityCheck",
        "CatchParameterNameCheck",
        "ClassDataAbstractionCouplingCheck",
        "ClassMemberImpliedModifierCheck",
        "ClassTypeParameterNameCheck",
        "CommentsIndentationCheck",
        "ConstantNameCheck",
        "ConstructorsDeclarationGroupingCheck",
        "CustomImportOrderCheck",
        "CyclomaticComplexityCheck",
        "DeclarationOrderCheck",
        "DefaultComesLastCheck",
        "DescendantTokenCheck",
        "DesignForExtensionCheck",
        "EmptyBlockCheck",
        "EmptyCatchBlockCheck",
        "EmptyForInitializerPadCheck",
        "EmptyForIteratorPadCheck",
        "EmptyLineSeparatorCheck",
        "EqualsAvoidNullCheck",
        "EqualsHashCodeCheck",
        "ExecutableStatementCountCheck",
        "ExplicitInitializationCheck",
        "FallThroughCheck",
        "FinalClassCheck",
        "FinalLocalVariableCheck",
        "FinalParametersCheck",
        "GenericWhitespaceCheck",
        "GoogleNonConstantFieldNameCheck",
        "HexLiteralCaseCheck",
        "HiddenFieldCheck",
        "HideUtilityClassConstructorCheck",
        "IllegalCatchCheck",
        "IllegalIdentifierNameCheck",
        "IllegalImportCheck",
        "IllegalInstantiationCheck",
        "IllegalSymbolCheck",
        "IllegalThrowsCheck",
        "IllegalTokenCheck",
        "IllegalTokenTextCheck",
        "IllegalTypeCheck",
        "ImportControlCheck",
        "ImportOrderCheck",
        "IndentationCheck",
        "InnerAssignmentCheck",
        "InterfaceIsTypeCheck",
        "InterfaceMemberImpliedModifierCheck",
        "InterfaceTypeParameterNameCheck",
        "InvalidJavadocPositionCheck",
        "JavaNCSSCheck",
        "JavadocBlockTagLocationCheck",
        "JavadocContentLocationCheck",
        "JavadocLeadingAsteriskAlignCheck",
        "JavadocMethodCheck",
        "JavadocMissingLeadingAsteriskCheck",
        "JavadocMissingWhitespaceAfterAsteriskCheck",
        "JavadocParagraphCheck",
        "JavadocStyleCheck",
        "JavadocTagContinuationIndentationCheck",
        "JavadocTypeCheck",
        "JavadocVariableCheck",
        "LambdaBodyLengthCheck",
        "LambdaParameterNameCheck",
        "LeftCurlyCheck",
        "LocalFinalVariableNameCheck",
        "LocalVariableNameCheck",
        "MagicNumberCheck",
        "MatchXpathCheck",
        "MemberNameCheck",
        "MethodCountCheck",
        "MethodLengthCheck",
        "MethodNameCheck",
        "MethodParamPadCheck",
        "MethodTypeParameterNameCheck",
        "MissingCtorCheck",
        "MissingDeprecatedCheck",
        "MissingJavadocMethodCheck",
        "MissingJavadocPackageCheck",
        "MissingJavadocTypeCheck",
        "MissingNullCaseInSwitchCheck",
        "MissingOverrideCheck",
        "MissingOverrideOnRecordAccessorCheck",
        "MissingSwitchDefaultCheck",
        "ModifiedControlVariableCheck",
        "ModifierOrderCheck",
        "MultipleStringLiteralsCheck",
        "MultipleVariableDeclarationsCheck",
        "MutableExceptionCheck",
        "NPathComplexityCheck",
        "NestedForDepthCheck",
        "NestedIfDepthCheck",
        "NestedTryDepthCheck",
        "NoArrayTrailingCommaCheck",
        "NoCloneCheck",
        "NoCodeInFileCheck",
        "NoEnumTrailingCommaCheck",
        "NoFinalizerCheck",
        "NoLineWrapCheck",
        "NoWhitespaceAfterCheck",
        "NoWhitespaceBeforeCaseDefaultColonCheck",
        "NoWhitespaceBeforeCheck",
        "NonEmptyAtclauseDescriptionCheck",
        "NumericalPrefixesInfixesSuffixesCharacterCaseCheck",
        "OneStatementPerLineCheck",
        "OneTopLevelClassCheck",
        "OperatorWrapCheck",
        "OuterTypeNumberCheck",
        "OverloadMethodsDeclarationOrderCheck",
        "PackageAnnotationCheck",
        "PackageDeclarationCheck",
        "PackageNameCheck",
        "ParameterAssignmentCheck",
        "ParameterNameCheck",
        "ParameterNumberCheck",
        "ParenPadCheck",
        "PatternVariableAssignmentCheck",
        "PatternVariableNameCheck",
        "RecordComponentNameCheck",
        "RecordComponentNumberCheck",
        "RecordTypeParameterNameCheck",
        "RedundantImportCheck",
        "RedundantModifierCheck",
        "RegexpCheck",
        "RegexpSinglelineJavaCheck",
        "RequireEmptyLineBeforeBlockTagGroupCheck",
        "RequireThisCheck",
        "ReturnCountCheck",
        "RightCurlyCheck",
        "SealedShouldHavePermitsListCheck",
        "SeparatorWrapCheck",
        "SimplifyBooleanExpressionCheck",
        "SimplifyBooleanReturnCheck",
        "SingleLineJavadocCheck",
        "SingleSpaceSeparatorCheck",
        "StaticVariableNameCheck",
        "SummaryJavadocCheck",
        "SuperCloneCheck",
        "SuperFinalizeCheck",
        "SuppressWarningsCheck",
        "SuppressWarningsHolder",
        "TextBlockGoogleStyleFormattingCheck",
        "ThrowsCountCheck",
        "TodoCommentCheck",
        "TrailingCommentCheck",
        "TypeNameCheck",
        "TypecastParenPadCheck",
        "UncommentedMainCheck",
        "UnnecessaryNullCheckWithInstanceOfCheck",
        "UnnecessaryParenthesesCheck",
        "UnnecessarySemicolonAfterOuterTypeDeclarationCheck",
        "UnnecessarySemicolonAfterTypeMemberDeclarationCheck",
        "UnnecessarySemicolonInEnumerationCheck",
        "UnnecessarySemicolonInTryWithResourcesCheck",
        "UnusedCatchParameterShouldBeUnnamedCheck",
        "UnusedImportsCheck",
        "UnusedLambdaParameterShouldBeUnnamedCheck",
        "UnusedLocalVariableCheck",
        "UnusedTryResourceShouldBeUnnamedCheck",
        "UpperEllCheck",
        "UseEnhancedSwitchCheck",
        "VariableDeclarationUsageDistanceCheck",
        "VisibilityModifierCheck",
        "WhenShouldBeUsedCheck",
        "WhitespaceAfterCheck",
        "WhitespaceAroundCheck",
        "WriteTagCheck"
    );

    @Test
    public void testAllChecksHaveCompactSourceCoverage() throws Exception {
        final List<String> failures = new ArrayList<>();

        for (Class<?> check : CheckUtil.getCheckstyleChecks()) {
            if (!SUPPRESSED_CHECKS.contains(check.getSimpleName())) {
                failures.addAll(findCoverageFailures(check));
            }
        }

        if (!failures.isEmpty()) {
            assertWithMessage("The following checks lack compact source input coverage:\n"
                    + String.join("\n", failures)).fail();
        }
    }

    /**
     * Collects every coverage failure for a check: a missing {@code compact/} folder, a missing
     * default-config input, or properties not exercised at a non-default value. Returns an empty
     * list when the check is fully covered.
     *
     * @param check the check to evaluate.
     * @return the failure messages, empty when covered.
     * @throws Exception if an input file cannot be parsed.
     */
    private static List<String> findCoverageFailures(Class<?> check) throws Exception {
        final Path folder = resolveCompactFolder(check);
        final List<String> failures;
        if (Files.isDirectory(folder)) {
            failures = evaluateFolderCoverage(check, folder);
        }
        else {
            failures = List.of(check.getSimpleName()
                    + ": missing compact source input folder '" + folder + "'");
        }
        return failures;
    }

    /**
     * Evaluates the two content rules for a check whose {@code compact/} folder exists, adding a
     * message for each unmet rule independently: it must contain at least one input running the
     * check with all properties at their default values, and its inputs must together exercise
     * every settable property at a non-default value.
     *
     * @param check the check to evaluate.
     * @param folder the compact source input folder.
     * @return the failure messages, empty when covered.
     * @throws Exception if an input file cannot be parsed.
     */
    private static List<String> evaluateFolderCoverage(Class<?> check, Path folder)
            throws Exception {
        final List<Set<String>> nonDefaultsPerInput =
                collectNonDefaultPropertiesPerInput(folder, check.getName());
        final Set<String> toExercise = propertiesToExercise(check);
        final List<String> failures = new ArrayList<>();
        if (nonDefaultsPerInput.stream().noneMatch(Set::isEmpty)) {
            final String detail;
            if (toExercise.isEmpty()) {
                detail = ": missing a compact source input";
            }
            else {
                detail = ": missing a default-config compact source input "
                        + "(all properties at default)";
            }
            failures.add(check.getSimpleName() + detail);
        }
        final Set<String> exercised = nonDefaultsPerInput.stream()
                .flatMap(Set::stream)
                .collect(Collectors.toCollection(HashSet::new));
        final Set<String> missing = toExercise.stream()
                .filter(property -> !exercised.contains(property))
                .collect(Collectors.toCollection(TreeSet::new));
        if (!missing.isEmpty()) {
            failures.add(check.getSimpleName()
                    + ": compact source inputs do not exercise these properties at a "
                    + "non-default value: " + missing);
        }
        return failures;
    }

    /**
     * Resolves the {@code compact/} input folder for a check from its package and simple name.
     *
     * @param check the check class.
     * @return the expected compact source input folder.
     */
    private static Path resolveCompactFolder(Class<?> check) {
        Path folder = NONCOMPILABLE_CHECKS_ROOT;
        final String packageName = check.getPackageName();
        if (!CHECKS_PACKAGE.equals(packageName)) {
            final String subPackage = packageName.substring(CHECKS_PACKAGE.length() + 1);
            folder = folder.resolve(subPackage.replace('.', '/'));
        }
        String simpleName = check.getSimpleName();
        if (simpleName.endsWith(CHECK_SUFFIX)) {
            simpleName = simpleName.substring(0, simpleName.length() - CHECK_SUFFIX.length());
        }
        return folder.resolve(simpleName.toLowerCase(Locale.ENGLISH)).resolve(COMPACT_FOLDER);
    }

    /**
     * Collects, for each input file that configures the given check, the set of properties the
     * file sets to a non-default value. An empty set marks an input that runs the check with all
     * properties at their default values.
     *
     * @param folder the compact source input folder.
     * @param checkClassName the fully qualified name of the check module to match.
     * @return the non-default property sets, one per input that configures the check.
     * @throws Exception if an input file cannot be parsed.
     */
    private static List<Set<String>> collectNonDefaultPropertiesPerInput(
            Path folder, String checkClassName) throws Exception {
        final List<Set<String>> nonDefaultsPerInput = new ArrayList<>();
        final List<Path> inputs;
        try (Stream<Path> files = Files.list(folder)) {
            inputs = files
                .filter(path -> path.getFileName().toString().endsWith(".java"))
                .toList();
        }
        for (Path input : inputs) {
            final TestInputConfiguration config = InlineConfigParser.parse(input.toString());
            for (ModuleInputConfiguration module : config.getChildrenModules()) {
                if (checkClassName.equals(module.getModuleName())) {
                    nonDefaultsPerInput.add(module.getNonDefaultProperties().keySet());
                }
            }
        }
        return nonDefaultsPerInput;
    }

    /**
     * Computes the settable, check-specific properties a check must exercise.
     *
     * @param check the check class.
     * @return a sorted set of the property names to exercise.
     */
    private static Set<String> propertiesToExercise(Class<?> check) {
        return Arrays.stream(PropertyUtils.getPropertyDescriptors(check))
            .filter(descriptor -> descriptor.getWriteMethod() != null)
            .map(PropertyDescriptor::getName)
            .filter(property -> !COMMON_PROPERTIES.contains(property))
            .collect(Collectors.toCollection(TreeSet::new));
    }

}
