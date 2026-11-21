/*
FallThrough
checkLastCaseGroup = (default)false
reliefPattern = (default)falls?[ -]?thr(u|ough)


*/

package com.puppycrawl.tools.checkstyle.checks.coding.fallthrough;

public class InputFallThroughNestedSwitchRules {
    void test(int outer, int inner, Object value) {
        outerSwitch:
        switch (outer) {
            case 0:
                switch (inner) {
                    case 0 -> /* rule body */ { return; }
                    /* default label */ default -> { return; } // trailing comment
                }
            case 1:
                switch (inner) {
                    case 0 -> throw new IllegalStateException();
                    default -> throw new IllegalArgumentException();
                }
            case 2:
                switch (inner) {
                    default -> { return; }
                    case 0 -> throw new IllegalStateException();
                }
            case 3:
                switch (inner) {
                    case 0, 2 -> { return; }
                    case 1 -> { return; }
                }
            case 4: // violation 'Fall through from previous branch'
                switch (inner) {
                    case 0 -> { return; }
                    default -> { }
                }
            case 5: // violation 'Fall through from previous branch'
                switch (inner) {
                    case 0 -> System.out.println(inner);
                    default -> { return; }
                }
            case 6: // violation 'Fall through from previous branch'
                switch (inner) {
                    case 0 -> { return; }
                    default -> { break; } // violation 2 lines below 'Fall through'
                }
            case 7:
                innerSwitch:
                switch (inner) {
                    case 0 -> { return; }
                    default -> { break innerSwitch; }
                }
            case 8: // violation 'Fall through from previous branch'
                switch (inner) {
                    case 0 -> { break outerSwitch; }
                    default -> { return; }
                }
            case 9:
                switch (value) {
                    case String text -> { return; }
                    case null, default -> { return; }
                }
            case 10:
                switch (value) {
                    case String text -> { return; }
                    case Object object -> { return; }
                }
            case 11:
                switch ((Box) value) {
                    case Box(int number) -> { return; }
                }
            case 12:
                switch ((Direction) value) {
                    case null -> { return; }
                    case LEFT -> { return; }
                    case RIGHT -> { return; }
                }
            default:
                return;
        }
    }

    void continuedLoop(int outer, int inner) {
        while (true) {
            switch (outer) {
                case 0:
                    switch (inner) {
                        case 0 -> { continue; }
                        default -> { return; }
                    }
                default:
                    return;
            }
        }
    }

    void loopInCase(int outer, int inner, boolean repeat) {
        switch (outer) {
            case 0:
                do {
                    switch (inner) {
                        case 0 -> { continue; }
                        default -> { return; }
                    }
                } while (repeat);
            default: // violation 'Fall through from previous branch'
                return;
        }
    }

    record Box(int number) { }

    enum Direction { LEFT, RIGHT }
}
