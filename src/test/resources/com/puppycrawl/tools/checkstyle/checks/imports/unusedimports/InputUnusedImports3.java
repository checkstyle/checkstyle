/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.awt.AWTException;
import java.awt.Rectangle; // violation 'Unused import - java.awt.Rectangle'
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent; // violation 'Unused import - java.awt.event.KeyEvent'

public class InputUnusedImports3 extends java.awt.Robot {
    private int delay;

    protected InputUnusedImports3() throws AWTException {
        super();
    }

    /**
     * Attention: usage of class by short name KeyEvent is not qualified as usage of type
     * Qualified usage is by javadoc tag 'link'.
     *
     * @param keycode which key to press. For example, KeyEvent
     */
    public void hitKey(int keycode) {
        keyPress(keycode);
        delay();
    }

    public void clickMouse(int buttons) {
        mousePress(buttons);
        delay();
    }

    public void clickMouse() {
        clickMouse(InputEvent.BUTTON1_MASK);
    }

    public void delay() {
        delay(delay);
    }
}
