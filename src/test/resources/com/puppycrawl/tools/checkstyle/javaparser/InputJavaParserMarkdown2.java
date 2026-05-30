package com.puppycrawl.tools.checkstyle.javaparser;

/// Multiline test
/// another line

public class InputJavaParserMarkdown2 {
    /// This is a markdown comment

    public class Test{

        int a; /// ending test

/// Misaligned markdown comment (starts at column 0)
    int b; // 'i' starts at column 4
    }
}
