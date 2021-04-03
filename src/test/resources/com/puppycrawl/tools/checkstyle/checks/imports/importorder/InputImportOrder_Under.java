package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

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
 */
import java.awt.Button; // ok
import java.awt.Frame; // ok
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent; // ok
import javax.swing.JComponent; // ok
import javax.swing.JTable; // ok
import static java.awt.Button.ABORT; // ok
import static javax.swing.WindowConstants.*; // ok
import static java.awt.Button.ABORT; // violation

import static java.io.File.createTempFile; // violation
import java.io.File; // violation
import java.io.IOException; // ok
import java.io.InputStream; // ok
import java.io.Reader; // ok

public class InputImportOrder_Under {
}
