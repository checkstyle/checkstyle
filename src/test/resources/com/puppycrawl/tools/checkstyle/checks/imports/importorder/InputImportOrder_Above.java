/*
ImportOrder
option = above
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

import static java.awt.Button.ABORT;
import static javax.swing.WindowConstants.*;
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for 'java.awt.Button.ABORT' import. Should be before 'javax.swing.WindowConstants.*'.'
import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong lexicographical order for 'java.awt.Dialog' import. Should be before 'java.awt.Frame'.'
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTable;
// violation below 'Extra separation in import group before 'java.io.File'
import java.io.File; //violation 'Wrong lexicographical order for 'java.io.File' import. Should be before 'javax.swing.JTable'.'
import static java.io.File.createTempFile; // violation 'Import 'java.io.File.createTempFile' violates the configured relative order between static and non-static imports.'
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputImportOrder_Above {
}
