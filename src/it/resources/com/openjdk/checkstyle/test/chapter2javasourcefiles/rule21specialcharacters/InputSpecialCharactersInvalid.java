package com.openjdk.checkstyle.test.chapter2javasourcefiles.rule21specialcharacters;

public class InputSpecialCharactersInvalid {

    private final char apostropheOctal = '\047';
    // violation above 'Consider using special escape sequence instead of octal value or Unicode escaped value.'

    private final char quoteOctal = '\042';
    // violation above 'Consider using special escape sequence instead of octal value or Unicode escaped value.'

    private final char slashOctal = '\134';
    // violation above 'Consider using special escape sequence instead of octal value or Unicode escaped value.'

    private final char backspaceOctal = '\010';
    // violation above 'Consider using special escape sequence instead of octal value or Unicode escaped value.'

    private final char tabOctal = '\011';
    // violation above 'Consider using special escape sequence instead of octal value or Unicode escaped value.'

    private final char formFeedOctal = '\014';
    // violation above 'Consider using special escape sequence instead of octal value or Unicode escaped value.'

    private final char carriageReturnOctal = '\015';
    // violation above 'Consider using special escape sequence instead of octal value or Unicode escaped value.'

    private final char newLineOctal = '\012';
    // violation above 'Consider using special escape sequence instead of octal value or Unicode escaped value.'

    private final char escapedLetter = '\u0041';
    // violation above 'Unicode escape(s) usage should be avoided.'
    // This file intentionally uses an ASCII-only byte sequence; the escape is still invalid.

}
