/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
          LITERAL_FOR,VARIABLE_DEF,EXPR

*/
package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

import java.util.HashMap;
import java.util.Locale;

public class InputFinalLocalVariableAssignedMultipleTimes4 {

    // Taken from findbugs, continuation of foo11
    public String foo11Annotations(String m, boolean getMinimal, String className) {
        String n = System.lineSeparator();
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
        n = new String(className);
        if (n == null) {
            String.CASE_INSENSITIVE_ORDER.equals("Default annotation for any is " + n);
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
}
