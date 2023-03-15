/*
ModifierOrder


*/

//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

sealed public class InputModifierOrderSealedAndNonSealed // violation
    permits Circle, Square, Rectangle {
}

final class Circle extends InputModifierOrderSealedAndNonSealed implements Squircle {
}

sealed class Rectangle extends InputModifierOrderSealedAndNonSealed // ok
    implements Cloneable
    permits TransparentRectangle, FilledRectangle {
}

final class TransparentRectangle extends Rectangle {
}

sealed class Square extends InputModifierOrderSealedAndNonSealed implements Squircle {
    sealed private class OtherSquare extends Square permits OtherSquare2 { // violation
    }

    private final class OtherSquare2 extends OtherSquare {
    }


    static non-sealed strictfp class StaticClass implements Squircle {

    }
}

final strictfp class FilledRectangle extends Rectangle {
}

sealed interface Squircle permits Circle, Square, Ellipse, Square.StaticClass { // ok
}

strictfp sealed interface Rhombus permits Parallelogram, Parallelogram.Diamond, // violation
        Parallelogram.Trapezoid {

}

record Parallelogram(int x, int y, double z) implements Rhombus{
    final public record Diamond(int x, int y, double z)implements Rhombus { // violation
    }

    strictfp static public final record Trapezoid(int x, int y, // violation
        double z)implements Rhombus {
    }
}

strictfp non-sealed interface Ellipse extends Squircle { // violation

}

class Oval implements Ellipse {

}
