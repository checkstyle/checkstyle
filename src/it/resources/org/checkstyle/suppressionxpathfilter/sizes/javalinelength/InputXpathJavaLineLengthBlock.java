package org.checkstyle.suppressionxpathfilter.sizes.javalinelength;

public class InputXpathJavaLineLengthBlock {

    String s1 = """
            This line is very long and exceeds 50 characters""";

    String s2 = "This line is very long and exceeds 50 characters"; // warn

}
