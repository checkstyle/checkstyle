/*
CustomImportOrder
customImportOrderRules =
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import java.util.Map;

import java.util.List; // violation 'Wrong order for 'java.util.List' import.'

public class InputCustomImportOrderEmptyRule {
}
