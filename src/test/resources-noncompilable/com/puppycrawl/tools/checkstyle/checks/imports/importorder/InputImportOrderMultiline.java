package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
/**
 * This test-input is intended to be checked using following configuration:
 *
 * caseSensitive = true
 * groups = {}
 * option = UNDER
 * ordered = true
 * separated = false
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 *
 */
import
    java.awt.Button;
import java.awt.Dialog
    ;
import
java.awt.Frame;
import java
    .awt.event.
    ActionEvent
    ;
import
java.io.File
;
import java.
    io.
    IOException;
import java.io.InputStream;
import
  static java.awt.Button.ABORT;
import static
 java.io.File.createTempFile;
import static javax.swing
    .WindowConstants.*
    ;
public class InputImportOrderMultiline {
}
