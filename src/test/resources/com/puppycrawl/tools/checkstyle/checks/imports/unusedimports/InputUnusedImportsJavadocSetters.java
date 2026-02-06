/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = true
javadocTokens = THROWS_BLOCK_TAG

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List; // violation 'Unused import - java.util.List.'

/**
 * {@link List}
 */
class InputUnusedImportsJavadocSetters {

    /**
     * <p> // violation, 'Unclosed HTML tag found: p'
     */
    public static final int CONST = 12;

}
