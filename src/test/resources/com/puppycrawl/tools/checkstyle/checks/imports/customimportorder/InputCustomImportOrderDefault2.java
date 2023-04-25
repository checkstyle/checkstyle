/*
CustomImportOrder
customImportOrderRules = STANDARD_JAVA_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = org.
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File.createTempFile;
import static java.awt.Button.ABORT; // violation - in ASCII order 'A' should go before 'c',
// as all uppercase come before lowercase letters
import static javax.swing.WindowConstants.*;

import java.util.List; // 2 violations
import java.util.StringTokenizer; // violation should be in STANDARD_JAVA_PACKAGE
import java.util.*; // violation should be in STANDARD_JAVA_PACKAGE
import java.util.concurrent.AbstractExecutorService; // violation should be in STANDARD_JAVA_PACKAGE
import java.util.concurrent.*; // violation should be in STANDARD_JAVA_PACKAGE

import com.puppycrawl.tools.checkstyle.checks.*;
import com.puppycrawl.tools.checkstyle.*; // violation should be before"checkstyle.checks"

import com.google.common.base.*; // 2 violations
import org.junit.*;

public class InputCustomImportOrderDefault2 {
}
