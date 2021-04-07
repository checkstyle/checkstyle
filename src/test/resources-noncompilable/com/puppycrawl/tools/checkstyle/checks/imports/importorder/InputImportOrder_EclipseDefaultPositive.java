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

import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok

import org.junit.Test; // ok
import org.powermock.api.mockito.PowerMockito; // ok

import com.some.api.DetailClass; // ok

import sun.tools.java.ArrayType; // ok

/*
 * Config:
 * option = top
 * groups = {java, javax, org, com}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = false
 */
public class InputImportOrder_EclipseDefaultPositive {
}
