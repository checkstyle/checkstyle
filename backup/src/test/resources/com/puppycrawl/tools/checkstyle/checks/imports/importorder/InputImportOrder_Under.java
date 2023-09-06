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

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.awt.Button; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.event.ActionEvent; // ok
import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok
import static java.awt.Button.ABORT; // ok
import static javax.swing.WindowConstants.*; // ok
import static java.awt.Button.ABORT; // violation 'Wrong order for 'java.awt.Button.ABORT' import.'

import static java.io.File.createTempFile; // violation 'Extra separation in import group before 'java.io.File.createTempFile''
import java.io.File; // violation 'Wrong order for 'java.io.File' import.'
import java.io.IOException; // ok
import java.io.InputStream; // ok
import java.io.Reader; // ok

public class InputImportOrder_Under {
}
