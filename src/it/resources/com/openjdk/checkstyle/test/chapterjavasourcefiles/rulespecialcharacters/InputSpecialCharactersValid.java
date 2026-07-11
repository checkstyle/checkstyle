package com.openjdk.checkstyle.test.chapterjavasourcefiles.rulespecialcharacters;

// violation first line 'Header is missing*'

public class InputSpecialCharactersValid {

    private final char apostrophe = '\'';
    private final char quote = '"';
    private final char slash = '\\';
    private final char backspace = '\b';
    private final char tab = '\t';
    private final char formFeed = '\f';
    private final char carriageReturn = '\r';
    private final char newLine = '\n';
    private final char space = ' ';

    // Unicode escapes (for reference, shown as plain text):
    // apostrophe: \\u0027, quote: \\u0022, backslash: \\u005c
    // tab: \\u0009, backspace: \\u0008, carriage return: \\u000d
    // form feed: \\u000c, newline: \\u000a, space: \\u0020

}
