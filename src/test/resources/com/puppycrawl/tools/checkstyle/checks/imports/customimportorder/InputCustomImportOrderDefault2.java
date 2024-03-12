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
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for.*. Should be before .*'
import static javax.swing.WindowConstants.*;

import java.util.List; // 2 violations
import java.util.StringTokenizer; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.util.*; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.util.concurrent.AbstractExecutorService; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.util.concurrent.*; // violation 'Wrong lexicographical order for.*. Should be before .*'

import com.google.errorprone.annotations.concurrent.*;
import com.google.errorprone.annotations.*; // violation 'Wrong lexicographical order for.*. Should be before .*'

import com.google.common.base.*; // 2 violations
import org.junit.*;

public class InputCustomImportOrderDefault2 {
}
