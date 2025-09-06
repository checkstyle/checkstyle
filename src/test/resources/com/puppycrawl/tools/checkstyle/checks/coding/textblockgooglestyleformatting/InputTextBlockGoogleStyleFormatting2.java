/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting2 {

    public static String textFun() {
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript = """
            s""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        final String simpleScript1 =
            """
            this is simple test""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript2 = simpleScript +
                simpleScript1 + """
                this is simple script""";
        // 2 violations above
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        final String simpleScript3 = simpleScript +
            simpleScript1 +
            """
            this is simple script""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript4 = simpleScript +
            simpleScript3.endsWith("""
                this is simple""");
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        // violation below 'Opening quotes (""") of text-block must be on the new line'
        getData("""
            Hello,
            This is a multi-line message.""");
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        // violation below 'Opening quotes (""") of text-block must be on the new line.'
        return """
            this is sample text""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'
    }

    public String textFun2() {
        final String simpleScript2 =
            """
            this is sample text""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        getData(
            """
            Hello,
            This is a multi-line message."""
        );// 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        getData(
            1, """
            this is a multi-line message""");
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        getData(
            1,
            """
            this is a multi-line message"""
        ); // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        getData(1,
            3, 5, 8,
            """
            some String""");
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 6 lines below 'Text-block quotes are not vertically aligned'

        // violation 4 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 4 lines below 'Closing quotes (""") of text-block must be on the new line'
        getData("""
            first string
            """ + """
            some String""", // violation 'Text-block quotes are not vertically aligned'
            """
            second string""");
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        // violation 6 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 6 lines below 'Closing quotes (""") of text-block must be on the new line'
        // violation 5 lines below 'Text-block quotes are not vertically aligned'
        getData(
            """
            first string
            """ + """
            some String""", """
            second string""");
        // violation 2 lines above 'Opening quotes (""") of text-block must be on the new line'

        // 2 violations 3 lines above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        return
            """
           THE MULTI-LINE MESSAGE""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'
    }

    public String textFun3() {

        String s =
            """
            Hello there""" + getName();
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        getData(
            """
              hello there1""", 0);
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        String test1 = s
            + """
            very good""".charAt(0) + getName();
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

        return s
            +
            """
            very good""".charAt(0) + getName();
        // 2 violations above:
        //   'Closing quotes (""") of text-block must be on the new line.'
        //   'Text-block quotes are not vertically aligned'

    }

    public String getName() {
        return "name";
    }

    public static void getData(String data) {}

    public static void getData(String data, String data2) {}

    public static void getData(String data, int length) {}

    public static void getData(int length, String data) {}

    public static void getData(int n1, int n2, int n3, int n4 , String data) {}
}
