/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingQuestionOperator {

    public static String test() {
        String result = true ? """
            text block
            """ : null; // violation 'Opening quotes (""") of text-block must be on the new line' 'Text-block quotes are not vertically aligned'

        return result;
    }
}
