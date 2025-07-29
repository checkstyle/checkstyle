/*
ModifierOrder


*/

// java17
package com.puppycrawl.tools.checkstyle.checks.modifier.modifierorder;

public sealed class InputModifierOrderSealedAndNonSealedNoViolation
    permits CircleTwo, SquareTwo, RectangleTwo {
}

final class CircleTwo extends InputModifierOrderSealedAndNonSealedNoViolation
        implements SquircleTwo {
}

sealed class RectangleTwo extends InputModifierOrderSealedAndNonSealedNoViolation
    implements Cloneable
    permits TransparentRectangleTwo, FilledRectangleTwo {
}

final class TransparentRectangleTwo extends RectangleTwo {
}

sealed class SquareTwo extends InputModifierOrderSealedAndNonSealedNoViolation
        implements SquircleTwo {
    private sealed class OtherSquare extends SquareTwo permits OtherSquare2 {
    }

    private final class OtherSquare2 extends OtherSquare {
    }
}

final strictfp class FilledRectangleTwo extends RectangleTwo {
}

sealed interface SquircleTwo permits CircleTwo, SquareTwo {
}
