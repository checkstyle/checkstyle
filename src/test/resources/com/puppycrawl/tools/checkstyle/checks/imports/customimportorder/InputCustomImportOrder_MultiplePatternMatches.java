/*
CustomImportOrder
customImportOrderRules = SPECIAL_IMPORTS###STANDARD_JAVA_PACKAGE
standardPackageRegExp = junit
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = org
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

//non-compiled with javac: special package and requires imports from the same package
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import org.junit.Test;

public class InputCustomImportOrder_MultiplePatternMatches { // ok
}
