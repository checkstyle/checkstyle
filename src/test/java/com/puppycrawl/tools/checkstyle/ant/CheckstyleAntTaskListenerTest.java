package com.puppycrawl.tools.checkstyle.ant;

import org.junit.Assert;
import org.junit.Test;

/**
 * Test-class for internal listener of {@link CheckstyleAntTask}.
 */
public class CheckstyleAntTaskListenerTest {

    /**
     * This string represents the custom name to be set for the listener.
     */
    public static final String CUSTOMNAME_STRING = "customName";

    /**
     * This should provide 100% Line Coverage for this internal listener class.
     */
    @Test
    public final void setClassNameTest() {
        CheckstyleAntTask.Listener listener = new CheckstyleAntTask.Listener();
        listener.setClassname(CUSTOMNAME_STRING);
        Assert.assertTrue(listener.getClassname().equals(CUSTOMNAME_STRING));
    }
}
