package com.openjdk.checkstyle.test.chapterjavasourcefiles.rulespecialcharacters;

// violation first line 'Header mismatch*'

public class InputSpecialCharactersInvalid {
    // The short forms (e.g. \t) are commonly used and easier to recognize than
    // the corresponding longer forms (\011, \u0009).
    // \', \", \\, \t, \b, \r, \f, and \n should be preferred over corresponding
    // octal (e.g. \047) or Unicode (e.g. \u0027) escaped characters.

    private final char apostropheOctal = '\047';
    // violation above 'special escape sequence'
    // Should use: \'  (short escape for apostrophe)

    private final char quoteOctal = '\042';
    // violation above 'special escape sequence'
    // Should use: \" (short escape for quote)

    private final char slashOctal = '\134';
    // violation above 'special escape sequence'
    // Should use: \\ (short escape for backslash)

    private final char backspaceOctal = '\010';
    // violation above 'special escape sequence'
    // Should use: \b (short escape for backspace)

    private final char tabOctal = '\011';
    // violation above 'special escape sequence'
    // Should use: \t (short escape for tab)

    private final char formFeedOctal = '\014';
    // violation above 'special escape sequence'
    // Should use: \f (short escape for form feed)

    private final char carriageReturnOctal = '\015';
    // violation above 'special escape sequence'
    // Should use: \r (short escape for carriage return)

    private final char newLineOctal = '\012';
    // violation above 'special escape sequence'
    // Should use: \n (short escape for newline)

    private final char escapedLetter = '\u0041';
    // violation above 'Unicode escape(s) usage should be avoided.'
    // Should use: 'A' (plain ASCII character instead of Unicode escape)

    private final char spaceOctal = '\040';
    // violation above 'special escape sequence'

}
