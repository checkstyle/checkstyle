/*
ImportOrder
option = bottom
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
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;

import java.io.IOException; // violation 'Extra separation in import group before 'java.io.IOException''
import java.io.InputStream;

import javax.swing.JComponent; // violation 'Extra separation in import group before 'javax.swing.JComponent''
import javax.swing.JTable;

import static java.io.File.*; // violation 'Extra separation in import group before 'java.io.File.*''
import java.io.File; // violation 'Wrong order for 'java.io.File' import.'

import static java.io.File.createTempFile; // violation 'Extra separation in import group before 'java.io.File.createTempFile''
import static java.awt.Button.ABORT;
import static javax.swing.WindowConstants.*;

import java.io.Reader; // 2 violations

public class InputImportOrder_Bottom {
}
