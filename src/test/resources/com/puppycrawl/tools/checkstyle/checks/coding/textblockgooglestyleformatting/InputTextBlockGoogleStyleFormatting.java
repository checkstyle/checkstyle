/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting {

    public static String textFun() {
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript = """
            s
        """; // violation 'Text-block quotes are not vertically aligned'

        final String simpleScript1 =
            """
            this is simple test;
            """;

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript2 = simpleScript +
                simpleScript1 + """
                this is simple script
                """; // violation 'Text-block quotes are not vertically aligned'

        final String simpleScript3 = simpleScript +
            simpleScript1 +
            """
            this is simple script
            """;

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript4 = simpleScript +
            simpleScript3.endsWith("""
                this is simple
                """); // violation 'Text-block quotes are not vertically aligned'

        // violation below 'Opening quotes (""") of text-block must be on the new line'
        getData("""
            Hello,
            This is a multi-line message.
            """); // violation 'Text-block quotes are not vertically aligned'

        // violation below 'Opening quotes (""") of text-block must be on the new line'
        return """
            this is sample text
            """; // violation 'Text-block quotes are not vertically aligned'
    }

    public void textFun2() {
        final String simpleScript2 =
            """
            this is sample text""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace char'
        //   'Text-block quotes are not vertically aligned'

        getData(
            """
            Hello,
            This is a multi-line message."""
        ); // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace charact'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        getData(
            1, """
               this is a multi-line message
               """
        );

        getData(
            1,
            """
            this is a multi-line message
            """
        );

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        getData(1,
            3, 5, 8, """
          some String""" // violation 'Closing quotes (""") of text-block should not be preceded'
        );

        getData(1,
            3, 5, 8,
                """
                some String
                """
        );

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 3 lines below 'Text-block quotes are not vertically aligned'
        getData("""
            first string
            """ + """
            some String
            """,
            """
            second string
            """
            // violation 6 lines above'Opening quotes (""") of text-block must be on the new line'
            // violation 5 lines above 'Text-block quotes are not vertically aligned'
        );
    }

    public static void getData(String data) {}
    public static void getData(String data, String data2) {}
    public static void getData(String data, int length) {}
    public static void getData(int length, String data) {}
    public static void getData(int n1, int n2, int n3, int n4 , String data) {}
}
