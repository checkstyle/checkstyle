/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting2 {

    public static String textFun() {
        // 2 violations 3 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        final String simpleScript = """
            s""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // violation 2 lines below 'Text-block quotes are not vertically aligned'
        final String simpleScript1 =
            """
            this is simple test""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // 2 violations 4 lines below:
        //   'Opening quotes (""") of text-block must be on the new line'
        //   'Text-block quotes are not vertically aligned'
        final String simpleScript2 = simpleScript +
                simpleScript1 + """
                this is simple script""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // violation 3 lines below 'Text-block quotes are not vertically aligned'
        final String simpleScript3 = simpleScript +
            simpleScript1 +
            """
            this is simple script""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // 2 violations 4 lines below:
        //   'Opening quotes (""") of text-block must be on the new line'
        //   'Text-block quotes are not vertically aligned'
        final String simpleScript4 = simpleScript +
            simpleScript3.endsWith("""
                this is simple""");
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // 2 violations 3 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        getData("""
            Hello,
            This is a multi-line message.""");
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // 2 violations 3 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        return """
            this is sample text""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'
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

        // 2 violations 4 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        getData(
            1, """
            this is a multi-line message""");
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // violation 3 lines below 'Text-block quotes are not vertically aligned.'
        getData(
            1,
            """
            this is a multi-line message"""
        ); // violation above 'Closing quotes (""") of text-block must be on the new line'

        // violation 3 lines below 'Text-block quotes are not vertically aligned.'
        getData(1,
            3, 5, 8,
            """
            some String"""); // violation 'Closing quotes (""") of text-block must be on the new line'

        // 2 violations 7 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'

        // 2 violations 5 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        getData("""
            first string
            """ + """
            some String""", // violation 'Closing quotes (""") of text-block must be on the new line'
            """
            second string""");
        // violation 2 lines above 'Text-block quotes are not vertically aligned'
        // violation 2 lines above 'Closing quotes (""") of text-block must be on the new line'

        // 2 violations 6 lines below:
        //  'Opening quotes (""") of text-block must be on the new line.'
        //  'Text-block quotes are not vertically aligned.'
        getData(
            """
            first string
            """ + """
            some String""", """
            second string""");
            // 3 violations 2 lines above:
            //   'Closing quotes (""") of text-block must be on the new line'
            //   'Opening quotes (""") of text-block must be on the new line'
            //   'Text-block quotes are not vertically aligned.'
        // violation 5 lines above 'Closing quotes (""") of text-block must be on the new line'

        // violation 2 lines below 'Text-block quotes are not vertically aligned'
        return
            """
           THE MULTI-LINE MESSAGE""";
        // violation above 'Closing quotes (""") of text-block must be on the new line.'
    }

    public String textFun3() {

        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
        String s =
            """
            Hello there""" + getName();
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
        getData(
            """
              hello there1""", 0);
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // 2 violations 4 lines below:
        //   'Opening quotes (""") of text-block must be on the new line'
        //   'Text-block quotes are not vertically aligned.'
        String test1 = s
            + """
            very good""".charAt(0) + getName();
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

        // violation 3 lines below 'Text-block quotes are not vertically aligned.'
        return s
            +
            """
            very good""".charAt(0) + getName();
        // violation above 'Closing quotes (""") of text-block must be on the new line.'

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
