/*
ImportOrder
option = (default)under
groups = java.awt,javax.swing,java.io
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import java.awt.Button; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent; // ok
import static java.awt.Button.ABORT // ok
;
import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok
import java.io.File; // ok
import static java.io.File.createTempFile; // ok
import java.io.IOException; // violation
import java.io.InputStream; // ok
import java.io.Reader; // ok
import static javax.swing.WindowConstants.*; // violation

import static sun.tools.util.ModifierFilter.ALL_ACCESS; // violation
import static sun.tools.util.ModifierFilter.PACKAGE; // ok

public class InputImportOrder2 {
}
