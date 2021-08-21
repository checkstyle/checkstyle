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
import static java.awt.Button.ABORT; // violation
import static javax.swing.WindowConstants.*;

import java.util.List; // 2 violations
import java.util.StringTokenizer; // violation
import java.util.*; // violation
import java.util.concurrent.AbstractExecutorService; // violation
import java.util.concurrent.*; // violation

import com.puppycrawl.tools.checkstyle.checks.*;
import com.puppycrawl.tools.checkstyle.*; // violation

import com.google.common.base.*; // 2 violations
import org.junit.*;

public class InputCustomImportOrderDefault2 {
}
