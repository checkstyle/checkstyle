/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting10 {
    public static void textFun() {
        final String simpleScript =
            """
     Less Indentation than expected
     Violation is expected here.
            """; // violation above, ok until #18227

        final String simpleScript1 =
            """
                More indentation than the quotes, ok.
            """;

        final String simpleScript2 =
                simpleScript +
                simpleScript1 +
                """
     Less Indentation than expected
                and each line has a different indentation value
                        Violation is expected here.
                """; // violation above, ok until #18227

        final String simpleScript3 = simpleScript +
            simpleScript1 +
"""
         this is simple script
""";

        final String simpleScript4 = simpleScript +
            simpleScript3.endsWith(
                """
                this is a simple sentence
                    this is a simple sentence
                       this is a simple sentence
                """);

        final String simpleScript5 =
                """
     test       """;
        // violation above '(""") of text-block should not be preceded by non-whitespace characte'
    }
}
