/*
MissingNullCaseInSwitch

*/

//non-compiled with javac: Compilable with Java17
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithPattern2 {
    static void m(Object o) {
        switch (o) {  // violation, 'Switch using reference types should have a null case.'
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

    void m3(Object o) {
        switch (o) {  // violation, 'Switch using reference types should have a null case.'
            case String s && s.length() > 4 -> {}
            case (String s && s.length() > 6) -> {}
            case String s -> {}
            case default -> {}
        }
    }

   void m4(Object o) {
        switch (o) {
            case String s && s.length() > 4 -> {}
            case (String s && s.length() > 6) -> {}
            case String s -> {}
            case null -> {}
            case default -> {}
        }
    }
    void m5(char o) {
        switch (o) {
            case 'a' -> {}
            case default -> {}
        }
    }
}
