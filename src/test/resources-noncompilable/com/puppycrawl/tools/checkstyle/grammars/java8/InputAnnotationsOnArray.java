//Compilable with Java8
package com.puppycrawl.tools.checkstyle.grammars.java8;

import static java.lang.annotation.ElementType.TYPE_USE;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public final class InputAnnotationsOnArray {

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
}

@Retention(RetentionPolicy.CLASS)
@Target({ TYPE_USE })
@interface Nullable {
}
