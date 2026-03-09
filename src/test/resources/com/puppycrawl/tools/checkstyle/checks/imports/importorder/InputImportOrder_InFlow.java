/*
ImportOrder
option = inflow
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
import static java.awt.Button.ABORT;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong lexicographical order for 'java.awt.Dialog' import. Should be before 'java.awt.Frame'.'
import java.awt.event.ActionEvent;

import javax.swing.JComponent; // violation 'Extra separation in import group before 'javax.swing.JComponent''
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE; // violation 'Wrong lexicographical order for 'javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE' import. Should be before 'javax.swing.WindowConstants.HIDE_ON_CLOSE'.'
import static javax.swing.WindowConstants.*; // violation 'Wrong lexicographical order for 'javax.swing.WindowConstants.*' import. Should be before 'javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE'.'
import javax.swing.JTable; // violation 'Wrong lexicographical order for 'javax.swing.JTable' import. Should be before 'javax.swing.WindowConstants.*'.'
// violation below 'Extra separation in import group before 'java.io.File'
import static java.io.File.createTempFile; // violation 'Wrong lexicographical order for 'java.io.File.createTempFile' import. Should be before 'javax.swing.JTable'.'
import java.io.File; // violation 'Wrong lexicographical order for 'java.io.File' import. Should be before 'java.io.File.createTempFile'.'
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputImportOrder_InFlow {
}
