/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package org.checkstyle.suppressionxpathfilter.imports.customimportorder;

import static java.util.Arrays.sort;

import java.io.File;
import java.io.FileInputStream;
import static java.lang.Math.PI; // violation 'Import statement for 'java.lang.Math.PI''
import java.util.HashMap;
import java.util.Scanner;

public class InputXpathCustomImportOrderFive {
    // code
}
