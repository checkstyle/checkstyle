/*
WhenShouldBeUsed

*/

//non-compiled with javac: Compilable with Java21
package com.puppycrawl.tools.checkstyle.checks.coding.whenshouldbeused;

public class InputWhenShouldBeUsedNonPatternsSwitch {
    void test(char c, int x) {
        switch (x) {
            case 1  :   // ok, guard is allowed after patterns only
                if (x == 3) {
                }
                break;
            case 4: {}
        }
        switch (x) {
            case 1 :
                if (x == 0) {
                }
            case 4:
        }

        switch (c) {
            case 'a'  -> {
                if (x == 0) {}
            }
        }

        E e = E.A;
        switch (e) {
            case A : {
                if (x == 0) {}
            }
            case B : {
                if (x == 2) {}
            }
        }

         switch (e) {
            case A -> {
                if (x == 0) {}
            }
            case B -> {
                if (x == 5) {}
            }
        }

        String s = "mahfouz";
        switch (s) {
            case  "a" :
            case "b": {
                if (x == 0) {}
            }
        }

        switch (s) {
            case  "a" , "b" -> {
                if (x == 0) {}
            }
        }
    }
    enum E {
        A, B
    }
}
