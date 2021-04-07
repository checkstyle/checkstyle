package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.awt.Button.ABORT; // ok
import static javax.swing.WindowConstants.*; // ok
import static java.awt.Button.ABORT; // violation
import java.awt.Button; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent; // ok
import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok

import java.io.File; // violation
import static java.io.File.createTempFile; // violation
import java.io.IOException; // ok
import java.io.InputStream; // ok
import java.io.Reader; // ok

/*
 * Config:
 * option = above
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrder_Above {
}
