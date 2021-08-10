/*
MissingDeprecated
violateExecutionOnNonTightHtml = (default)false


*/

/**
 * Wrong header
 */
// test

package com.puppycrawl.tools.checkstyle.checks.annotation.missingdeprecated;

/**
 * @deprecated do not use
 */
@Deprecated
class InputMissingDeprecatedSingleComment { // ok
}
