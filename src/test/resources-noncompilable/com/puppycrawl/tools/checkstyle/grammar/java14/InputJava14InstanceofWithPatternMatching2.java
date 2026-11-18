// non-compiled with eclipse: s cannot be resolved
// non-compiled with javac: Compilable with Java21 individually
package com.puppycrawl.tools.checkstyle.grammar.java14;

public class InputJava14InstanceofWithPatternMatching2 {

    interface VoidPredicate {
        public boolean get();
    }

    public void t(Object o1, Object o2) {
        if (!(o1 instanceof String s) || !(o2 instanceof Integer i)){
        } else {
            s.length();
            i.intValue();
        }

        if (!(o1 instanceof String s) || !(o2 instanceof Integer i)){
        } else {
            s.length();
            i.intValue();
        }

        if (o1 instanceof String s && o2 instanceof Integer in) {
            s.length();
            in.intValue();
        }

        L1: {
            if (o1 instanceof String s) {
                s.length();
            } else {
                break L1;
            }
            s.length();
        }

        {
            L2: for ( ;!(o1 instanceof String s); ) {

            }

            s.length();
        }

        {
            while (!(o1 instanceof String s)) {
                L3: break L3;
            }

            while(o1 instanceof String str) {
                str.length();
            }
        }

        if (!new VoidPredicate() { public boolean get() { return o1 instanceof String str
                && !str.isEmpty();} }.get()) {
            throw new AssertionError();
        }

        if (!((VoidPredicate) () -> o1 instanceof String str && !str.isEmpty()).get()) {
            throw new AssertionError();
        }

        if (o1 instanceof String j && j.length() == 5 && o2 instanceof Integer z && z == 42) {
            System.out.println(j);
            System.out.println(z);
        } else {
        }

        int x = o1 instanceof String j ? j.length() : 2;

        x = !(o1 instanceof String j) ? 2 : j.length();

        Object ch = null;

        for (; o1 instanceof String j; j.length()) {
            System.out.println(j);
        }

        String formatted;
        if (o1 instanceof Integer i) formatted = String.format("int %d", i);
        else if (o1 instanceof Byte by) formatted = String.format("byte %d", by);
        else if (o1 instanceof Long l) formatted = String.format("long %d", l);
        else if (o1 instanceof Double d) formatted = String.format("double %f", d);
        else if (o1 instanceof String s) formatted = String.format("String %s", s);
        else formatted = String.format("Something else "+ o1.toString());

        do {
            L4: break L4;
        } while (!(o1 instanceof String s));

        do {
            L4: break L4;
        } while (!(o1 instanceof java.lang.Double log));
    }

    static class Pattern_Simple {
        public static void test(Object o) {
            if (o instanceof String s) {}
        }
    }

    static class Pattern_Lambda {
        public static void test(Object o) {
            if (o instanceof String s) {
                Runnable r = () -> {
                    s.length();
                };
            }
        }
    }
}
