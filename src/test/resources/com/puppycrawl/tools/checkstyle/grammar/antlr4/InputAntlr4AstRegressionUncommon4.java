package com.puppycrawl.tools.checkstyle.grammar.antlr4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOError;
import java.io.IOException;
import java.util.function.BiFunction;

public class InputAntlr4AstRegressionUncommon4 {
    void m3() throws FileNotFoundException {
        int x = 2 /* comment */ + 2;
        int y = (/*comment */ x /*comment */ + 2);
        m4(/* param1 */ x, /* param2 */ x);
        BiFunction<Integer, Integer, Integer> lambda9
                = (/* param1 */ a, /* param2 */ b) -> a + b;

        BiFunction<Integer, Integer, Integer> lambda13
                = (/* param1 */ a /* param1 */, /* param2 */ b /* param2 */) -> a + b;
    }

    int m4 (int z, int q) throws FileNotFoundException {
        try (
                java
                        .
                        io
                        .
                        BufferedReader bufferedReader
                        = new BufferedReader(new FileReader("path"))) {
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 8;
    }

    class CelebrationLunch<I extends Number, L extends Number, D extends Number> {
        class Section<A extends Number, B extends Number, C extends Number> {

        }
    }

    private CelebrationLunch<Integer,Long,Double> inner;
    CelebrationLunch<Integer, Long, Double>
            .Section<Number, Number, Number> section = inner.new Section<>();

    @NonNull String p1 = "hello";
    void method() {
        try {
            System.out.println();
        }
        catch (@NonNull IOError | ArithmeticException e) {

        }
    }

    void m23(Class<Object> clazz) {
        if (InputAntlr4AstRegressionUncommon4.this != null) {
        }
    }

}

class H006_ComplexConstructors<T> {
    public <V> H006_ComplexConstructors(T t, V v) {
    }

    static {
        H006_ComplexConstructors<? extends Number> x =
                new <String>H006_ComplexConstructors<Integer>(0, "");
    }


    class Inner3 {
        Inner3(int x) {
            H006_ComplexConstructors<Integer> instance =
                    new <String>H006_ComplexConstructors<Integer>(0, "");
            Object o = instance.new Inner3(5).new <String>InnerInner3("hey");
        }

        class InnerInner3 {
            <D> InnerInner3(D in) {
            }
        }
    }
}

@interface NonNull{}
