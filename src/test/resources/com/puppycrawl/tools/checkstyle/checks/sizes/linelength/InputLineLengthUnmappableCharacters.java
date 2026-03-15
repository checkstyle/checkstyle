package com.puppycrawl.tools.checkstyle.checks.sizes.linelength;

public class InputLineLengthUnmappableCharacters {
    String a = "ᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀᾀ"; // violation, 'Line is longer than 75 characters (found 288).'
}
