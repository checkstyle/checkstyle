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

import java.awt.Button; // violation 'Wrong order for 'java.awt.Button' import.'
import java.awt.Dialog; // violation 'Wrong order for 'java.awt.Dialog' import.'
import java.io.InputStream; // violation 'Wrong order for 'java.io.InputStream' import.'

import javax.swing.JComponent; // violation 'Wrong order for 'javax.swing.JComponent' import.'
import javax.swing.JTable; // violation 'Wrong order for 'javax.swing.JTable' import.'

import org.junit.Test; // violation 'Wrong order for 'org.junit.Test' import.'
import org.mockito.Mock; // violation 'Wrong order for 'org.mockito.Mock' import.'

import com.some.api.DetailClass;

import sun.tools.java.ArrayType; // violation 'Wrong order for 'sun.tools.java.ArrayType' import.'

public class InputCustomImportOrderEclipseDefaultPositive {
}
