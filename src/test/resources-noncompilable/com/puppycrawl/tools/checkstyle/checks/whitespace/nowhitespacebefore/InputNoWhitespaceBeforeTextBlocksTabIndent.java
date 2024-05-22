/*
NoWhitespaceBefore
allowLineBreaks = (default)false
tokens = (default)COMMA, SEMI, POST_INC, POST_DEC, ELLIPSIS, LABELED_STAT


*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespacebefore;

public class InputNoWhitespaceBeforeTextBlocksTabIndent {

    public static void main(String[] args) {}

    private void workingCheck() {
        String x = """
                Some Text
               """;
    }

    private void workingCheck_spaces() {
        String x = """
            Some Text
        """;
    }

    private void workingCheck_no_spaces() {
        String x = """
            Some Text
""";
    }

    private void brokenCheck_space_before_tabs() {
        String x = """
            Some Text
 		""";
    }

    private void brokenCheck_space_after_tabs() {
        String x = """
            Some Text
		 """;
    }

    private void brokenCheck_just_tabs() {
        String x = """
            Some Text
		""";
    }
}
