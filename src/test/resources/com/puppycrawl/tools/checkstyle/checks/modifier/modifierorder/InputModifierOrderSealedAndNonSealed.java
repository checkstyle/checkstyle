/*
ModifierOrder


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;
// violation below ''public' modifier out of order with the JLS suggestions.'
sealed public class InputModifierOrderSealedAndNonSealed
    permits CircleOne, SquareOne, RectangleOne {
}

final class CircleOne extends InputModifierOrderSealedAndNonSealed
        implements SquircleOne {
}

sealed class RectangleOne extends InputModifierOrderSealedAndNonSealed
    implements Cloneable
    permits TransparentRectangleOne, FilledRectangleOne {
}

final class TransparentRectangleOne extends RectangleOne {
}

sealed class SquareOne extends InputModifierOrderSealedAndNonSealed implements SquircleOne {
    sealed private class OtherSquare extends SquareOne permits OtherSquare2 {
    } // violation above ''private' modifier out of order with the JLS suggestions.'

    private final class OtherSquare2 extends OtherSquare {
    }


    static non-sealed strictfp class StaticClass implements SquircleOne {

    }
}

final strictfp class FilledRectangleOne extends RectangleOne {
}

sealed interface SquircleOne permits CircleOne, SquareOne, Ellipse, SquareOne.StaticClass {
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

strictfp non-sealed interface Ellipse extends SquircleOne {
// violation above ''non-sealed' modifier out of order with the JLS suggestions.'
}

class Oval implements Ellipse {

}
