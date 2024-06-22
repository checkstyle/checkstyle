//non-compiled with javac: Compilable with Java21
package org.checkstyle.suppressionxpathfilter.missingnullcaseinswitch;

public class InputXpathMissingNullCaseInSwitchSimple {

    void test(Object obj) {

        switch (obj) { // warn
            case Rectangle(ColoredPoint _, ColoredPoint _) -> {}
            case ColoredPoint(int _, int _, String _) -> {}
            default -> {}
        }
    }
    record ColoredPoint(int p, int x, String c) { }
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }
}
