/*
ImportOrder
option = (default)under
groups = java.awt,javax.swing,java.io
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.event.ActionEvent;
import static java.awt.Button.ABORT
;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.io.File;
import static java.io.File.createTempFile;
import java.io.IOException; // violation 'Wrong order for 'java.io.IOException' import.'
import java.io.InputStream;
import java.io.Reader;
import static javax.swing.WindowConstants.*; // violation 'Wrong order for 'javax.swing.WindowConstants.*' import.'

import static sun.tools.util.ModifierFilter.ALL_ACCESS; // violation 'Extra separation in import group before .*'
import static sun.tools.util.ModifierFilter.PACKAGE;

public class InputImportOrder2 {
}
