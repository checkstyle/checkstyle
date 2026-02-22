/*
JavaLineLength
max = (default)100
tokens = TEXT_BLOCK_LITERAL_BEGIN


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.javalinelength;

public class InputJavaLineLengthTextBlockLiteral {

    String s1 = """
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical
            """;

    String s2 = """
        Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical""";

    String s3 = "Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical";
    // violation above, 'Line is longer than 100 characters (found 122).'

    String s4 = """
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical
            """;
}
