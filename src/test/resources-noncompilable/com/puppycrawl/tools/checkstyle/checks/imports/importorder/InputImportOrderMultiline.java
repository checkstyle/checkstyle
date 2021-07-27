/*
ImportOrder
option = (default)under
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

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

public class InputImportOrderMultiline {
}
