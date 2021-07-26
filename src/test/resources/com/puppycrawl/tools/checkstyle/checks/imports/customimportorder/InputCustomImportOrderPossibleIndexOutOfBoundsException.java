/*
CustomImportOrder
customImportOrderRules = STATIC###SPECIAL_IMPORTS###THIRD_PARTY_PACKAGE###STANDARD_JAVA_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = com.google
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import javax.xml.transform.Source;

import org.w3c.dom.Node; // violation

class InputCustomImportOrderPossibleIndexOutOfBoundsException {}
