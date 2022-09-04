/*
SuppressWithNearbyPlainTextCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = (default)0

com.puppycrawl.tools.checkstyle.checks.whitespace.FileTabCharacterCheck
eachLine = true
fileExtensions = (default)

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)all files
ignorePattern = (default)^(package|import) .*
max = 90


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbyplaintextcommentfilter;

public class InputSuppressWithNearbyPlainTextCommentFilterDefaultConfig {
	private int SOME_NAME = 5; // violation '.* contains a tab .*'

    void loop() {
	    while (SOME_NAME != 0) { // violation '.* contains a tab .*'
            System.out.println("Current num is: " + SOME_NAME);

            // filtered violation below '.* contains a tab .*'
	        SOME_NAME--; // SUPPRESS CHECKSTYLE FileTabCharacterCheck
        }
    }

    // violation below 'Line is longer than 90 characters (found 94).'
    void someMethodWithReaaaaaaaaaaaalyLoooooooooooooooooongNaaaaaaaaaaaaaaaaaaaaaaaaaaame() {
        // filtered violation below 'Line is longer than 90 characters (found 97).'
        for (int longName = 0; longName < 5; longName++) { // SUPPRESS CHECKSTYLE LineLengthCheck
            System.out.println(longName);
            if (longName == 4) {
	            break; // violation '.* contains a tab .*'
            }
            else {
                // filtered violation below '.* contains a tab .*'
	            break; // SUPPRESS CHECKSTYLE FileTabCharacterCheck
            }
        }
    }

    void method() {
        // violation below 'Line is longer than 90 characters (found 94).'
        int aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa = 1;

        // 2 violations below
	    int bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb = 2;

        // filtered violation below 'Line is longer than 90 characters (found 94).'
        /* SUPPRESS CHECKSTYLE LineLengthCheck */ int ccccccccccccccccccccccccccccccccccc = 3;

        // filtered violation below '.* contains a tab .*'
	    int ccc = /* SUPPRESS CHECKSTYLE FileTabCharacterCheck */ 2;
    }
}
