//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;
import static com.some.Class.MESSAGE_ORDERING;
import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import static javax.swing.WindowConstants.*;
import static org.junit.Assert.assertEquals;

import java.awt.Button;
import java.awt.Dialog;
import java.io.InputStream;

import javax.swing.JComponent;
import javax.swing.JTable;

import org.junit.Test;
import org.powermock.api.mockito.PowerMockito;

import com.some.api.DetailClass;

import sun.tools.java.ArrayType;

public class InputCustomImportOrderEclipseDefaultPositive {
}
/*
 * test: testInputCustomImportOrderEclipseDefaultPositive()
 *
 * Config = default
 * customImportOrderRules = STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###SAME_PACKAGE(2)###STATIC
 * standardPackageRegExp = ^java\.
 * thirdPartyPackageRegExp = ^java\.
 * specialImportsRegExp = ^org\.
 * sortImportsInGroupAlphabetically = true
 * separateLineBetweenGroups = true
 *
 */
