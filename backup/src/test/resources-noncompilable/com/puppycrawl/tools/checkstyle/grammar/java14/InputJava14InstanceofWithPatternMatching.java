//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Locale;

/**
 * Input for Java 14 enhanced instanceof with pattern mathcing
 */
public class InputJava14InstanceofWithPatternMatching {

    public class Keyboard {
        private String model = null;
        private final int price;

        public Keyboard() {
            price = 0;
        }

        // Java 14 enhanced instanceof use in conditional
        @Override
        public boolean equals(Object obj) {
            return obj instanceof Keyboard other &&
                    model.equals(other.model) &&
                    price == other.price;
        }

        public int getPrice() {
            return price;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }
    }

    static {
        Object o = "";
        // Java 14 enhanced instanceof pattern variable usage
        if (o instanceof String s) {
            System.out.println(s.toLowerCase(Locale.forLanguageTag(s)));
            boolean stringCheck = "test".equals(s);
        }

        if (o instanceof Integer count) {
            int value = count.byteValue();
            if (count.equals(value)) {
                value = 25;
            }
        }

        // Canonical instanceof
        if (o instanceof String) {
            System.out.println("Yes");
        }

        String[] someString1 = {"some string"};
        if (someString1 instanceof Object[]) {
            System.out.println(Arrays.toString(someString1));
        }

        String[][] someString2 = new String[2][3];
        if (someString2 instanceof Object[][]) {
            System.out.println(Arrays.toString(someString2));
        }

        String[][][] someString3 = new String[3][4][5];
        if (someString3 instanceof Object[][]) {
            System.out.println(Arrays.toString(someString3));
        }
    }

    interface VoidPredicate {
        public boolean get();
    }

    public void t(Object o1, Object o2) {
        Object b;
        Object c;
        if (!(o1 instanceof String s) && (o2 instanceof String s)) {} //ok
        if (o1 instanceof String s || !(o2 instanceof String s)) {} //ok
        b = ((VoidPredicate) () -> o1 instanceof String s).get();

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> ai) {
            System.out.println("Blah");
        }

        if (!(o1 instanceof String k)) {
            return ;
        }

        if (o1 instanceof String s2) {}


        if (!(o1 instanceof String s4)) {
            return ;
        }

        boolean result = (o1 instanceof String a1) ?
                (o1 instanceof String a2) : (!(o1 instanceof String a3));

        if (!(o1 instanceof String s) ? false : s.length()>0){
            System.out.println("done");
        }

        if ((o1 instanceof String s) ? s.length()>0 : false){
            System.out.println("done");
        }

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
        } while (!(o1 instanceof java.util.logging.Logger log));
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
