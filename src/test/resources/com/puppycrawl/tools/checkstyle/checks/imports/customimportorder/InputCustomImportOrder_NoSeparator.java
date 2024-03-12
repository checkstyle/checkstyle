/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = false
sortImportsInGroupAlphabetically = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;
import static java.awt.Button.ABORT;
import static javax.swing.WindowConstants.*;
import static java.io.File.createTempFile; // violation 'Wrong lexicographical order for.*. Should be before .*'
import java.util.*;
import java.util.StringTokenizer;
import java.util.concurrent.locks.AbstractOwnableSynchronizer.*;
import java.util.concurrent.locks.*; // violation 'Wrong lexicographical order for.*. Should be before .*'
import org.apache.commons.beanutils.*;

public class InputCustomImportOrder_NoSeparator {
}
