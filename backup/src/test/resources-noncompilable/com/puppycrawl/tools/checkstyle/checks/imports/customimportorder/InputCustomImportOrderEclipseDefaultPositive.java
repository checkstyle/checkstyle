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

import java.awt.Button; // violation '.* wrong order. Should be in the .* group, expecting not assigned imports.*'
import java.awt.Dialog; // violation '.*wrong order. Should be in the .* group, expecting not assigned imports.*'
import java.io.InputStream; // violation '.* wrong order. Should be in the .* group, expecting not assigned imports.*'

import javax.swing.JComponent; // violation '.* wrong order. Should be in the .* group, expecting not assigned imports.*'
import javax.swing.JTable; // violation '.* wrong order. Should be in the .* group, expecting not assigned imports.*'

import org.junit.Test; // violation '.* wrong order. Should be in the .* group, expecting not assigned imports.*'
import org.mockito.Mock; // violation '.* wrong order. Should be in the .* group, expecting not assigned imports.*'

import com.some.api.DetailClass;

import sun.tools.java.ArrayType; // violation 'Extra separation in import group before .*ArrayType'

public class InputCustomImportOrderEclipseDefaultPositive {
}
