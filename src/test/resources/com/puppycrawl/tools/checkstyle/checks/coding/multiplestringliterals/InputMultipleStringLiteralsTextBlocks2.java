/*
MultipleStringLiterals
allowedDuplicates = (default)1
ignoreStringsRegexp = (null)
ignoreOccurrenceContext = (default)ANNOTATION


*/


package com.puppycrawl.tools.checkstyle.checks.coding.multiplestringliterals;

public class InputMultipleStringLiteralsTextBlocks2 {
    // violation below 'The String "another test" appears 2 times in the file.'
    String str3 = "another test"; // occurrence #1
    String str4 = """
            another test"""; // violation #1

    // violation below 'The String "" appears 6 times in the file.'
    String str5a = ""; // occurrence #1
    String str5b = """
            """; // violation #1
    String str6 = """
                        """; // violation #2
    String str7 = """
"""; // violation #3

    String str8 = """
                             """; // violation #4
    // violation below 'The String "        .\\n         .\\n.\\n" appears 2 times in the file.'
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
    // violation below 'The String "             foo\\n\\n\\n        bar" appears 2 times in the file.'
    String str10a = """
             foo


        bar""";
    String str10b = """
             foo


        bar""";
    String emptyWithLotsOfLines = """




""";
}
