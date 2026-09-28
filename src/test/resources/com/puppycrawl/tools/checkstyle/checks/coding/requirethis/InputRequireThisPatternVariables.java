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
            p = "local";
        }
    }

    public void simpleAnd(Object o) {
        if (o instanceof String p && p.length() > 0) {
            p = "local";
        }
    }

    public void simpleOr(Object o) {
        if (!(o instanceof String p) || p.isEmpty()) {
            p = "outside"; // violation 'Reference to instance variable 'p' needs "this.".'
        }
    }

    public void ternary(Object o) {
        String q = o instanceof String p ? p : this.p;
        q = o instanceof String p ? this.s : p; // violation .*'p' needs "this.".'
        q = (o instanceof String p) ? p : this.p;
    }

    public void elseBranchIntroduces(Object o) {
        if (!(o instanceof String p)) {
            p = "outside"; // violation 'Reference to instance variable 'p' needs "this.".'
        }
        else {
            p = "local";
        }
    }

    public void nestedNot(Object o) {
        if (!(!(o instanceof String p))) {
            p = "local";
        }
    }

    public void recordPattern(Object o) {
        if (o instanceof Box(String p)) {
            p = "local";
        }
    }

    public void orIntersect(Object o) {
        if (o instanceof String p || o instanceof Integer s) {
            p = "x"; // violation 'Reference to instance variable 'p' needs "this.".'
            s = "y"; // violation 'Reference to instance variable 's' needs "this.".'
        }
    }

    public void ternaryElseIntroducesPattern(Object o) {
        String r = !(o instanceof String p) ? this.p : p;
    }

    public void andFieldInLeftOperand(Object o) {
        if (identity(p) && o instanceof String p) { // violation .*'p' needs "this.".'
            p = "z";
        }
    }

    public void andNestedPatternInRight(Object o) {
        if (identity(this.s) && (identity(p) && o instanceof String p)) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            p = "w";
        }
    }
    public void ternaryConditionWalk(Object o) {
        String r = !((identity(p)) && (o instanceof String p)) ? this.p : this.p;
        // violation above 'Reference to instance variable 'p' needs "this.".'
    }
    public void andNestedPatternInLeft(Object o) {
        if ((identity(p) && o instanceof String p) && identity(this.s)) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            p = "u";
        }
    }
    private boolean identity(String x) {
        return x != null;
    }
    record Box(String p) { }
}
