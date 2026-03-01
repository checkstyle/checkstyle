/*
SetterSinceTag


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.settersincetag;

public class InputSetterSinceTagCheckNonSetterFilter {

    /**
     * Gets something — name doesn't start with "set".
     *
     * @return true always
     */
    public boolean getEnabled() {
        return true;
    }

    /**
     * A method starting with "set" but returning non-void — not a setter.
     *
     * @param value the value
     * @return the value unchanged
     */
    public int setAndReturn(int value) {
        return value;
    }

    /**
     * A method starting with "set" but taking zero parameters — not a setter.
     */
    public void setNothing() {
        // no-op
    }

    /**
     * A non-public setter — should be skipped.
     *
     * @param enabled the value
     */
    void setEnabled(boolean enabled) {
        // package-private, not public
    }
}
