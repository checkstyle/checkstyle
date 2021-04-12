//non-compiled with javac: Compilable with Java15
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

/* Config:
 * default
 */

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
}

final strictfp class FilledRectangle extends Rectangle {
}

sealed interface Squircle permits Circle, Square { // ok
}
