/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###SAME_PACKAGE(3)
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = org.
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for.*. Should be before .*'
import static java.awt.print.Paper.*; // violation 'Wrong lexicographical order for.*. Should be before .*'
import static javax.swing.WindowConstants.*;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.io.File; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.io.IOException; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.io.InputStream; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.io.Reader; // violation 'Wrong lexicographical order for.*. Should be before .*'

import com.google.common.collect.*; // violation '.* should be placed at the end of the import list: .*'
import org.junit.*; // violation '.* should be separated from previous import group by one line'

public class InputCustomImportOrderDefault4 {
}
