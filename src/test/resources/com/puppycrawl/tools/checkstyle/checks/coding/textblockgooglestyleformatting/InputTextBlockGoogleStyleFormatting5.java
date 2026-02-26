/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting5 {

    static void testing(String input) {}

    static void testing(String s1, String s2) {}

    static {
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        testing(thisMethodReturnsString() + """
        I am testing
        """);
        // violation 3 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 2 lines above 'Text-block quotes are not vertically aligned'
    }

    static {
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        testing(thisMethodReturnsString(), """
            I am testing
            """);
        // violation 3 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 2 lines above 'Text-block quotes are not vertically aligned'
    }

    // violation below 'Opening quotes (""") of text-block must be on the new line'
    String testing1 = thisMethodReturnsString() + """
                         I am testing
                         """;
    // violation 3 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    // violation 2 lines above 'Text-block quotes are not vertically aligned'

    String type = "json";
    String template = switch (type) { // violation below 'Opening quotes (""") of text-block must'
        case "json" -> """
            {
                "status": "ok"
            }
            """;
        // violation 5 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 2 lines above 'Text-block quotes are not vertically aligned'

        // violation below 'Opening quotes (""") of text-block must be on the new line'
        case "xml" -> """
            <status>ok</status>
            """;
        // violation 3 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 2 lines above 'Text-block quotes are not vertically aligned'

        // violation below 'Opening quotes (""") of text-block must be on the new line'
        default -> """
            plain text ok
            """;
        // violation 3 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
        // violation 2 lines above 'Text-block quotes are not vertically aligned'
     };

     static void validate(int value) {
        if (value < 0) {
            // violation below 'Opening quotes (""") of text-block must be on the new line'
            throw new IllegalArgumentException("""
                Value must be non-negative.
                You passed a negative number.
            """);
            // violation 4 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
            // violation 2 lines above 'Text-block quotes are not vertically aligned'
        }
    }

    final boolean condition = true;
    // violation 3 lines below 'Opening quotes (""") of text-block must be on the new line'
    // violation 2 lines below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    // violation 3 lines below 'Text-block quotes are not vertically aligned'
    String s = condition ? """
        Yes case
        """ : """
        No case
        """;
    // violation 3 lines above 'Opening quotes (""") of text-block must be on the new line'
    // violation 4 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    // violation 3 lines above 'Text-block quotes are not vertically aligned'

    public static String thisMethodReturnsString() {
        return "thisMethodReturnsString";
    }
}
