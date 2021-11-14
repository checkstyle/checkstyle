/*
ImportOrder
option = top
groups = java, javax, org, com
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static com.some.Class.MESSAGE_ORDERING; // ok
import static java.awt.Button.ABORT; // ok
import static java.io.File.createTempFile; // ok
import static javax.swing.WindowConstants.*; // ok
import static org.junit.Assert.assertEquals; // ok

import java.awt.Button; // ok
import java.awt.Dialog; // ok
import java.io.InputStream; // ok
import javax.swing.JComponent; // violation
import javax.swing.JTable; // ok

import sun.tools.java.ArrayType; // ok

import org.junit.Test; // violation
import org.mockito.Mock; // ok

import com.some.api.DetailClass; // ok

public class InputImportOrder_EclipseDefaultNegative {
}
