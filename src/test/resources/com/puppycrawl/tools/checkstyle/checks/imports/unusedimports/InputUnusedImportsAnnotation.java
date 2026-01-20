/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import javax.annotation.Nullable;

public class InputUnusedImportsAnnotation {

    /** @see List */
    @Nullable
    public boolean isTrue() {
        return true;
    }

    /** @see Map */
    <V> void foo(V v) {
    }

    @interface MyAnnotation {

       /**
        * @see HashMap
        */
       String value() default "";
    }
}
