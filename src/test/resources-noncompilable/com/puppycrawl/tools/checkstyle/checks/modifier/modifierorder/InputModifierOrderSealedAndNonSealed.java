/*
ModifierOrder


*/

//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;
// violation below ''public' modifier out of order with the JLS suggestions.'
sealed public class InputModifierOrderSealedAndNonSealed
    permits Circle, Square, Rectangle {
}

final class Circle extends InputModifierOrderSealedAndNonSealed implements Squircle {
}

sealed class Rectangle extends InputModifierOrderSealedAndNonSealed
    implements Cloneable
    permits TransparentRectangle, FilledRectangle {
}

final class TransparentRectangle extends Rectangle {
}

sealed class Square extends InputModifierOrderSealedAndNonSealed implements Squircle {
    sealed private class OtherSquare extends Square permits OtherSquare2 {
    } // violation above ''private' modifier out of order with the JLS suggestions.'

    private final class OtherSquare2 extends OtherSquare {
    }


    static non-sealed strictfp class StaticClass implements Squircle {

    }
}

final strictfp class FilledRectangle extends Rectangle {
}

sealed interface Squircle permits Circle, Square, Ellipse, Square.StaticClass {
}
// violation below ''sealed' modifier out of order with the JLS suggestions.'
strictfp sealed interface Rhombus permits Parallelogram, Parallelogram.Diamond,
        Parallelogram.Trapezoid {

}

record Parallelogram(int x, int y, double z) implements Rhombus{
    final public record Diamond(int x, int y, double z)implements Rhombus {
    } // violation above ''public' modifier out of order with the JLS suggestions.'
// violation below ''static' modifier out of order with the JLS suggestions.'
    strictfp static public final record Trapezoid(int x, int y,
        double z)implements Rhombus {
    }
}

strictfp non-sealed interface Ellipse extends Squircle {
// violation above ''non-sealed' modifier out of order with the JLS suggestions.'
}

class Oval implements Ellipse {

}
