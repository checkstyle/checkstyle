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

    void testPatternVariables() {
        Object o = new Object();
        if (o instanceof String s || s.equals("dfsdf") && s.equals("fsdf")) { // violation
            int s = 12;
            s /= 2;
        }
        if (m.equals("fd") && (o instanceof String m
                && (m.toLowerCase(Locale.ROOT).equals("das")
                || m.equals("sa") && m.equals("fdsfsdf"))) && m.equals("asd")) {
        }
        if (m.equals("fd") && (o instanceof String m
                && (m.toLowerCase(Locale.ROOT).equals("das")
                || m.equals("sa") && m.equals("fdsfsdf"))) || m.equals("asd")) {
            String m; // violation
            m = this.m;
        }
        if(o instanceof String m && m.equals("fdsfsdf")) {
        }
        if(o instanceof String m // violation
                || m.equals("fdsfsdf")) {
        }
        if(m.equals("abc") && o instanceof String m) { // violation
        }
        if (s.equals("abc") && o instanceof String s && s.equals("abc") || s.equals("abc")) {
        }
        if (s.equals("abc") && o instanceof String s && s.equals("abc")
                && s.equals("abc") || s.equals("abc")) {
        }
        method(s.equals("abc") && o instanceof String s && s.equals("abc")
                && s.equals("abc") || s.equals("abc"));
        do {
            s.concat("abc");
        }
        while (o instanceof String s && s.equals("abc"));
        do {
            s.concat("abc");
        }
        while (o instanceof String s); // violation
        if(s.equals("abc") && o instanceof String s) {
            s.equals("a");
        }
        if(o instanceof String m // violation
                     || m.equals("asd")) {
        }
        if(o instanceof String s // violation
                && m.equals("a") && m.equals("b") || s.equals("asd")) {
        }
        if (m.equals("sad") && o instanceof String m
                && (m.toLowerCase(Locale.ROOT).equals("das")
                || m.equals("sa")) || m.equals("a")) {
            m = "foo";
        }
        if(o instanceof Boolean b && b || s.equals("a")) {
        }
        if(o instanceof Boolean b
                && b || s.equals("a")) {
        }
        if (!(o instanceof String aA) // violation
                && (o instanceof String a1_a)) { // violation
        }
        boolean x = !(o instanceof String $a) ? // violation
                !(s.equals("abc") && o instanceof String s) : s.equals($a); // violation

        boolean min = !(o instanceof String g)
                ? ((o instanceof String w) ? !(s.equals("abc") // violation
                && o instanceof String s && s.equals("abc")
                || s.equals("abc")) : s.length() == 0) : (o instanceof String r) ? // violation
                !(s.equals("abc") && o instanceof String s // violation
                        || s.equals("abc")) : s.length() == g.length();
        method(min);
    }

    void testStatementsWithoutSlistToken() {
        Object a = null;
        while (a instanceof String k) // violation
            k = "a";
    }

    void method(boolean a) {
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
