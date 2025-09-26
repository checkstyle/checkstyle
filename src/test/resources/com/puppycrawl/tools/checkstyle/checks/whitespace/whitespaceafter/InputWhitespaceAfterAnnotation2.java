/*
WhitespaceAfter
tokens = COMMA, SEMI, TYPECAST, LITERAL_IF, LITERAL_ELSE, LITERAL_WHILE, \
         LITERAL_DO, LITERAL_FOR, DO_WHILE, ANNOTATIONS


*/

package com.puppycrawl.tools.checkstyle.checks.whitespace.whitespaceafter;

import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.util.List;

public class InputWhitespaceAfterAnnotation2 {
    abstract class TestParameterizedType implements
        @TypeAnno("M")Dummy // violation '')' is not followed by whitespace'
            <@TypeAnno("S")String, // violation  '')' is not followed by whitespace'
                @TypeAnno("I")@TypeAnno2("I2") // violation '')' is not followed by whitespace'
                    Integer> {

        public ParameterizedOuter<String>.ParameterizedInner<Integer> foo() {
            return null;
        }

        public @TypeAnno("O") ParameterizedOuter<@TypeAnno("S1") @TypeAnno2("S2") String>
                .@TypeAnno("I") ParameterizedInner<@TypeAnno("I1") @TypeAnno2("I") Integer> fo2() {
            return null;
        }
    }

    String@TA[]@TB[] x7;
    // 2 violations above:
    //   ''TA' is not followed by whitespace'
    //   ''TB' is not followed by whitespace'

    @TA String @TB [] f = new @TA String @TB[0];
    // violation above ''TB' is not followed by whitespace'

    @Foo(0) String @Foo(1)[] field1;
    // violation above '')' is not followed by whitespace'

    @Foo(0) String @Foo(1) [] @Foo(2)[] field2;
    // violation above '')' is not followed by whitespace'

    @Foo(0) String @Foo(1) [] @Foo(2) [] @Foo(3)[] field3;
    // violation above '')' is not followed by whitespace'

    void varargPlain(Object @TA... objs) {}
    // violation above ''TA' is not followed by whitespace'

    public enum Color {
        @JsonProperty("d")RED,  // violation '')' is not followed by whitespace'
        @JsonProperty("e")BLUE, // violation '')' is not followed by whitespace'
        @JsonProperty("j")GREEN // violation '')' is not followed by whitespace'
    }

    public enum Store {
        GROCERY;

        private List<@NonNull String> items;
        void process(String @TypeAnno("1")[] args) {}
        // violation above '')' is not followed by whitespace'
    }
    @Target(ElementType.TYPE_USE)
    @interface NonNull {}
}

@Target(ElementType.TYPE_USE)
@interface TypeAnno {
    String value();
}

interface Dummy<K, V> {}

@Target(ElementType.TYPE_USE)
@interface TypeAnno2 {
    String value();
}

class ParameterizedOuter<T> {
    class ParameterizedInner<U> {}
}

@Target(ElementType.TYPE_USE)
@interface TA {}

@Target(ElementType.TYPE_USE)
@interface TB {}

@Target(ElementType.TYPE_USE)
@interface Foo {
    int value();
}

@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER})
@interface JsonProperty {
    String value();
}
