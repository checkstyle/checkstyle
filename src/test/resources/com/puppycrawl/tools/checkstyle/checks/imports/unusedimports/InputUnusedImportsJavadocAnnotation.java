/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Map;
import java.util.List;
import java.util.ArrayList; // violation 'Unused import - java.util.ArrayList.'

/**
 * The @deprecated annotation is important for the test.
 * Valid {@link Map.Entry}.
 */
@Deprecated
public class InputUnusedImportsJavadocAnnotation {
    /**
     * Annotation declaration
     */
    @interface InputWriteTag2a {
        /**
         * This javadoc will be under TYPE
         * Valid {@link List}.
         */
        int foo() default 0;
    }
}
