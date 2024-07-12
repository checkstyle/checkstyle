/*
MissingNullCaseInSwitch

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithPrimitives {
    void testIntegers(int obj) {
        switch (obj) {   // ok, case labels are primitive types
            default -> {}
            case 1 + 4 -> {System.out.println("xxx");}
            case 2 -> {}
        }
        switch (obj) {
            // case null -> {}  this will not compile
            case 1 + 4 -> {}
            case 2 -> {}
            default -> {}
        }
        switch (obj) {
            case 1 :{System.out.println("xxx");}
            case 2: {}
            default :{}
        }
         int w = switch (obj) {
            case 1 + 2 + 5 + (( 1 + 3) + (3 + 1)) -> {
                System.out.println("xxx");
                yield 2;
            }
            default -> 3;
        };
        String s = switch (obj) {
            case 1 -> "4";
            default -> "3";
        };
        String f = switch (obj) {
            case 1 :
            case 4 :
                yield "$";
            default :yield "3";
        };
    }

    void testChar(char c) {
        switch (c) {   // ok, case labels are primitive types
            default -> {}
            case 'a' + 'c' -> {}
            case 'b' -> {}
        }
        switch (c) {
            // case null -> {}  this will not compile
            case 'a' -> {}
            case 'b' -> {}
            default -> {}
        }
        switch (c) {
            case 'a' :{}
            case 'b': {}
            default :{}
        }
    }
    void testCaseLabelAsIdent(int obj) {
        final int x = 1;
        final int y = 0;
        switch (obj) {   // ok, case labels are idents we ignore it
            default -> {}
            case x -> {}
            case y -> {}
        }
        switch (obj) {
            // case null -> {}
            case x -> {}
            case y -> {}
            default -> {}
        }
        E e = E.X;
        switch (e) {
            case X :{}
            case Y: {}
        }
    }

    void boxedTypes(Integer i) {
        switch (i) {   // case label are of a refetence type (Integer) but we can't detect
            default -> {}
            case 1 -> {}
            case 2 -> {}
        }

        switch (i) {
            case null -> {} // this compiles
            default -> {}
            case 1 -> {}
            case 2 -> {}
        }
    }
    enum E {X, Y}
}
