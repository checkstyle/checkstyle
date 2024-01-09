/*
CustomImportOrder
customImportOrderRules = STATIC, \t, STANDARD_JAVA_PACKAGE, THIRD_PARTY_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = com.|org.
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for\s.*. Should be before .*'
import static java.awt.print.Paper.*; // violation 'Wrong lexicographical order for\s.*. Should be before .*'
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

import com.google.errorprone.annotations.*;

import com.google.common.collect.*; // 2 violations
import org.junit.*;

public class InputCustomImportOrderListRules {
}
