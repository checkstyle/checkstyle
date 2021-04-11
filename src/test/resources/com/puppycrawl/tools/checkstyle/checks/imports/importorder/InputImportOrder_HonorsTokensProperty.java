package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE; // ok
import static java.awt.Button.ABORT; // ok
import java.awt.Dialog; // ok
import java.awt.Button; // violation

/*
 * Config:
 * option = under
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 * tokens = IMPORT
 */
public class InputImportOrder_HonorsTokensProperty {
}
