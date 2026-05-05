package com.openjdk.checkstyle.test.chapter2javasourcefiles.rule21specialcharacters;

public class InputSpecialCharactersInvalid {

    private final char apostropheOctal = '\047';
    // violation above 'special escape sequence'

    private final char quoteOctal = '\042';
    // violation above 'special escape sequence'

    private final char slashOctal = '\134';
    // violation above 'special escape sequence'

    private final char backspaceOctal = '\010';
    // violation above 'special escape sequence'

    private final char tabOctal = '\011';
    // violation above 'special escape sequence'

    private final char formFeedOctal = '\014';
    // violation above 'special escape sequence'

    private final char carriageReturnOctal = '\015';
    // violation above 'special escape sequence'

    private final char newLineOctal = '\012';
    // violation above 'special escape sequence'

    private final char escapedLetter = '\u0041';
    // violation above 'Unicode escape(s) usage should be avoided.'
    // ASCII bytes; this Unicode escape is still invalid.

}
