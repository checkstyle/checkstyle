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

import java.awt.Button; // violation should be at the start
import java.awt.Dialog; // violation should be at the start
import java.io.InputStream; // violation should be at the start

import javax.swing.JComponent; // violation should be before static imports
import javax.swing.JTable; // violation should be before static imports

import org.junit.Test; // violation should be before static imports
import org.mockito.Mock; // violation should be before static imports

import com.some.api.DetailClass;

import sun.tools.java.ArrayType; // violation should be before static imports

public class InputCustomImportOrderEclipseDefaultPositive {
}
