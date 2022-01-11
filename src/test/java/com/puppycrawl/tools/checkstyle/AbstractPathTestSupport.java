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

import com.puppycrawl.tools.checkstyle.api.CheckstyleException;
import com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheckTest;

public abstract class AbstractPathTestSupport {

    /**
     * Input files which have more than 120 lines.
     */
    public static final Set<String> FILE_SIZE_SUPPRESSIONS = new HashSet<>(Arrays.asList(
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc"
                    + "/nonemptyatclausedescription/InputNonEmptyAtclauseDescription.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle8.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyleNoJavadoc.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle10.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle7.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle9.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocstyle"
                    + "/InputJavadocStyle3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc"
                    + "/InputAbstractJavadocPosition.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc"
                    + "/InputAbstractJavadocPositionWithSinglelineComments.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc"
                    + "/InputAbstractJavadocNonTightHtmlTagsNoViolation.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc"
                    + "/InputAbstractJavadocCorrectParagraph.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc"
                    + "/InputAbstractJavadocNonTightHtmlTagsVisitCount.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc"
                    + "/InputAbstractJavadocNonTightHtmlTags.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/abstractjavadoc"
                    + "/InputAbstractJavadocLeaveToken.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype"
                    + "/InputMissingJavadocTypeNoJavadoc3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype"
                    + "/InputMissingJavadocTypeNoJavadoc2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype"
                    + "/InputMissingJavadocTypePublicOnly2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype"
                    + "/InputMissingJavadocTypePublicOnly1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype"
                    + "/InputMissingJavadocTypeNoJavadoc1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/missingjavadoctype"
                    + "/InputMissingJavadocTypeTags.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc"
                    + "/javadoctagcontinuationindentation/InputJavadocTagContinuationIndentation"
                    + ".java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable"
                    + "/InputJavadocVariableNoJavadoc.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable"
                    + "/InputJavadocVariableNoJavadoc5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable"
                    + "/InputJavadocVariablePublicOnly.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable"
                    + "/InputJavadocVariableTags.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable"
                    + "/InputJavadocVariableNoJavadoc4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable"
                    + "/InputJavadocVariableNoJavadoc2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable"
                    + "/InputJavadocVariablePublicOnly2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocvariable"
                    + "/InputJavadocVariableNoJavadoc3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/atclauseorder"
                    + "/InputAtclauseOrderIncorrectCustom.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/atclauseorder"
                    + "/InputAtclauseOrderIncorrect.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/atclauseorder"
                    + "/InputAtclauseOrderCorrect.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodPublicOnly.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodTags.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodExtraThrows.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodThrowsDetection.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodIgnoreThrows.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodNoJavadocDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodSurroundingAccessModifier.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodNoJavadocOnlyPrivateScope.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodNoJavadocProtectedScope.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodPublicOnly1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocmethod"
                    + "/InputJavadocMethodProtectedScopeJavadoc.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypePublicOnly1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypeNoJavadoc.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypeNoJavadoc_1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypePublicOnly.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypeNoJavadoc_2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypeTags.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypeJavadoc_1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypeJavadoc_2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypeJavadoc.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadoctype"
                    + "/InputJavadocTypeJavadoc_3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc"
                    + "/InputSummaryJavadocInlineDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc"
                    + "/InputSummaryJavadocIncorrect2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc"
                    + "/InputSummaryJavadocInlineCorrect.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc"
                    + "/InputSummaryJavadocIncorrect.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc"
                    + "/InputSummaryJavadocInlineForbidden.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/summaryjavadoc"
                    + "/InputSummaryJavadocCorrect.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc"
                    + "/missingjavadocmethod/InputMissingJavadocMethodNoJavadoc3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc"
                    + "/missingjavadocmethod/InputMissingJavadocMethodNoJavadoc2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc"
                    + "/missingjavadocmethod/InputMissingJavadocMethodTags.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc"
                    + "/missingjavadocmethod/InputMissingJavadocMethodNoJavadoc.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/javadoc/javadocpackage"
                    + "/InputJavadocPackageNoJavadoc.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks"
                    + "/avoidescapedunicodecharacters/InputAvoidEscapedUnicodeCharacters1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks"
                    + "/avoidescapedunicodecharacters/InputAvoidEscapedUnicodeCharacters.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks"
                    + "/avoidescapedunicodecharacters"
                    + "/InputAvoidEscapedUnicodeCharactersAllEscapedUnicodeCharacters.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks"
                    + "/avoidescapedunicodecharacters/InputAvoidEscapedUnicodeCharacters4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks"
                    + "/avoidescapedunicodecharacters/InputAvoidEscapedUnicodeCharacters2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks"
                    + "/avoidescapedunicodecharacters/InputAvoidEscapedUnicodeCharacters3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/todocomment"
                    + "/InputTodoCommentSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation"
                    + "/commentsindentation/InputCommentsIndentationCommentIsAtTheEndOfBlock.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation"
                    + "/commentsindentation/InputCommentsIndentationSurroundingCode.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation"
                    + "/commentsindentation/InputCommentsIndentationInSwitchBlock.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation"
                    + "/commentsindentation/InputCommentsIndentationSurroundingCode2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation"
                    + "/commentsindentation/InputCommentsIndentationInMultiblockStructures.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation"
                    + "/commentsindentation/InputCommentsIndentationSurroundingCode3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationInvalidMethodIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationValidMethodIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationInvalidClassDefIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationLabels.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationCorrectIfAndParameter.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationCustomAnnotation.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationValidClassDefIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationInvalidBlockIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationCorrectMultipleAnnotationsWithWrappedLines.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationValidBlockIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationTryWithResourcesStrict.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationValidArrayInitIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationLambda1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationLongConcatenatedString.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationFromGuava2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationFromGuava.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationValidIfIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationCtorCall.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationInvalidArrayInitIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationTryResourcesNotStrict.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/indentation/indentation"
                    + "/InputIndentationInvalidIfIndent.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/annotation"
                    + "/annotationlocation/InputAnnotationLocationIncorrect2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/annotation"
                    + "/annotationlocation/InputAnnotationLocationIncorrect3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/annotation"
                    + "/annotationlocation/InputAnnotationLocationIncorrect.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/methodlength"
                    + "/InputMethodLengthSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/methodlength"
                    + "/InputMethodLengthCountEmptyIsFalse.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/methodlength"
                    + "/InputMethodLengthModifier.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/methodcount"
                    + "/InputMethodCount.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/methodcount"
                    + "/InputMethodCount1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/linelength"
                    + "/InputLineLengthSimple1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/linelength"
                    + "/InputLineLengthSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/parameternumber"
                    + "/InputParameterNumberSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/parameternumber"
                    + "/InputParameterNumberSimple4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/parameternumber"
                    + "/InputParameterNumberSimple2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/parameternumber"
                    + "/InputParameterNumberSimple3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/filelength"
                    + "/InputFileLength2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/filelength"
                    + "/InputFileLength.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/filelength"
                    + "/InputFileLength3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/filelength"
                    + "/InputFileLength4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/outertypenumber"
                    + "/InputOuterTypeNumberSimple1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/outertypenumber"
                    + "/InputOuterTypeNumberSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/anoninnerlength"
                    + "/InputAnonInnerLength2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/sizes/anoninnerlength"
                    + "/InputAnonInnerLength.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp"
                    + "/regexpsinglelinejava/InputRegexpSinglelineJavaSemantic7.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp"
                    + "/regexpsinglelinejava/InputRegexpSinglelineJavaSemantic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp"
                    + "/regexpsinglelinejava/InputRegexpSinglelineJavaSemantic2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp"
                    + "/regexpsinglelinejava/InputRegexpSinglelineJavaSemantic5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp"
                    + "/regexpsinglelinejava/InputRegexpSinglelineJavaSemantic6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp"
                    + "/regexpsinglelinejava/InputRegexpSinglelineJavaSemantic3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp"
                    + "/regexpsinglelinejava/InputRegexpSinglelineJavaSemantic4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic8.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic7.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic9.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpmultiline"
                    + "/InputRegexpMultilineSemantic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic9.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic13.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic7.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic11.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic10.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpCheckStopEarly.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic14.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic12.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic8.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexp"
                    + "/InputRegexpSemantic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexponfilename"
                    + "/InputRegexpOnFilenameSemantic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline"
                    + "/InputRegexpSinglelineSemantic3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline"
                    + "/InputRegexpSinglelineSemantic2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline"
                    + "/InputRegexpSinglelineSemantic4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline"
                    + "/InputRegexpSinglelineSemantic5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline"
                    + "/InputRegexpSinglelineSemantic8.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline"
                    + "/InputRegexpSinglelineSemantic7.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline"
                    + "/InputRegexpSinglelineSemantic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/regexp/regexpsingleline"
                    + "/InputRegexpSinglelineSemantic6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/requirethis"
                    + "/InputRequireThisEnumInnerClassesAndBugs2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/requirethis"
                    + "/InputRequireThisValidateOnlyOverlappingFalse.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/requirethis"
                    + "/InputRequireThisEnumInnerClassesAndBugs3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/requirethis"
                    + "/InputRequireThisValidateOnlyOverlappingTrue.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/requirethis"
                    + "/InputRequireThisEnumInnerClassesAndBugs.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/illegalinstantiation/InputIllegalInstantiationSemantic2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/illegalinstantiation/InputIllegalInstantiationSemantic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenField7.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenFieldLambdas.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenField2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenField8.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenField4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenField3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenField5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenField6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenFieldReorder.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/hiddenfield"
                    + "/InputHiddenField1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/variabledeclarationusagedistance"
                    + "/InputVariableDeclarationUsageDistanceFinal.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/variabledeclarationusagedistance"
                    + "/InputVariableDeclarationUsageDistanceRegExp.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/variabledeclarationusagedistance"
                    + "/InputVariableDeclarationUsageDistanceGeneral.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/variabledeclarationusagedistance"
                    + "/InputVariableDeclarationUsageDistanceScopes.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/variabledeclarationusagedistance/InputVariableDeclarationUsageDistance"
                    + ".java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/variabledeclarationusagedistance"
                    + "/InputVariableDeclarationUsageDistanceDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/equalsavoidnull"
                    + "/InputEqualsAvoidNullIgnoreCase.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/equalsavoidnull"
                    + "/InputEqualsAvoidNull.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/covariantequals"
                    + "/InputCovariantEquals.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/nofinalizer"
                    + "/InputNoFinalizerFallThrough.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/defaultcomeslast"
                    + "/InputDefaultComesLast.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/defaultcomeslast"
                    + "/InputDefaultComesLastSkipIfLastAndSharedWithCase.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/declarationorder"
                    + "/InputDeclarationOrder.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/declarationorder"
                    + "/InputDeclarationOrderOnlyModifiers.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/declarationorder"
                    + "/InputDeclarationOrderOnlyConstructors.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/overloadmethodsdeclarationorder/InputOverloadMethodsDeclarationOrder.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/fallthrough"
                    + "/InputFallThrough.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/fallthrough"
                    + "/InputFallThrough3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/fallthrough"
                    + "/InputFallThroughDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/innerassignment"
                    + "/InputInnerAssignment.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/unnecessaryparentheses/InputUnnecessaryParenthesesIfStatement.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/equalshashcode"
                    + "/InputEqualsHashCodeSemantic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/finallocalvariable"
                    + "/InputFinalLocalVariableFalsePositives.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/finallocalvariable"
                    + "/InputFinalLocalVariableAssignedMultipleTimes.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/finallocalvariable"
                    + "/InputFinalLocalVariable2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/finallocalvariable"
                    + "/InputFinalLocalVariable.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/onestatementperline"
                    + "/InputOneStatementPerLineSingleLine.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/onestatementperline"
                    + "/InputOneStatementPerLineMultiline.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/magicnumber"
                    + "/InputMagicNumber_4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/magicnumber"
                    + "/InputMagicNumber_2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/magicnumber"
                    + "/InputMagicNumber.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/magicnumber"
                    + "/InputMagicNumber_6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/magicnumber"
                    + "/InputMagicNumber_3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/magicnumber"
                    + "/InputMagicNumber_1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/magicnumber"
                    + "/InputMagicNumber_7.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/magicnumber"
                    + "/InputMagicNumber_5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/coding/noenumtrailingcomma"
                    + "/InputNoEnumTrailingComma.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/emptylineseparator/InputEmptyLineSeparatorWithComments2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/emptylineseparator/InputEmptyLineSeparatorWithComments.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/emptylineseparator/InputEmptyLineSeparator.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/emptylineseparator"
                    + "/InputEmptyLineSeparatorNoViolationOnEmptyLineBeforeComments.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/emptylineseparator/InputEmptyLineSeparator2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace/whitespaceafter"
                    + "/InputWhitespaceAfterDefaultConfig.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace/whitespaceafter"
                    + "/InputWhitespaceAfterTypeCast.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad"
                    + "/InputParenPadLeftRightAndNoSpace2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad"
                    + "/InputParenPadWhitespace.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad"
                    + "/InputParenPadLeftRightAndNoSpace3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad"
                    + "/InputParenPadWhitespace2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace/parenpad"
                    + "/InputParenPadLeftRightAndNoSpace1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespacebefore/InputNoWhitespaceBeforeDotAllowLineBreaks.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespacebefore/InputNoWhitespaceBeforeDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespacebefore/InputNoWhitespaceBeforeDot.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/filetabcharacter/InputFileTabCharacterSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/filetabcharacter/InputFileTabCharacterSimple1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/whitespacearound/InputWhitespaceAroundSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/whitespacearound/InputWhitespaceAroundBraces.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/whitespacearound/InputWhitespaceAroundBraces2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/whitespacearound/InputWhitespaceAroundKeywordsAndOperators.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespaceafter/InputNoWhitespaceAfterTestDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespaceafter/InputNoWhitespaceAfterTestAllowLineBreaks.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespaceafter/InputNoWhitespaceAfterTestTypecast.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespaceafter/InputNoWhitespaceAfterNewTypeStructure.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespaceafter/InputNoWhitespaceAfterTestAllTokens.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/nowhitespaceafter/InputNoWhitespaceAfterArrayDeclarations2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/typecastparenpad/InputTypecastParenPadWhitespaceTestSpace.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/whitespace"
                    + "/typecastparenpad/InputTypecastParenPadWhitespace.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/upperell"
                    + "/InputUpperEllSemantic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/emptycatchblock"
                    + "/InputEmptyCatchBlockDefaultLF.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/emptycatchblock"
                    + "/InputEmptyCatchBlockDefault2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/emptycatchblock"
                    + "/InputEmptyCatchBlockDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly"
                    + "/InputRightCurlyTestAloneOrSingleline.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly"
                    + "/InputRightCurlyLeftTestNewLine.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly"
                    + "/InputRightCurlyLeftTestDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly"
                    + "/InputRightCurlyLeftTestShouldStartLine2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly"
                    + "/InputRightCurlyLeftTestSame.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly"
                    + "/InputRightCurlyLeftTestAlone.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/rightcurly"
                    + "/InputRightCurlyTestWithAnnotations.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/needbraces"
                    + "/InputNeedBracesTestSingleLineCaseDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/needbraces"
                    + "/InputNeedBracesLoopBodyFalse.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/needbraces"
                    + "/InputNeedBracesTestIt.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/needbraces"
                    + "/InputNeedBracesTestItWithAllowsOn.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/needbraces"
                    + "/InputNeedBracesSingleLineStatements.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/needbraces"
                    + "/InputNeedBracesLoopBodyTrue.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/leftcurly"
                    + "/InputLeftCurlyTestMissingBraces.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/leftcurly"
                    + "/InputLeftCurlyTestDefault3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/blocks/leftcurly"
                    + "/InputLeftCurlyTestNewLine3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/design/visibilitymodifier"
                    + "/InputVisibilityModifierSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/design/designforextension"
                    + "/InputDesignForExtensionIgnoredAnnotations.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/design/designforextension"
                    + "/InputDesignForExtension.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/design/designforextension"
                    + "/InputDesignForExtensionOverridableMethods.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/design/finalclass"
                    + "/InputFinalClass.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/finalparameters"
                    + "/InputFinalParameters4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/finalparameters"
                    + "/InputFinalParameters2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/finalparameters"
                    + "/InputFinalParameters5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/finalparameters"
                    + "/InputFinalParameters3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/finalparameters"
                    + "/InputFinalParameters.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/modifier/redundantmodifier"
                    + "/InputRedundantModifierIt.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/modifier/modifierorder"
                    + "/InputModifierOrderTypeAnnotations.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/modifier/modifierorder"
                    + "/InputModifierOrderIt.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/metrics"
                    + "/cyclomaticcomplexity/InputCyclomaticComplexity.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/metrics"
                    + "/classfanoutcomplexity"
                    + "/InputClassFanOutComplexityRemoveIncorrectAnnotationToken.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/metrics/npathcomplexity"
                    + "/InputNPathComplexityOverflow.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/metrics/npathcomplexity"
                    + "/InputNPathComplexity.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/metrics/npathcomplexity"
                    + "/InputNPathComplexityDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/metrics/npathcomplexity"
                    + "/InputNPathComplexityDefault2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/packagename"
                    + "/InputPackageNameSimple1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/packagename"
                    + "/InputPackageNameSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/catchparametername"
                    + "/InputCatchParameterNameSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/membername"
                    + "/InputMemberNameSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/localvariablename"
                    + "/InputLocalVariableName.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/staticvariablename"
                    + "/InputStaticVariableName2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/staticvariablename"
                    + "/InputStaticVariableName1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/methodname"
                    + "/InputMethodNameSimple.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/localfinalvariablename/InputLocalFinalVariableName.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/localfinalvariablename/InputLocalFinalVariableName1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/parametername"
                    + "/InputParameterName.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/parametername"
                    + "/InputParameterNameOne.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/constantname"
                    + "/InputConstantNameSimple1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming/constantname"
                    + "/InputConstantNameSimple2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname"
                    + "/InputAbbreviationAsWordInNameIgnoreStaticKeepStaticFinal.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameIgnore.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameIgnoreStatic.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameType3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameType2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameIgnoreStaticFinal"
                    + ".java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameType.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname"
                    + "/InputAbbreviationAsWordInNameIgnoreNonStaticFinal.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameType5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname"
                    + "/InputAbbreviationAsWordInNameIgnoreFinalKeepStaticFinal.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameType4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameIgnoreFinal.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameNoIgnore.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/checks/naming"
                    + "/abbreviationaswordinname/InputAbbreviationAsWordInNameType6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/InputRegressionJavaClass1"
                    + ".java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputAntlr4AstRegressionCassandraInputWithComments.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputAntlr4AstRegressionCommentsOnAnnotationsAndEnums.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputAntlr4AstRegressionTrickySwitch.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputAntlr4AstRegressionKeywordsAndOperators.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputRegressionJavaClass1.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputAntlr4AstRegressionTrickySwitchWithComments.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputAntlr4AstRegressionFallThroughDefault.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputAntlr4AstRegressionNewTypeTree.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/meta/javadocmetadatascraper"
                    + "/InputJavadocMetadataScraperAnnotationUseStyleCheck.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/xpath/xpathmapper"
                    + "/InputXpathMapperStringConcat.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter11.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter3.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter4.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter9.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter6.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter7.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter2.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter8.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter10.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/filters/suppressioncommentfilter"
                    + "/InputSuppressionCommentFilter5.java",
            "src/test/resources/com/puppycrawl/tools/checkstyle/main/InputMainComplexityOverflow"
                    + ".java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/javadoc"
                    + "/atclauseorder/InputAtclauseOrderLotsOfRecords.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/sizes"
                    + "/recordcomponentnumber/InputRecordComponentNumberMax1.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/sizes"
                    + "/recordcomponentnumber/InputRecordComponentNumberMax20.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/sizes"
                    + "/recordcomponentnumber/InputRecordComponentNumberPrivateModifier.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/sizes"
                    + "/recordcomponentnumber/InputRecordComponentNumber.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/variabledeclarationusagedistance"
                    + "/InputVariableDeclarationUsageDistanceCheckSwitchExpressions.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/finallocalvariable/InputFinalLocalVariableCheckSwitchExpressions.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/coding"
                    + "/multiplestringliterals/InputMultipleStringLiteralsTextBlocks.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/blocks"
                    + "/needbraces/InputNeedBracesTestSwitchExpression.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/blocks"
                    + "/emptyblock/InputEmptyBlockSwitchExpressions.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/metrics"
                    + "/cyclomaticcomplexity/InputCyclomaticComplexityRecords.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/checks/metrics"
                    + "/javancss/InputJavaNCSSRecordsAndCompactCtors.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/grammar/java14"
                    + "/InputJava14TextBlocks.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/grammar/java14"
                    + "/InputJava14SwitchExpression.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/grammar/java14"
                    + "/InputJava14InstanceofWithPatternMatching.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/grammar/java14"
                    + "/InputJava14Records.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/grammar/antlr4"
                    + "/InputAntlr4AstRegressionUncommon.java",
            "src/test/resources-noncompilable/com/puppycrawl/tools/checkstyle/javaparser"
                    + "/InputJavaParserFullJavaIdentifierSupport.java",
            "src/it/resources/com/google/checkstyle/test/chapter2filebasic/rule231filetab"
                    + "/InputFileTabCharacter.java",
            "src/it/resources/com/google/checkstyle/test/chapter2filebasic/rule232specialescape"
                    + "/InputIllegalTokenText.java",
            "src/it/resources/com/google/checkstyle/test/chapter7javadoc/rule72thesummaryfragment"
                    + "/InputCorrectSummaryJavaDocCheck.java",
            "src/it/resources/com/google/checkstyle/test/chapter7javadoc/rule713atclauses"
                    + "/InputIncorrectAtClauseOrderCheck.java",
            "src/it/resources/com/google/checkstyle/test/chapter7javadoc/rule713atclauses"
                    + "/InputJavaDocTagContinuationIndentation.java",
            "src/it/resources/com/google/checkstyle/test/chapter7javadoc/rule713atclauses"
                    + "/InputCorrectAtClauseOrderCheck.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule411bracesareused"
                    + "/InputNeedBraces.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule4861blockcommentstyle"
                    + "/InputCommentsIndentationCommentIsAtTheEndOfBlock.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule4861blockcommentstyle/InputCommentsIndentationSurroundingCode.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule4861blockcommentstyle/InputCommentsIndentationInSwitchBlock.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule413emptyblocks"
                    + "/InputEmptyBlockBasic.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule4842fallthrough"
                    + "/InputFallThrough.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule43onestatement"
                    + "/InputOneStatementPerLine.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule452indentcontinuationlines/InputIndentationCorrectIfAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule452indentcontinuationlines/InputIndentationCorrectReturnAndParameter"
                    + ".java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule452indentcontinuationlines"
                    + "/InputIndentationCorrectWhileDoWhileAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule452indentcontinuationlines/InputIndentationCorrectFieldAndParameter"
                    + ".java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule42blockindentation/InputIndentationCorrectIfAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule42blockindentation/InputIndentationCorrectReturnAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule42blockindentation/InputIndentationCorrectWhileDoWhileAndParameter"
                    + ".java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule42blockindentation/InputIndentationCorrectFieldAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule412nonemptyblocks"
                    + "/InputRightCurlyOtherAlone.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule412nonemptyblocks"
                    + "/InputRightCurlyOther.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule412nonemptyblocks"
                    + "/InputLeftCurlyBraces.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule44columnlimit"
                    + "/InputLineLength.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule4822variabledistance/InputVariableDeclarationUsageDistanceCheck.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule461verticalwhitespace/InputEmptyLineSeparator.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule451wheretobreak"
                    + "/InputOperatorWrap.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule451wheretobreak"
                    + "/InputMethodParamPad.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule487modifiers"
                    + "/InputModifierOrder.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule4841indentation"
                    + "/InputIndentationCorrectIfAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule4841indentation"
                    + "/InputIndentationCorrectReturnAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule4841indentation"
                    + "/InputIndentationCorrectWhileDoWhileAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting/rule4841indentation"
                    + "/InputIndentationCorrectFieldAndParameter.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule462horizontalwhitespace/InputParenPad.java",
            "src/it/resources/com/google/checkstyle/test/chapter4formatting"
                    + "/rule462horizontalwhitespace/InputWhitespaceAroundBasic.java",
            "src/it/resources/com/google/checkstyle/test/chapter3filestructure/rule332nolinewrap"
                    + "/InputLineLength.java",
            "src/it/resources/com/google/checkstyle/test/chapter3filestructure/rule3sourcefile"
                    + "/InputEmptyLineSeparator.java",
            "src/it/resources/com/google/checkstyle/test/chapter5naming"
                    + "/rule525nonconstantfieldnames/InputMemberNameSimple.java",
            "src/it/resources/com/google/checkstyle/test/chapter6programpractice/rule64finalizers"
                    + "/InputNoFinalizeExtend.java"
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
        final String userDir = System.getProperty("user.dir");
        for (File file : theFiles) {
            if (!isBadInputFile(file.getName())) {
                final long count = Files.lines(file.toPath()).count();
                final String currFilePath = file.getPath();
                if (count > maxLines
                        && !isTempFile(file.getName())
                        && !FILE_SIZE_SUPPRESSIONS.contains(
                        currFilePath.substring(userDir.length() + 1))) {
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
