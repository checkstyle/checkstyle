package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.awt.Button; // ok
import static java.awt.Button.ABORT; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent; // ok

import javax.swing.JComponent; // violation
import static javax.swing.WindowConstants.HIDE_ON_CLOSE; // ok
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE; // violation
import static javax.swing.WindowConstants.*; // violation
import javax.swing.JTable; // violation

import static java.io.File.createTempFile; // violation
import java.io.File; // violation
import java.io.IOException; // ok
import java.io.InputStream; // ok
import java.io.Reader; // ok

/*
 * Config:
 * option = inflow
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrder_InFlow {
}
