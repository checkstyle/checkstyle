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
import static java.awt.Button.ABORT; // violation 'Wrong lexicographical order for\s.*. Should be before 'java.io.File.createTempFile''
import static javax.swing.WindowConstants.*;

import java.util.List; // 2 violations
import java.util.StringTokenizer; // violation 'Wrong lexicographical order for\s.*. Should be before 'javax.swing.WindowConstants.*''
import java.util.*; // violation 'Wrong lexicographical order for\s.*. Should be before 'javax.swing.WindowConstants.*''
import java.util.concurrent.AbstractExecutorService; // violation 'Wrong lexicographical order for\s.*. Should be before 'javax.swing.WindowConstants.*''
import java.util.concurrent.*; // violation 'Wrong lexicographical order for\s.*. Should be before 'javax.swing.WindowConstants.*''

import com.puppycrawl.tools.checkstyle.checks.*;
import com.puppycrawl.tools.checkstyle.*; // violation 'Wrong lexicographical order for\s.*. Should be before 'com.puppycrawl.tools.checkstyle.checks.*''

import com.google.common.base.*; // 2 violations
import org.junit.*;

public class InputCustomImportOrderDefault2 {
}
