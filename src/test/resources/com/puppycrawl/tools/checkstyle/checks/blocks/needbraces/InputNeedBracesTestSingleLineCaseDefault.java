/*
NeedBraces
allowSingleLineStatement = true
allowEmptyLoopBody = (default)false
tokens = LITERAL_CASE, LITERAL_DEFAULT


*/

package com.puppycrawl.tools.checkstyle.checks.blocks.needbraces;

public class InputNeedBracesTestSingleLineCaseDefault
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
        if (SomeClass.test(true) == true)
            return 4;
        return 0;
    }

    private int foo3() {
        if (SomeClass.test(true) == true) if (true) return 4;
        return 0;
    }

    private void foo(Object o) {
        if (o != null) this.notify();
    }

    private void foo2(Object o) {
        if (o != null)
            this.notify();
    }

    private void loopTest(Object o) {
        while (o != null) {
            this.notify();
        }
        while (o != null)
            this.notify();
        while (o != null) this.notify();
        do {
            this.notify();
        } while (o != null);
        do this.notify(); while (o != null);
        do
            this.notify();
        while (o != null);
        for (;;)
            break;
        for (;;) break;
        for (int i = 0; i < 10; i++) {
             this.notify();
        }
        for (int i = 0; i < 10; i++)
             this.notify();
        for (int i = 0; ; ) this.notify();
    }

    private int getSmth(int num)
    {
        int counter = 0;
        switch (num) {
            case 1: counter++; break;
            case 2: // violation, 'case' construct must use '{}'s
                counter += 2;
                break;
            case 3: // violation, 'case' construct must use '{}'s
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
        if (true)
            throw new RuntimeException();
        if (true) {
            return 1;
        } else
            return 2;
    }

    void enhancedForLoop(int[] array) {
        for (int value: array) return;
    }

    int[] sourceLocators;

    private class StateInfo {
        public boolean isInitial() {
            for (int locator: sourceLocators) if (locator != 0) return false;
            return true;
        }
    }

    private void forEachLoop() {
        for (String s: new String[]{""}) break;
        for (String s: new String[]{""})
            break;
        for (;;)
        ;
    }
    private void method(){
        if(false) {
            switch (0) {
                case -1: // violation, 'case' construct must use '{}'s
                    return;
                default: // violation, 'default' construct must use '{}'s
                    return;
            }
        }
        switch(1){
            case 1: return;
            default: throw new RuntimeException("");
        }
    }
}
