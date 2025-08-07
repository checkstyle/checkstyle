package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

// violation 2 lines below ''public' modifier out of order with the JLS suggestions.'
/** some javadoc. */
sealed public class InputModifierOrderNonSealed
    permits CircleOne1, SquareOne1, RectangleOne1 {
}

// violation below 'Top-level class CircleOne1 has to reside in its own source file'
final class CircleOne1 extends InputModifierOrderNonSealed
        implements SquircleOne1 {
}

// violation below 'Top-level class RectangleOne1 has to reside in its own source file'
sealed class RectangleOne1 extends InputModifierOrderNonSealed
    implements Cloneable
    permits TransparentRectangleOne1, FilledRectangleOne1 {
}

// violation below 'Top-level class TransparentRectangleOne1 has to reside in its own source file'
final class TransparentRectangleOne1 extends RectangleOne1 {
}

// violation below 'Top-level class SquareOne1 has to reside in its own source file'
sealed class SquareOne1 extends InputModifierOrderNonSealed implements SquircleOne1 {
  sealed private class OtherSquare extends SquareOne1 permits OtherSquare2 {
  } // violation above ''private' modifier out of order with the JLS suggestions.'

  private final class OtherSquare2 extends OtherSquare {
  }

  static non-sealed class StaticClass1 implements SquircleOne1 {
  }
}

// violation below 'Top-level class FilledRectangleOne1 has to reside in its own source file'
final class FilledRectangleOne1 extends RectangleOne1 {
}

// violation below 'Top-level class SquircleOne1 has to reside in its own source file'
sealed interface SquircleOne1 permits CircleOne1, SquareOne1, Ellipse1, SquareOne1.StaticClass1 {
}

// violation below 'Top-level class Rhombus1 has to reside in its own source file'
sealed interface Rhombus1 permits Parallelogram1,
        Parallelogram1.Trapezoid1 {
}

// violation below 'Top-level class Parallelogram1 has to reside in its own source file'
record Parallelogram1(int x, int y, double z) implements Rhombus1 {

  public record Trapezoid1(int x, int y, double z) implements Rhombus1 {
  }
}

// violation below 'Top-level class Ellipse1 has to reside in its own source file'
non-sealed interface Ellipse1 extends SquircleOne1 {
}

// violation below 'Top-level class Oval1 has to reside in its own source file'
class Oval1 implements Ellipse1 {
}
