/*
NestedIfDepth
depth = (default)0

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.nestedifdepth;

public class InputNestedIfDepthInsideCaseBody {

    public void test(Object obj) {
        switch (obj) {
            case ColoredPoint(int x, int y, String z) when (x >= 2) -> {
                if (x >= 2) {
                    if (y >= 2) {
                        if (z.equals("red")) { // violation 'Nested if-else depth is 2 (max allowed is 1)'

                        }
                    }
                }
            }
            default -> {
            }
        }
    }

    record ColoredPoint(int x, int y, String z) { }
}
