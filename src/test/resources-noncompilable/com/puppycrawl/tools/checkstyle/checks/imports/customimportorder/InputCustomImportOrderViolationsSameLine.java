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
import    static   java.util.Collections   .*; // violation 'Import statement for 'java.util.Collections.*' is in the wrong order. Should be in the 'STATIC' group, expecting not assigned imports on this line.'
import static java.lang.String . CASE_INSENSITIVE_ORDER; // violation 'Import statement for 'java.lang.String.CASE_INSENSITIVE_ORDER' is in the wrong order. Should be in the 'STATIC' group, expecting not assigned imports on this line.'


import java . // 2 violations
        net.Socket    ;

public class InputCustomImportOrderViolationsSameLine {}
