/*
CustomImportOrder
customImportOrderRules = STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###\
                         SAME_PACKAGE(2)###STATIC
standardPackageRegExp = ^java\\.
thirdPartyPackageRegExp = ^org\\.
specialImportsRegExp = ^javax\\.
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;
import static com.some.Class.MESSAGE_ORDERING;
import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.*;
import static org.junit.Assert.assertEquals;

import java.awt.Button; // violation 'Import statement for 'java.awt.Button' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting not assigned imports on this line'
import java.awt.Dialog; // violation 'Import statement for 'java.awt.Dialog' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting not assigned imports on this line'
import java.io.InputStream; // violation 'Import statement for 'java.io.InputStream' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting not assigned imports on this line'

import javax.swing.JComponent; // violation 'Import statement for 'javax.swing.JComponent' is in the wrong order. Should be in the 'SPECIAL_IMPORTS' group, expecting not assigned imports on this line'
import javax.swing.JTable; // violation 'Import statement for 'javax.swing.JTable' is in the wrong order. Should be in the 'SPECIAL_IMPORTS' group, expecting not assigned imports on this line'

import org.junit.Test; // violation 'Import statement for 'org.junit.Test' is in the wrong order. Should be in the 'THIRD_PARTY_PACKAGE' group, expecting not assigned imports on this line'
import org.mockito.Mock; // violation 'Import statement for 'org.mockito.Mock' is in the wrong order. Should be in the 'THIRD_PARTY_PACKAGE' group, expecting not assigned imports on this line'

import com.some.api.DetailClass;

import sun.tools.java.ArrayType; // violation 'Extra separation in import group before 'sun.tools.java.ArrayType''

public class InputCustomImportOrderEclipseDefaultPositive {
}
