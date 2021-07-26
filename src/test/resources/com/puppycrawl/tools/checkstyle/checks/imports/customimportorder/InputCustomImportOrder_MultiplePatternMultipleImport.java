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
import org.junit.Test; // violation

public class InputCustomImportOrder_MultiplePatternMultipleImport {
}
