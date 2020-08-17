//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

/* Config:
 *
 * allowedDuplicates = 1
 * ignoreStringsRegexp = null
 * ignoreOccurrenceContext = ANNOTATION
 */
public class InputMultipleStringLiteralsTextBlocks {
    String string1 = "string";
    String string2 = "string"; // violation

    String string3 = """
            other string""";
    String string4 = """
            other string"""; // violation
    String string5 = """
            other string
            """;
    String string6 = """
            other string
            """; // violation

    String escape1 = """
            <html>\u000D\u000A\n
                <body>\u000D\u000A\n
                    <p>Hello, world</p>\u000D\u000A\n
                </body>\u000D\u000A\n
            </html>\u000D\u000A
            """;
    String testMoreEscapes1 = """
            fun with\n
            whitespace\t\r
            and other escapes \"""
            """;
    String evenMoreEscapes1 = """
            \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
            \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
            \\' \\\' \'
            """;
    String escape2 = """
            <html>\u000D\u000A\n
                <body>\u000D\u000A\n
                    <p>Hello, world</p>\u000D\u000A\n
                </body>\u000D\u000A\n
            </html>\u000D\u000A
            """; // violation
    String testMoreEscapes2 = """
            fun with\n
            whitespace\t\r
            and other escapes \"""
            """; // violation
    String evenMoreEscapes2 = """
            \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
            \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
            \\' \\\' \'
            """; // violation
}
