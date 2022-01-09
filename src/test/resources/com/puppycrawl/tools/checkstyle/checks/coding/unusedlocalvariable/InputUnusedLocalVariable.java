/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.function.Predicate;

public class InputUnusedLocalVariable {

    int sameName = 20;

    static int a = 12;

    static int b = 12;

    String s;

    Test obj = new Test() {
        int a = 12;
        int b = 12;
    };

    private void sameName(int unusedParameter) {
        int sameName = 10; // violation
        int b = 12; // violation
        b = 23;
        this.sameName /= 2;
        int testInLambdas = 0; // violation
        testInLambdas = 12;
        int coding = 0; // violation
        int InputUnusedLocalVariable = 1; // violation
        com.puppycrawl.tools.checkstyle.checks
                .coding.unusedlocalvariable
                .InputUnusedLocalVariable.testInLambdas(a);
    }

    public void testCallingMethods() {
        int var = 21; // ok
        sameName(var);
        var = sameName;
    }

    public static void testInLambdas(int q) {
        int test = 21; // ok
        int isInOuterScope = 32; // ok
        Predicate<Integer> foo  = integer -> {
            int b; // violation
            int a = integer; // ok
            b = integer;
            boolean ans = a > 12; // ok
            int c = 21; // violation
            c = -isInOuterScope;
            return ans;
        };
        foo.test(test);
    }

    public void testIncrementAndDecrementKinds() {
        int a = 0; // violation
        a = ++this.a;
        a++;
        a--;
        int index = 1;
        int[] arr = {1};
        arr[--index] = 3;
        int ind = 0;
        testInLambdas(--ind);
        int k = 1;
        if (++k > 12) {
        }
        int p = 2;
        if ((++p - --p) > 21) {
        }
        int m = 2;
        int h = ++m;
        h += 1;
    }

    public void testMultipleIdentifiers() {
        int[] arr = {1};
        int a = 21;
        arr[0] = a;
        Class<Integer> b = int.class; // violation
        @SuppressWarnings("unused")
        int c = 41; // violation
    }

    public void testChainedCalls() throws InterruptedException {
        Obj p; // violation
        Obj q = null; // ok
        p = q.foo().p;
        if(s instanceof String) {
        }
        Boolean r = false;
        while(r.booleanValue() != true) {
        }
        int a = 13, b= 21;
        q.getClass().wait(a,b);
        int f = 12; // violation
        Predicate<String> obj = InputUnusedLocalVariable::f;
        obj.test("test");
        int foo = 12; // violation
        foo(q);
    }

    class SubClass extends InputUnusedLocalVariable {

        void testSuperKeyword() {
            int a = 12; // violation
            a = super.a;
        }
    }

    class Obj {
        Obj p;
        Obj foo() {
            return new Obj();
        }
    }

    public void testStatementsWithoutSlistToken() {
        int k = 1;
        for (int i = 0; k < 1; i++) // violation
            i++;

        for (int j = 0; ; ) { // violation
            j++;
            break;
        }

        ArrayList<String> obj = new ArrayList<>();

        for (String s : obj)
            System.out.println(s);
    }

    public void testChainedCallsWithNewKeyword() {
        Object a; // violation
        Object b; // violation
        a = foo(new c.b(new d.a()));
        a = foo(new b().new a());
        b = foo(new a.d());
    }

    public void testBooleanExpressions() {
        boolean b = false; // ok
        if (!b) {
        }
        boolean b1 = true; // ok
        if (b1) {
        }
        boolean a; // violation
        if ((a = true) != false) {
        }
        boolean j; // violation
        if (j = true) {
        }
        boolean k = true, l = false;
        if (k && !l) {
        }
    }

    public void testTryWithResources() {
        try (BufferedReader br =
                new BufferedReader(new FileReader("someFile.txt"))) { // ok
            br.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class Test implements a {

        public void testMethodReference() {
            int b = 12; // violation
            int Test = 12; // violation
            a obj = Test::new; // violation
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

    public void testDifferentAssignments() {
        int a = 12; // violation
        a = 13;
        int b = 13; // ok
        b /= 12;
        int c = 1; // ok
        c -= this.a;
        int h = 1; // ok
        h <<= 12;
    }

    public void testDoWhileLoops(int s) {
        do {
            int a = 12; // ok
            do {
                int b = 1; // violation
                b = a;
            }
            while (s > 1);
        }
        while (s > 0);
    }

    interface a {
        void method ();

        class d {
        }
    }

    Object foo(Object a) {
        return a;
    }

    public static boolean f(String a) {

        class k { // local class not to be added to "classes" stack
        }
        return true;
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
