/*
ImportOrder
option = top
groups = java, javax, org, com
ordered = (default)true
separated = true
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = true
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import static com.some.Class.MESSAGE_ORDERING;
import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.*;
import static org.junit.Assert.assertEquals;

import java.awt.Button;
import java.awt.Dialog;
import java.io.InputStream;
import javax.swing.JComponent; // violation ''javax.swing.JComponent' should be separated from previous imports.'
import javax.swing.JTable;

import sun.tools.java.ArrayType;

import org.junit.Test; // violation 'Wrong order for 'org.junit.Test' import.'
import org.mockito.Mock;

import com.some.api.DetailClass;

public class InputImportOrder_EclipseDefaultNegative {
}
