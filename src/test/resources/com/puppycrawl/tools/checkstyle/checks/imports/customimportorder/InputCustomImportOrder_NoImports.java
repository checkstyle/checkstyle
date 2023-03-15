/*
CustomImportOrder
customImportOrderRules = SAME_PACKAGE(3)
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

//non-compiled with javac: special package and requires imports from the same package
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;


public class InputCustomImportOrder_NoImports { // ok
}
