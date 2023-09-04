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
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.event.ActionEvent;

import javax.swing.JComponent; // violation 'Extra separation in import group before 'javax.swing.JComponent''
import static javax.swing.WindowConstants.HIDE_ON_CLOSE;
import static javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE; // violation 'Wrong order for .* import.'
import static javax.swing.WindowConstants.*; // violation 'Wrong order for 'javax.swing.WindowConstants.*' import.'
import javax.swing.JTable; // violation 'Wrong order for 'javax.swing.JTable' import.'

import static java.io.File.createTempFile; // 2 violations
import java.io.File; // violation 'Wrong order for 'java.io.File' import.'
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputImportOrder_InFlow {
}
