/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting11 {

    public static void killMutation() {

        final String withWhitespaceLine =
            """
            test1
            \s\s\s
            test2
            """;

        final String sameIndentation =
            """
            Same indent
            """;

        final String allWhitespace =
            """
            \s
            """;

        final String zeroIndent =
            """
test with no indent
            """;
        // violation 3 lines above 'Each line of text in the text block must be indented'

        final String properlyIndented =
            """
            test1
            test2
            """;

        final String string1 =
"""
\s\s\s\s
"""  ;

    }
}
