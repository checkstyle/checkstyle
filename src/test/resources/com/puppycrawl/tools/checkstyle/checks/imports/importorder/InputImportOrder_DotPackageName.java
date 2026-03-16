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

import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong lexicographical order for 'java.awt.Dialog' import. Should be before 'java.awt.Frame'.'
import java.awt.event.ActionEvent;
import javax.swing.JComponent; // violation 'Import statement for 'javax.swing.JComponent' violates the configured import group order.'
import javax.swing.JTable;

public class InputImportOrder_DotPackageName {
}
