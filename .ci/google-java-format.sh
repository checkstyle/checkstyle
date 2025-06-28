#!/bin/bash

set -e

JAR_PATH="$1"

echo "Checking that all excluded java files in this script have matching InputFormatted* file:"
NOT_FOUND_CONTENT=$(grep -e '^  .*/Input' "${BASH_SOURCE}" \
  | sed -E 's/.*Input([^\.]+)\..*java.*/\1/' \
  | while read -r name; do \
    [[ $(find ./src -type f -name "InputFormatted${name}.java") ]] \
    || echo "Create InputFormatted${name}.java for Input${name}.java";\
  done)

if [[ $(echo -n "$NOT_FOUND_CONTENT" | wc --chars) -eq 0 ]]; then
  echo "Excluded Input files matches to InputFormatted files."
else
  echo "not found matches: $NOT_FOUND_CONTENT"
  exit 1
fi

echo "Formatting all Input files file at src/it/resources/com/google/checkstyle/test :"
COMPILABLE_INPUT_PATHS=($(find src/it/resources/com/google/checkstyle/test/ -name "Input*.java" \
    | sed "s|src/it/resources/com/google/checkstyle/test/||" \
    | grep -v "rule231filetab/InputWhitespaceCharacters.java" \
    | grep -v "rule3sourcefile/InputSourceFileStructure.java" \
    | grep -v "rule332nolinewrap/InputNoLineWrapping.java" \
    | grep -v "rule333orderingandspacing/InputOrderingAndSpacing1.java" \
    | grep -v "rule333orderingandspacing/InputOrderingAndSpacing2.java" \
    | grep -v "rule333orderingandspacing/InputOrderingAndSpacing3.java" \
    | grep -v "rule333orderingandspacing/InputOrderingAndSpacing4.java" \
    | grep -v "rule333orderingandspacing/InputOrderingAndSpacing5.java" \
    | grep -v "rule333orderingandspacing/InputOrderingAndSpacingValid.java" \
    | grep -v "rule333orderingandspacing/InputOrderingAndSpacingValid2.java" \
    | grep -v "rule412nonemptyblocks/InputNonemptyBlocksLeftRightCurly.java" \
    | grep -v "rule412nonemptyblocks/InputLeftCurlyAnnotations.java" \
    | grep -v "rule412nonemptyblocks/InputLeftCurlyMethod.java" \
    | grep -v "rule412nonemptyblocks/InputRightCurly.java" \
    | grep -v "rule412nonemptyblocks/InputRightCurlyOther.java" \
    | grep -v "rule412nonemptyblocks/InputRightCurlySwitchCase.java" \
    | grep -v "rule412nonemptyblocks/InputRightCurlySwitchCasesBlocks.java" \
    | grep -v "rule412nonemptyblocks/InputTryCatchIfElse.java" \
    | grep -v "rule412nonemptyblocks/InputTryCatchIfElse2.java" \
    | grep -v "rule413emptyblocks/InputEmptyBlocksAndCatchBlocks.java" \
    | grep -v "rule42blockindentation/ClassWithChainedMethods.java" \
    | grep -v "rule42blockindentation/InputIndentationCodeBlocks.java" \
    | grep -v "rule43onestatement/InputOneStatementPerLine.java" \
    | grep -v "rule44columnlimit/InputColumnLimit.java" \
    | grep -v "rule451wheretobreak/InputOperatorWrap.java" \
    | grep -v "rule451wheretobreak/InputMethodParamPad.java" \
    | grep -v "rule451wheretobreak/InputSeparatorWrap.java" \
    | grep -v "rule451wheretobreak/InputSeparatorWrapComma.java" \
    | grep -v "rule451wheretobreak/InputSeparatorWrapMethodRef.java" \
    | grep -v "rule451wheretobreak/InputSeparatorWrapEllipsis.java" \
    | grep -v "rule451wheretobreak/InputSeparatorWrapArrayDeclarator.java" \
    | grep -v "rule451wheretobreak/InputLambdaBodyWrap.java" \
    | grep -v "rule452indentcontinuationlines/ClassWithChainedMethods.java" \
    | grep -v "rule461verticalwhitespace/InputVerticalWhitespace.java" \
    | grep -v "rule462horizontalwhitespace/InputWhitespaceAroundBasic.java" \
    | grep -v "rule462horizontalwhitespace/InputWhitespaceAroundArrow.java" \
    | grep -v "rule462horizontalwhitespace/InputWhitespaceAfterBad.java" \
    | grep -v "rule462horizontalwhitespace/InputWhitespaceAfterGood.java" \
    | grep -v "rule462horizontalwhitespace/InputParenPad.java" \
    | grep -v "rule462horizontalwhitespace/InputNoWhitespaceBeforeEmptyForLoop.java" \
    | grep -v "rule462horizontalwhitespace/InputNoWhitespaceBeforeColonOfLabel.java" \
    | grep -v "rule462horizontalwhitespace/InputNoWhitespaceBeforeCaseDefaultColon.java" \
    | grep -v "rule462horizontalwhitespace/InputMethodParamPad.java" \
    | grep -v "rule462horizontalwhitespace/InputWhitespaceAroundGenerics.java" \
    | grep -v "rule462horizontalwhitespace/InputGenericWhitespace.java" \
    | grep -v "rule4821onevariableperline/InputOneVariablePerDeclaration.java" \
    | grep -v "rule4841indentation/InputClassWithChainedMethods.java" \
    | grep -v "rule4841indentation/InputAnnotationArrayInitMultiline.java" \
    | grep -v "rule4841indentation/InputAnnotationArrayInitMultiline2.java" \
    | grep -v "rule4841indentation/InputNewKeywordChildren.java" \
    | grep -v "rule4852classannotations/InputClassAnnotations.java" \
    | grep -v "rule4853methodsandconstructorsannotations/InputMethodsAndConstructorsAnnotations.java" \
    | grep -v "rule4854fieldannotations/InputFieldAnnotations.java" \
    | grep -v "rule4861blockcommentstyle/InputCommentsIndentationCommentIsAtTheEndOfBlock.java" \
    | grep -v "rule4861blockcommentstyle/InputCommentsIndentationInEmptyBlock.java" \
    | grep -v "rule4861blockcommentstyle/InputCommentsIndentationInSwitchBlock.java" \
    | grep -v "rule4861blockcommentstyle/InputCommentsIndentationSurroundingCode.java" \
    | grep -v "rule487modifiers/InputModifierOrder.java" \
    | grep -v "rule522classnames/InputClassNames.java" \
    | grep -v "rule53camelcase/InputCamelCaseDefined.java" \
    | grep -v "rule711generalform/InputSingleLineJavadocAndInvalidJavadocPosition.java" \
    | grep -v "rule712paragraphs/InputIncorrectRequireEmptyLineBeforeBlockTagGroup.java" \
    | grep -v "rule712paragraphs/InputIncorrectJavadocParagraph.java" \
    | grep -v "rule713atclauses/InputJavaDocTagContinuationIndentation.java" \
    | grep -v "rule734nonrequiredjavadoc/InputInvalidJavadocPosition.java" \
    ))

