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

public class InputSuppressWithNearbyTextFilterMultipleFiles {
    void methodOne() {
        // violation below 'Line is longer than 90 characters (found 98).'
        final String reaaaaaaaaaaaaaaaaaaaaaaaaaaaaallyLooooooooooooongNaaaaaaaaame = "value1234";
    }

    void methodTwo() {
        final int alpha = 1;
        final int beta = 2;
        final int gamma = alpha + beta;
        final int delta = gamma + 1;
        // violation below 'Line is longer than 90 characters (found 100).'
        final String reaaaaaaaaaaaaaaaaaaaaaaaaaaaallyLooooooooooongNaaaame = String.valueOf(delta);
    }

    // filtered violation below '.*'badConstant2' must match pattern.*'
    final static int badConstant2 = 5; /* SUPPRESS CHECKSTYLE ConstantNameCheck */

    // violation below '.*'badConstant3' must match pattern.*'
    final static int badConstant3 = 5;
    // violation below '.*'badConstant4' must match pattern.*'
    final static int badConstant4 = 5;
}
