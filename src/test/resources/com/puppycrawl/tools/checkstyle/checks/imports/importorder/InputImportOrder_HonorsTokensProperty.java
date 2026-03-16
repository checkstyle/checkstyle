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
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for 'java.awt.Button.ABORT' import. Should be before 'javax.swing.WindowConstants.DISPOSE_ON_CLOSE'.'
import java.awt.Dialog; // violation 'Import 'java.awt.Dialog' violates the configured relative order between static and non-static imports.'
import java.awt.Button; // violation 'Wrong lexicographical order for 'java.awt.Button' import. Should be before 'java.awt.Dialog'.'

public class InputImportOrder_HonorsTokensProperty {
}
