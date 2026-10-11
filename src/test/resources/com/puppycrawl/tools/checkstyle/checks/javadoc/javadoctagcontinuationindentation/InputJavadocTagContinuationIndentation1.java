/*
JavadocTagContinuationIndentation
violateExecutionOnNonTightHtml = true
offset = (default)4


*/

package com.puppycrawl.tools.checkstyle.checks.javadoc.javadoctagcontinuationindentation;

import java.io.IOException;

public class InputJavadocTagContinuationIndentation1 {

    int key;
    boolean usesShift;

    /**
     * Constructs a new MenuShortcut for the specified virtual keycode.
     * @param key the raw keycode for this MenuShortcut, as would be returned.
     *
     * @param useShiftModifier indicates whether this MenuShortcut is invoked
     * with the SHIFT key down.
     * @see java.awt.event.KeyEvent
     **/
    // violation 3 lines above 'Line continuation have .* expected level should be 4'
    public InputJavadocTagContinuationIndentation1(int key, boolean useShiftModifier) {
        this.key = key;
        this.usesShift = useShiftModifier;
    }
}
