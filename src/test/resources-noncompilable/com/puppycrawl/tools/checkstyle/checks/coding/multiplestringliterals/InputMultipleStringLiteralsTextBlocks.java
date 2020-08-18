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
    String string2a = "string"; // violation
    String string2b = """
            string"""; // violation #1

    String string3 = """
            other string""";
    String string4 = """
            other string"""; // violation #1
    String string5 = """
            other string
            """; // occurrence #1
    String string6 = """
            other string
            """; // violation #1

    String escape1 = """
            <html>\u000D\u000A\n
                <body>\u000D\u000A\n
                    <p>Hello, world</p>\u000D\u000A\n
                </body>\u000D\u000A\n
            </html>\u000D\u000A
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
            <html>\u000D\u000A\n
                <body>\u000D\u000A\n
                    <p>Hello, world</p>\u000D\u000A\n
                </body>\u000D\u000A\n
            </html>\u000D\u000A
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

    String str9a = """
            test    """; // occurrence #1
    String str9b = "test    "; // violation #1

}
