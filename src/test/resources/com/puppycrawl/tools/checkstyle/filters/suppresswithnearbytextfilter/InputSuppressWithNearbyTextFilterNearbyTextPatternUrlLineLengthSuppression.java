/*
SuppressWithNearbyTextFilter
nearbyTextPattern = <a href="[^"]+">
checkPattern = LineLength
messagePattern = (default)(null)
idPattern = (default)(null)
lineRange = (default)0

com.puppycrawl.tools.checkstyle.checks.sizes.LineLengthCheck
fileExtensions = (default)(null)
ignorePattern = (default)^(package|import) .*
max = 90


*/

package com.puppycrawl.tools.checkstyle.filters.suppresswithnearbytextfilter;

public class InputSuppressWithNearbyTextFilterNearbyTextPatternUrlLineLengthSuppression {
    /**
     * Simple SARIF logger.
     * SARIF stands for the static analysis results interchange format.
     * See <a href="https://sarifweb.azurewebsites.net/">reference</a>
     */
    public class SarifLogger{
    }


    /**
     *
     * // filtered violation below 'Line is longer than 90 characters (found 98).'
     * <a href="https://docs.oracle.com/javase/8/docs/technotes/tools/unix/javadoc.html#CHDHJECF">
     * Oracle Docs</a>
     * @see #PARAM_LITERAL
     */
    public static final int PARAMETER_NAME = 1;

    // filtered violation below 'Line is longer than 90 characters (found 97).'
    // <a href="http://www.sdfjlgbhnSLDRKFGvsdjkfhOdfjdfgdfsgaaaaaa.org/asd.html#aabbccddffgghh">
    public static final int PARAMETER_NAME2 = 2;
}
