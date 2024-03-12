/*
ImportOrder
option = top
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

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT;
import static javax.swing.WindowConstants.*;

import java.awt.Button; // violation 'Extra separation in import group before 'java.awt.Button''
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.event.ActionEvent;
/***comment test***/
import java.io.IOException; // violation 'Extra separation in import group before 'java.io.IOException''
import java.io.InputStream;

import javax.swing.JComponent; // violation 'Extra separation in import group before 'javax.swing.JComponent''
import javax.swing.JTable;

import static java.io.File.*; // 2 violations
import java.io.File;
import java.io.Reader;

public class InputImportOrder_Top2 {
}
