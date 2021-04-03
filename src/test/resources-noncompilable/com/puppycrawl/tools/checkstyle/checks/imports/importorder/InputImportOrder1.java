//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import java.awt.Button; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent; // ok
import static java.awt.Button.ABORT // ok
;
import javax.swing.JComponent; // violation
import javax.swing.JTable; // ok
import java.io.File; // violation
import static java.io.File.createTempFile; // ok
import java.io.IOException; // violation
import java.io.InputStream; // ok
import java.io.Reader; // ok
import static javax.swing.WindowConstants.*; // ok

import static sun.tools.util.ModifierFilter.ALL_ACCESS; // violation
import static sun.tools.util.ModifierFilter.PACKAGE; // ok

/*
 * Config:
 * option = under
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrder1 {
}
