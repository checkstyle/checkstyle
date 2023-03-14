/*
IllegalImport
illegalPkgs = java\\.util
illegalClasses = (default)
regexp = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

import com.puppycrawl.tools.checkstyle.checks.imports.illegalimport.*; // ok

import java.io.*; // ok
import java.lang.*; // ok
import java.sql.Connection; // ok
import java.util.List; // violation
import java.util.List; // violation
import java.lang.ArithmeticException; // ok
import org.junit.jupiter.api.*; // ok
import java.util.Enumeration; // violation
import java.util.Arrays; // violation

import javax.swing.JToolBar; // ok
import javax.swing.JToggleButton; // ok
import javax.swing.ScrollPaneLayout; // ok
import javax.swing.BorderFactory; // ok
import static java.io.File.listRoots; // ok

import static javax.swing.WindowConstants.*; // ok
import static javax.swing.WindowConstants.*; // ok
import static java.io.File.createTempFile; // ok
import org.junit.jupiter.api.*; // ok

import java.awt.Component; // ok
import java.awt.Graphics2D; // ok
import java.awt.HeadlessException; // ok
import java.awt.Label; // ok
import java.util.Date; // violation
import java.util.Calendar; // violation
import java.util.BitSet; // violation

class InputIllegalImportDefault5
{
    /** ignore **/
    private Class mUse1 = Connection.class;
    /** ignore **/
    private Class mUse2 = java.io.File.class;
    /** ignore **/
    private Class mUse3 = null;
    /** ignore **/
    private Class mUse4 = java.util.Enumeration[].class;
    /** usage of illegal import **/
    private String ftpClient = null;

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
     * First is a class with a method with arguments {@link TestClass1#method1(TestClass2)}.
     * Next is a class with typed method {@link TestClass3#method2(TestClass4, TestClass5)}.
     *
     * @param param1 with a link {@link TestClass6}
     * @throws TestClass7 when broken
     * @deprecated in 1 for removal in 2. Use {@link TestClass8}
     */
    @Deprecated
    public void aMethodWithManyLinks() {}
}
