/*
TextBlockGoogleStyleFormatting


*/

package com.puppycrawl.tools.checkstyle.checks.coding.textblockgooglestyleformatting;

// non-compiled with javac: javac requires a line terminator after the opening
// delimiter of a text block, but the parser accepts the single-line form below

public class InputTextBlockGoogleStyleFormattingSpacesOnlyContent {

    public String singleSpace = """ """;
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation 2 lines above 'Text-block quotes are not vertically aligned'

    public String multipleSpaces = """   """;
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation 2 lines above 'Text-block quotes are not vertically aligned'

    public String letterThenSpaces = """a  """;
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation 2 lines above 'Each line of text in the text block must be indented'
    // violation 3 lines above 'Closing quotes (""") of text-block should not be'
    // violation 4 lines above 'Text-block quotes are not vertically aligned'

    public String spacesThenLetter = """  a""";
    // violation above 'Opening quotes (""") of text-block must be on the new line'
    // violation 2 lines above 'Each line of text in the text block must be indented'
    // violation 3 lines above 'Closing quotes (""") of text-block should not be'
    // violation 4 lines above 'Text-block quotes are not vertically aligned'
}
