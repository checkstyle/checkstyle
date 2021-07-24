/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/


package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder; import static java.io.File.createTempFile; import java.util.Map; class InputCustomImportOrderSingleLine2 {} // violation
