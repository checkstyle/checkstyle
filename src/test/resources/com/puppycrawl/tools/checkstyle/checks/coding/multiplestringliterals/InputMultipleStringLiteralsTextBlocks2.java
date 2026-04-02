/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreStringsRegexp = (null)
ignoreOccurrenceContext = (default)ANNOTATION


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

public class InputMultipleStringLiteralsTextBlocks2 {
    String str3 = "another test"; // violation, 'The String "another test" appears 2 times in the file.'
    String str4 = """
            another test""";

    String str5a = ""; // violation, 'The String "" appears 6 times in the file.'
    String str5b = """
            """;
    String str6 = """
                        """;
    String str7 = """
""";

    String str8 = """
                             """;
     // violation below, 'The String ".*" appears 2 times in the file.'
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
     // violation below, 'The String ".*bar" appears 2 times in the file.'
    String str10a = """
             foo


        bar""";
    String str10b = """
             foo


        bar""";
    String emptyWithLotsOfLines = """




""";
}
