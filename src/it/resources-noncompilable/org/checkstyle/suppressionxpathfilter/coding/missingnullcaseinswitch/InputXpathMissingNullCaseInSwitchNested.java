/*
MissingNullCaseInSwitch


*/

// non-compiled with javac: Compilable with Java25
package org.checkstyle.suppressionxpathfilter.coding.missingnullcaseinswitch;

public class InputXpathMissingNullCaseInSwitchNested {
    void test(Object obj) {
       int x =  switch (obj) {
            case Rectangle(ColoredPoint _, ColoredPoint _) -> 1;
            case ColoredPoint(int _, int _, String _) -> 2;
            case null -> 3;
            default -> {
                // violation below 'Switch using reference types should have a null case.'
                int y = switch (obj) {
                    case Rectangle(ColoredPoint _, ColoredPoint _) -> 1;
                    case ColoredPoint(int _, int _, String _) -> 2;
                    default -> 4;
                };
                yield y;
            }
        };
    }
    record ColoredPoint(int p, int x, String c) { }
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }
}
