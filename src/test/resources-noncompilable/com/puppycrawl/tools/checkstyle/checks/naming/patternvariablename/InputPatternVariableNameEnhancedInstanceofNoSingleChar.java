/*
PatternVariableName
format = ^[a-z][a-zA-Z0-9]+$


*/

//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

import java.util.ArrayList;
import java.util.Locale;

public class InputPatternVariableNameEnhancedInstanceofNoSingleChar {
    private Object obj;

    static boolean doStuff(Object obj) {
        return obj instanceof Integer OTHER && OTHER > 0; // violation
    }

    static {
        Object o = "";
        if (o instanceof String s) { // violation
            System.out.println(s.toLowerCase(Locale.forLanguageTag(s)));
            boolean stringCheck = "test".equals(s);
        }

        if (o instanceof Integer Count) { // violation
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
        if (!(o1 instanceof String S) // violation
                && (o2 instanceof String STRING)) { // violation
        }
        if (o1 instanceof String STRING // violation
                || !(o2 instanceof String STRING)) { // violation
        }
        b = ((VoidPredicate) () -> o1 instanceof String s).get(); // violation

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> ai) {
            System.out.println("Blah");
        }

        boolean result = (o1 instanceof String a) ? // violation
                (o1 instanceof String x) // violation
                : (!(o1 instanceof String y)); // violation

        if (!(o1 instanceof Integer INTEGER) ? false : INTEGER > 0) { // violation
            System.out.println("done");
        }

        {
            while (!(o1 instanceof String Thing1)) { // violation
                L3:
                break L3;
            }
            while (o1 instanceof String Thing2) { // violation
                Thing2.length();
            }
        }

        int x = o1 instanceof String j ? j.length() : 2; // violation
        x = !(o1 instanceof String j) ? 2 : j.length(); // violation
        for (; o1 instanceof String j; j.length()) { // violation
            System.out.println(j);
        }

        do {
            L4:
            break L4;
        } while (!(o1 instanceof String s)); // violation
    }
}
