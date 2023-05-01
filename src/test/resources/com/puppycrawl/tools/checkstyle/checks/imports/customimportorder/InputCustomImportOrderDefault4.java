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
import static java.awt.Button.ABORT; 
// violation 'Wrong order for 'java.awt.Button.ABORT' import.'
import static java.awt.print.Paper.*; 
// violation 'Wrong order for 'java.awt.print.Paper.*' import.'
import static javax.swing.WindowConstants.*;

import java.awt.Button;
import java.awt.Frame;
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.color.ColorSpace;
import java.awt.event.ActionEvent;
import javax.swing.JComponent;
import javax.swing.JTable;
import java.io.File; // violation 'Wrong order for 'java.io.File' import.'
import java.io.IOException; // violation 'Wrong order for 'java.io.IOException' import.'
import java.io.InputStream; // violation 'Wrong order for 'java.io.InputStream' import.'
import java.io.Reader; // violation 'Wrong order for 'java.io.Reader' import.'

import com.puppycrawl.tools.checkstyle.*; 
// violation 'Wrong order for 'com.puppycrawl.tools.checkstyle.*' import.'

import com.google.common.collect.*; 
// violation 'Wrong order for 'com.google.common.collect.*' import.'
import org.junit.*; // violation 'Wrong order for 'org.junit.*' import.'

public class InputCustomImportOrderDefault4 {
}
