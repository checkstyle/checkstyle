/*
SuppressWithNearbyTextFilter
nearbyTextPattern = (default)SUPPRESS CHECKSTYLE (\\w+)
checkPattern = (default).*
messagePattern = (default)(null)
idPattern = (default)(null)
lineRange = (default)0

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)(null)
ignorePattern = (default)^(package|import) .*
max = 90

com.puppycrawl.tools.checkstyle.checks.naming.ConstantNameCheck
format = (default)^[A-Z][A-Z0-9]*(_[A-Z0-9]+)*$
applyToPublic = (default)true
applyToProtected = (default)true
applyToPackage = (default)true
applyToPrivate = (default)true


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;

public class InputSuppressWithNearbyTextFilterDefaultConfig {

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

    final static int badConstant = 5; // violation '.*'badConstant' must match pattern.*'

    // filtered violation below '.*'badConstant1' must match pattern.*'
    final static int badConstant1 = 5; /* SUPPRESS CHECKSTYLE ConstantNameCheck */
}
