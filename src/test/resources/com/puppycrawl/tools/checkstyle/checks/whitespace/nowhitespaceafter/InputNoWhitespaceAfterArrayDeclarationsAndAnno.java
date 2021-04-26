package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

/* Config:
 *
 *
 */
@Target(ElementType.TYPE_USE)
@interface NonNull {}

public class InputNoWhitespaceAfterArrayDeclarationsAndAnno {

    @NonNull int @NonNull[] @NonNull[] field1; // ok
    @NonNull int @NonNull [] @NonNull [] field2; // ok
    int[] array[] = new int[2][2]; // ok
    int array2[][][] = new int[3][3][3]; // ok

    public void foo(final char @NonNull [] param) {} // ok
}
