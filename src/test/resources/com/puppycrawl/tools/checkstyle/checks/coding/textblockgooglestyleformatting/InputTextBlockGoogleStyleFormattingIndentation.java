/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingIndentation {

    public void textFunContent() {
        String e1 =
            """
  content of the block. e1
  """; // violation 'Text-block quotes are not vertically aligned.'

        String e3 =
            """
        content of the block of e3
            """;

        getData(
          """
    Indentation of Text-block
            """,
          5
        );
        // violation 3 lines above 'Text-block quotes are not vertically aligned.'
    }

    public String testFunContent2() {
        String basic = "Im a string";

        return
        """
            this is sample text
            """;
        // violation above 'Text-block quotes are not vertically aligned.'
    }
    public String getName() {
        return "name";
    }

    public static void getData(String data) {}

    public static void getData(String data, int length) {}
}
