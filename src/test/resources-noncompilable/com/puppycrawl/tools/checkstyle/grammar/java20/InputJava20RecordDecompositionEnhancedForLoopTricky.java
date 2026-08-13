// non-compiled with javac: but was compiled on jdk before 21, so we need to continue to support
package com.puppycrawl.tools.checkstyle.grammar.java20;

import java.util.List;

public class InputJava20RecordDecompositionEnhancedForLoopTricky {
    void m() {

        for (Point(@A var x, @A @B var y) :
                List.of(new Point(1, 2))) {
            System.out.println(x + y);
        }

        for (Point(@B final Integer x, @A final @B Integer y)
                : List.of(new Point(1, 2))) {
            System.out.println(x + y);
        }

        List<Points> points =
                List.of(new Points(new Point(1, 2), new Point(3, 4)));

        for (Points(@A Point[] points1) : points) {
            for (Point(final var x, final var y) : points1) {
                System.out.println(x + y);
            }
        }

        for (Points(@A @B final Point[] points1) : points) {
            for (Point(final var x, final var y) : points1) {
                System.out.println(x + y);
            }
        }

        List<Line> lines =
                List.of(new Line(new Point(1, 2), new Point(3, 4)));

        for (Line(Point(@A var x1, @B var y1), @A @B Point p2) : lines) {
            System.out.println(x1 + y1);
        }

        for (Line(@A Point(final var x1, @A @B final var y1), final Point p2)
                : lines) {
            System.out.println(x1 + y1);
        }

        for (Points(@A Point @B [] points1) : points) {
            System.out.println(points1.length);
        }

        List<Box<Integer>> boxes = List.of(new Box<>(1));

        for (Box<Integer>(@C(1) Integer i) : boxes) {
            System.out.println(i);
        }

        for (Box<@A Integer>(@C(value = 2) final var i) : boxes) {
            System.out.println(i);
        }

        for (Box<Integer>(
                @InputJava20RecordDecompositionEnhancedForLoopTricky.A Integer i)
                : boxes) {
            System.out.println(i);
        }
    }

    record Point(@A Integer x, Integer y) { }
    record Points(Point... points) { }
    record Line(Point p1, Point p2) { }
    record Box<T>(T t) { }
    @ interface A {}
    @ interface B {}
    @ interface C { int value(); }
}
