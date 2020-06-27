//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.grammar.java14;

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

    public static void main(String[] args) {
        Object o1 = "hello";
        Integer in = 42;
        Object o2 = in;
        Object o3 = "there";

        if (Ktrue() ? o2 instanceof Integer x : o2 instanceof String x) {
            x.intValue();
        }
        if (Ktrue() ? o2 instanceof Integer x : true) {
            x.intValue();
        }

        if (o1 instanceof String s ? true : true) {
            s.length();
        }
        if (o1 instanceof String s ? true : o2 instanceof Integer s) {
            s.length();
        }
        if (o1 instanceof String s ? true : o2 instanceof Integer i) {
            s.length();
        }

        // Test for (e1 ? e2 : e3).T contains intersect(e1.F, e2.T)
        if (!(o1 instanceof String s) ? true : true) {
            s.length();
        }
        if (!(o1 instanceof String s) ? (o2 instanceof Integer s) : true) {
            s.length();
        }
        if (!(o1 instanceof String s) ? (o2 instanceof Integer i) : true) {
            s.length();
            i.intValue();
        }
        if (!(o1 instanceof String s) ? (o1 instanceof String s2) : true) {
            s.length();
            s2.length();
        }

        // Test for (e1 ? e2 : e3).F contains intersect(e2.F, e3.F)
        if (Ktrue() ? !(o2 instanceof Integer x) : !(o1 instanceof String x)) {
        } else {
            x.intValue();
        }
        if (Ktrue() ? !(o2 instanceof Integer x) : !(o1 instanceof String s)) {
        } else {
            x.intValue();
        }
        if (Ktrue() ? !(o2 instanceof Integer x) : !(o2 instanceof Integer x1)) {
        } else {
            x.intValue();
            x1.intValue();
        }
        if (Ktrue() ? !(o2 instanceof Integer x) : false) {
        } else {
            x.intValue();
        }

        // Test for (e1 ? e2 : e3).F contains intersect(e1.T, e3.F)
        if (o1 instanceof String s ? true : !(o2 instanceof Integer s)) {
        } else {
            s.length();
        }
        if (o1 instanceof String s ? true : !(o2 instanceof Integer i)) {
        } else {
            s.length();
            i.intValue();
        }
        if (o1 instanceof String s ? true : !(o2 instanceof String s1)) {
        } else {
            s.length();
            s1.length();
        }
        // Test for (e1 ? e2 : e3).F contains intersect(e1.F, e2.F)
        if (!(o1 instanceof String s) ? !(o1 instanceof String s1) : true) {
        } else {
            s.length();
            s1.length();
        }
        if (!(o1 instanceof String s) ? !(o2 instanceof Integer s) : true) {
        } else {
            s.length();
        }
        if (!(o1 instanceof String s) ? !(o2 instanceof Integer i) : true) {
        } else {
            s.length();
            i.intValue();
        }

        // Test for e1 ? e2: e3 - include e1.T in e2
        if (o1 instanceof String s ? false : s.length() > 0) {
            System.out.println("done");
        }
        if (o1 instanceof String s ? false : s.intValue != 0) {
            System.out.println("done");
        }

        // Test for e1 ? e2 : e3 - include e1.F in e3
        if (!(o1 instanceof String s) ? s.length() > 0 : false) {
            System.out.println("done");
        }
        if (!(o1 instanceof String s) ? s.intValue > 0 : false) {
            System.out.println("done");
        }
    }
}
