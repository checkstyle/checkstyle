/*
SuppressWithNearbyPlainTextCommentFilter
commentFormat = (default)SUPPRESS CHECKSTYLE (\\w+)
checkFormat = (default).*
messageFormat = (default)(null)
idFormat = (default)(null)
influenceFormat = (default)0

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)all files
ignorePattern = (default)^(package|import) .*
max = 90


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbyplaintextcommentfilter;

public class InputSuppressWithNearbyPlainTextCommentFilterDefaultConfig {

    // violation below 'Line is longer than 90 characters (found 94).'
    void someMethodWithReaaaaaaaaaaaalyLoooooooooooooooooongNaaaaaaaaaaaaaaaaaaaaaaaaaaame() {
        // filtered violation below 'Line is longer than 90 characters (found 97).'
        for (int longName = 0; longName < 5; longName++) { // SUPPRESS CHECKSTYLE LineLengthCheck
            System.out.println(longName);
            if (longName == 4) {
                break;
            }
        }
    }

    void method() {
        // filtered violation below 'Line is longer than 90 characters (found 94).'
        /* SUPPRESS CHECKSTYLE LineLengthCheck */ int ccccccccccccccccccccccccccccccccccc = 3;
    }
}