for INPUT_PATH in "${COMPILABLE_INPUT_PATHS[@]}"; do
  java -jar "$JAR_PATH" --replace src/it/resources/com/google/checkstyle/test/"$INPUT_PATH"
done

echo "Formatting all Non-compatiable Input files file at src/it/resources-noncompilable/com/google/checkstyle/test :"
NON_COMPILABLE_INPUT_PATHS=($(find src/it/resources-noncompilable/com/google/checkstyle/test/ -name "Input*.java" \
    | sed "s|src/it/resources-noncompilable/com/google/checkstyle/test/||" \
    | grep -v "rule43onestatement/InputOneStatementPerLine.java" \
    | grep -v "rule462horizontalwhitespace/InputWhitespaceAroundArrow.java" \
    | grep -v "rule462horizontalwhitespace/InputWhitespaceAroundWhen.java" \
    | grep -v "rule4841indentation/InputLambdaChild.java" \
    | grep -v "rule4841indentation/InputSwitchOnStartOfTheLine.java" \
    | grep -v "rule4841indentation/InputCatchParametersOnNewLine.java" \
    | grep -v "rule4841indentation/InputLambdaAndChildOnTheSameLine.java" \
    | grep -v "rule4841indentation/InputSingleSwitchStatementWithoutCurly.java" \
    | grep -v "rule4841indentation/InputSwitchWrappingIndentation.java" \
    | grep -v "rule526parameternames/InputRecordComponentName.java" \
    | grep -v "rule527localvariablenames/InputPatternVariableNameEnhancedInstanceofTestDefault.java" \
    ))

echo "${NON_COMPILABLE_INPUT_PATHS[@]}"

for INPUT_PATH in "${NON_COMPILABLE_INPUT_PATHS[@]}"; do
  java -jar "$JAR_PATH" --replace src/it/resources-noncompilable/com/google/checkstyle/test/"$INPUT_PATH"
done
