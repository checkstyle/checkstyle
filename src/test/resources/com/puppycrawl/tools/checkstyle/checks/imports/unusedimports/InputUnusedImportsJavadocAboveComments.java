/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)REFERENCE, PARAMETER_TYPE, THROWS_BLOCK_TAG

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Map;
import java.util.List; // violation 'Unused import - java.util.List.'

/**
 * Use {@link Map.Entry} in this javadoc.
 */
/*
    Input for UnusedImports
*/
public class InputUnusedImportsJavadocAboveComments {
}
