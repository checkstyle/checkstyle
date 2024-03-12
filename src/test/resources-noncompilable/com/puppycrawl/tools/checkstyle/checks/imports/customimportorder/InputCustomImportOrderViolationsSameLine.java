/*
CustomImportOrder
customImportOrderRules = STATIC###THIRD_PARTY_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = true


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import   java . util . * ;
import    static   java.util.Collections   .*; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'
import static java.lang.String . CASE_INSENSITIVE_ORDER; // violation '.* wrong order. Should be in the .*group, expecting not assigned imports.*'


import java . // 2 violations
        net.Socket    ;

public class InputCustomImportOrderViolationsSameLine {}
