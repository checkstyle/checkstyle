package com.openjdk.checkstyle.test.chapter2javasourcefiles.rule21specialcharacters;

public class InputSpecialCharactersInvalid {

    private final String escapedTab = "\011";
    // violation above 'Consider using special escape sequence.'

    private final String escapedLetter = "\u0041";
    // violation above 'Unicode escape(s) usage should be avoided.'

}
