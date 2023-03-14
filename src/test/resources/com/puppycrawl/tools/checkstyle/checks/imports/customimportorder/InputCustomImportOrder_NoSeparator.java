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
import static java.io.File.createTempFile; // violation
import java.util.*;
import java.util.StringTokenizer;
import com.puppycrawl.tools.checkstyle.checks.*;
import com.puppycrawl.tools.checkstyle.*; // violation
import org.apache.commons.beanutils.*;

public class InputCustomImportOrder_NoSeparator {
}
