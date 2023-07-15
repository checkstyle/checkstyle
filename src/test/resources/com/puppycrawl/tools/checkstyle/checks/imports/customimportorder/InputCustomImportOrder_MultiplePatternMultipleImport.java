/*
CustomImportOrder
customImportOrderRules = SPECIAL_IMPORTS###STANDARD_JAVA_PACKAGE
standardPackageRegExp = unit
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = Test
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import java.util.Scanner;
import org.junit.Test; // violation 'Import statement for 'org.junit.Test' is in the wrong order. Should be in the 'STANDARD_JAVA_PACKAGE' group, expecting not assigned imports on this line'

public class InputCustomImportOrder_MultiplePatternMultipleImport {
}
