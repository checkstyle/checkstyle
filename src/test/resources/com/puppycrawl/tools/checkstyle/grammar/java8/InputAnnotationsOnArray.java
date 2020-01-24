package com.puppycrawl.tools.checkstyle.grammar.java8;

import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


public final class InputAnnotationsOnArray {

    private String array1 @Nullable [];
    private @Nullable int array2 @Nullable [] @Nullable [];

    private InputAnnotationsOnArray() {
    }

    public static <T> T[] checkNotNullContents(T @Nullable [] array) {
        if (array == null) {
            throw new NullPointerException();
        }

        return array;
    }

    public static <T> T[][] checkNotNullContents2(T @Nullable [] @Nullable [] array) {
        if (array == null) {
            throw new NullPointerException();
        }

        return array;
    }

    public static <T> T @Nullable [] checkNotNullContents3(T array @Nullable []) {
        if (array == null) {
            throw new NullPointerException();
        }

        return array;
    }

    public <T> T checkNotNullContents4(T @Nullable [] array) @Nullable [] {
        if (array == null) {
            throw new NullPointerException();
        }
        String tmp1 @Nullable [];
        @Nullable Object[] tmp2 = new @Nullable Integer[3];
        @Nullable int[] tmp3 = new @Nullable int[3];
        @Nullable Object tmp4 = new @Nullable String @Nullable [3] @Nullable [2];
        @Nullable Object tmp5 = new @Nullable int @Nullable [3] @Nullable [2];

        return array;
    }
}

@Retention(RetentionPolicy.CLASS)
@Target({ TYPE_USE })
@interface Nullable {
}
