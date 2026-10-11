package com.google.checkstyle.test.chapter4formatting.rule487modifiers;

/** Some javadoc. */
public sealed class InputFormattedModifierOrderNonSealed
    permits InputFormattedModifierOrderNonSealed.CircleOne,
        InputFormattedModifierOrderNonSealed.RectangleOne,
        InputFormattedModifierOrderNonSealed.SquareOne {

  final class CircleOne extends InputFormattedModifierOrderNonSealed implements SquircleOne {}

  sealed class RectangleOne extends InputFormattedModifierOrderNonSealed
      permits TransparentRectangleOne, FilledRectangleOne {}

  final class TransparentRectangleOne extends RectangleOne {}

  sealed class SquareOne extends InputFormattedModifierOrderNonSealed implements SquircleOne {

    private sealed class OtherSquare extends SquareOne permits OtherSquare2 {}

    private final class OtherSquare2 extends OtherSquare {}

    static non-sealed class StaticClass implements SquircleOne {}
  }

  final class FilledRectangleOne extends RectangleOne {}

  sealed interface SquircleOne permits CircleOne, SquareOne, Ellipse, SquareOne.StaticClass {}

  sealed interface Rhombus permits Parallelogram, Parallelogram.Trapezoid {}

  record Parallelogram(int x, int y, double z) implements Rhombus {

    public record Trapezoid(int x, int y, double z) implements Rhombus {}
  }

  non-sealed interface Ellipse extends SquircleOne {}

  class Oval implements Ellipse {}
}
