/*
UnusedLocalVariable


*/

//non-compiled with javac: Non compilable as hypothetical methods and variables used
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import somepackage.c;
import somepackage.d;

public class InputUnusedLocalVariableChainedCalls {

    public String s;

    public String m;

    public void testChainedCallsWithNewKeyword() {
        Object a; // violation
        Object b; // violation
        a = foo(new c.b(new d.a()));
        a = foo(new b().new a());
    }

    public void testChainedCalls() {
        Object p; // violation
        Object q; // ok
        p = q.foo().p;
    }

    public void testPatternVariables() {
        Object o = new Object();
        if (o instanceof String s || s.equals("dfsdf") && s.equals("fsdf")) { // violation
            int s = 12;
            s /= 2;
        }
        if (m.equals("fd") && (o instanceof String m
                && (m.toLowerCase(Locale.ROOT).equals("das")
                || m.equals("sa") && m.equals("fdsfsdf"))) && m.equals("asd")) {
        }
    }

    class b {
        class a {
        }
    }

}

