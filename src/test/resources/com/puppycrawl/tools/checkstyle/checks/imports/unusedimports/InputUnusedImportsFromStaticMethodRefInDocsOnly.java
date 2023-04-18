/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collections;
import static java.util.Collections.emptyEnumeration; // violation 'Unused import - java.util.Collections.emptyEnumeration.'

public class InputUnusedImportsFromStaticMethodRefInDocsOnly {

    /**
     * Use {@link Collections::emptyEnumeration} in this javadoc.
     * @see Collections::emptyEnumeration
     * @throws IllegalAccessError::new
     */
    public static void m() {}

}
