/*
ImportOrder
option = above
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

import static java.awt.Button.ABORT; // ok
import static javax.swing.WindowConstants.*; // ok
import static java.awt.Button.ABORT; // violation
import java.awt.Button; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent; // ok
import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok

import java.io.File; // 2 violations
import static java.io.File.createTempFile; // violation
import java.io.IOException; // ok
import java.io.InputStream; // ok
import java.io.Reader; // ok

public class InputImportOrder_Above {
}
