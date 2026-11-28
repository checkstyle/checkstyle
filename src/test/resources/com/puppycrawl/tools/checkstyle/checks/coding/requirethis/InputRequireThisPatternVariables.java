/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariables {

    String p;
    String s;
    String n;

    public void issueExample(Object o) {
        this.p = null;
        this.s = null;
        this.n = null;
        if (!(o instanceof String p && p.equals("sd")) || !p.equals("wq")) {
            p = "a"; // violation 'Reference to instance variable 'p' needs "this.".'
        }
        else if (!(o instanceof Integer s && o instanceof String n)) {
            p = "b";
            s = "b"; // violation 'Reference to instance variable 's' needs "this.".'
            n = "b"; // violation 'Reference to instance variable 'n' needs "this.".'
        }
        else {
            p = "c";
            s = 41;
            n = "c";
        }
    }

    public void simpleIf(Object o) {
        if (o instanceof String p) {
            p.length();
        }
    }

    public void simpleAnd(Object o) {
        if (o instanceof String p && p.length() > 0) {
            p.length();
        }
    }

    public void fieldInLeftOperand(Object o) {
        if (identity(p) && o instanceof String p) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            p.length();
        }
    }

    public void simpleOr(Object o) {
        if (!(o instanceof String p) || p.isEmpty()) {
            p = "outside"; // violation 'Reference to instance variable 'p' needs "this.".'
        }
    }

    public void fieldInRightOperandOfOr(Object o) {
        if (o instanceof String p || p.length() > 0) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            p = "outside"; // violation 'Reference to instance variable 'p' needs "this.".'
        }
    }

    public void ternary(Object o) {
        String q = o instanceof String p ? p : this.p;
        q = o instanceof String p ? this.s : p;
        // violation above 'Reference to instance variable 'p' needs "this.".'
        q = (o instanceof String p) ? (p) : this.p;
    }

    public void ternaryElse(Object o) {
        String r = !(o instanceof String p) ? this.p : p;
    }

    public void whileLoop(Object o) {
        while (o instanceof String p) {
            p.length();
            o = null;
        }
        while (!(o instanceof String p)) {
            p = "outside"; // violation 'Reference to instance variable 'p' needs "this.".'
            o = "";
        }
        p.length();
    }

    public void forLoop(Object o) {
        for (; o instanceof String p; ) {
            p.length();
        }
        for (; o instanceof String p; p = "") {
            p.length();
        }
        p = "outside"; // violation 'Reference to instance variable 'p' needs "this.".'
    }

    private boolean identity(String x) {
        return x != null;
    }
}
