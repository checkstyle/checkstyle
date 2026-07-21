/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreStringsRegexp = (null)
ignoreOccurrenceContext = (default)ANNOTATION


*/


package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

public class InputMultipleStringLiteralsTextBlocks1 {
    // violation below 'The String "string" appears 3 times in the file.'
    String string1 = "string"; // occurrence #1
    String string2a = "string";
    String string2b = """
            string""";

    // violation below 'The String "other string" appears 2 times in the file.'
    String string3 = """
            other string"""; // occurrence #1
    String string4 = """
            other string""";
    // violation below 'The String "other string\\n" appears 2 times.*'
    String string5 = """
            other string
            """; // occurrence #1
    String string6 = """
            other string
            """;
    // violation below 'The String "<html>.*" appears 2 times in the file.'
    String escape1 = """
            <html>\u000D\u000A\n\u2000
                <body>\u000D\u000A\n\u2000
                    <p>Hello, world</p>\u000D\u000A\n\u2000
                </body>\u000D\u000A\n\u2000
            </html>\u000D\u000A\u2000
            """; // occurrence #1
    // violation below 'The String "fun with.*" appears 2 times in the file.'
    String testMoreEscapes1 = """
            fun with\n
             whitespace\t\r
             and other escapes \"""
            """; // occurrence #1
    // violation below 'The String "\\b \\f.*" appears 2 times in the file.'
    String evenMoreEscapes1 = """
            \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
            \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
            \\' \\\' \'
            """; // occurrence #1
    String escape2 = """
            <html>\u000D\u000A\n\u2000
                <body>\u000D\u000A\n\u2000
                    <p>Hello, world</p>\u000D\u000A\n\u2000
                </body>\u000D\u000A\n\u2000
            </html>\u000D\u000A\u2000
            """;
    String testMoreEscapes2 = """
            fun with\n
             whitespace\t\r
             and other escapes \"""
            """;
    String evenMoreEscapes2 = """
            \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
            \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
            \\' \\\' \'
            """;

    // violation below 'The String "foo" appears 4 times in the file.'
    String str1a = "foo"; // occurrence #1
    String str1b = "foo";
    String str2a = """
            foo""";
    String str2b = """
            foo""";
}
