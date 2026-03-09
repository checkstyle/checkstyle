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

// non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong lexicographical order for 'java.awt.Dialog' import. Should be before 'java.awt.Frame'.'
import java.awt.event.ActionEvent;
import static java.awt.Button.ABORT
;
import javax.swing.JComponent; // violation 'Import 'javax.swing.JComponent' violates the configured relative order between static and non-static imports.'
import javax.swing.JTable;
import java.io.File; // violation 'Wrong lexicographical order for 'java.io.File' import. Should be before 'javax.swing.JTable'.'
import static java.io.File.createTempFile;
import java.io.IOException; // violation 'Import 'java.io.IOException' violates the configured relative order between static and non-static imports.'
import java.io.InputStream;
import java.io.Reader;
import static javax.swing.WindowConstants.*;

import static sun.tools.util.ModifierFilter.ALL_ACCESS; // violation 'Extra separation in import group before 'sun.tools.util.ModifierFilter.ALL_ACCESS''
import static sun.tools.util.ModifierFilter.PACKAGE;

public class InputImportOrder1 {
}
