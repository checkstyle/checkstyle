/*
SetterSinceTag


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.settersincetag;

public class InputSetterSinceTagCheckValid {

    private boolean enabled;

    private boolean enabled2;

    /**
     * Sets the enabled flag.
     *
     * @param enabled the value to set
     * @since 13.3.0
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Sets the enabled flag
     *
     * @param enabled2 the value to set
     * @since 13.3.0
     */
    protected void setEnabled2(boolean enabled2) {
        this.enabled2 = enabled2;
    }

    public void updateEnabled(boolean enabled2) {
        this.enabled2 = enabled2;
    }
}

