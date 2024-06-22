/*
MissingNullCaseInSwitch

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithStringLiterals {

    void testSwitchRule(String obj) {
        switch (obj) {   // violation, 'Switch using reference types should have a null case.'
            default -> {}
            case "mohamed" -> {}
            case "mahfouz" -> {}
        }
        switch (obj) {
            case null -> {}
            case "HOTD" -> {}
            case "GOT" -> {}
            default -> {}
        }
        switch (obj) { // violation, 'Switch using reference types should have a null case.'
            case 1 + "fa" -> {}
            case ("xxx" + "yyy") -> {}
            default -> {}
        }
    }

    void testSwitchStatments(String obj) {
        switch (obj) {  // violation, 'Switch using reference types should have a null case.'
            case "af": {}break;
            default: {}
        }
        switch (obj) {
            case null: {}break;
            case 1 + "a": {}break;
            case "ama" : {}break;
            default: {}
        }
        switch (obj) {
            case "boza": {}
            case null: {}
            default: {}
        }
    }

    int testSwitchExpression(String obj, int x) {
        if (x == 1) {
            // violation below, 'Switch using reference types should have a null case.'
            return switch (obj) {
                default -> 3;
                case "aa" -> 1;
                case "tt" -> 2;
            };
        } else {
            return switch (obj) {
                case null -> 1;
                case "aa" -> 1;
                case 1 + "x" -> 2;
                default -> 4;
            };
        }
    }
    int testSwitchExpression2(String obj, int x) {
        if (x == 1) {
            // violation below, 'Switch using reference types should have a null case.'
            return switch (obj) {
                case "a": yield 1;
                case "bb": yield 2;
                default : yield 3;
            };
        } else {
            return switch (obj) {
                case null : yield 1;
                case ("moh" +"fouz") : yield 2;
                case "ColoredPoint(int x, int y, String c)": yield 3;
                default : yield 4;
            };
        }
    }

    void test(String obj, int i) {
        switch (obj) {
            case "ColoredPoint(_,_,_)" -> {}
            case null, default -> {}
        }
        // violation below, 'Switch using reference types should have a null case.'
        int x = switch (obj) {
            case ("aa" + 11 + 12) -> 1;
            default -> 3;
        };
        int y = switch (obj) {
            case "PB" -> 2;
            case null, default -> 1;
        };
        // violation below, 'Switch using reference types should have a null case.'
        int z = switch (obj) {
            case 1 + 2 + 3 + "a" + 5 -> 2;
            default -> 3;
        };
        // violation below, 'Switch using reference types should have a null case.'
        int w = switch (obj) {
            case 1 + 2 + ("a" + 1) -> 2;
            default -> 3;
        };
        // violation below, 'Switch using reference types should have a null case.'
        int p = switch (obj) {
            case (1 + 2 + 3 + 4 + (1 + 2+ 3 + ("a" + 4 + 5 + 1) )) -> 2;
            default -> 3;
        };
        switch (i) {
            case 1: { String s = "A"; }
        }
        switch (i) {
            case 1 -> { String s = "A"; }
        }   String s = "A";
    }
}
