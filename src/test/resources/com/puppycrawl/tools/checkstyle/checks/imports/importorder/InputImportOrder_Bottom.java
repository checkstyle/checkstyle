package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.awt.Button; // ok
import java.awt.Dialog; // ok
import java.awt.Frame; // ok
import java.awt.event.ActionEvent; // ok

import java.io.IOException; // violation
import java.io.InputStream; // ok

import javax.swing.JComponent; // violation
import javax.swing.JTable; // ok

import static java.io.File.*; // violation
import java.io.File; // violation

import static java.io.File.createTempFile; // violation
import static java.awt.Button.ABORT; // ok
import static javax.swing.WindowConstants.*; // ok

import java.io.Reader; // violation

/*
 * Config:
 * option = bottom
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrder_Bottom {
}
