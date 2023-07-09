/*
CustomImportOrder
customImportOrderRules = STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = com
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import java.util.Map.Entry;
import java.util.Map; // violation 'Wrong lexicographical order for 'java.util.Map' import. Should be before 'java.util.Map.Entry''

import com.google.common.base.*;
import com.google.common.base.internal.*;

public class InputCustomImportOrderCompareImports {
}
