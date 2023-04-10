//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.grammar.java19;

public class InputRecordPatternsPreviewNestedDecomposition {
    record A(Object o) {
    }

    record B(Object o) {
    }

    record Point(int x, int y) {
    }

    enum Color {RED, GREEN, BLUE}

    record ColoredPoint(Point p, Color c) {
    }

    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) {
    }

    void method(Object param) {
        switch (param) {
            case A(Object o) -> {
            }
            case B(var o) -> {
            }
            default -> {
            }
        }
        if (param instanceof A(var o)) {

        }
    }

    // Decompose record
    static void p1(Rectangle r) {
        if (r instanceof Rectangle(ColoredPoint ul,ColoredPoint lr)) {
            System.out.println(ul.c());
        }
    }

    // We can decompose this record further, since Point is also
    // a record
    static void p2(Rectangle r) {
        if (r instanceof Rectangle(ColoredPoint(Point p1,Color c1),
                ColoredPoint lr1)
                && r instanceof Rectangle(ColoredPoint(Point p2,Color c2),
                ColoredPoint lr2) && lr2.c == Color.BLUE) {
            System.out.println(r);
        }
    }

    // We can keep decomposing down to the last "inner" record
    static void p3(Rectangle r) {
        if (r instanceof Rectangle(ColoredPoint(Point p1,Color c1),
                ColoredPoint lr1)
                && r instanceof Rectangle(
                ColoredPoint(Point(int x,int y),Color c2),
                ColoredPoint lr2)) {
            System.out.println(r);
        }
    }

    static void p4() {
        int x1 = 0;
        int x2 = 0;
        int y1 = 0;
        int y2 = 0;
        Color c1 = Color.BLUE;
        Color c2 = Color.GREEN;
        Rectangle r = new Rectangle(new ColoredPoint(new Point(x1, y1), c1),
                new ColoredPoint(new Point(x2, y2), c2));
        if (r instanceof Rectangle(
                ColoredPoint(Point(var x,var y),var c),
                var lr)) {
            System.out.println(x);
        }
    }
}
