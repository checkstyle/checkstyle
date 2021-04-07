package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.io.File.createTempFile; // ok
import static java.awt.Button.ABORT; // ok
import static javax.swing.WindowConstants.*; // ok

import java.awt.Button; // violation
import java.awt.Dialog; // ok
import java.awt.Frame; // ok
import java.awt.event.ActionEvent; // ok
/***comment test***/
import java.io.IOException; // violation
import java.io.InputStream; // ok

import javax.swing.JComponent; // violation
import javax.swing.JTable; // ok

import static java.io.File.*; // violation
import java.io.File; // ok
import java.io.Reader; // ok

/*
 * Config:
 * option = top
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrder_Top2 {
}
