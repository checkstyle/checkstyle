package org.checkstyle.suppressionxpathfilter.textblockgooglestyleformatting;

public class InputXpathTextBlockGoogleStyleFormatting {
    public String textFun() {
        final String simpleScript =
               """
            s
            """; // warn

        final String simpleScript1 =
            """
            this is simple test;
            """;

        getData(
            """
            Hello,
            This is a multi-line message.
            """);

        return
            """
            this is sample text
            """;
    }

    public void getData(String data) {}
}
