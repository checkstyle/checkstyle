//non-compiled with javac: Compilable with Java14
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

import java.util.ArrayList;
import java.util.Locale;

public class InputPatternVariableNameEnhancedInstanceof {
    private Object obj;

    static boolean doStuff(Object obj) {
        // Violation, OTHER doesn't match ^[a-z][a-zA-Z0-9]*$
        return obj instanceof Integer OTHER && OTHER > 0;
    }

    static {
        Object o = "";
        //Violation, s doesn't match ^[a-z][a-zA-Z0-9]+$
        if (o instanceof String s) {
            System.out.println(s.toLowerCase(Locale.forLanguageTag(s)));
            boolean stringCheck = "test".equals(s);
        }

        // Violation, Count doesn't match ^[a-z][a-zA-Z0-9]*$
        if (o instanceof Integer Count) {
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
        // Violation, STRING doesn't match ^[a-z][a-zA-Z0-9]*$
        if (!(o1 instanceof String S) && (o2 instanceof String STRING)) {
        }
        // Violation, STRING doesn't match ^[a-z][a-zA-Z0-9]*$
        // Check two on the same line
        if (o1 instanceof String STRING || !(o2 instanceof String STRING)) {
        }
        //Violation, s doesn't match ^[a-z][a-zA-Z0-9]+$
        b = ((VoidPredicate) () -> o1 instanceof String s).get();

        ArrayList<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> ai) {
            System.out.println("Blah");
        }

        //Violation, a doesn't match ^[a-z][a-zA-Z0-9]+$ (x3)
        boolean result = (o1 instanceof String a) ?
                (o1 instanceof String a) : (!(o1 instanceof String a));

        // Violation, INTEGER doesn't match ^[a-z][a-zA-Z0-9]*$
        if (!(o1 instanceof Integer INTEGER) ? false : INTEGER.length() > 0) {
            System.out.println("done");
        }

        {
            // Violation, Thing doesn't match ^[a-z][a-zA-Z0-9]*$
            while (!(o1 instanceof String Thing)) {
                L3:
                break L3;
            }
            // Violation, Thing doesn't match ^[a-z][a-zA-Z0-9]*$
            while (o1 instanceof String Thing) {
                Thing.length();
            }
        }

        // Violation, j doesn't match ^[a-z][a-zA-Z0-9]+$ (x3)
        int x = o1 instanceof String j ? j.length() : 2;
        x = !(o1 instanceof String j) ? 2 : j.length();
        for (; o1 instanceof String j; j.length()) {
            System.out.println(j);
        }

        do {
            L4:
            break L4;
            // Violation, s doesn't match ^[a-z][a-zA-Z0-9]+$ (x3)
        } while (!(o1 instanceof String s));
    }
}
