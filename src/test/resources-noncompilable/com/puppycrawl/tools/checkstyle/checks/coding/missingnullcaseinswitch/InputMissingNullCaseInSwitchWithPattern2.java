/*
MissingNullCaseInSwitch

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithPattern2 {
    static void m(Object o) {
        switch (o) {  // violation, 'switch using reference types should have a null case.'
            case String s &&s.length() > 2 ->{
            }
            default -> {
            }
        }
        switch (o) {
            case null, String s -> {
            }
            default -> {
            }
        }
        switch (o) {
            case default, null -> {
            }
        }
        switch (o) {
            case default, null: {
            }
        }
        switch (o) {
            case String s, null -> {
            }
            default -> {
            }
        }
    }

    static void m2(Object s) {
        switch (o) { // violation, 'switch using reference types should have a null case.'
            case String s &&s.length() > 4:
                break;
            case (String s && s.length() > 6):
                break;
            case String s:
                break;
            case default:
                throw new UnsupportedOperationException("not supported!");
        }
    }
}
