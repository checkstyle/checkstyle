package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

/** some javadoc. */
public sealed class InputFormattedModifierOrderNonSealed
    permits CircleOne, SquareOne, RectangleOne {
}

// violation below 'Top-level class CircleOne has to reside in its own source file'
final class CircleOne extends InputFormattedModifierOrderNonSealed
        implements SquircleOne {
}

// violation below 'Top-level class RectangleOne has to reside in its own source file'
sealed class RectangleOne extends InputFormattedModifierOrderNonSealed
    implements Cloneable
    permits TransparentRectangleOne, FilledRectangleOne {
}

// violation below 'Top-level class TransparentRectangleOne has to reside in its own source file'
final class TransparentRectangleOne extends RectangleOne {
}

// violation below 'Top-level class SquareOne has to reside in its own source file'
sealed class SquareOne extends InputFormattedModifierOrderNonSealed implements SquircleOne {
  private sealed class OtherSquare extends SquareOne permits OtherSquare2 {
  }

  private final class OtherSquare2 extends OtherSquare {
  }

  static non-sealed class StaticClass implements SquircleOne {
  }
}

// violation below 'Top-level class FilledRectangleOne has to reside in its own source file'
final class FilledRectangleOne extends RectangleOne {
}

// violation below 'Top-level class SquircleOne has to reside in its own source file'
sealed interface SquircleOne permits CircleOne, SquareOne, Ellipse, SquareOne.StaticClass {
}

// violation below 'Top-level class Rhombus has to reside in its own source file'
sealed interface Rhombus permits Parallelogram,
        Parallelogram.Trapezoid {
}

// violation below 'Top-level class Parallelogram has to reside in its own source file'
record Parallelogram(int x, int y, double z) implements Rhombus {

  public record Trapezoid(int x, int y, double z) implements Rhombus {
  }
}

// violation below 'Top-level class Ellipse has to reside in its own source file'
non-sealed interface Ellipse extends SquircleOne {
}

// violation below 'Top-level class Oval has to reside in its own source file'
class Oval implements Ellipse {
}
