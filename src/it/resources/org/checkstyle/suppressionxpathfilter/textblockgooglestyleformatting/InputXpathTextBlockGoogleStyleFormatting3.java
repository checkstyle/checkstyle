package org.checkstyle.suppressionxpathfilter.textblockgooglestyleformatting;

public class InputXpathTextBlockGoogleStyleFormatting3 {

    public String textFun1() {
        final String simpleScriptViolation =
                    """
 this is sample text
                    """; // warn

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

    public static void getData(String data) {}
}
