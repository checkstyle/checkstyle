// non-compiled with javac: Compilable with Java21
package org.checkstyle.suppressionxpathfilter.missingnullcaseinswitch;

public class InputXpathMissingNullCaseInSwitchNested {
    void test(Object obj) {
       int x =  switch (obj) {
            case Rectangle(ColoredPoint _, ColoredPoint _) -> 1;
            case ColoredPoint(int _, int _, String _) -> 2;
            case null -> 3;
            default -> {
                int y = switch (obj) { // warn
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
