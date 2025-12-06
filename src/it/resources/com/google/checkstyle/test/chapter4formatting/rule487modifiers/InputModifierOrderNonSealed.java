package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

// violation 2 lines below ''public' modifier out of order with the JLS suggestions.'
/** Some javadoc. */
sealed public class InputModifierOrderNonSealed
    permits InputModifierOrderNonSealed.CircleOne1, InputModifierOrderNonSealed.RectangleOne1,
        InputModifierOrderNonSealed.SquareOne1 {

  final class CircleOne1 extends InputModifierOrderNonSealed
      implements SquircleOne1 {
  }

  sealed class RectangleOne1 extends InputModifierOrderNonSealed
          permits TransparentRectangleOne1, FilledRectangleOne1 {
  }

  final class TransparentRectangleOne1 extends RectangleOne1 {
  }

  sealed class SquareOne1 extends InputModifierOrderNonSealed implements SquircleOne1 {

    sealed private class OtherSquare extends SquareOne1 permits OtherSquare2 {
    } // violation above ''private' modifier out of order with the JLS suggestions.'

    private final class OtherSquare2 extends OtherSquare {}

    static non-sealed class StaticClass1 implements SquircleOne1 {}
  }

  final class FilledRectangleOne1 extends RectangleOne1 {}

  sealed interface SquircleOne1 permits CircleOne1, SquareOne1, Ellipse1, SquareOne1.StaticClass1 {
  }

  sealed interface Rhombus1 permits Parallelogram1,
          Parallelogram1.Trapezoid1 {}

  record Parallelogram1(int x, int y, double z) implements Rhombus1 {

    public record Trapezoid1(int x, int y, double z) implements Rhombus1 {}
  }

  non-sealed interface Ellipse1 extends SquircleOne1 {}

  class Oval1 implements Ellipse1 {}
}
