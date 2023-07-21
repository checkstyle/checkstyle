//non-compiled with javac: Compilable with Java20
package com.puppycrawl.tools.checkstyle.grammar.java20;

import java.util.List;

public class InputJava20RecordDecompositionEnhancedForLoopTricky {
    void m() {
        // compiler is failing for annotations on some record components, need to file a ticket
//        for (Point(@A var x, @A @B var y) :
//                List.of(new Point(1, 2))) {
//            System.out.println(x + y);
//        }
//
//        for (Point(@B final Integer x, @A final @B Integer y)
//                : List.of(new Point(1, 2))) {
//            System.out.println(x + y);
//        }

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
    }

    record Point(@A Integer x, Integer y) { }
    record Points(Point... points) { }
    @ interface A {}
    @ interface B {}
}
