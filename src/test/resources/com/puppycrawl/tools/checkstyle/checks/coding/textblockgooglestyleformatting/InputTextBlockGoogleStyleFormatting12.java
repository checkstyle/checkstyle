/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting12 {
    public static void testIndentationCheckSkippedWhenQuotesUnaligned() {
        final String value =
            """
Under-indented line should not be checked when quotes are unaligned
              """; // violation 'Text-block quotes are not vertically aligned'
    }
}
