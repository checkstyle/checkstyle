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
tokens = IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE; // ok
import static java.awt.Button.ABORT; // ok
import java.awt.Dialog; // ok
import java.awt.Button; // violation

public class InputImportOrder_HonorsTokensProperty {
}
