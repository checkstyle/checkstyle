/*
JavaLineLength
max = (default)100
tokens = (default)TEXT_BLOCK_LITERAL_BEGIN, PACKAGE_DEF, IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.sizes.javalinelength;

import java.util.                                                                          ArrayList;
import java.                util.                                                               List;

public class InputJavaLineLengthDefault {

    String s1 = """
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical
            """;

    String s2 = """
            Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical""";

    List<Integer> list = new ArrayList<>();

	String s3 = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
    // violation above, 'Line is longer than 100 characters (found 105).'

    String s4 = "12345678901234567890123456789012345678901234567890123456789012345678901234567890😀";
}
