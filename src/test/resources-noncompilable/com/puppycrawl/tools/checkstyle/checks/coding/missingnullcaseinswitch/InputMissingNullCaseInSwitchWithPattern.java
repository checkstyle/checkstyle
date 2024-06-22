/*
MissingNullCaseInSwitch

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithPattern {

    void testSwitchRule(Object obj) {
        switch (obj) {   // violation, 'switch using reference types should have a null case.'
            case Integer i -> {}
            case String s -> {}
            default -> {}
        }
        switch (obj) {
            case null -> {}
            case Integer i -> {}
            case String s -> {}
            default -> {}
        }
        switch (obj) {
            case Integer i -> {}
            case null -> {}
            case String s -> {}
            default -> {}
        }
    }

    void testSwitchStatments(Object obj) {
        switch (obj) {  // violation, 'switch using reference types should have a null case.'
            case Integer i: {}break;
            case String s: {}break;
            default: {}
        }
        switch (obj) {
            case null: {}break;
            case Integer i: {}break;
            case String s: {}break;
            default: {}
        }
        switch (obj) {
            case Integer _: {}
            case String _: {}
            case null: {}
            default: {}
        }
    }

    int testSwitchExpression(Object obj, int x) {
        if (x == 1) {
            // violation below, 'switch using reference types should have a null case.'
            return switch (obj) {
                case Integer i -> 1;
                case String s -> 2;
                default -> 3;
            };
        } else {
            return switch (obj) {
                case null -> 1;
                case Integer i -> 2;
                case String s -> 3;
                default -> 4;
            };
        }
    }
    int testSwitchExpression2(Object obj, int x) {
        if (x == 1) {
            // violation below, 'switch using reference types should have a null case.'
            return switch (obj) {
                case Integer i : yield 1;
                case String s : yield 2;
                default : yield 3;
            };
        } else {
            return switch (obj) {
                case null : yield 1;
                case Integer i : yield 2;
                case String s : yield 3;
                default : yield 4;
            };
        }
    }

    void testWithCaseNullDefault(Object obj) {
        switch (obj) {
            case Integer i -> {}
            case String s -> {}
            case null, default -> {}
        }
        // violation below, 'switch using reference types should have a null case.'
        int x = switch (obj) {
            case Integer i -> 1;
            case String s -> 2;
            default -> 3;
        };
        int y = switch (obj) {
            case Integer i -> 2;
            case String s -> 3;
            case null, default -> 1;
        };

        switch (obj) {
            case Integer i : {}
            case String s : {}
            case null, default : {}
        }
    }
}
