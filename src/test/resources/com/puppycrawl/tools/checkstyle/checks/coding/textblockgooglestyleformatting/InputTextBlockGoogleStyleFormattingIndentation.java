/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormattingIndentation {

    public void textFunContent() {
        String e1 =
            """
  content of the block. e1
  """;

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
    }

    public String getName() {
        return "name";
    }

    public static void getData(String data) {}

    public static void getData(String data, int length) {}
}
