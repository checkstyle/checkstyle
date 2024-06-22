/*
MissingNullCaseInSwitch

*/

//non-compiled with javac: Compilable with Java19
package com.puppycrawl.tools.checkstyle.checks.coding.missingnullcaseinswitch;

public class InputMissingNullCaseInSwitchWithPattern2 {

    void test (Object obj){
        // violation below, 'switch using reference types should have a null case.'
        switch (o) {
            case String s &&s.length() > 4:
                break;
            case (String s && s.length() > 6):
                break;
            case String s:
                break;
            case default:
                throw new UnsupportedOperationException("not supported!");
        }

         switch (o) {
            case String s &&s.length() > 4:
                break;
            case (String s && s.length() > 6):
                break;
            case String s:
                break;
            case default, null:
                throw new UnsupportedOperationException("not supported!");
        }

        switch (o) {
            case Integer i, null:
                break;
            case default:
                throw new UnsupportedOperationException("not supported!");
        }
        switch (o) {
            case Integer i, null:
                break;
            case String s && s.length() > 4: {}
        }
        // violation below, 'switch using reference types should have a null case.'
        switch (o) {
            case Integer i:
                break;
            case default: {}
        }
    }
}