package org.checkstyle.suppressionxpathfilter.textblockgooglestyleformatting;

public class InputXpathTextBlockGoogleStyleFormatting2 {

    public String textFun1() {
        final String simpleScript2 =
            """
            warn
            this is sample text
            """;

        getData(
            """
            Hello,
            This is a multi-line message.
            """
        );

        return
           """
           THE MULTI-LINE MESSAGE
           """;
    }

    public String textFun2() {

        String s =
            """
            Hello there
            """ + getName();

        getData(
            """
              hello there1
              """, 0);

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
