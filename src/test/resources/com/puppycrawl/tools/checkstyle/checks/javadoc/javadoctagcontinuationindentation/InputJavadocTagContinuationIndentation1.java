/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = true
offset = (default)4
forceStrictCondition = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.io.IOException;

public class InputJavadocTagContinuationIndentation1 {

    int key;
    boolean usesShift;

    // violation 6 lines below 'Line continuation have .* expected level should be 4'
    /**
     * Constructs a new MenuShortcut for the specified virtual keycode.
     * @param key the raw keycode for this MenuShortcut, as would be returned.
     *
     * @param useShiftModifier indicates whether this MenuShortcut is invoked
     * with the SHIFT key down.
     * @see java.awt.event.KeyEvent
     **/
    public InputJavadocTagContinuationIndentation1(int key, boolean useShiftModifier) {
        this.key = key;
        this.usesShift = useShiftModifier;
    }
}
