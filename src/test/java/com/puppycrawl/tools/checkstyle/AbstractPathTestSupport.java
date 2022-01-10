////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code for adherence to a set of rules.
// Copyright (C) 2001-2021 the original author or authors.
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

package com.puppycrawl.tools.checkstyle;

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheckTest;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class AbstractPathTestSupport {

    /**
     * Input files which have more than 120 lines.
     */
    public static final Set<String> FILE_SIZE_SUPPRESSIONS = new HashSet<>(Arrays.asList(
            "InputNonEmptyAtclauseDescription.java", "InputJavadocStyle2.java",
            "InputJavadocStyle8.java",
            "InputJavadocStyle4.java", "InputJavadocStyleNoJavadoc.java",
            "InputJavadocStyle10.java",
            "InputJavadocStyle6.java", "InputJavadocStyle7.java", "InputJavadocStyle9.java",
            "InputJavadocStyle1.java", "InputJavadocStyle5.java", "InputJavadocStyle3.java",
            "InputAbstractJavadocPosition.java",
            "InputAbstractJavadocPositionWithSinglelineComments.java",
            "InputAbstractJavadocNonTightHtmlTagsNoViolation.java",
            "InputAbstractJavadocCorrectParagraph.java",
            "InputAbstractJavadocNonTightHtmlTagsVisitCount.java",
            "InputAbstractJavadocNonTightHtmlTags.java", "InputAbstractJavadocLeaveToken.java",
            "InputMissingJavadocTypeNoJavadoc3.java", "InputMissingJavadocTypeNoJavadoc2.java",
            "InputMissingJavadocTypePublicOnly2.java", "InputMissingJavadocTypePublicOnly1.java",
            "InputMissingJavadocTypeNoJavadoc1.java", "InputMissingJavadocTypeTags.java",
            "InputJavadocTagContinuationIndentation.java", "InputJavadocVariableNoJavadoc.java",
            "InputJavadocVariableNoJavadoc5.java", "InputJavadocVariablePublicOnly.java",
            "InputJavadocVariableTags.java", "InputJavadocVariableNoJavadoc4.java",
            "InputJavadocVariableNoJavadoc2.java", "InputJavadocVariablePublicOnly2.java",
            "InputJavadocVariableNoJavadoc3.java", "InputAtclauseOrderIncorrectCustom.java",
            "InputAtclauseOrderIncorrect.java", "InputAtclauseOrderCorrect.java",
            "InputJavadocMethodPublicOnly.java", "InputJavadocMethodTags.java",
            "InputJavadocMethodExtraThrows.java", "InputJavadocMethodThrowsDetection.java",
            "InputJavadocMethodIgnoreThrows.java", "InputJavadocMethodNoJavadocDefault.java",
            "InputJavadocMethodSurroundingAccessModifier.java",
            "InputJavadocMethodNoJavadocOnlyPrivateScope.java",
            "InputJavadocMethodNoJavadocProtectedScope.java", "InputJavadocMethodPublicOnly1.java",
            "InputJavadocMethodProtectedScopeJavadoc.java", "InputJavadocTypePublicOnly1.java",
            "InputJavadocTypeNoJavadoc.java", "InputJavadocTypeNoJavadoc_1.java",
            "InputJavadocTypePublicOnly.java", "InputJavadocTypeNoJavadoc_2.java",
            "InputJavadocTypeTags.java", "InputJavadocTypeJavadoc_1.java",
            "InputJavadocTypeJavadoc_2.java",
            "InputJavadocTypeJavadoc.java", "InputJavadocTypeJavadoc_3.java",
            "InputSummaryJavadocInlineDefault.java", "InputSummaryJavadocIncorrect2.java",
            "InputSummaryJavadocInlineCorrect.java", "InputSummaryJavadocIncorrect.java",
            "InputSummaryJavadocInlineForbidden.java", "InputSummaryJavadocCorrect.java",
            "InputMissingJavadocMethodNoJavadoc3.java", "InputMissingJavadocMethodNoJavadoc2.java",
            "InputMissingJavadocMethodTags.java", "InputMissingJavadocMethodNoJavadoc.java",
            "InputJavadocPackageNoJavadoc.java", "InputAvoidEscapedUnicodeCharacters1.java",
            "InputAvoidEscapedUnicodeCharacters.java",
            "InputAvoidEscapedUnicodeCharactersAllEscapedUnicodeCharacters.java",
            "InputAvoidEscapedUnicodeCharacters4.java", "InputAvoidEscapedUnicodeCharacters2.java",
            "InputAvoidEscapedUnicodeCharacters3.java", "InputTodoCommentSimple.java",
            "InputCommentsIndentationCommentIsAtTheEndOfBlock.java",
            "InputCommentsIndentationSurroundingCode.java",
            "InputCommentsIndentationInSwitchBlock.java",
            "InputCommentsIndentationSurroundingCode2.java",
            "InputCommentsIndentationInMultiblockStructures.java",
            "InputCommentsIndentationSurroundingCode3.java",
            "InputIndentationInvalidMethodIndent.java",
            "InputIndentationValidMethodIndent.java", "InputIndentationInvalidClassDefIndent.java",
            "InputIndentationLabels.java", "InputIndentationCorrectIfAndParameter.java",
            "InputIndentationCustomAnnotation.java", "InputIndentationValidClassDefIndent.java",
            "InputIndentationInvalidBlockIndent.java",
            "InputIndentationCorrectMultipleAnnotationsWithWrappedLines.java",
            "InputIndentationValidBlockIndent.java", "InputIndentationTryWithResourcesStrict.java",
            "InputIndentationValidArrayInitIndent.java", "InputIndentationLambda1.java",
            "InputIndentationLongConcatenatedString.java", "InputIndentationFromGuava2.java",
            "InputIndentationFromGuava.java", "InputIndentationValidIfIndent.java",
            "InputIndentationCtorCall.java", "InputIndentationInvalidArrayInitIndent.java",
            "InputIndentationTryResourcesNotStrict.java", "InputIndentationInvalidIfIndent.java",
            "InputAnnotationLocationIncorrect2.java", "InputAnnotationLocationIncorrect3.java",
            "InputAnnotationLocationIncorrect.java", "InputMethodLengthSimple.java",
            "InputMethodLengthCountEmptyIsFalse.java", "InputMethodLengthModifier.java",
            "InputMethodCount.java", "InputMethodCount1.java", "InputLineLengthSimple1.java",
            "InputLineLengthSimple.java", "InputParameterNumberSimple.java",
            "InputParameterNumberSimple4.java", "InputParameterNumberSimple2.java",
            "InputParameterNumberSimple3.java", "InputFileLength2.java", "InputFileLength.java",
            "InputFileLength3.java", "InputFileLength4.java", "InputOuterTypeNumberSimple1.java",
            "InputOuterTypeNumberSimple.java", "InputAnonInnerLength2.java",
            "InputAnonInnerLength.java",
            "InputRegexpSinglelineJavaSemantic7.java", "InputRegexpSinglelineJavaSemantic.java",
            "InputRegexpSinglelineJavaSemantic2.java", "InputRegexpSinglelineJavaSemantic5.java",
            "InputRegexpSinglelineJavaSemantic6.java", "InputRegexpSinglelineJavaSemantic3.java",
            "InputRegexpSinglelineJavaSemantic4.java", "InputRegexpMultilineSemantic8.java",
            "InputRegexpMultilineSemantic6.java", "InputRegexpMultilineSemantic4.java",
            "InputRegexpMultilineSemantic3.java", "InputRegexpMultilineSemantic2.java",
            "InputRegexpMultilineSemantic7.java", "InputRegexpMultilineSemantic9.java",
            "InputRegexpMultilineSemantic5.java", "InputRegexpMultilineSemantic.java",
            "InputRegexpSemantic9.java", "InputRegexpSemantic3.java", "InputRegexpSemantic13.java",
            "InputRegexpSemantic5.java", "InputRegexpSemantic7.java", "InputRegexpSemantic11.java",
            "InputRegexpSemantic4.java", "InputRegexpSemantic6.java", "InputRegexpSemantic10.java",
            "InputRegexpSemantic2.java", "InputRegexpCheckStopEarly.java",
            "InputRegexpSemantic14.java",
            "InputRegexpSemantic12.java", "InputRegexpSemantic8.java", "InputRegexpSemantic.java",
            "InputRegexpOnFilenameSemantic.java", "InputRegexpSinglelineSemantic3.java",
            "InputRegexpSinglelineSemantic2.java", "InputRegexpSinglelineSemantic4.java",
            "InputRegexpSinglelineSemantic5.java", "InputRegexpSinglelineSemantic8.java",
            "InputRegexpSinglelineSemantic7.java", "InputRegexpSinglelineSemantic.java",
            "InputRegexpSinglelineSemantic6.java", "InputRequireThisEnumInnerClassesAndBugs2.java",
            "InputRequireThisValidateOnlyOverlappingFalse.java",
            "InputRequireThisEnumInnerClassesAndBugs3.java",
            "InputRequireThisValidateOnlyOverlappingTrue.java",
            "InputRequireThisEnumInnerClassesAndBugs.java",
            "InputIllegalInstantiationSemantic2.java",
            "InputIllegalInstantiationSemantic.java", "InputHiddenField7.java",
            "InputHiddenFieldLambdas.java", "InputHiddenField2.java", "InputHiddenField8.java",
            "InputHiddenField4.java", "InputHiddenField3.java", "InputHiddenField5.java",
            "InputHiddenField6.java", "InputHiddenFieldReorder.java", "InputHiddenField1.java",
            "InputVariableDeclarationUsageDistanceFinal.java",
            "InputVariableDeclarationUsageDistanceRegExp.java",
            "InputVariableDeclarationUsageDistanceGeneral.java",
            "InputVariableDeclarationUsageDistanceScopes.java",
            "InputVariableDeclarationUsageDistance.java",
            "InputVariableDeclarationUsageDistanceDefault.java",
            "InputEqualsAvoidNullIgnoreCase.java",
            "InputEqualsAvoidNull.java", "InputCovariantEquals.java",
            "InputNoFinalizerFallThrough.java",
            "InputDefaultComesLast.java", "InputDefaultComesLastSkipIfLastAndSharedWithCase.java",
            "InputDeclarationOrder.java", "InputDeclarationOrderOnlyModifiers.java",
            "InputDeclarationOrderOnlyConstructors.java",
            "InputOverloadMethodsDeclarationOrder.java",
            "InputFallThrough.java", "InputFallThrough3.java", "InputFallThroughDefault.java",
            "InputInnerAssignment.java", "InputUnnecessaryParenthesesIfStatement.java",
            "InputEqualsHashCodeSemantic.java", "InputFinalLocalVariableFalsePositives.java",
            "InputFinalLocalVariableAssignedMultipleTimes.java", "InputFinalLocalVariable2.java",
            "InputFinalLocalVariable.java", "InputOneStatementPerLineSingleLine.java",
            "InputOneStatementPerLineMultiline.java", "InputMagicNumber_4.java",
            "InputMagicNumber_2.java",
            "InputMagicNumber.java", "InputMagicNumber_6.java", "InputMagicNumber_3.java",
            "InputMagicNumber_1.java", "InputMagicNumber_7.java", "InputMagicNumber_5.java",
            "InputNoEnumTrailingComma.java", "InputEmptyLineSeparatorWithComments2.java",
            "InputEmptyLineSeparatorWithComments.java", "InputEmptyLineSeparator.java",
            "InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments.java",
            "InputEmptyLineSeparator2.java", "InputWhitespaceAfterDefaultConfig.java",
            "InputWhitespaceAfterTypeCast.java", "InputParenPadLeftRightAndNoSpace2.java",
            "InputParenPadWhitespace.java", "InputParenPadLeftRightAndNoSpace3.java",
            "InputParenPadWhitespace2.java", "InputParenPadLeftRightAndNoSpace1.java",
            "InputNoWhitespaceBeforeDotAllowLineBreaks.java", "InputNoWhitespaceBeforeDefault.java",
            "InputNoWhitespaceBeforeDot.java", "InputFileTabCharacterSimple.java",
            "InputFileTabCharacterSimple1.java", "InputWhitespaceAroundSimple.java",
            "InputWhitespaceAroundBraces.java", "InputWhitespaceAroundBraces2.java",
            "InputWhitespaceAroundKeywordsAndOperators.java",
            "InputNoWhitespaceAfterTestDefault.java",
            "InputNoWhitespaceAfterTestAllowLineBreaks.java",
            "InputNoWhitespaceAfterTestTypecast.java",
            "InputNoWhitespaceAfterNewTypeStructure.java",
            "InputNoWhitespaceAfterTestAllTokens.java",
            "InputNoWhitespaceAfterArrayDeclarations2.java",
            "InputTypecastParenPadWhitespaceTestSpace.java", "InputTypecastParenPadWhitespace.java",
            "InputUpperEllSemantic.java", "InputEmptyCatchBlockDefaultLF.java",
            "InputEmptyCatchBlockDefault2.java", "InputEmptyCatchBlockDefault.java",
            "InputRightCurlyTestAloneOrSingleline.java", "InputRightCurlyLeftTestNewLine.java",
            "InputRightCurlyLeftTestDefault.java", "InputRightCurlyLeftTestShouldStartLine2.java",
            "InputRightCurlyLeftTestSame.java", "InputRightCurlyLeftTestAlone.java",
            "InputRightCurlyTestWithAnnotations.java",
            "InputNeedBracesTestSingleLineCaseDefault.java",
            "InputNeedBracesLoopBodyFalse.java", "InputNeedBracesTestIt.java",
            "InputNeedBracesTestItWithAllowsOn.java", "InputNeedBracesSingleLineStatements.java",
            "InputNeedBracesLoopBodyTrue.java", "InputLeftCurlyTestMissingBraces.java",
            "InputLeftCurlyTestDefault3.java", "InputLeftCurlyTestNewLine3.java",
            "InputVisibilityModifierSimple.java", "InputDesignForExtensionIgnoredAnnotations.java",
            "InputDesignForExtension.java", "InputDesignForExtensionOverridableMethods.java",
            "InputFinalClass.java", "InputFinalParameters4.java", "InputFinalParameters2.java",
            "InputFinalParameters5.java", "InputFinalParameters3.java", "InputFinalParameters.java",
            "InputRedundantModifierIt.java", "InputModifierOrderTypeAnnotations.java",
            "InputModifierOrderIt.java", "InputCyclomaticComplexity.java",
            "InputClassFanOutComplexityRemoveIncorrectAnnotationToken.java",
            "InputNPathComplexityOverflow.java", "InputNPathComplexity.java",
            "InputNPathComplexityDefault.java", "InputNPathComplexityDefault2.java",
            "InputPackageNameSimple1.java", "InputPackageNameSimple.java",
            "InputCatchParameterNameSimple.java", "InputMemberNameSimple.java",
            "InputLocalVariableName.java", "InputStaticVariableName2.java",
            "InputStaticVariableName1.java",
            "InputMethodNameSimple.java", "InputLocalFinalVariableName.java",
            "InputLocalFinalVariableName1.java", "InputParameterName.java",
            "InputParameterNameOne.java",
            "InputConstantNameSimple1.java", "InputConstantNameSimple2.java",
            "InputAbbreviationAsWordInNameIgnoreStaticKeepStaticFinal.java",
            "InputAbbreviationAsWordInNameIgnore.java",
            "InputAbbreviationAsWordInNameIgnoreStatic.java",
            "InputAbbreviationAsWordInNameType3.java", "InputAbbreviationAsWordInNameType2.java",
            "InputAbbreviationAsWordInNameIgnoreStaticFinal.java",
            "InputAbbreviationAsWordInNameType.java",
            "InputAbbreviationAsWordInNameIgnoreNonStaticFinal.java",
            "InputAbbreviationAsWordInNameType5.java",
            "InputAbbreviationAsWordInNameIgnoreFinalKeepStaticFinal.java",
            "InputAbbreviationAsWordInNameType4.java",
            "InputAbbreviationAsWordInNameIgnoreFinal.java",
            "InputAbbreviationAsWordInNameNoIgnore.java", "InputAbbreviationAsWordInNameType6.java",
            "InputRegressionJavaClass1.java",
            "InputAntlr4AstRegressionCassandraInputWithComments.java",
            "InputAntlr4AstRegressionCommentsOnAnnotationsAndEnums.java",
            "InputAntlr4AstRegressionTrickySwitch.java",
            "InputAntlr4AstRegressionKeywordsAndOperators.java",
            "InputAntlr4AstRegressionTrickySwitchWithComments.java",
            "InputAntlr4AstRegressionFallThroughDefault.java",
            "InputAntlr4AstRegressionNewTypeTree.java",
            "InputJavadocMetadataScraperAnnotationUseStyleCheck.java",
            "InputXpathMapperStringConcat.java",
            "InputSuppressionCommentFilter11.java", "InputSuppressionCommentFilter3.java",
            "InputSuppressionCommentFilter4.java", "InputSuppressionCommentFilter9.java",
            "InputSuppressionCommentFilter6.java", "InputSuppressionCommentFilter7.java",
            "InputSuppressionCommentFilter2.java", "InputSuppressionCommentFilter8.java",
            "InputSuppressionCommentFilter10.java", "InputSuppressionCommentFilter5.java",
            "InputMainComplexityOverflow.java", "InputAtclauseOrderLotsOfRecords.java",
            "InputRecordComponentNumberMax1.java", "InputRecordComponentNumberMax20.java",
            "InputRecordComponentNumberPrivateModifier.java", "InputRecordComponentNumber.java",
            "InputVariableDeclarationUsageDistanceCheckSwitchExpressions.java",
            "InputFinalLocalVariableCheckSwitchExpressions.java",
            "InputMultipleStringLiteralsTextBlocks.java",
            "InputNeedBracesTestSwitchExpression.java",
            "InputEmptyBlockSwitchExpressions.java", "InputCyclomaticComplexityRecords.java",
            "InputJavaNCSSRecordsAndCompactCtors.java", "InputJava14TextBlocks.java",
            "InputJava14SwitchExpression.java", "InputJava14InstanceofWithPatternMatching.java",
            "InputJava14Records.java", "InputAntlr4AstRegressionUncommon.java",
            "InputJavaParserFullJavaIdentifierSupport.java", "InputFileTabCharacter.java",
            "InputIllegalTokenText.java", "InputCorrectSummaryJavaDocCheck.java",
            "InputIncorrectAtClauseOrderCheck.java", "InputJavaDocTagContinuationIndentation.java",
            "InputCorrectAtClauseOrderCheck.java", "InputNeedBraces.java",
            "InputCommentsIndentationCommentIsAtTheEndOfBlock.java",
            "InputCommentsIndentationSurroundingCode.java",
            "InputCommentsIndentationInSwitchBlock.java",
            "InputEmptyBlockBasic.java", "InputFallThrough.java", "InputOneStatementPerLine.java",
            "InputIndentationCorrectIfAndParameter.java",
            "InputIndentationCorrectReturnAndParameter.java",
            "InputIndentationCorrectWhileDoWhileAndParameter.java",
            "InputIndentationCorrectFieldAndParameter.java",
            "InputIndentationCorrectIfAndParameter.java",
            "InputIndentationCorrectReturnAndParameter.java",
            "InputIndentationCorrectWhileDoWhileAndParameter.java",
            "InputIndentationCorrectFieldAndParameter.java", "InputRightCurlyOtherAlone.java",
            "InputRightCurlyOther.java", "InputLeftCurlyBraces.java", "InputLineLength.java",
            "InputVariableDeclarationUsageDistanceCheck.java", "InputEmptyLineSeparator.java",
            "InputOperatorWrap.java", "InputMethodParamPad.java", "InputModifierOrder.java",
            "InputIndentationCorrectIfAndParameter.java",
            "InputIndentationCorrectReturnAndParameter.java",
            "InputIndentationCorrectWhileDoWhileAndParameter.java",
            "InputIndentationCorrectFieldAndParameter.java", "InputParenPad.java",
            "InputWhitespaceAroundBasic.java", "InputLineLength.java",
            "InputEmptyLineSeparator.java",
            "InputMemberNameSimple.java", "InputNoFinalizeExtend.java"
    ));

    // we are using positive lookahead here, to convert \r\n to \n
    // and \\r\\n to \\n (for parse tree dump files),
    // by replacing the full match with the empty string
    private static final String CR_FOLLOWED_BY_LF_REGEX = "(?x)\\\\r(?=\\\\n)|\\r(?=\\n)";

    private static final String EOL = System.lineSeparator();

    /**
     * Returns the exact location for the package where the file is present.
     *
     * @return path for the package name for the file.
     */
    protected abstract String getPackageLocation();

    /**
     * Retrieves the name of the folder location for resources.
     *
     * @return The name of the folder.
     */
    protected String getResourceLocation() {
        return "test";
    }

    /**
     * Returns canonical path for the file with the given file name.
     * The path is formed base on the root location.
     * This implementation uses 'src/test/resources/'
     * as a root location.
     *
     * @param filename file name.
     * @return canonical path for the file name.
     * @throws IOException if I/O exception occurs while forming the path.
     */
    protected final String getPath(String filename) throws IOException {
        return new File("src/" + getResourceLocation() + "/resources/" + getPackageLocation() + "/"
                + filename).getCanonicalPath();
    }

    /**
     * Returns the path for resources for the given file name.
     *
     * @param filename name of the file.
     * @return the path for resources for the given file based on its package location.
     */
    protected final String getResourcePath(String filename) {
        return "/" + getPackageLocation() + "/" + filename;
    }

    /**
     * Reads the contents of a file.
     *
     * @param filename the name of the file whose contents are to be read
     * @return contents of the file with all {@code \r\n} replaced by {@code \n}
     * @throws IOException if I/O exception occurs while reading
     */
    protected static String readFile(String filename) throws IOException {
        return toLfLineEnding(new String(Files.readAllBytes(
                Paths.get(filename)), StandardCharsets.UTF_8));
    }

    /**
     * Join given strings with {@link #EOL} delimiter and add EOL at the end.
     *
     * @param strings strings to join
     * @return joined strings
     */
    public static String addEndOfLine(String... strings) {
        return Stream.of(strings).collect(Collectors.joining(EOL, "", EOL));
    }

    /**
     * Returns a string containing "\r\n" converted to "\n" and "\\r\\n" converted to "\\n"
     * by replacing with empty string.
     *
     * @param text the text string.
     * @return the converted text string.
     */
    protected static String toLfLineEnding(String text) {
        return text.replaceAll(CR_FOLLOWED_BY_LF_REGEX, "");
    }

    /**
     * Validate that all input files do not exceed specified max length.
     *
     * @param theFiles list of files
     * @throws CheckstyleException if file length is more than max length
     * @throws IOException if I/O exception occurs while reading
     */
    protected static void validateFileLength(
            List<File> theFiles) throws CheckstyleException, IOException {
        final int maxLines = 120;
        for (File file : theFiles) {
            final String fileName = file.getName();
            if (!isBadInputFile(fileName)) {
                final long count = Files.lines(file.toPath()).count();
                if (count > maxLines
                        && !isTempFile(fileName)
                        && !FILE_SIZE_SUPPRESSIONS.contains(fileName)) {
                    throw new CheckstyleException(
                            file + " has " + count + " lines, exceeds max limit of "
                                    + maxLines + " lines");
                }
            }
        }
    }

    /**
     * Whether file is created in junit's temporary file.
     * Example - {@link DetailAstImplTest#testManyComments()}
     *
     * @param fileName name of file
     * @return {@code true} is file is a temp file
     */
    private static boolean isTempFile(String fileName) {
        // Currently, there is just this single file.
        final String tempFile = "InputDetailASTManyComments.java";
        return tempFile.equals(fileName);
    }

    /**
     * Whether a file is a bad input file. Often non-existing files are used
     * for testing purposes. Example - {@link FileTabCharacterCheckTest#testBadFile()}
     *
     * @param fileName name of file
     * @return {@code true} is file is a bad input file
     */
    private static boolean isBadInputFile(String fileName) {
        // Currently, there is just this single file.
        final String badInputFile = "Claira";
        return badInputFile.equals(fileName);
    }

}
