/*
UnusedImports
processJavadoc = false
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)REFERENCE, PARAMETER_TYPE, THROWS_BLOCK_TAG

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import com.google.errorprone.annotations.*;
import com.google.errorprone.annotations.concurrent.GuardedBy; // violation 'Unused import - .*GuardedBy.'

import java.io.*;
import java.lang.*;
import java.lang.String; // violation 'Unused import - java.lang.String.'

import java.util.List; // violation 'Unused import - java.util.List.'
import java.util.List; // violation 'Unused import - java.util.List.'
import java.lang.*;
import java.util.Iterator;
import java.util.Enumeration; // violation 'Unused import - java.util.Enumeration.'
import java.util.Arrays;
import javax.swing.JToolBar;
import javax.swing.JToggleButton; // violation 'Unused import - javax.swing.JToggleButton.'

import javax.swing.BorderFactory; // violation 'Unused import - javax.swing.BorderFactory.'

import static java.io.File.listRoots;

import static javax.swing.WindowConstants.*;
import static java.io.File. // violation 'Unused import - java.io.File.createTempFile.'
    createTempFile;

import java.awt.Graphics2D; // violation 'Unused import - java.awt.Graphics2D.'
import java.awt.HeadlessException; // violation 'Unused import - java.awt.HeadlessException.'
import java.awt.Label; // violation 'Unused import - java.awt.Label.'
import java.util.Date; // violation 'Unused import - java.util.Date.'
import java.util.Calendar; // violation 'Unused import - java.util.Calendar.'
import java.util.BitSet; // violation 'Unused import - java.util.BitSet.'

import com.google.errorprone.annotations.CheckReturnValue; // violation 'Unused import - .*CheckReturnValue.'
import com.google.errorprone.annotations.CanIgnoreReturnValue; // violation 'Unused import - .*CanIgnoreReturnValue.'
import com.google.errorprone.annotations.CompatibleWith; // violation 'Unused import - .*CompatibleWith.'
import com.google.errorprone.annotations.concurrent.LazyInit; // violation 'Unused import - .*LazyInit.'
import com.google.errorprone.annotations.DoNotCall; // violation 'Unused import - .*DoNotCall.'
import com.google.errorprone.annotations.CompileTimeConstant; // violation 'Unused import - .*CompileTimeConstant.'
import com.google.errorprone.annotations.FormatMethod; // violation 'Unused import - .*FormatMethod.'
import com.google.errorprone.annotations.FormatString; // violation 'Unused import - .*FormatString.'

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
     * First is a class with a method with arguments
     * {@link CheckReturnValue#method1(CanIgnoreReturnValue)}.
     * Next is a class with typed method
     * {@link BaseFileSetCheckTestSupport#method2(CompatibleWith, LazyInit)}.
     *
     * @param param1 with a link {@link DoNotCall}
     * @throws CompileTimeConstant when broken
     * @deprecated in 1 for removal in 2. Use {@link FormatMethod}
     */
    public void aMethodWithManyLinks() {}
}
