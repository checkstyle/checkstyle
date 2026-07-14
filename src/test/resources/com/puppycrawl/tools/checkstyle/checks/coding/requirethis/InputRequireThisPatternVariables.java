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
            || !p.equals("wq")) { // violation 'Reference to instance variable 'p' needs "this."\.'
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
}
