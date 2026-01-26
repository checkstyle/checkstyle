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
        final String ref = "<a href=\""; // violation 'Token text matches the illegal pattern '"''
        // violation below 'Token text matches the illegal pattern '"'.'
        final String refCase1 = "<A hReF=\"";

        final String ref2 = """
                <a href=\""""; // violation above 'Token text matches the illegal pattern '"''
        final String refCase2 = """
                <A hReF=\""""; // violation above 'Token text matches the illegal pattern '"''

        String escape = """
                <html>\u000D\u000A\n
                    <body>\u000D\u000A\n
                        <p>Hello, world</p>\u000D\u000A\n
                    </body>\u000D\u000A\n
                </html>\u000D\u000A
                """; // violation below 'Token text matches the illegal pattern '"''
        String testMoreEscapes = """
                fun with\n
                whitespace\t\r
                and other escapes \"""
                """; // violation below 'Token text matches the illegal pattern '"''
        String evenMoreEscapes = """
                \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
                \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
                \\uffff \\' \\\' \'
                """;
        // violation 2 lines below 'Token text matches the illegal pattern '"''
        String concat = """
                The quick brown fox""" + "  \n" + """
                jumps over the lazy dog
                """;
    }
}
