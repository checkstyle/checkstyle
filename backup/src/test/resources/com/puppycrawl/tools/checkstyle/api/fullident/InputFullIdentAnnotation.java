package com.puppycrawl.tools.checkstyle.api.fullident;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;

public class InputFullIdentAnnotation {
    public void method(final char @TypeAnnotation [] a) {
    }
}

@Target(ElementType.TYPE_USE)
@interface TypeAnnotation {
}
