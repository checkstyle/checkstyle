/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting15 {
    public static void testCoverageBranches() {
        final String empty =
                """
                """;

        final String trailingSpaces =
                """
                content with trailing spaces\s\s
                """;

        final String spacesOnly =
                """
\s\s
                """;

        final String explicitLeadingSpace =
                """
\sunder-indented to exercise getIndentation branch
                """;
        // violation 7 lines above 'Each line in the text-block should be indented'
        // violation 3 lines above 'Each line in the text-block should be indented'

                final String ignore = empty + trailingSpaces + spacesOnly + explicitLeadingSpace;
    }
}
