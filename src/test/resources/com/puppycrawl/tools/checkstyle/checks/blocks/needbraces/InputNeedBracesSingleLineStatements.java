/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = (default)LITERAL_DO, LITERAL_ELSE, LITERAL_FOR, LITERAL_IF, LITERAL_WHILE


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesSingleLineStatements
{
    private static class SomeClass {
        boolean flag = true;
        private static boolean test(boolean k) {
            return k;
        }
    }

    private int foo() {
        if (SomeClass.test(true) == true) return 4; //No warning if 'mAllowSingleLineIf' is true
        return 0;
    }

    private int foo1() {
        if (SomeClass.test(true)) return 4; int k = 3; //No warning if 'mAllowSingleLineIf' is true
        return 0;
    }

    private int foo2() {
        if (SomeClass.test(true) == true) // violation
            return 4;
        return 0;
    }

    private int foo3() {
        if (SomeClass.test(true) == true) if (true) return 4; // violation
        return 0;
    }

    private void foo(Object o) {
        if (o != null) this.notify();
    }

    private void foo2(Object o) {
        if (o != null) // violation
            this.notify();
    }

    private void loopTest(Object o) {
        while (o != null) {
            this.notify();
        }
        while (o != null) // violation
            this.notify();
        while (o != null) this.notify();
        do {
            this.notify();
        } while (o != null);
        do this.notify(); while (o != null);
        do // violation
            this.notify();
        while (o != null);
        for (;;) // violation
            break;
        for (;;) break;
        for (int i = 0; i < 10; i++) {
             this.notify();
        }
        for (int i = 0; i < 10; i++) // violation
             this.notify();
        for (int i = 0; ; ) this.notify();
    }

    private int getSmth(int num)
    {
        int counter = 0;
        switch (num) {
            case 1: counter++; break;
            case 2: // ok
                counter += 2;
                break;
            case 3: // ok
                counter += 3;
                break;
            case 6: counter += 10; break;
            default: counter = 100; break;
        }
        return counter;
    }

    private void testElse(int k) {
        if (k == 4) System.identityHashCode("yes");
        else System.identityHashCode("no");
        for (;;);
    }

    private int testMissingWarnings() {
        if (true) // violation
            throw new RuntimeException();
        if (true) {
            return 1;
        } else // violation
            return 2;
    }

    void enhancedForLoop(int[] array) {
        for (int value: array) return;
    }

    int[] sourceLocators;

    private class StateInfo {
        public boolean isInitial() {
            for (int locator: sourceLocators) if (locator != 0) return false; // violation
            return true;
        }
    }

    private void forEachLoop() {
        for (String s: new String[]{""}) break;
        for (String s: new String[]{""}) // violation
            break;
        for (;;)
        ;
    }
    private void method(){
        if(false) {
            switch (0) {
                case -1: // ok
                    return;
                default: // ok
                    return;
            }
        }
        switch(1){
            case 1: return;
            default: throw new RuntimeException("");
        }
    }
}
