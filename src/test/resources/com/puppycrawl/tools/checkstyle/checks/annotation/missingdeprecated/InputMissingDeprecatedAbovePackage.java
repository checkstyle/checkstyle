/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

/**
 * Wrong header
 */
// test

/** @deprecated */
package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;
// violation above 'Must.*@java.lang.Deprecated annotation.*@deprecated Javadoc tag.*description.'

/**
 * @deprecated do not use
 */
@Deprecated
class InputMissingDeprecatedAbovePackage {
}
