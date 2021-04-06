//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.grammar.java15;

public sealed class InputAstRegressionSealedAndPermits permits Circle, Square, Rectangle {
}

final class Circle extends InputAstRegressionSealedAndPermits implements Squircle {
}

sealed class Rectangle extends InputAstRegressionSealedAndPermits
    implements Cloneable
    permits TransparentRectangle, FilledRectangle {
}

final class TransparentRectangle extends Rectangle {
}

non-sealed class Square extends InputAstRegressionSealedAndPermits implements Squircle {
}

final class FilledRectangle extends Rectangle {
}

sealed interface Squircle permits Circle, Square {

}

class Tricky {
    public static void main(String[] args) {
        int non = 2;
        int sealed = 4;
        int permits = non - sealed;
    }
}
