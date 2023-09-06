/*
ImportOrder
option = (default)under
groups = java.awt, javax.swing, java.io, java.util
ordered = false
separated = true
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
import java.awt.Dialog;
import java.awt.event.ActionEvent;
import static java.awt.Button.ABORT
;
import javax.swing.JComponent; // violation ''javax.swing.JComponent' should be separated from previous imports.'
import javax.swing.JTable;
import java.io.File; // violation ''java.io.File' should be separated from previous imports.'
import static java.io.File.createTempFile;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import static javax.swing.WindowConstants.*; // violation 'Wrong order for 'javax.swing.WindowConstants.*' import.'

import static sun.tools.util.ModifierFilter.ALL_ACCESS;
import static sun.tools.util.ModifierFilter.PACKAGE;

public class InputImportOrder4 {
}
