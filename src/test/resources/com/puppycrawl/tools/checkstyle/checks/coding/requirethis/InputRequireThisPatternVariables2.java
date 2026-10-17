/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariables2 {

    String p;
    String s;
    String n;

    public void andFieldInLeftOperand(Object o) {
        if (identity(p) && o instanceof String p) { // violation '.*'p' needs "this.".'
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
    public void andFalseIntersection(Object o) {
        if (!(o instanceof String p) && this.s == null) {
            p = "outside"; // violation 'Reference to instance variable 'p' needs "this.".'
        }
        else {
            p = "else"; // violation 'Reference to instance variable 'p' needs "this.".'
        }
    }
    public void ternaryConditionIntroduces(Object o) {
        String r = (identity(p) && o instanceof String p) ? p : this.p;
        // violation above 'Reference to instance variable 'p' needs "this.".'
    }
    public void parenthesizedOperands(Object o) {
        if ((o != null) && (o instanceof String p)) {
            p = "local";
        }
    }
    public void whileLoop(Object o) {
        while (o instanceof String p) {
            p = "local";
            o = null;
        }
        while (!(o instanceof String p)) {
            p = "outside"; // violation 'Reference to instance variable 'p' needs "this.".'
            o = "";
        }
    }
    private boolean identity(String x) {
        return x != null;
    }
}
