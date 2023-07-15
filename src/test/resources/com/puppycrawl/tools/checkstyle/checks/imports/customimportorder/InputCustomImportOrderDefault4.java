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
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for\s.*. Should be before 'java.io.File.createTempFile''
import static java.awt.print.Paper.*; // violation 'Wrong lexicographical order for\s.*. Should be before 'java.io.File.createTempFile''
import static javax.swing.WindowConstants.*;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong lexicographical order for\s.*. Should be before 'java.awt.Frame''
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.io.File; // violation 'Wrong lexicographical order for\s.*. Should be before 'javax.swing.JTable''
import java.io.IOException; // violation 'Wrong lexicographical order for\s.*. Should be before 'javax.swing.JTable''
import java.io.InputStream; // violation 'Wrong lexicographical order for\s.*. Should be before 'javax.swing.JTable''
import java.io.Reader; // violation 'Wrong lexicographical order for\s.*. Should be before 'javax.swing.JTable''

import com.puppycrawl.tools.checkstyle.*; // violation 'wrong order..* expecting group 'THIRD_PARTY_PACKAGE' on this line'

import com.google.common.collect.*; // violation 'Imports without groups should be placed at the end of the import list: 'com.google.common.collect.*''
import org.junit.*; // violation ''org.junit.*' should be separated from previous import group by one line'

public class InputCustomImportOrderDefault4 {
}
