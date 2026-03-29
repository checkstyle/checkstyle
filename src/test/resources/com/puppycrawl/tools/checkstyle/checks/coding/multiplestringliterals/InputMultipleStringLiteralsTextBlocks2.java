/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreStringsRegexp = (null)
ignoreOccurrenceContext = (default)ANNOTATION


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

public class InputMultipleStringLiteralsTextBlocks2 {
    String str3 = "another test"; // occurrence #1 // violation
    String str4 = """
            another test"""; // violation #1

    String str5a = ""; // occurrence #1 // violation
    String str5b = """
            """; // violation #1
    String str6 = """
                        """; // violation #2
    String str7 = """
"""; // violation #3

    String str8 = """
                             """; // violation #4
     // violation below
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
     // violation below
    String str10a = """
             foo


        bar""";
    String str10b = """
             foo


        bar""";
    String emptyWithLotsOfLines = """




""";
}
