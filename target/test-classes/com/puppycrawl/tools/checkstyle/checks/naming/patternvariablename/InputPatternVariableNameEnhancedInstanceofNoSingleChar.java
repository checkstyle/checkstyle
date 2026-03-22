/*
PatternVariableName
format = ^[a-z][a-zA-Z0-9]+$


*/

// Java17
package com.puppycrawl.tools.checkstyle.checks.naming.patternvariablename;

import java.util.*;
import java.util.Locale;

public class InputPatternVariableNameEnhancedInstanceofNoSingleChar {
    private Object obj;

    static boolean doStuff(Object obj) {
        return obj instanceof Integer OTHER && OTHER > 0;
    } // violation above, 'Name 'OTHER' must match pattern*.'

    static {
        Object o = "";
        if (o instanceof String s) { // violation, 'Name 's' must match pattern*.'
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
        // violation above, 'Name 's' must match pattern*.'

        List<Integer> arrayList = new ArrayList<Integer>();
        if (arrayList instanceof ArrayList<Integer> ai) {
            System.out.println("Blah");
        }

        boolean result = (o1 instanceof String a) ? // violation, 'Name 'a' must match pattern*.'
                (o1 instanceof String x) // violation, 'Name 'x' must match pattern*.'
                : (!(o1 instanceof String y)); // violation, 'Name 'y' must match pattern*.'

        if (!(o1 instanceof Integer INTEGER) ? false : INTEGER > 0) {
            System.out.println("done"); // violation above, 'Name 'INTEGER' must match pattern*.'
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

        int x=o1 instanceof String j?j.length():2;//violation,'Name 'j' must match pattern*.'
        x=!(o1 instanceof String j)?2:j.length();//violation,'Name 'j' must match pattern*.'
        for (; o1 instanceof String j; j.length()) { // violation, 'Name 'j' must match pattern*.'
            System.out.println(j);
        }

        do {
            L4:
            break L4;
        } while (!(o1 instanceof String s)); // violation, 'Name 's' must match pattern*.'
    }
}
