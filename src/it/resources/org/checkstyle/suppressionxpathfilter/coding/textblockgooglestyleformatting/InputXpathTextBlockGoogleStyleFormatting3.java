/*
TextBlockGoogleStyleFormatting


*/

package org.checkstyle.suppressionxpathfilter.coding.textblockgooglestyleformatting;

public class InputXpathTextBlockGoogleStyleFormatting3 {

    public String textFun1() {
        final String simpleScriptViolation =
                    """
                        this is sample text
                   """; // violation 'Text-block quotes are not vertically aligned.'

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
