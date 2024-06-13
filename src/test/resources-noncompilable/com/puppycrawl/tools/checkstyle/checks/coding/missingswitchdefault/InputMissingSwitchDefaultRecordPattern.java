/*
MissingSwitchDefault


*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingswitchdefault;

public class InputMissingSwitchDefaultRecordPattern {
    sealed interface I permits C, D { }
    final class C implements I {}
    final class D implements I { }
    record Pair<T>(T x, T y) { }

    sealed interface W {
        record X(int x, int y) implements W { }
        record Y(int a, int b) implements W { }
    }

    sealed interface TwoDimensional { }
    record Point(int x, int y) implements TwoDimensional { }
    record Line(Point start, Point end) implements TwoDimensional { }


    void test1(Pair<I> p) {
        switch (p) {   // ok , exhaustiveness check
            case Pair<I>(I i, C j) -> System.out.println("Pair (C|D, C)");
            case Pair<I>(I i, D j) -> System.out.println("Pair (C|D, D)");
        }
    }

    void test2(W w) {
        switch (w) {
            case W.X(_, _) -> System.out.println("X");
            case W.Y(_, _) -> System.out.println("Y");
        }
        switch (w) {
            case W.X(int x, _) when x <= 0 -> System.out.println("X");
            case W.X(_, _) -> System.out.println("X2");
            case W.Y(_, _) -> System.out.println("Y");
        }
    }

    void test3(TwoDimensional obj) {
        switch (obj) {
            case Point(int x, int y) : {
                System.out.println("Point");
                break;
            }
            case Line(Point start, Point end) : {
                System.out.println("Line");
            }
        }
    }

    void test4(TwoDimensional obj) {
        switch (obj) {
            case Point(int x, int y) : {
                System.out.println("Point");
                break;
            }
            default: {
                System.out.println("Line");
            }
        }
    }

}
