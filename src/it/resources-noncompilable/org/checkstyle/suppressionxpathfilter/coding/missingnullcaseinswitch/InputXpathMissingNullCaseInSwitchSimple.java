/*
MissingNullCaseInSwitch


*/

// non-compiled with javac: Compilable with Java25
package org.checkstyle.suppressionxpathfilter.coding.missingnullcaseinswitch;

public class InputXpathMissingNullCaseInSwitchSimple {

    void test(Object obj) {

        // violation below 'Switch using reference types should have a null case.'
        switch (obj) {
            case Rectangle(ColoredPoint _, ColoredPoint _) -> {}
            case ColoredPoint(int _, int _, String _) -> {}
            default -> {}
        }
    }
    record ColoredPoint(int p, int x, String c) { }
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }
}
