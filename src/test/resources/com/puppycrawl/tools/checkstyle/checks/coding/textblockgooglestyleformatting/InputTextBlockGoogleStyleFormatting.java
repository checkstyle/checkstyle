/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting {

    public static String textFun() {
        // violation below 'Opening quotes (""") of text-block must be on the new line.'
        final String simpleScript = """
            s
        """;

        final String simpleScript1 =
            """
            this is simple test;
            """;

        // violation below 'Opening quotes (""") of text-block must be on the new line.'
        getData("""
            Hello,
            This is a multi-line message.
            """);

        // violation below 'Opening quotes (""") of text-block must be on the new line.'
        return """
            this is sample text
            """;
    }

    public String textFun2() {
        final String simpleScript2 =
            """
            this is sample text""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        getData(
            """
            Hello,
            This is a multi-line message."""
        ); // violation above 'Closing quotes (""") of text-block must be on the new line.'

        return
            """
           THE MULTI-LINE MESSAGE""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'
    }

    public String textFun3() {

        String s =
            """
            Hello there
            """ + getName();

        getData(
            """
              hello there1
              """, 0);

        return s
            +
            """
            very good
            """.charAt(0) + getName();
    }

    public String getName() {
        return "name";
    }

    public static void getData(String data) {}

    public static void getData(String data, int length) {}
}
