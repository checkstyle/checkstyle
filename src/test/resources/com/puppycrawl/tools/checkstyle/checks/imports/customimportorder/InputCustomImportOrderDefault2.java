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
import static java.awt.Button.ABORT; // violation 'Wrong order for 'java.awt.Button.ABORT' import.'
import static javax.swing.WindowConstants.*;

import java.util.List; // 2 violations
import java.util.StringTokenizer; // violation 'Wrong order for 'java.util.StringTokenizer' import.'
import java.util.*; // violation 'Wrong order for 'java.util.*' import.'
import java.util.concurrent.AbstractExecutorService; 
// violation 'Wrong order for 'java.util.concurrent.AbstractExecutorService' import.'
import java.util.concurrent.*; // violation 'Wrong order for 'java.util.concurrent.*' import.'

import com.puppycrawl.tools.checkstyle.checks.*;
import com.puppycrawl.tools.checkstyle.*; 
// violation 'Wrong order for 'com.puppycrawl.tools.checkstyle.*' import.'

import com.google.common.base.*; // 2 violations
import org.junit.*;

public class InputCustomImportOrderDefault2 {
}
