package com.puppycrawl.tools.checkstyle.grammar.antlr4;

public class InputAntlr4AstRegressionAnnotationOnArrays {



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

    public <T> T checkNotNullContents4(T @Nullable [] array) {
        if (array == null) {
            throw new NullPointerException();
        }
        return (T) array;
    }
}
