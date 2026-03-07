/*
SetterSinceTag


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.settersincetag;

public class InputSetterSinceTagCheckNoJavadocCheck {

    private boolean enabled;

    /* regular block comment, not javadoc comment */
    public void setEnabled(boolean enabled) { // violation, '@since' tag is missing
        this.enabled = enabled;
    }
}
