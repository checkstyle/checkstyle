/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting {

    public static String textFun() {
        // violation below 'Opening quotes (""") of text-block must be on the new line.'
        final String simpleScript = """
            s
        """; // violation 'Text-block quotes are not vertically aligned.'

        final String simpleScript1 =
            """
            this is simple test;
            """;

        // violation below 'Opening quotes (""") of text-block must be on the new line.'
        getData("""
            Hello,
            This is a multi-line message.
            """); // violation 'Text-block quotes are not vertically aligned.'

        // violation below 'Opening quotes (""") of text-block must be on the new line.'
        return """
            this is sample text
            """; // violation 'Text-block quotes are not vertically aligned.'
    }

    public String textFun2() {
        final String simpleScript2 =
            """
            this is sample text""";
        // 2 violations above:
        //  'Closing quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'

        getData(
            """
            Hello,
            This is a multi-line message."""
        );
        // 2 violations 2 lines above:
        //  'Closing quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'

        return
            """
           THE MULTI-LINE MESSAGE""";
        // 2 violations above:
        //  'Closing quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'

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
        // violation above 'Text-block quotes are not vertically aligned.'

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
