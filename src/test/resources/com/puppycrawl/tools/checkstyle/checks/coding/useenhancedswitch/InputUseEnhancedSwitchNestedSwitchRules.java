/*
UseEnhancedSwitch

*/

package com.puppycrawl.tools.checkstyle.checks.coding.useenhancedswitch;

public class InputUseEnhancedSwitchNestedSwitchRules {
    void terminated(int outer, int inner, Object value) {
        // violation below 'Switch can be replaced with enhanced switch.'
        switch (outer) {
            case 0:
                switch (inner) {
                    case 0 -> { return; }
                    default -> { return; }
                }
            case 1:
                switch (inner) {
                    case 0 -> throw new IllegalStateException();
                    default -> throw new IllegalArgumentException();
                }
            case 2:
                switch (value) {
                    case String text -> { return; }
                    case null, default -> { return; }
                }
            case 3:
                switch (value) {
                    case String text -> { return; }
                    case Object object -> { return; }
                }
            case 4:
                switch ((Box) value) {
                    case Box(int number) -> { return; }
                }
            case 5:
                switch ((Direction) value) {
                    case null -> { return; }
                    case LEFT -> { return; }
                    case RIGHT -> { return; }
                }
            default:
                return;
        }
    }

    void nonExhaustive(int outer, int inner) {
        switch (outer) {
            case 0:
                switch (inner) {
                    case 0 -> { return; }
                }
            default:
                return;
        }
    }

    void completesNormally(int outer, int inner) {
        switch (outer) {
            case 0:
                switch (inner) {
                    case 0 -> { return; }
                    default -> System.out.println(inner);
                }
            default:
                return;
        }
    }

    void innerBreak(int outer, int inner) {
        switch (outer) {
            case 0:
                switch (inner) {
                    case 0 -> { return; }
                    default -> { break; }
                }
            default:
                return;
        }
    }

    record Box(int number) { }

    enum Direction { LEFT, RIGHT }
}
