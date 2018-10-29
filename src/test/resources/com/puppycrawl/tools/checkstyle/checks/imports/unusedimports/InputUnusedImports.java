////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import com.puppycrawl.tools.checkstyle.checks.imports.*;
        import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImportsBug;
import java.io.*;
import java.lang.*;
import java.lang.String;

import java.util.List;
import java.util.List;
import java.lang.*;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Arrays;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;

import javax.swing.BorderFactory;

import static java.io.File.listRoots;

import static javax.swing.WindowConstants.*;
import static java.io.File.
    createTempFile;

import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Label;
import java.util.Date;
import java.util.Calendar;
import java.util.BitSet;

import com.puppycrawl.tools.checkstyle.Checker;
import com.puppycrawl.tools.checkstyle.CheckerTest;
import com.puppycrawl.tools.checkstyle.Definitions;
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImports15Extensions;
import com.puppycrawl.tools.checkstyle.ConfigurationLoaderTest;
import com.puppycrawl.tools.checkstyle.PackageNamesLoader;
import com.puppycrawl.tools.checkstyle.DefaultConfiguration;
import com.puppycrawl.tools.checkstyle.DefaultLogger;

/**
 * Test case for imports
 * Here's an import used only by javadoc: {@link Date}.
 * @author Oliver Burn
 * @author lkuehne
 * @author Michael Studman
 * @see Calendar Should avoid unused import for Calendar
 **/
class InputUnusedImports
{
    /** ignore {@literal <B>Test Javadoc Tag that is not processed for imports</B>}**/
    private Class mUse1 = null;
    /** ignore **/
    private Class mUse2 = java.io.File.class;
    /** ignore **/
    private Class mUse3 = Iterator[].class;
    /** ignore **/
    private Class mUse4 = java.util.Enumeration[].class;
    /** usage of illegal import **/
    private Object ftpClient = null;

    /** usage via static method, both normal and fully qualified */
    {
        int[] x = {};
        Arrays.sort(x);
        Object obj = javax.swing.BorderFactory.createEmptyBorder();
        File[] files = listRoots();
    }

    /** usage of inner class as type */
    private JToolBar.Separator mSep = null;

    /** usage of inner class in Constructor */
    private Object mUse5 = new Object();

    /** usage of inner class in constructor, fully qualified */
    private Object mUse6 = new javax.swing.JToggleButton.ToggleButtonModel();

    /** we use class name as member's name.
     *  also an inline JavaDoc-only import {@link Vector linkText} */
    private int Component;

    /**
     * method comment with JavaDoc-only import {@link BitSet#aMethod()}
     */
    public void Label() {}

    /**
     * Renders to a {@linkplain Graphics2D graphics context}.
     * @throws HeadlessException if no graphis environment can be found.
     * @exception HeadlessException if no graphis environment can be found.
     */
    public void render() {}

    /**
     * First is a class with a method with arguments {@link Checker#method1(CheckerTest)}.
     * Next is a class with typed method
     * {@link BaseFileSetCheckTestSupport#method2(Definitions, InputUnusedImports15Extensions)}.
     *
     * @param param1 with a link {@link ConfigurationLoaderTest}
     * @throws PackageNamesLoader when broken
     * @deprecated in 1 for removal in 2. Use {@link DefaultConfiguration}
     */
    public void aMethodWithManyLinks() {}
}
