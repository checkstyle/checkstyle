/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting2 {

    public static String textFun() {
        // violation below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript = """
            s""";
        // 3 violations above:
        //   'Text block content indentation is less than the opening quotes indentation'
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        final String simpleScript1 =
            """
            this is simple test""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript2 = simpleScript +
                simpleScript1 + """
                this is simple script""";
        // 3 violations above
        //   'Text block content indentation is less than the opening quotes indentation'
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        final String simpleScript3 = simpleScript +
            simpleScript1 +
            """
            this is simple script""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        final String simpleScript4 = simpleScript +
            simpleScript3.endsWith("""
                this is simple""");
        // 3 violations above:
        //   'Text block content indentation is less than the opening quotes indentation'
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        // violation 2 lines below 'Text block content indentation is less than the opening quotes indentation'
        getData("""
            Hello,
            This is a multi-line message.""");
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        // violation below 'Opening quotes (""") of text-block must be on the new line.'
        return """
            this is sample text""";
        // 3 violations above:
        //   'Text block content indentation is less than the opening quotes indentation'
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace character'
        //   'Text-block quotes are not vertically aligned'
    }

    public void textFun2() {
        final String simpleScript2 =
            """
            this is sample text""";
        // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        getData(
            """
            Hello,
            This is a multi-line message."""
        );// 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
        getData(
            1, """
            this is a multi-line message""");
        // 3 violations above:
        //   'Text block content indentation is less than the opening quotes indentation'
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        getData(
            1,
            """
            this is a multi-line message"""
        ); // 2 violations above:
        //   'Closing quotes (""") of text-block should not be preceded by non-whitespace characte'
        //   'Text-block quotes are not vertically aligned'

        getData(1,
            3, 5, 8,
            """
            some String""");
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
