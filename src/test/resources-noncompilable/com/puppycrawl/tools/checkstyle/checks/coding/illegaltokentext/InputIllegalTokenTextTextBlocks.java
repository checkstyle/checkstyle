//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.illegaltokentext;

/* Config:
 *
 * format  = "a href"
 * ignoreCase = false
 * message = false
 * tokens = {STRING_LITERAL, TEXT_BLOCK_CONTENT}
 */
public class InputIllegalTokenTextTextBlocks {
    public void methodWithLiterals() {
        final String ref = "<a href=\""; // violation
        final String refCase1 = "<A hReF=\""; // ok

        final String ref2 = """
                <a href=\""""; // violation
        final String refCase2 = """
                <A hReF=\""""; // ok

        String escape = """
                <html>\u000D\u000A\n
                    <body>\u000D\u000A\n
                        <p>Hello, world</p>\u000D\u000A\n
                    </body>\u000D\u000A\n
                </html>\u000D\u000A
                """;
        String testMoreEscapes = """
                fun with a href\n
                whitespace\t\r
                and other escapes \"""
                """; // violation
        String evenMoreEscapes = """
                \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
                \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
                \\uffffa href \\' \\\' \'
                """;
        String concat = """
                The quick brown fox""" + "  \n" + """
                jumps over the lazy dog a href
                """; // violation
    }
}
