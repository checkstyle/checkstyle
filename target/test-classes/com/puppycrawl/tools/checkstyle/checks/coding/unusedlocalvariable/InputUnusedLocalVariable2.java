/*
UnusedLocalVariable
allowUnnamedVariables = false

*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.ArrayList;
import java.util.function.Predicate;

public class InputUnusedLocalVariable2 {
    int a = 12;

    public void testStatementsWithoutSlistToken() {
        int k = 1;
        for (int i = 0; k < 1; i++) // violation, 'Unused local variable'
            i++;
        for (int j = 0; ; ) { // violation, 'Unused local variable'
            j++;
            break;
        }
        ArrayList<String> obj = new ArrayList<>();
        for (String s : obj) {
            System.out.println(s);
        }
    }

    public void testChainedCallsWithNewKeyword() {
        Object a; // violation, 'Unused local variable'
        Object b; // violation, 'Unused local variable'
        a = testDifferentAssignments(new c.b(new d.a()));
        a = testDifferentAssignments(new b().new a());
        b = testDifferentAssignments(new a.d());
    }

    static class Test implements a {
        public void testMethodReference() {
            int b = 12; // violation, 'Unused local variable'
            int Test = 12; // violation, 'Unused local variable'
            a obj = Test::new; // violation, 'Unused local variable'
            Test ab = new Test();
            Predicate<String> a = ab::b;
            a.test("abc");
        }

        private boolean b(String s) {
            return true;
        }

        public static boolean ba(String s) {
            return true;
        }

        @Override
        public void method() {
        }
    }

    public Object testDifferentAssignments(Object obj) {
        int a = 12; // violation, 'Unused local variable'
        a = 13;
        int b = 13;
        b /= 12;
        int c = 1;
        c -= this.a;
        int h = 1;
        h <<= 12;
        return null;
    }

    public void testDoWhileLoops(int s) {
        do {
            int a = 12;
            do {
                int b = 1; // violation, 'Unused local variable'
                b = a;
            }
            while (s > 1);
        }
        while (s > 0);
    }

    interface a {
        void method();

        class d {
        }
    }

    static class c {
        static class b {
            public b(Object a) {
            }
        }
    }

    static class d {
        public static class a {
        }
    }

    class b {
        class a {
        }
    }
}
