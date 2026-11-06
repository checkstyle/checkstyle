/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package org.checkstyle.suppressionxpathfilter.imports.customimportorder;

import static java.util.Arrays.sort;
import static java.lang.Math.PI;

import com.puppycrawl.tools.checkstyle.api.DetailAST; // violation 'Import statement'

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

public class InputXpathCustomImportOrderSix {
    // code
}
