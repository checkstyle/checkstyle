package com.puppycrawl.tools.checkstyle.checks.design;

import java.awt.Dimension;
import javax.swing.JPanel;

/**
 * Not a util class because it's not directly derived from java.lang.Object.
 */
public class InputNonUtilityClass extends JPanel
{
    /** HideUtilityClassConstructorCheck should not report this */
    public InputNonUtilityClass()
    {
	this.setPreferredSize(new Dimension(100, 100));
    }

    public static void utilMethod()
    {
	System.identityHashCode("I'm a utility method");
    }
}
