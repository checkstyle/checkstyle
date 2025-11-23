/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collections;
import java.util.Arrays;
import java.lang.Integer; // violation 'Unused import - java.lang.Integer'
import static java.util.Collections.emptyEnumeration; // violation 'Unused import - java.util.Collections.emptyEnumeration.'
import static java.util.Arrays.sort; // violation 'Unused import - java.util.Arrays.sort.'
import static java.util.Collections.shuffle; // violation 'Unused import - java.util.Collections.shuffle.'

public class InputUnusedImportsFromStaticMethodRefInDocsOnly {

    /**
     * This {@link Collections::emptyEnumeration} is not a valid link
     * and this check does not count this as a usage.
     * @see Collections#emptyEnumeration
     * @throws IllegalAccessError::new
     */
    public static void m() {}

    /**
     * This {@link Collections#shuffle} is a valid link
     * and the check counts this as a usage.
     * @see Arrays#sort
     * @throws IllegalAccessError::new
     */
    public static void n() {}

    /**
     * This  {@link Integer::parseInt} is not a valid link
     * and this check does not count this as a usage.
     * @throws IllegalAccessError::new
     */
    public static void l() {}

}
