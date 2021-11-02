/*
UnusedLocalVariable


*/

package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.ArrayList;
import java.util.function.Predicate;

public class InputUnusedLocalVariable {

    int sameName = 20;

    int a = 12;

    String s;

    private void sameName(int unusedParameter) {
        int sameName = 10; // violation
        int b = 12; // violation
        b = 23;
        this.sameName /= 2;
    }

    public void testCallingMethods() {
        int var = 21; // ok
        sameName(var);
        var = sameName;
    }

    public void testInLambdas(int q) {
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
    }

    public void testMultipleIdentifiers() {
        int[] arr = {1};
        int a = 21;
        arr[0] = a;
        Class<Integer> b = int.class; // violation
    }

    public void testChainedCalls() {
        Obj p; // violation
        Obj q = null; // ok
        p = q.foo().p;
        if(s instanceof String) {
        }
        Boolean r = false;
        while(r.booleanValue() != true) {
        }
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
        for (int i = 0; i < 1; i++)
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
    }

    Object foo(Object a) {
        return a;
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
