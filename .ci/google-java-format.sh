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

echo "Checking that all excluded java files have same size as matching InputFormatted* file:"
MISMATCH_CONTENT=$(grep -e '^  .*/Input' "${BASH_SOURCE}" \
  | sed -E 's/.*"([^"]+Input[^"]+\.java)".*/\1/' \
  | while read -r path; do
    size=$(sed -E 's/\s*\/\/ violation.*//' "$path" | wc -c)
    name=$(echo "$path" | sed -E 's/.*Input([^\.]+)\.java.*/\1/')
    formatted_path=$(find ./src -type f -name "InputFormatted${name}.java")
    size_formatted=$(stat -c %s "$formatted_path")
    diff=$(( size - size_formatted ))
    if (( diff < 0 )); then
      diff=$(( -diff ))
    fi
    if (( diff > 20 )); then
      echo "Difference in $name and InputFormatted${name} is more than 20 bytes"
    fi
  done)

if [[ $(echo -n "$MISMATCH_CONTENT" | wc --chars) -eq 0 ]]; then
  echo "All excluded Input files match their InputFormatted counterparts in size."
else
  echo "Size mismatches found:"
  echo "$MISMATCH_CONTENT"
  exit 1
fi

echo "Formatting all Input files file at src/it/resources/com/google/checkstyle/test :"
COMPILABLE_INPUT_PATHS=($(find src/it/resources/com/google/checkstyle/test/ -name "Input*.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/chapter2filebasic/rule231filetab/InputWhitespaceCharacters.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule3sourcefile/InputSourceFileStructure.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule332nolinewrap/InputNoLineWrapping.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule333orderingandspacing/InputOrderingAndSpacing1.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule333orderingandspacing/InputOrderingAndSpacing2.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule333orderingandspacing/InputOrderingAndSpacing3.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule333orderingandspacing/InputOrderingAndSpacing4.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule333orderingandspacing/InputOrderingAndSpacing5.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule333orderingandspacing/InputOrderingAndSpacingValid.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule333orderingandspacing/InputOrderingAndSpacingValid2.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputNonemptyBlocksLeftRightCurly.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputLeftCurlyAnnotations.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputLeftCurlyMethod.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputRightCurly.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputRightCurlyOther.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputRightCurlySwitchCase.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputRightCurlySwitchCasesBlocks.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputTryCatchIfElse.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule412nonemptyblocks/InputTryCatchIfElse2.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule413emptyblocks/InputEmptyBlocksAndCatchBlocks.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule42blockindentation/ClassWithChainedMethods.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule42blockindentation/InputIndentationCodeBlocks.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule43onestatement/InputOneStatementPerLine.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule44columnlimit/InputColumnLimit.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputOperatorWrap.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputMethodParamPad.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputSeparatorWrap.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputSeparatorWrapComma.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputSeparatorWrapMethodRef.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputSeparatorWrapEllipsis.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputSeparatorWrapArrayDeclarator.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputLambdaBodyWrap.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule451wheretobreak/InputIllegalLineBreakAroundLambda.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule452indentcontinuationlines/ClassWithChainedMethods.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule461verticalwhitespace/InputVerticalWhitespace.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputWhitespaceAroundBasic.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputWhitespaceAroundArrow.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputWhitespaceAfterBad.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputWhitespaceAfterGood.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputParenPad.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputNoWhitespaceBeforeEmptyForLoop.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputNoWhitespaceBeforeColonOfLabel.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputNoWhitespaceBeforeCaseDefaultColon.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputMethodParamPad.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputWhitespaceAroundGenerics.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputGenericWhitespace.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule462horizontalwhitespace/InputWhitespaceAfterDoubleSlashes.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4821onevariableperline/InputOneVariablePerDeclaration.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4841indentation/InputClassWithChainedMethods.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4841indentation/InputAnnotationArrayInitMultiline.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4841indentation/InputAnnotationArrayInitMultiline2.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4841indentation/InputNewKeywordChildren.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4852classannotations/InputClassAnnotations.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4853methodsandconstructorsannotations/InputMethodsAndConstructorsAnnotations.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4854fieldannotations/InputFieldAnnotations.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4861blockcommentstyle/InputCommentsIndentationCommentIsAtTheEndOfBlock.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4861blockcommentstyle/InputCommentsIndentationInEmptyBlock.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4861blockcommentstyle/InputCommentsIndentationInSwitchBlock.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule4861blockcommentstyle/InputCommentsIndentationSurroundingCode.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule487modifiers/InputModifierOrder.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule489textblocks/InputTextBlocksGeneralForm.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule489textblocks/InputTextBlocksIndentation.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule522classnames/InputClassNames.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule53camelcase/InputCamelCaseDefined.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule711generalform/InputSingleLineJavadocAndInvalidJavadocPosition.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule712paragraphs/InputIncorrectRequireEmptyLineBeforeBlockTagGroup.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule712paragraphs/InputIncorrectJavadocParagraph.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule713atclauses/InputJavaDocTagContinuationIndentation.java" \
    | grep -v "src/it/resources/com/google/checkstyle/test/rule734nonrequiredjavadoc/InputInvalidJavadocPosition.java" \
    ))

for INPUT_PATH in "${COMPILABLE_INPUT_PATHS[@]}"; do
  java -jar "$JAR_PATH" --replace "$INPUT_PATH"
done

echo "Formatting all Non-compilable Input files file at src/it/resources-noncompilable/com/google/checkstyle/test :"
NON_COMPILABLE_INPUT_PATHS=($(find src/it/resources-noncompilable/com/google/checkstyle/test/ -name "Input*.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule43onestatement/InputOneStatementPerLine.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule462horizontalwhitespace/InputWhitespaceAroundArrow.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule462horizontalwhitespace/InputWhitespaceAroundWhen.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule4841indentation/InputLambdaChild.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule4841indentation/InputSwitchOnStartOfTheLine.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule4841indentation/InputCatchParametersOnNewLine.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule4841indentation/InputLambdaAndChildOnTheSameLine.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule4841indentation/InputSingleSwitchStatementWithoutCurly.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule4841indentation/InputSwitchWrappingIndentation.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule526parameternames/InputRecordComponentName.java" \
    | grep -v "src/it/resources-noncompilable/com/google/checkstyle/test/rule527localvariablenames/InputPatternVariableNameEnhancedInstanceofTestDefault.java" \
    ))

for INPUT_PATH in "${NON_COMPILABLE_INPUT_PATHS[@]}"; do
  java -jar "$JAR_PATH" --replace "$INPUT_PATH"
done
