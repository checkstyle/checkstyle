/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting13 {
    public static void testIndentationCheckSkippedWhenOpeningQuotesNotAlone() {
        final String value =
            """
under-indented content line should only be checked by mutant
            content before close       """;
        // 2 violations above:
        // 'Closing quotes (""") of text-block should not be preceded by non-whitespace characters.'
        // 'Text-block quotes are not vertically aligned.'
    }
}
