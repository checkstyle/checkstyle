/*
ImportOrder
option = (default)under
groups = java, javax
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.awt.Button; // ok
import java.awt.Dialog; // ok
import java.awt.event.ActionEvent; // ok
import java.awt.event.ActionEvent; // ok

import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok

public class InputImportOrderRepetition {
}
