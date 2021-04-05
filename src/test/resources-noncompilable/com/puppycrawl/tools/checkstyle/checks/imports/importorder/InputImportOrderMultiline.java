package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import
    java.awt.Button; // ok
import java.awt.Dialog
    ; // ok
import
java.awt.Frame; // ok
import java
    .awt.event.
    ActionEvent
    ; // ok
import
java.io.File
; // ok
import java.
    io.
    IOException; // ok
import java.io.InputStream; // ok
import
  static java.awt.Button.ABORT; // ok
import static
 java.io.File.createTempFile; // ok
import static javax.swing
    .WindowConstants.*
    ; // ok

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
public class InputImportOrderMultiline {
}
