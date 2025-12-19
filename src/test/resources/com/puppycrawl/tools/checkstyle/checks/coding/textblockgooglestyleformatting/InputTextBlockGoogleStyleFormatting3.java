/*
TextBlockGoogleStyleFormatting



*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting3 {

    private static String testMethod1() {
        // violation 7 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 7 lines below 'Text indentation is less than opening quotes indentation'
        // violation 7 lines below 'Text-block quotes are not vertically aligned'

        // violation 5 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 5 lines below 'Text indentation is less than opening quotes indentation'
        // violation 4 lines below 'Closing quotes (""") of text-block should not be preceded by'
        getData("""
            first string
            """ + """
            some String""", // violation 'Text-block quotes are not vertically aligned'
            """
            second string""");
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace'
        //   'Text-block quotes are not vertically aligned'

        // violation 7 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 7 lines below 'Text indentation is less than opening quotes indentation'
        // violation 6 lines below 'Closing quotes (""") of text-block should not be preceded by'
        // violation 5 lines below 'Text-block quotes are not vertically aligned'
        getData(
            """
            first string
            """ + """
            some String""", """
            second string""");
        // violation 2 lines above 'Opening quotes (""") of text-block must be on the new line'
        // violation 2 lines above 'Text indentation is less than opening quotes indentation'
        // violation 3 lines above 'Closing quotes (""") of text-block should not be preceded by'
        // violation 4 lines above 'Text-block quotes are not vertically aligned'

        return
            """
            THE MULTI-LINE MESSAGE""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace charac'
        //   'Text-block quotes are not vertically aligned'
    }

    public String textFun3() {
        String s =
            """
            Hello there""" + getName();
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace charact'
        //   'Text-block quotes are not vertically aligned'

        getData(
            """
              hello there1""", 0);
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        String test1 = s
            + """
            very good""".charAt(0) + getName();
        // 3 violations above:
        //   'Text indentation is less than opening quotes indentation'
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        return s
            +
            """
            very good""".charAt(0) + getName();
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
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
