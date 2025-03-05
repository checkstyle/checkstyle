/*
IllegalImport
illegalPkgs = java.io
illegalClasses = (default)
regexp = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

import java.io.*; // violation 'Illegal import'
import java.lang.*;
import java.sql.Connection;
import java.util.List;
import java.util.List;
import java.lang.ArithmeticException;
import org.junit.jupiter.api.*;
import java.util.Enumeration;
import java.util.Arrays;

import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneLayout;
import javax.swing.BorderFactory;
import static java.io.File.listRoots; // violation 'Illegal import'

import static javax.swing.WindowConstants.*;
import static javax.swing.WindowConstants.*;
import static java.io.File.createTempFile; // violation 'Illegal import'
import org.junit.jupiter.api.*;

import java.awt.Component;
import java.awt.Graphics2D;
import java.awt.HeadlessException;
import java.awt.Label;
import java.util.Date;
import java.util.Calendar;
import java.util.BitSet;

/*
 * Config:
 * illegalPkgs = "java.io"
 */
class InputIllegalImportDefault1
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
