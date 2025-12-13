/*
IllegalTokenText
format = a href
ignoreCase = (default)false
message = (null)
tokens = STRING_LITERAL, TEXT_BLOCK_CONTENT


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

public class InputIllegalTokenTextTextBlocks {
    public void methodWithLiterals() {
        final String ref = "<a href=\""; // violation 'Token text matches the illegal pattern .a href.'
        final String refCase1 = "<A hReF=\"";

        final String ref2 = """ // violation 'Token text matches the illegal pattern .a href.'
                <a href=\"""";
        final String refCase2 = """
                <A hReF=\"""";

        String escape = """
                <html>\u000D\u000A\n
                    <body>\u000D\u000A\n
                        <p>Hello, world</p>\u000D\u000A\n
                    </body>\u000D\u000A\n
                </html>\u000D\u000A
                """;
        String testMoreEscapes = """ // violation 'Token text matches the illegal pattern .a href.'
                fun with a href\n
                whitespace\t\r
                and other escapes \"""
                """;
        String evenMoreEscapes = """ // violation 'Token text matches the illegal pattern .a href.'
                \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
                \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
                \\uffffa href \\' \\\' \'
                """;
        String concat = """ // violation 'Token text matches the illegal pattern .a href.'
                The quick brown fox""" +
                "  \n" + """ // violation 'Token text matches the illegal pattern .a href.'
                jumps over the lazy dog a href
                """;
    }
}
