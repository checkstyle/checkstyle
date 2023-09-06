/*
UnusedImports
processJavadoc = false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import com.puppycrawl.tools.checkstyle.checks.imports.*;
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports. //violation
        InputUnusedImportsBug;
import java.io.*;
import java.lang.*;
import java.lang.String; // violation

import java.util.List; // violation
import java.util.List; // violation
import java.lang.*;
import java.util.Iterator;
import java.util.Enumeration; // violation
import java.util.Arrays;
import javax.swing.JToolBar;
import javax.swing.JToggleButton; // violation

import javax.swing.BorderFactory; // violation

import static java.io.File.listRoots;

import static javax.swing.WindowConstants.*;
import static java.io.File. // violation
    createTempFile;

import java.awt.Graphics2D; // violation
import java.awt.HeadlessException; // violation
import java.awt.Label; // violation
import java.util.Date; // violation
import java.util.Calendar; // violation
import java.util.BitSet; // violation

import com.puppycrawl.tools.checkstyle.Checker; // violation
import com.puppycrawl.tools.checkstyle.CheckerTest; // violation
import com.puppycrawl.tools.checkstyle.Definitions; // violation
import com.puppycrawl.tools.checkstyle.checks.imports.unusedimports.InputUnusedImports15Extensions; // violation
import com.puppycrawl.tools.checkstyle.ConfigurationLoaderTest; // violation
import com.puppycrawl.tools.checkstyle.PackageNamesLoader; // violation
import com.puppycrawl.tools.checkstyle.DefaultConfiguration; // violation
import com.puppycrawl.tools.checkstyle.DefaultLogger; // violation

/**
 * Test case for imports
 * Here's an import used only by javadoc: {@link Date}.
 * @author Oliver Burn
 * @author lkuehne
 * @author Michael Studman
 * @see Calendar Should avoid unused import for Calendar
 **/
class InputUnusedImports2
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
