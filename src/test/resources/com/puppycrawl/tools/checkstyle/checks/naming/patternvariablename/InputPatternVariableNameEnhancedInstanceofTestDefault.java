/*
PatternVariableName
format = (default)^([a-z][a-zA-Z0-9]*|_)$


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

import java.util.*;
import java.util.Locale;

public class InputPatternVariableNameEnhancedInstanceofTestDefault {
    private Object obj;

    static boolean doStuff(Object obj) {
        return obj instanceof Integer OTHER && OTHER > 0;
    } // violation above, 'Name 'OTHER' must match pattern*.'

    static {
        Object o = "";
        if (o instanceof String s) {
            System.out.println(s.toLowerCase(Locale.forLanguageTag(s)));
            boolean stringCheck = "test".equals(s);
        }

        if (o instanceof Integer Count) { // violation, 'Name 'Count' must match pattern*.'
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
        if (!(o1 instanceof String S) // violation, 'Name 'S' must match pattern*.'
                && (o2 instanceof String STRING)) {
            // violation above, 'Name 'STRING' must match pattern*.'
        }

        if (o1 instanceof String STRING // violation, 'Name 'STRING' must match pattern*.'
                || !(o2 instanceof String STRING)) {
            // violation above, 'Name 'STRING' must match pattern*.'
        }
        b = ((VoidPredicate) () -> o1 instanceof String s).get();

        List<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> ai) {
            System.out.println("Blah");
        }

        boolean result = (o1 instanceof String a) ?
                (o1 instanceof String x) : (!(o1 instanceof String y));

        if (!(o1 instanceof Integer INTEGER) ? // violation, 'Name 'INTEGER' must match pattern*.'
                false : INTEGER > 0) {
            System.out.println("done");
        }

        {
            while (!(o1 instanceof String Thing1)) {
                L3: // violation above, 'Name 'Thing1' must match pattern*.'
                break L3;
            }
            while (o1 instanceof String Thing2) { // violation, 'Name 'Thing2' must match pattern*.'
                Thing2.length();
            }
        }

        int x = o1 instanceof String j ? j.length() : 2;
        x = !(o1 instanceof String j) ? 2 : j.length();
        for (; o1 instanceof String j; j.length()) {
            System.out.println(j);
        }

        do {
            L4:
            break L4;
        } while (!(o1 instanceof String s));
    }
}
