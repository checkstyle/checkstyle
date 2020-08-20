//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

/* Config:
 *
 * allowedDuplicates = 1
 * ignoreStringsRegexp = null
 * ignoreOccurrenceContext = ANNOTATION
 */
public class InputMultipleStringLiteralsTextBlocks {
    String string1 = "string"; // occurance #1
    String string2a = "string"; // violation #1
    String string2b = """
            string"""; // violation #2

    String string3 = """
            other string"""; // occurance #1
    String string4 = """
            other string"""; // violation
    String string5 = """
            other string
            """; // occurrence #1
    String string6 = """
            other string
            """; // violation

    String escape1 = """
            <html>\u000D\u000A\n\u2000
                <body>\u000D\u000A\n\u2000
                    <p>Hello, world</p>\u000D\u000A\n\u2000
                </body>\u000D\u000A\n\u2000
            </html>\u000D\u000A\u2000
            """; // occurrence #1
    String testMoreEscapes1 = """
            fun with\n
             whitespace\t\r
             and other escapes \"""
            """; // occurrence #1
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
            """; // violation #1
    String testMoreEscapes2 = """
            fun with\n
             whitespace\t\r
             and other escapes \"""
            """; // violation #1
    String evenMoreEscapes2 = """
            \b \f \\ \0 \1 \2 \r \r\n \\r\\n \\''
            \\11 \\57 \n\\n\n\\\n\n \\ ""a "a
            \\' \\\' \'
            """; // violation #1

    String str1a = "foo"; // occurrence #1
    String str1b = "foo"; // violation #1
    String str2a = """
            foo"""; // violation #2
    String str2b = """
            foo"""; // violation #3


    String str3 = "another test"; // occurrence #1
    String str4 = """
            another test"""; // violation #1

    String str5a = ""; // occurrence #1
    String str5b = """
            """; // violation #1
    String str6 = """
                        """; // violation #2
    String str7 = """
"""; // violation #3

    String str8 = """
                             """; // violation #4

    String str8b = """
        .
         .
.
        """;

    String str8c = """
        .
         .
.
        """;

    String str9a = """
            test    """; // trailing whitespace removed per javac result
    String str9b = "test    "; // trailing whitespace not removed, strings are not equal!

    String str10a = """
             foo


        bar""";
    String str10b = """
             foo


        bar""";
    String emptyWithLotsOfLines = """




""";

}
