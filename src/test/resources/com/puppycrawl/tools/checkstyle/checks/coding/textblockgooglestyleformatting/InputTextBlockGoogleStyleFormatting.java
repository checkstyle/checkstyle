/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting {

    public static String textFun() {
        // 2 violations 3 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        final String simpleScript = """
            s
        """;

        final String simpleScript1 =
            """
            this is simple test;
            """;

        // 2 violations 3 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        getData("""
            Hello,
            This is a multi-line message.
            """);

        // 2 violations 3 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        return """
            this is sample text
            """;
    }

    public String textFun2() {
        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
        final String simpleScript2 =
            """
            this is sample text""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
        getData(
            """
            Hello,
            This is a multi-line message."""
        ); // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
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

        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
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
