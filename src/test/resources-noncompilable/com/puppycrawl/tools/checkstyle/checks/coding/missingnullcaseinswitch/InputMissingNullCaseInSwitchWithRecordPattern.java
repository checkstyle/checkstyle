/*
MissingNullCaseInSwitch

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithRecordPattern {

    void testSwitchRule(Object obj) {
        switch (obj) {   // violation, 'Switch using reference types should have a null case.'
            case Rectangle(ColoredPoint _, ColoredPoint _) -> {}
            case ColoredPoint(int x, int _ ,String _) when x > 0 -> {}
            default -> {}
        }
        switch (obj) {
            case null -> {}
            case Rectangle(ColoredPoint _, ColoredPoint _) -> {}
            case ColoredPoint(int _, int _ ,String _) -> {}
            default -> {}
        }
        switch (obj) {
            case Rectangle(ColoredPoint _, ColoredPoint _) -> {}
            case null -> {}
            case ColoredPoint(int _, int _ ,String _) -> {}
            default -> {}
        }
    }

    void testSwitchStatments(Object obj) {
        switch (obj) {  // violation, 'Switch using reference types should have a null case.'
            case Rectangle(ColoredPoint(int _, int _, String _), ColoredPoint lc): {}break;
            default: {}
        }
        switch (obj) {
            case null: {}break;
            case Rectangle(ColoredPoint(int _, int _, String _), ColoredPoint lc): {}break;
            case ColoredPoint(_,_, String s): {}break;
            default: {}
        }
        switch (obj) {
            case Rectangle(ColoredPoint(int _, int _, String _), ColoredPoint lc): {}
            case null: {}
            default: {}
        }
    }

    int testSwitchExpression(Object obj, int x) {
        if (x == 1) {
            // violation below, 'Switch using reference types should have a null case.'
            return switch (obj) {
                case Rectangle(ColoredPoint(int _, int _, String _), ColoredPoint lc) -> 1;
                case ColoredPoint(int z, int y, String c) -> 2;
                default -> 3;
            };
        } else {
            return switch (obj) {
                case null -> 1;
                case Rectangle(ColoredPoint(int _, int _, String _), ColoredPoint lc) -> 1;
                case ColoredPoint(int z, int y, String c) -> 2;
                default -> 4;
            };
        }
    }
    int testSwitchExpression2(Object obj, int x) {
        if (x == 1) {
            // violation below, 'Switch using reference types should have a null case.'
            return switch (obj) {
                case Rectangle(ColoredPoint(int _, int _, String _), ColoredPoint lc): yield 1;
                case ColoredPoint(int z, int y, String c): yield 2;
                default : yield 3;
            };
        } else {
            return switch (obj) {
                case null : yield 1;
                case Rectangle(ColoredPoint(int _, int _, String _), ColoredPoint lc): yield 2;
                case ColoredPoint(int z, int y, String c): yield 3;
                default : yield 4;
            };
        }
    }

    void testWithCaseNullDefault(Object obj) {
        switch (obj) {
            case ColoredPoint(_,_,_) -> {}
            case null, default -> {}
        }
        // violation below, 'Switch using reference types should have a null case.'
        int x = switch (obj) {
            case ColoredPoint(_,_,_) -> 1;
            default -> 3;
        };
        int y = switch (obj) {
            case Rectangle(ColoredPoint _, ColoredPoint z) -> 2;
            case null, default -> 1;
        };
    }

    record ColoredPoint(int p, int x, String c) { }
    record Rectangle(ColoredPoint upperLeft, ColoredPoint lowerRight) { }
}
