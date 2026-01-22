/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

public class InputTextBlockGoogleStyleFormatting9 {

    @SuppressWarnings({"membername"})
    // violation below 'Opening quotes (""") of text-block must be on the new line'
    String STRING1 = """
            string""";
    // 2 violations above:
    //   'Closing quotes (""") of text-block should not be preceded by non'
    //   'Text-block quotes are not vertically aligned'

    @SuppressWarnings({
    "membername"}) // violation below 'Opening quotes (""") of text-block must be on the new line'
    String STRING2 = """
        string""";
    // 2 violations above:
    //   'Closing quotes (""") of text-block should not be preceded by non'
    //   'Text-block quotes are not vertically aligned'

    @SuppressWarnings({
        "checkstyle:membername"
    }) // violation below 'Opening quotes (""") of text-block must be on the new line'
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
