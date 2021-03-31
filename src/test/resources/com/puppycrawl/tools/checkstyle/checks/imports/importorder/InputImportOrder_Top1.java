package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = invalid_option
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
import static java.io.File.createTempFile; // ok
import static java.awt.Button.ABORT; // ok
import static javax.swing.WindowConstants.*; // ok

import java.awt.Button; // ok
import java.awt.Dialog; // ok
import java.awt.Frame; // ok
import java.awt.event.ActionEvent; // ok
/***comment test***/
import java.io.IOException; // ok
import java.io.InputStream; // ok

import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok

import static java.io.File.*; // ok
import java.io.File; // ok
import java.io.Reader; // ok

public class InputImportOrder_Top1 {
}
