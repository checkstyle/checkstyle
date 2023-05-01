/*
CustomImportOrder
customImportOrderRules = STATIC###SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = com|org
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

import java.awt.Button; // violation 'Wrong order for 'java.awt.Button' import.'
import java.awt.Frame; // violation 'Wrong order for 'java.awt.Frame' import.'
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.awt.color.ColorSpace; 
// violation 'Wrong order for 'java.awt.color.ColorSpace' import.'
import java.awt.event.ActionEvent; 
// violation 'Wrong order for 'java.awt.event.ActionEvent' import.'
import javax.swing.JComponent; // violation 'Wrong order for 'javax.swing.JComponent' import.'
import javax.swing.JTable; // violation 'Wrong order for 'javax.swing.JTable' import.'
import java.io.File; // violation 'Wrong order for 'java.io.File' import.'
import java.io.IOException; // violation 'Wrong order for 'java.io.IOException' import.'
import java.io.InputStream; // violation 'Wrong order for 'java.io.InputStream' import.'
import java.io.Reader; // violation 'Wrong order for 'java.io.Reader' import.'

import com.puppycrawl.tools.checkstyle.*;

import com.google.common.collect.*;
import org.junit.*;

public class InputCustomImportOrderDefault {
}
