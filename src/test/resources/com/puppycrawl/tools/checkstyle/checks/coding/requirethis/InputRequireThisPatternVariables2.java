/*
RequireThis
checkFields = (default)true
checkMethods = false
validateOnlyOverlapping = false


*/

package com.puppycrawl.tools.checkstyle.checks.coding.requirethis;

public class InputRequireThisPatternVariables2 {

    String p;

    public int scopeAfterIfTerminates(Object o) {
        if (!(o instanceof String p)) {
            return 0;
        }
        return p.length();
    }

    public int scopeAfterIfElseTerminates(Object o) {
        if (o instanceof String p) {
            identity(p);
        }
        else {
            return 0;
        }
        return p.length();
    }

    public int scopeAfterIfThenTerminatesElseCanComplete(Object o) {
        if (!(o instanceof String p)) {
            return 0;
        }
        else if (p.isEmpty()) {
            return 1;
        }
        return p.length();
    }

    public int scopeAfterIfElseNeitherTerminates(Object o) {
        if (o instanceof String p) {
            identity(p);
        }
        else {
            identity("other");
        }
        return p.length(); // violation 'Reference to instance variable 'p' needs "this.".'
    }

    public int scopeAfterIfWithoutElseNeitherTerminates(Object o) {
        if (o instanceof String p) {
            identity(p);
        }
        return p.length(); // violation 'Reference to instance variable 'p' needs "this.".'
    }

    public Object anonymousClassFieldShadowing(Object o) {
        if (o instanceof String p) {
            return new Object() {
                Integer p;
                int get() {
                    return p; // violation 'Reference to instance variable 'p' needs "this.".'
                }
            };
        }
        return null;
    }

    public Object anonymousClassCapturingOuter(Object o) {
        if (o instanceof String p) {
            return new Object() {
                int get() {
                    return p.length();
                }
            };
        }
        return null;
    }

    public int switchRule(Object o) {
        return switch (o) {
            case String p -> p.length();
            default -> 0;
        };
    }

    public int switchCaseGroup(Object o) {
        switch (o) {
            case String p:
                return p.length();
            default:
                return 0;
        }
    }

    public void recordPattern(Object o) {
        if (o instanceof Box(String p)) {
            p.length();
        }
    }

    private boolean identity(String x) {
        return x != null;
    }

    record Box(String p) { }
}
