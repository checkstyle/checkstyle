/*
ModifierOrder


*/

//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

public sealed class InputModifierOrderSealedAndNonSealedNoViolation // ok
    permits Circle, Square, Rectangle {
}

final class Circle extends InputModifierOrderSealedAndNonSealedNoViolation implements Squircle {
}

sealed class Rectangle extends InputModifierOrderSealedAndNonSealedNoViolation // ok
    implements Cloneable
    permits TransparentRectangle, FilledRectangle {
}

final class TransparentRectangle extends Rectangle {
}

sealed class Square extends InputModifierOrderSealedAndNonSealedNoViolation implements Squircle {
    private sealed class OtherSquare extends Square permits OtherSquare2 { // ok
    }

    private final class OtherSquare2 extends OtherSquare {
    }
}

final strictfp class FilledRectangle extends Rectangle {
}

sealed interface Squircle permits Circle, Square { // ok
}
