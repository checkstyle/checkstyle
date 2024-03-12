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
import static java.awt.Button.ABORT; // violation 'Wrong order for 'java.awt.Button.ABORT' import.'
import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTable;

import java.io.File; // 2 violations
import static java.io.File.createTempFile; // violation 'Wrong order for 'java.io.File.createTempFile' import.'
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;

public class InputImportOrder_Above {
}
