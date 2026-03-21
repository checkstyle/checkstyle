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

import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong lexicographical order for 'java.awt.Dialog' import. Should be before 'java.awt.Frame'.'
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTable;
import static java.awt.Button.ABORT;
import static javax.swing.WindowConstants.*;
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for 'java.awt.Button.ABORT' import. Should be before 'javax.swing.WindowConstants.*'.'

import static java.io.File.createTempFile; // violation 'Extra separation in import group before 'java.io.File.createTempFile''
import java.io.File; // violation 'Import 'java.io.File' violates the configured relative order between static and non-static imports.'
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputImportOrder_Under {
}
