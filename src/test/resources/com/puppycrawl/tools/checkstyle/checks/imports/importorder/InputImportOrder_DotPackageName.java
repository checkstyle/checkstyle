package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = under
 * groups = {javax.swing., java.awt.}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
import java.awt.Button; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent; // ok
import javax.swing.JComponent; // violation
import javax.swing.JTable; // ok

public class InputImportOrder_DotPackageName {
}

