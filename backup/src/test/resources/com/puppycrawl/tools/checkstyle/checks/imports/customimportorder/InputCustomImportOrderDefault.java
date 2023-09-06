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
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for.*. Should be before .*'
import static java.awt.print.Paper.*; // violation 'Wrong lexicographical order for.*. Should be before .*'
import static javax.swing.WindowConstants.*;

import java.awt.Button; // violation 'wrong order..* expecting group .* on this line'
import java.awt.Frame; // violation 'wrong order..* expecting group .* on this line'
import java.awt.Dialog; // violation 'wrong order..* expecting group .* on this line'
import java.awt.color.ColorSpace; // violation 'wrong order..* expecting group .* on this line'
import java.awt.event.ActionEvent; // violation 'wrong order..* expecting group .* on this line'
import javax.swing.JComponent; // violation 'wrong order..* expecting group .* on this line'
import javax.swing.JTable; // violation 'wrong order..* expecting group .* on this line'
import java.io.File; // violation 'wrong order..* expecting group .* on this line'
import java.io.IOException; // violation 'wrong order..* expecting group .* on this line'
import java.io.InputStream; // violation 'wrong order..* expecting group .* on this line'
import java.io.Reader; // violation 'wrong order..* expecting group .* on this line'

import com.puppycrawl.tools.checkstyle.*;

import com.google.common.collect.*;
import org.junit.*;

public class InputCustomImportOrderDefault {
}
