package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation
import java.awt.event.ActionEvent;
import javax.swing.JComponent; // violation
import javax.swing.JTable;

/**
 * Config:
 * groups = javax.swing.,java.awt.
 */
public class InputImportOrder_DotPackageName {
}

