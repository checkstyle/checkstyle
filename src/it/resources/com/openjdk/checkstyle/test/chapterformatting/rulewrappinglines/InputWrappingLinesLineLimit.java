package com.openjdk.checkstyle.test.chapterformatting.rulewrappinglines;

// violation first line 'Header mismatch'

// violation 4 lines below 'Line is longer than 100 characters (found 103).'
/**
 * This is a short Javadoc comment.
 * This is a very long url https://verylongurlexamplebutitdoesnotthrowanyerrorbecausetheURLisignoredbylineLengthCheck.com
 * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfHundredCharactersSoitwillgiveserror.
 */
public class InputWrappingLinesLineLimit {
    void testMethod(String str) {
        System.out.println("This is a short line.");

        System.out.println("This line is long and exceeds the default limit of 100 characters............");
        // violation above 'Line is longer than 100 characters (found 108).'

        String str1 = """
                    This is a very really long string that exceeds the limit it will gives violation..........................
                    """;
        // violation 2 lines above 'Line is longer than 100 characters (found 126).'

        String str2 = """
                    This is a very really long string that is exactly equal 100 so no violation....
                    """;
    }
}
