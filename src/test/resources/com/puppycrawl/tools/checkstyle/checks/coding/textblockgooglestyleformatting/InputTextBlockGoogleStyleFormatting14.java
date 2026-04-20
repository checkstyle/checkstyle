/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting14 {
    public static void testIndentationCheckSkippedWhenOnlyOpeningQuotesNotAlone() {
        consume("""
      under-indented content line that should not be checked
                """);
        // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'
    }

    private static void consume(String text) {
        // no code
    }
}
