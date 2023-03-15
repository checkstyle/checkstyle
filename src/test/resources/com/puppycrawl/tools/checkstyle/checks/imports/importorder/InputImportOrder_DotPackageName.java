/*
ImportOrder
option = (default)under
groups = javax.swing., java.awt.
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
import javax.swing.JComponent; // violation 'Wrong order for 'javax.swing.JComponent' import.'
import javax.swing.JTable; // ok

public class InputImportOrder_DotPackageName {
}

