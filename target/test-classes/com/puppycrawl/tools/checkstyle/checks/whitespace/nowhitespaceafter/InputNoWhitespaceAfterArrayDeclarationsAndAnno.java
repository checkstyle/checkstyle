/*
NoWhitespaceAfter
allowLineBreaks = (default)true
tokens = ARRAY_INIT, AT, INC, DEC, UNARY_MINUS, UNARY_PLUS, BNOT, LNOT, DOT, TYPECAST, \
         ARRAY_DECLARATOR, INDEX_OP, LITERAL_SYNCHRONIZED, METHOD_REF


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.nowhitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.Map;

@Target(ElementType.TYPE_USE)
@interface NonNull {}

@Retention(RetentionPolicy.CLASS)
@Target({ElementType.TYPE_USE})
@interface MyAnnotation {
}
public class InputNoWhitespaceAfterArrayDeclarationsAndAnno {

    @NonNull int @NonNull[] @NonNull[] field1;
    @NonNull int @NonNull [] @NonNull [] field2;
    int[] array[] = new int[2][2];
    int array2[][][] = new int[3][3][3];

    public void foo(final char @NonNull [] param) {}

    public void m1(@NonNull String @NonNull ... vararg) {
    }

    public String m2()@NonNull[]@NonNull[] {
        return null;
    }

    public void instructions() {
        // annotations
        Map.@MyAnnotation Entry e;
        String str = (@MyAnnotation String)"";
        (new Inner3()).<@MyAnnotation String>m();
        Object arr = new @MyAnnotation String@MyAnnotation[3];
        for (String a@MyAnnotation[] : m2()) {
        }
        Object arr2 = new @MyAnnotation int[3];
    }

    static class Inner3<T> {
        public void m() {
        }
    }

}
