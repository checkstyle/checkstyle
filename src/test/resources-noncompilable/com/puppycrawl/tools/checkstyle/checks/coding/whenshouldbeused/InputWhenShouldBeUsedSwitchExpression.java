/*
WhenShouldBeUsed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.whenshouldbeused;

public class InputWhenShouldBeUsedSwitchExpression {

    void test(Object j, int x) {
        int a = switch (j) {
            case Point(int _, int y):
                if (y == 0) {
                    yield 0;
                }
            case String _: {if (x == 0)  yield 4;}
            case Integer _: if (x == 0)  yield 4;
            default: { yield 3;}
        };
        int b = switch (j) {
            case Point(int _, int y): {
                if (y == 0) {
                    yield 0;
                }
            }
            case ColoredPoint(Point(_, _), String _) : {
                if (x == 0) yield 4;
            }
            default: { yield 3;}
        };
        int bb = switch (j) {
            case Point(int _, int y) when y == 0:  {
                yield 0;
            }
            case ColoredPoint(Point(_, _), String _) when x == 0: {
                yield 4;
            }
            default: { yield 3;}
        };

        int d = switch (j) {
            case String s: // ok, not a single if
                if (s.isEmpty()) {
                    System.out.println("empty String");
                    yield 0;
                }
                if (!s.isEmpty())
                      yield 1;
            default: { yield 3;}
        };

        int f = switch (j) {
            case Point(int _, int y) -> {
                if (y == 0) {
                    yield 0;
                }
                yield 3;
            }
            case ColoredPoint(Point(int xx, int y), String color) -> {
                if (y == 0 && xx == 0 && color.equals("red")) {
                    yield 1;
                }
                System.out.println("red point at origin");
                yield 4;
            }
            default -> 3;
        };

        int m = switch (j) {
            case Point _:
            case ColoredPoint _:
                if (x == 0) {
                    System.out.println("Integer, String");
                    yield 4;
                }
            default: {yield 3;}
        };

        int mm = switch (j) {
            case Point _ when x == 0:
            case ColoredPoint _ when x == 0:
                    System.out.println("Integer, String");
                    yield 4;
            default: {yield 3;}
        };

        int zz = switch (j) {
            case Point _ , ColoredPoint _ when x == 0:
                    System.out.println("Integer, String");
                    yield 4;
            default: {yield 3;}
        };

        int z = switch (j) {
            case Point _ , ColoredPoint _ -> {
                if (x == 0) {
                    yield 4;
                }
                yield 0;
            }
             default -> 3;
        };
    }
    record ColoredPoint(Point p , String color){}
    record Point(int x, int y) {}
}
