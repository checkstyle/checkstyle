/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
tokens = (default)VARIABLE_DEF


*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.HashMap;
import java.util.Locale;



public class InputFinalLocalVariableAssignedMultipleTimes {

    void foo1() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
        }
        i = 2;
    }

    void foo2() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
        }
        else {

        }
        i = 2;
    }

    void foo3() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
            if (i >= 1) {

            }
            else {

            }
        }
        i = 2;
    }


    void foo4() {
        final boolean some_condition = true;
        int i; // violation, "Variable 'i' should be declared final"
        if (some_condition) {
            if (true) {
            }
            else {
            }
            i = 1;
        }
        else {
            i = 2;
        }
        if (true) {

        }
        else {
        }

    }

    void foo5() {
        final boolean some_condition = true;
        int i;

        {
            i = 2;
        }

        if (some_condition) {
            i = 1;
        }
    }

    void foo6() {
        final boolean some_condition = true;
        int i;

        {
            i = 2;
        }

        if (some_condition) {
            i = 1;
        }
        else {
            i = 6;
        }
    }

    void foo7() {
        final boolean some_condition = true;
        int i;
        if (some_condition) {
            i = 1;
        }
        else {
            i = 1;
        }
        i = 2;
    }

    void foo8() {
        final boolean some_condition = true;
        final int i;
        if (some_condition) {
            i = 1;
        }
        else {

        }
    }

    // Taken from findbugs
    public static String foo9(String filePath, String project) {
        String path = new String(filePath); // violation, "Variable 'path' should be declared final"
        String commonPath;
        if (project != null) {
            commonPath = "";
            // violation below "Variable 'relativePath' should be declared final"
            String relativePath = "";
            if (!relativePath.equals(path)) {
                return relativePath;
            }
        }
        commonPath = "";
        return commonPath;
    }

    // Taken from findbugs
    public int foo10(String factory1, String factory2) {
        int result = 0;
        String s1, s2;
        switch (result) {
            case 1:
                s1 = "Java";
                s2 = "C#";
                break;
            case 2:
                s1 = "C++";
                s2 = "Pascal";
                ;
                break;
            case 3:
                s1 = "Basic";
                s2 = "Angol";
                break;
            case 4:
                s1 = "JavaScript";
                s2 = "Go";
                break;
            case 5:
            default:
                s1 = "F#";
                s2 = "Objective C";
                break;
        }
        if (s1 == null) {
            s1 = "incorrect language";
        }
        if (s2 == null) {
            s2 = "incorrect language";
        }
        result = s1.compareTo(s2);

        if (result == 0) {
            switch (result) {
                case 1:
                    s1 = "Java";
                    s2 = "C#";
                    break;
                case 2:
                default:
                    s1 = "C++";
                    s2 = "C";
                    break;
            }
            result = s1.compareTo(s2);
        }
        else if (result == 1) {
            result = -8;
        }
        return result;
    }

    // Taken from findbugs
    public String foo11(final Object o, boolean getMinimal) {

        String n = System.lineSeparator();
        if (n != null) {
            return n;
        }

        try {

            String className;
            int kind; // violation, "Variable 'kind' should be declared final"
            boolean isParameterToInitMethodofAnonymousInnerClass = false;
            boolean isSyntheticMethod = false;
            if (o instanceof String || o instanceof Integer) {

                String m; // violation, "Variable 'm' should be declared final"
                if (o instanceof String) {
                    m = (String) o;
                    isSyntheticMethod = m.equals("");
                    kind = 1;
                    className = this.getClass().getName();
                }
                else if (o instanceof String) {
                    m = "";
                    // Don't
                    isSyntheticMethod = m.equals("");
                    className = this.getClass().getName();
                    kind = 2;
                    if ("<init>".equals(m.toLowerCase(Locale.getDefault()))) {
                        final int i = className.lastIndexOf('$');
                        if (i + 1 < className.length()) {
                            isParameterToInitMethodofAnonymousInnerClass = true;
                        }
                    }
                }
                else {
                    throw new IllegalStateException("impossible");
                }

                if (!m.equals("") && !"<init>".equals(m.toLowerCase(Locale.getDefault()))) {
                    final String c = "className";
                    // get inherited annotation
                    String inheritedAnnotations = new String();
                    if (c.charAt(1) > 0) {

                        n = "";
                        if (n != null) {
                            inheritedAnnotations += "";
                        }
                    }
                    for (int i = 5; i < 10; i++) {
                        n = new String("");
                        if (n != null) {
                            inheritedAnnotations += "";
                        }
                    }
                    if (n == null) {
                        String.CASE_INSENSITIVE_ORDER.equals("#" + inheritedAnnotations.length());
                    }
                    if (!inheritedAnnotations.isEmpty()) {
                        if (inheritedAnnotations.length() == 1) {
                            return inheritedAnnotations;
                        }
                        if (!getMinimal) {
                            return inheritedAnnotations;
                        }

                        String min = inheritedAnnotations;
                        if (min.length() == 0) {
                            inheritedAnnotations = null;
                            min = inheritedAnnotations;
                        }
                        return min;
                    }
                    // check to see if method is defined in this class;
                    // if not, on't consider default annotations
                    if (inheritedAnnotations == null) {
                        return null;
                    }
                    if (inheritedAnnotations.equals("")) {
                        String.CASE_INSENSITIVE_ORDER.equals("l" + " defines " + m);
                    }
                } // if not static
            } // associated with method
            else if (o instanceof String) {

                className = ((String) o).getClass().getName();
                kind = 3;
            }
            else if (o instanceof String) {
                assert false;
                className = (String) o;
                kind = 4;
            }
            else {
                throw new IllegalArgumentException("Can't" + o.getClass().getName());
            }

            // <init> method parameters for inner classes don't inherit default
            // annotations
            // since some of them are synthetic
            if (isParameterToInitMethodofAnonymousInnerClass) {
                return null;
            }

            // synthetic elements should not inherit default annotations
            if (isSyntheticMethod) {
                return null;
            }
            try {
                final String c = new String(className);

                if (c != null && c.equals("")) {
                    return null;
                }
            }
            catch (Exception e) {
                assert true;
            }

            // look for default annotation
            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for " + kind + " is " + n);
            }
            if (n != null) {
                return n;
            }

            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for any is " + n);
            }
            if (n != null) {
                return n;
            }

            final int p = className.lastIndexOf('.');
            className = className.substring(0, p + 1) + "package-info";
            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for " + kind + " is " + n);
            }
            if (n != null) {
                return n;
            }

            n = new String(className);
            if (n == null) {
                String.CASE_INSENSITIVE_ORDER.equals("Default annotation for any is " + n);
            }
            if (n != null) {
                return n;
            }

            return n;
        }
        catch (Exception e) {
            String.CASE_INSENSITIVE_ORDER.equals(e);
            ;
            return null;
        }

    }

    // Taken from findbugs
    private void foo12(Long start, Long end) {
        HashMap<Object, Object> headMap;
        if (end < Long.MAX_VALUE) {
            headMap = new HashMap<>();
            Long tailEnd = 1L;
            if (tailEnd != null) {
                end = tailEnd;
            }
            if (!headMap.isEmpty()) {
                tailEnd = (Long) headMap.get(headMap.size());
                if (tailEnd > end) {
                    end = tailEnd;
                }
            }
        }
        headMap = new HashMap<>();
        if (!headMap.isEmpty()) {
            final int headStart = headMap.size();
            final Long headEnd = (Long) headMap.get(headStart);
            if (headEnd >= start - 1) {
                headMap.remove(headStart);
                start = Long.valueOf(headStart);
            }
        }
        headMap.clear();
        headMap.remove(end);
        headMap.put(start, end);
    }

    // Taken from Guava
    public static int foo13(int p, int q, int mode) {
        String.CASE_INSENSITIVE_ORDER.equals(mode);
        ;
        if (q == 0) {
            throw new ArithmeticException("/ by zero"); // for GWT
        }
        final int div = p / q;
        final int rem = p - q * div; // equal to p % q

        if (rem == 0) {
            return div;
        }

      /*
      * Normal Java division rounds towards 0, consistently with RoundingMode.DOWN. We just have to
      * deal with the cases where rounding towards 0 is wrong,which typically depends on the sign of
      * p / q.
      *
      * signum is 1 if p and q are both nonnegative or both negative, and -1 otherwise.
      */
        final int signum = 1 | ((p ^ q) >> (Integer.SIZE - 1));
        boolean increment; // violation, "Variable 'increment' should be declared final"
        switch (mode) {
            case 1:
                String.CASE_INSENSITIVE_ORDER.equals(rem == 0);
                // fall through
            case 2:
                increment = false;
                break;
            case 3:
                increment = true;
                break;
            case 4:
                increment = signum > 0;
                break;
            case 5:
                increment = signum < 0;
                break;
            case 6:
            case 7:
            case 8:
                final int absRem = 1;
                final int cmpRemToHalfDivisor = absRem - (1 - absRem);
                // subtracting two nonnegative ints can't overflow
                // cmpRemToHalfDivisor has the same sign as compare(abs(rem), abs(q) / 2).
                if (cmpRemToHalfDivisor == 0) { // exactly on the half mark
                    increment = (mode == 1) || (mode == 2 & (div & 1) != 0);
                }
                else {
                    increment = cmpRemToHalfDivisor > 0; // closer to the UP value
                }
                break;
            default:
                throw new AssertionError();
        }
        return increment ? div + signum : div;
    }
}
