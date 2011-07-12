////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.imports;

import com.puppycrawl.tools.checkstyle.imports.*;
                                     import com.puppycrawl.tools.checkstyle.imports.GlobalProperties;
import java.io.*;
import java.lang.*;
import java.lang.String;
import java.sql.Connection;
import java.util.List;
import java.util.List;
import sun.net.ftpclient.FtpClient;
import java.util.Iterator;
import java.util.Enumeration;
import java.util.Arrays;
import javax.swing.JToolBar;
import javax.swing.JToggleButton;
import javax.swing.ScrollPaneLayout;
import javax.swing.BorderFactory;
import static java.io.File.listRoots;

import static javax.swing.WindowConstants.*;
import static javax.swing.WindowConstants.*;
import static java.io.File.createTempFile;
import static sun.net.ftpclient.FtpClient.*;
import java.awt.Component;
import java.awt.Label;
import java.util.Date;
import java.util.Calendar;
import java.util.BitSet;

/**
 * Test case for imports
 * Here's an import used only by javadoc: {@link Date}.
 * @author Oliver Burn
 * @author lkuehne
 * @author Michael Studman
 * @see Calendar Should avoid unused import for Calendar
 **/
class InputImport
{
    /** ignore **/
    private Class mUse1 = Connection.class;
    /** ignore **/
    private Class mUse2 = java.io.File.class;
    /** ignore **/
    private Class mUse3 = Iterator[].class;
    /** ignore **/
    private Class mUse4 = java.util.Enumeration[].class;
    /** usage of illegal import **/
    private FtpClient ftpClient = null;

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
    private Object mUse5 = new ScrollPaneLayout.UIRessource();

    /** usage of inner class in constructor, fully qualified */
    private Object mUse6 = new javax.swing.JToggleButton.ToggleButtonModel();

    /** we use class name as member's name.
     *  also an inline JavaDoc-only import {@link Vector linkText} */
    private int Component;

    /**
     * method comment with JavaDoc-only import {@link BitSet#aMethod()}
     */
    public void Label() {}
}
