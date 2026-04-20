/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting11 {
    public static void testBlankLines() {
        // Test with blank lines in content
        final String withBlankLine =
            """
            First line

            Third line
            """;

        // Test with whitespace-only line
        final String withWhitespaceLine =
            """
            First line
            \s\s\s\s
            Third line
            """;

        // Test with exactly same indentation as quotes
        final String sameIndentation =
            """
            Same indent
            """;

        // Test with content that is all whitespace at exact indentation level
        final String allWhitespace =
            """
            \s
            """;

        // Test empty text block (no content lines)
        final String empty =
            """
            """;

        // Test with zero indentation (content starts at column 0)
        final String zeroIndent =
            """
No indent // violation 'Each line in the text-block should be indented'
            """;

        // Test with content at exactly expected indentation (no violation)
        final String exactIndent =
            """
            Exactly at expected indentation
            Another line at expected indentation
            """;

        // Test with line that has only spaces (whitespace-only, should be skipped)
        final String whitespaceOnlyLine =
            """
            First line
            \s\s\s\s\s\s\s\s
            Third line
            """;

        // Test with line containing only whitespace (all whitespace)
        final String tabOnlyLine =
            """
            First line
            \s\s\s\s
            Third line
            """;
    }
}
