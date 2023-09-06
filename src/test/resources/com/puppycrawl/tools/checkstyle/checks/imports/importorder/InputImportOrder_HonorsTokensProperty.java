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



*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static java.awt.Button.ABORT; // violation 'Wrong order for 'java.awt.Button.ABORT' import.'
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.Button; // violation 'Wrong order for 'java.awt.Button' import.'

public class InputImportOrder_HonorsTokensProperty {
}
