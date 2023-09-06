/*
ImportOrder
option = inflow
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
import static java.awt.Button.ABORT; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.event.ActionEvent; // ok

import javax.swing.JComponent; // violation 'Extra separation in import group before 'javax.swing.JComponent''
import static javax.swing.WindowConstants.HIDE_ON_CLOSE; // ok
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE; // violation 'Wrong order for .* import.'
import static javax.swing.WindowConstants.*; // violation 'Wrong order for 'javax.swing.WindowConstants.*' import.'
import javax.swing.JTable; // violation 'Wrong order for 'javax.swing.JTable' import.'

import static java.io.File.createTempFile; // 2 violations
import java.io.File; // violation 'Wrong order for 'java.io.File' import.'
import java.io.IOException; // ok
import java.io.InputStream; // ok
import java.io.Reader; // ok

public class InputImportOrder_InFlow {
}
