/*
UnusedImports
processJavadoc = (default)true


*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collections;
import java.util.Arrays;
import static java.lang.Integer.parseInt; // violation 'Unused import - java.lang.Integer.parseInt.'
import static java.util.Collections.emptyEnumeration; // violation 'Unused import - java.util.Collections.emptyEnumeration.'
import static java.util.Arrays.sort; // violation 'Unused import - java.util.Arrays.sort.'

public class InputUnusedImportsFromStaticMethodRefInDocsOnly {

    /**
     * Use {@link Collections::emptyEnumeration} this line is skipped as it is not valid link.
     * @see Collections#emptyEnumeration
     * @throws IllegalAccessError::new
     */
    public static void m() {}

    /**
     * @see Arrays#sort
     * @throws IllegalAccessError::new
     */
    public static void n() {}

    /**
     * Use {@link Integer::parseInt} this line is skipped as it is not valid link.
     * @throws IllegalAccessError::new
     */
    public static void l() {}

}
