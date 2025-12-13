/*
IllegalTokenText
format = "
ignoreCase = (default)false
message = (default)
tokens = STRING_LITERAL, TEXT_BLOCK_CONTENT


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

public class InputIllegalTokenTextTextBlocksQuotes {
    public void methodWithLiterals() {
        final String ref =
                "<a href=\""; // violation 'Token text matches the illegal pattern '"'.'
        final String refCase1 =
                "<A hReF=\""; // violation 'Token text matches the illegal pattern '"'.'

        final String ref2 = """ // violation 'Token text matches the illegal pattern '"'.'
                <a href=\"""";
        final String refCase2 = """ // violation 'Token text matches the illegal pattern '"'.'
                <A hReF=\"""";

        String escape = """
                <html>\u000D\u000A\n
                    <body>\u000D\u000A\n
                        <p>Hello, world</p>\u000D\u000A\n
                    </body>\u000D\u000A\n
                </html>\u000D\u000A
                """;
        String testMoreEscapes = """ // violation 'Token text matches the illegal pattern '"'.'
                fun with\n
                whitespace\t\r
                and other escapes \"""
                """;
        String evenMoreEscapes = """ // violation 'Token text matches the illegal pattern '"'.'
                \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
                \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
                \\uffff \\' \\\' \'
                """;
        String concat = """ // violation 'Token text matches the illegal pattern '"'.'
                The quick brown fox""" +
                "  \n" + // violation 'Token text matches the illegal pattern '"'.'
                """ // violation 'Token text matches the illegal pattern '"'.'
                jumps over the lazy dog a href
                """;
    }
}
