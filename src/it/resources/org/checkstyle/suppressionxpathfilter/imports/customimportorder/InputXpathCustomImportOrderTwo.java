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
import static java.lang.Math.PI;
import java.io.File; // violation ''java.io.File' should be separated from previous import group'
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Scanner;

public class InputXpathCustomImportOrderTwo {
    // code
}
