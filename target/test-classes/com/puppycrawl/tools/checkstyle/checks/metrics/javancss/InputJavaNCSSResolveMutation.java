/*
JavaNCSS
methodMaximum = (default)50
classMaximum = 80
fileMaximum = (default)2000
recordMaximum = (default)150


*/

package com.puppycrawl.tools.checkstyle.checks.metrics.javancss;

public class InputJavaNCSSResolveMutation {
    // violation above 'NCSS for this class is 84 (max allowed is 80)'

    public static class Some // violation 'NCSS for this class is 83 (max allowed is 80)'
    {
        static class SomeClass {
            boolean flag = true;

            static boolean test(boolean k) {
                return k;
            }
        }

        private int foo() {
            if (SomeClass.test(true)) return 4;
            return 0;
        }

        private int foo1() {
            return 4;
        }

        private int foo2() {
            if (SomeClass.test(true))
                return 4;
            return 0;
        }

        private int foo3() {
            if (SomeClass.test(true)) if (true) return 4;
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
            for (; ; )
                break;
            for (; ; ) break;
            for (int i = 0; i < 10; i++) {
                this.notify();
            }
            for (int i = 0; i < 10; i++)
                this.notify();
            for (int i = 0; ; ) this.notify();
        }

        private int getSmth(int num) {
            int counter = 0;
            switch (num) {
                case 1:
                    counter++;
                    break;
                case 2:
                    counter += 2;
                    break;
                case 3:
                    counter += 3;
                    break;
                case 6:
                    counter += 10;
                    break;
                default:
                    counter = 100;
                    break;
            }
            return counter;
        }

        private void testElse(int k) {
            if (k == 4) System.identityHashCode("yes");
            else System.identityHashCode("no");
            for (; ; ) ;
        }

        void enhancedForLoop(int[] array) {
            for (int value : array) return;
        }

        private void forEachLoop() {
            for (String s : new String[]{""}) break;
            for (String s : new String[]{""})
                break;
            for (; ; )
                ;
        }
    }
}
