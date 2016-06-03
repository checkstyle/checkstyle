
package com.puppycrawl.tools.checkstyle.checks.design;

/** Shows that sealed enum is good as final. */
public enum InputEnumIsSealed {
    SOME_VALUE;

    static class Hole {
    }

    /** Normally disallowed if final enclosing class is required. */
    public final int someField = Integer.MAX_VALUE;

    /** Disallowed because mutable. */
    public final Hole hole = null;
}
