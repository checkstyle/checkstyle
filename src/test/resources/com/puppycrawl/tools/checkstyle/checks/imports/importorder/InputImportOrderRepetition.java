package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.awt.Button; // ok
import java.awt.Dialog; // ok
import java.awt.event.ActionEvent; // ok
import java.awt.event.ActionEvent; // ok

import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok

/*
 * Config:
 * option = under
 * groups = {java, javax}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrderRepetition {
}
