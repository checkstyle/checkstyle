/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting9 {

    @SuppressWarnings({"membername"})
    // violation below 'Opening quotes (""") of text-block must be on the new line'
    String STRING1 = """
            string""";
    // violation 2 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    // 2 violations 2 lines above:
    //   'Closing quotes (""") of text-block should not be preceded by non'
    //   'Text-block quotes are not vertically aligned'

    @SuppressWarnings({
    "membername"}) // violation below 'Opening quotes (""") of text-block must be on the new line'
    String STRING2 = """
        string""";
    // violation 2 lines above 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    // 2 violations 2 lines above:
    //   'Closing quotes (""") of text-block should not be preceded by non'
    //   'Text-block quotes are not vertically aligned'

    @SuppressWarnings({
        "checkstyle:membername"
    }) // violation 2 lines below 'Opening quotes (""") of text-block must be on the new line'
    // violation below 'Each line of text in the text block must be indented at least as much as the opening and closing quotes'
    String STRING3 = """
        string
        """; // violation 'Text-block quotes are not vertically aligned'

    @SuppressWarnings({
        "checkstyle:misspelled"
    })
    String STRING4 =
        """
        string
        """;
}
