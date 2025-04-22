/*
com.puppycrawl.tools.checkstyle.checks.SuppressWarningsHolder
aliasList = LineLengthCheck=line

com.puppycrawl.tools.checkstyle.filters.SuppressWarningsFilter

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
ignorePattern = ^ *\\* *[^ ]+$

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
max = 75


*/

package com.puppycrawl.tools.checkstyle.checks.suppresswarningsholder;

import static com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck.MSG_KEY;
// violation above, 'Line is longer than 80 characters'


public class InputSuppressWarningsHolderAlias6 {

    void testMethod(String str) {
        str = MSG_KEY;
        System.out.println("This is a short line.");

        System.out.println("This line is long and exceeds the default limit of 80 characters.");
        // 2 violations above:
        //  'Line is longer than 75 characters'
        //  'Line is longer than 80 characters'
    }

    @SuppressWarnings("Line")
    void testMethod2(String str) {
        str = MSG_KEY;
        System.out.println("This is a short line.");

        System.out.println("This line is long and exceeds the default limit of 80 characters.");
        // filtered 2 violations above:
        //  'Line is longer than 75 characters'
        //  'Line is longer than 80 characters'
    }

    @SuppressWarnings("LIne")
    void testMethod3(String str) {
        str = MSG_KEY;
        System.out.println("This is a short line.");

        System.out.println("This line exceeds the limit of 75 characters.");
        // filtered violation above, 'Line is longer than 75 characters'
    }

    void testMethod4(String str) {
        str = MSG_KEY;
        System.out.println("This is a short line.");

        System.out.println("This line exceeds the limit of 75 characters.");
        // violation above, 'Line is longer than 75 characters'
    }

    // violation 3 lines below 'Line is longer than 75 characters'
    /**
    * This is a short Javadoc comment.
    * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfEightyCharacters.
    */
    void testMethod5(String str) {
        str = MSG_KEY;
        System.out.println("This is a short line.");
    }

    @SuppressWarnings("LINE")
    // filtered violation 3 lines below, 'Line is longer than 75 characters'
    /**
    * This is a short Javadoc comment.
    * ThisJavadocCommentIsAReallyLongWordThatExceedsDefaultLineLimitOfEightyCharacters.
    */
    void testMethod6(String str) {
        str = MSG_KEY;
        System.out.println("This is a short line.");
    }
}
