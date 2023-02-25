/*
ImportOrder
option = (default)under
groups = (default)
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
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.event.ActionEvent; // ok
import static java.awt.Button.ABORT // ok
;
import javax.swing.JComponent; // violation 'Wrong order for 'javax.swing.JComponent' import.'
import javax.swing.JTable; // ok
import java.io.File; // violation 'Wrong order for 'java.io.File' import.'
import static java.io.File.createTempFile; // ok
import java.io.IOException; // violation 'Wrong order for 'java.io.IOException' import.'
import java.io.InputStream; // ok
import java.io.Reader; // ok
import static javax.swing.WindowConstants.*; // ok

import static sun.tools.util.ModifierFilter.ALL_ACCESS; // violation 'Extra separation in import group before .*'
import static sun.tools.util.ModifierFilter.PACKAGE; // ok

public class InputImportOrder1 {
}
