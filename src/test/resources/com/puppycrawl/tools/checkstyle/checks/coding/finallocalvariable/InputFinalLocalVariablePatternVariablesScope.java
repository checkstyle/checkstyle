/*
FinalLocalVariable
validateEnhancedForLoopVariable = (default)false
validateUnnamedVariables = (default)false
validatePatternVariables = true
tokens = (default)IDENT,CTOR_DEF,METHOD_DEF,SLIST,OBJBLOCK,COMPACT_COMPILATION_UNIT,LITERAL_BREAK, \
         LITERAL_FOR,VARIABLE_DEF,PATTERN_VARIABLE_DEF,EXPR

*/

package com.puppycrawl.tools.checkstyle.checks.coding.finallocalvariable;

public class InputFinalLocalVariablePatternVariablesScope {
    public static boolean bigEnoughRect(String s) {
        if (!(s instanceof String r)) {
            return false;
        }
        r = "hello";
        return r.length() > 5;
    }

    public static int effectiveFinalRect(String s) {
        if (!(s instanceof String r)) { // violation
            return 0;
        }
        return r.length();
    }

    public static int testMultipleSameName(Object s) {
        if (s instanceof String r) { // violation
            System.out.println(r);
        }

        if (s instanceof Integer r) { // violation
            System.out.println(r);
        }

        return 0;
    }

    public void branchCoverage(Object obj) {
        // !isPatternVariableFlowScopeConditional
        boolean b = (obj instanceof String s) && s.length() > 0; // 2 violations
        if (b) {
            String s1 = "shadow"; // violation, out of scope
        }

        // if inside, else not descendant
        if (obj instanceof String s) { // violation
            System.out.println(s);
        } else {
            String s1 = "shadow"; // violation, out of scope
        }

        // if inside, else descendant, inverted
        if (!(obj instanceof String s)) {
            String s1 = "shadow"; // violation, out of scope
        } else {
            System.out.println(s); // inside scope
            s = "reassigned"; // no warning for reassignment
        }

        // while
        while (obj instanceof String s) { // violation
            System.out.println(s);
        }

        // if inverted without braces, RETURN
        if (!(obj instanceof Integer i)) return;
        i = 1; // reassigned

        // if inverted without braces, THROW
        if (!(obj instanceof Double d)) throw new RuntimeException();
        d = 1.0;
    }

    public void abruptCompletions(Object obj) {
        for (int i=0; i<10; i++) {
            // BREAK
            if (!(obj instanceof Float f)) break;
            f = 1.0f;

            // CONTINUE
            if (!(obj instanceof Character c)) continue;
            c = 'a';
        }
    }

    public void emptyBlock(Object obj) {
        if (!(obj instanceof String s)) {} // violation
        String s1 = "shadow"; // violation
    }

    public void test(Object obj) {
        if (obj instanceof String s) { // violation
            System.out.println(s);
        }

        String s = ""; // violation
    }

    public void test2(Object obj) {
        if (obj instanceof String r) {
            r = "1";
        }

        String r = ""; // violation
    }

    public void test3(Object obj) {
        if (obj instanceof String r) {
            r = "1";
        }

        if (obj instanceof String r) {
            r = "1";
        }
    }

    public void test4(Object obj) {
        if (!(obj instanceof String b)) {
            return;
        }
        b = "1";
    }

    public void test5(Object obj) {
        if (!(obj instanceof String s)) { // violation
            System.out.println("not a string");
        } else {
            System.out.println(s);
        }
        String s = ""; // violation
    }

    public void test6(Object obj) {
        if (!!(obj instanceof String s)) { // violation
            System.out.println(s);
        }
        String s = ""; // violation
    }

    public void test7(Object obj) {
        while (obj instanceof String s) { // violation
            System.out.println(s);
        }
        String s = ""; // violation
    }

    public void test8(Object obj) {
        for (; obj instanceof String s;) { // violation
            System.out.println(s);
        }
        String s = ""; // violation
    }

    public void extraCoverage(Object obj) {
        // do-while
        do {
            System.out.println("do");
        } while (obj instanceof String s); // violation
        String s = ""; // violation

        // switch
        switch (obj) {
            case String s3 -> System.out.println(s3);
            default -> {}
        }
        String s3 = ""; // violation

        // try-with-resources
        try (java.util.Scanner sc = new java.util.Scanner(System.in)) {
        }

        if (obj instanceof String s4) { // violation
             System.out.println(s4);
        }
        String s4 = ""; // violation

        // test switch with instance of
        boolean b = switch(obj) { // violation
            case String s5 when s5.length() > 0 -> true; // violation
            default -> false;
        };
        String s5 = ""; // violation
    }

    public void test10(Object obj) {
        if (obj instanceof String s8) {
            s8 = "1";
        } else {
            System.out.println("else");
        }
    }

    public void test13(Object obj) {
        while (obj instanceof String s12) {
            s12 = "1";
        }

        do {
            System.out.println("do");
        } while (obj instanceof String s13 && (s13 = "1").equals("1"));
    }

    public void test15(Object obj) {
        while (obj != null) {
            if (!(obj instanceof String s15)) {
                continue;
            }
            s15 = "1";
        }

        while (obj != null) {
            if (!(obj instanceof String s16)) {
                break;
            }
            s16 = "1";
        }

        if (!(obj instanceof String s17)) {
            throw new RuntimeException();
        }
        s17 = "1";
    }

    public void test17(Object obj) {
        switch (obj) {
            case String s20 -> {
                s20 = "1";
            }
            default -> {}
        }
    }

    public interface Test22 {
        void test22(int param);
    }

    public record Test21(int param) {}

    public void test16(Object obj) {
        if (!!(obj instanceof String s19)) {
            s19 = "1";
        }
    }
}
