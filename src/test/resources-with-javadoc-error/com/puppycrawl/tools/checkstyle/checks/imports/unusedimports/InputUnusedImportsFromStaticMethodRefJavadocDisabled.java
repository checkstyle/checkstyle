/*
UnusedImports
processJavadoc = false
violateExecutionOnNonTightHtml = (default)false


*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Arrays; // violation 'Unused import - java.util.Arrays.'
import static java.lang.Integer.parseInt; // violation 'Unused import - java.lang.Integer.'

/**
 * This {@link Arrays::sort} is NOT a valid link,
 * same as the one below.
 * Use {@link Integer::parseInt}.
 */
class InputUnusedImportsFromStaticMethodRefJavadocDisabled {
}
