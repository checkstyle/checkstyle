/*
RequireThis
checkFields = (default)true
checkMethods = (default)true
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariables {
    String p;
    String s;
    String n;

    public void run(String... arguments) {
        this.p = null;
        this.s = null;
        this.n = null;

        Object o = 45;
        if (!(o instanceof String p && p.equals("sd"))
            || !p.equals("wq")) {
            p = "a"; // violation 'Reference to instance variable 'p' needs "this."\.'
        }
        else if (!(o instanceof Integer s && o instanceof String n)) {
            p = "b";
            s = "b"; // violation 'Reference to instance variable 's' needs "this."\.'
            n = "b"; // violation 'Reference to instance variable 'n' needs "this."\.'
        }
        else {
            p = "c";
            s = 41;
            n = "c";
        }

        System.out.println(this.p + ":" + this.s + ":" + this.n);
    }

    public void testLogicalAnd(Object value) {
        if (!(value instanceof String p) && p.isEmpty()) { // violation '.*variable 'p'.*'
            p = "field"; // violation 'Reference to instance variable 'p' needs "this."\.'
        }
        if (value instanceof String p && !p.isEmpty() && p.length() > 1) {
            System.out.println(p);
        }
    }

    public void testLogicalOr(boolean first, boolean second) {
        if (!first || !second || p.isEmpty()) { // violation '.*variable 'p'.*'
            p = "field"; // violation 'Reference to instance variable 'p' needs "this."\.'
        }
    }

    public void testNestedLogicalAnd(Object first, Object second) {
        if (first instanceof String p
                && second instanceof String s
                && !p.isEmpty()
                && !s.isEmpty()) {
            System.out.println(p + s);
        }
    }

    public void testNestedLogicalOr(Object first, Object second) {
        if (!(first instanceof String p)
                || !(second instanceof String s)
                || p.isEmpty()
                || s.isEmpty()) {
            System.out.println();
        }
    }

    public void testScopeBeforePattern(Object value) {
        if ((p.isEmpty() && value instanceof String p) // violation '.*variable 'p'.*'
                && p.length() > 1) {
            System.out.println(p);
        }

        if ((s.isEmpty() || !(value instanceof String s)) // violation '.*variable 's'.*'
                || s.isEmpty()) {
            System.out.println();
        }
    }

    public void testDoubleNegation(Object value) {
        if (!!(value instanceof String p) && !p.isEmpty()) {
            System.out.println(p);
        }
    }

    public void testFalseBranchLogicalOr(Object first, Object second) {
        if (!(first instanceof String p) || !(second instanceof String s)) {
            System.out.println();
        }
        else {
            System.out.println(p + s);
        }
    }

    public void testNestedLastOperands(Object first, Object second) {
        if (true && !(p.isEmpty() || !(first instanceof String p)) // violation
                && (second instanceof String s)) {
            System.out.println(p + s);
        }
        if (false || !(s.isEmpty() && second instanceof String s)) { // violation
            System.out.println();
        }
    }

    public void testFieldBeforePatternWithElse(Object value) {
        if (p.isEmpty() // violation '.*variable 'p'.*'
                || !(value instanceof String p)) {
            System.out.println();
        }
        else {
            System.out.println(p);
        }
    }
}
