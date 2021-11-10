/*
UnusedLocalVariable


*/

//non-compiled with javac: Compilable with Java16
package com.puppycrawl.tools.checkstyle.checks.coding.unusedlocalvariable;

import java.util.Locale;

public class InputUnusedLocalVariableChainedCalls {

    public String s;

    public String m;

    public void testChainedCallsWithNewKeyword() {
        Object a; // violation
        Object b; // violation
        a = foo(new c.b(new d.a()));
        a = foo(new b().new a());
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

    class c {
        static class b {
            public b(Object a) {
            }
        }
    }

    class d {
        public static class a {
        }
    }

    class Obj {
    }

    Obj foo(Object a) {
        return new Obj();
    }

    class b {
        class a {
        }
    }

}

