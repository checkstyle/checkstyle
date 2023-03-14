/*
CustomImportOrder
customImportOrderRules = SPECIAL_IMPORTS###STANDARD_JAVA_PACKAGE
standardPackageRegExp = Test
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = unit
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

//non-compiled with javac: special package and requires imports from the same package
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import org.junit.Test;

public class InputCustomImportOrder_MultiplePatternMatches3 { // ok
}
