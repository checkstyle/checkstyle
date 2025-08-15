/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingIndentation {

    public void textFunContent() {
        String e1 =
            """
  content of the block. e1
  """; // violation 2 lines above 'Text-block quotes are not vertically aligned.'

        String e3 =
            """
        content of the block of e3
            """;

        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
        getData(
          """
    Indentation of Text-block
            """,
          5
        );
    }

    public String testFunContent2() {
        String basic = "Im a string";

        // violation 2 lines below 'Text-block quotes are not vertically aligned.'
        return
        """
            this is sample text
            """;
    }
    public String getName() {
        return "name";
    }

    public static void getData(String data) {}

    public static void getData(String data, int length) {}
}
