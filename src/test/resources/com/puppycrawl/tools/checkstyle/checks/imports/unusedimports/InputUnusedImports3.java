/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.awt.AWTException;
import java.awt.Component; // violation 'Unused import - java.awt.Component'
import java.awt.Dimension; // violation 'Unused import - java.awt.Dimension'
import java.awt.EventQueue; // violation 'Unused import - java.awt.EventQueue'
import java.awt.Point; // violation 'Unused import - java.awt.Point'
import java.awt.Rectangle; // violation 'Unused import - java.awt.Rectangle'
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent; // violation 'Unused import - java.awt.event.KeyEvent'
import javax.swing.SwingUtilities; // violation 'Unused import - javax.swing.SwingUtilities'

public class InputUnusedImports3 extends java.awt.Robot {
    private static final int DEFAULT_DELAY = 550;
    private static final int INTERNAL_DELAY = 250;
    private final boolean delaysEnabled;
    private int delay;

    protected InputUnusedImports3(boolean enableDelays) throws AWTException {
        super();
        delaysEnabled = enableDelays;
        setAutoWaitForIdle(enableDelays);
        if (enableDelays) {
            setAutoDelay(INTERNAL_DELAY);
            setDelay(DEFAULT_DELAY);
        }
    }

    /**
     * Press and release a key.
     *
     * @param keycode which key to press. For example, KeyEvent.VK_DOWN
     */
    public void hitKey(int keycode) {
        keyPress(keycode);
        keyRelease(keycode);
        delay();
    }

    /**
     * Perform a mouse click, i.e. press and release mouse button(s).
     *
     * @param buttons mouse button(s).
     *                For example, MouseEvent.BUTTON1_MASK
     */
    public void clickMouse(int buttons) {
        mousePress(buttons);
        mouseRelease(buttons);
        delay();
    }

    /**
     * Perform a click with the first mouse button.
     */
    public void clickMouse() {
        clickMouse(InputEvent.BUTTON1_MASK);
    }

    public void delay() {
        delay(delay);
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
