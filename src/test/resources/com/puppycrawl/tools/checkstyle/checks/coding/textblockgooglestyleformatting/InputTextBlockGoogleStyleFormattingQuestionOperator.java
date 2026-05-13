/*
TextBlockGoogleStyleFormatting Question Operator


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingQuestionOperator {

    public static String testQuestionOperatorWithTextBlock() {
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        Object value = null;
        String result = value instanceof String ? """
            text block
            """ : null; // violation 'Text-block quotes are not vertically aligned'

        return result;
    }
}
