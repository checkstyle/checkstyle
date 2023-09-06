//non-compiled with javac: Compilable with Java14
package com.google.checkstyle.test.chapter5naming.rule527localvariablenames;

import java.util.ArrayList;
import java.util.Locale;

public class InputPatternVariableNameEnhancedInstanceofTestDefault {
    private Object obj;

    static boolean doStuff(Object obj) {
        return obj instanceof Integer OTHER && OTHER > 0; // warn
    }

    static {
        Object o = "";
        if (o instanceof String s) {
            System.out.println(s.toLowerCase(Locale.forLanguageTag(s)));
            boolean stringCheck = "test".equals(s);
        }

        if (o instanceof Integer Count) { // warn
            int value = Count.byteValue();
            if (Count.equals(value)) {
                value = 25;
            }
        }
    }

    interface VoidPredicate {
        public boolean get();
    }

    public void t(Object o1, Object o2) {
        Object b;
        Object c;
        if (!(o1 instanceof String aA) // warn
                && (o2 instanceof String a1_a)) { // warn
        }

        if (o1 instanceof String A_A // warn
                || !(o2 instanceof String aa2_a)) { // warn
        }
        b = ((VoidPredicate) () -> o1 instanceof String s).get();

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> ai) {
            System.out.println("Blah");
        }

        boolean result = (o1 instanceof String a) ?
                (o1 instanceof String x) : (!(o1 instanceof String y));

        if (!(o1 instanceof Integer _a) ? // warn
                false : _a > 0) {
            System.out.println("done");
        }

        {
            while (!(o1 instanceof String _aa)) { // warn
                L3:
                break L3;
            }
            while (o1 instanceof String aa_) { // warn
                aa_.length();
            }
        }

        int x = o1 instanceof String aaa$aaa ? aaa$aaa.length() : 2; // warn
        x = !(o1 instanceof String $aaaaaa) ? 2 : $aaaaaa.length(); // warn
        for (; o1 instanceof String aaaaaa$; aaaaaa$.length()) { // warn
            System.out.println(aaaaaa$);
        }

        do {
            L4:
            break L4;
        } while (!(o1 instanceof String _A_aa_B)); // warn
    }
}
