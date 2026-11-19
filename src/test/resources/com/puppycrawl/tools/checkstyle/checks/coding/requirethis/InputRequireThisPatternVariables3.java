/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariables3 {

    String p;

    public void namedLocalClassFieldShadowing(Object o) {
        if (o instanceof String p) {
            class Local {
                String p;
                void test() {
                    p.length(); // violation 'Reference to instance variable 'p' needs "this.".'
                }
            }
        }
    }

    public int scopeAfterIfTerminatesWithInterveningStmt(Object o) {
        if (!(o instanceof String p)) {
            return 0;
        }
        int x = 1;
        return p.length() + x;
    }

    public int switchNoPattern(Object o) {
        switch (o) {
            case Integer i:
                return p.length(); // violation 'Reference to instance variable 'p' needs "this.".'
            default:
                return 0;
        }
    }

    public int switchRuleNoPattern(Object o) {
        return switch (o) {
            case Integer i -> p.length(); // violation 'Reference to instance variable 'p' needs "this.".'
            default -> 0;
        };
    }

    public void ternaryCondition(Object o) {
        String q = (identity(p) && o instanceof String p) ? p : this.p;
        // violation above 'Reference to instance variable 'p' needs "this.".'
        String r = (!(o instanceof String p) && identity(p)) ? this.p : this.p;
        // violation above 'Reference to instance variable 'p' needs "this.".'
    }

    public void ifAndWhileConditions(Object o) {
        if (!(o instanceof String p) && identity(p)) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            this.p = "";
        }
        while (identity(p) && o instanceof String p) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            o = null;
        }
        for (identity(p); o instanceof String p; ) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            p.length();
        }
        for (; ; ) {
            p = "infinite"; // violation 'Reference to instance variable 'p' needs "this.".'
            break;
        }
    }

    public void nestedLogicalOps(Object o) {
        if ((identity(p) && o instanceof String p) && o != null) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            p.length();
        }
        if ((!(o instanceof String p) && identity(p)) || o == null) {
            // violation above 'Reference to instance variable 'p' needs "this.".'
            this.p = "reset";
        }
    }

    private boolean identity(String x) {
        return x != null;
    }
}
