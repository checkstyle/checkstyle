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
