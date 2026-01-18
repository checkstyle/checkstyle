/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.HashSet;

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

    /**
     * @see ArrayList
     */
    @Nullable
    /* @see List */
    public String test() {
        return "test";
    }

    /**
     * @see HashSet
     */
    @Nullable
    <V> void fooOne(V v) {
    }
}
