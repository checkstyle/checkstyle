/*
CustomImportOrder
customImportOrderRules = STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = (default)^$
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import java.io.File;
import javax.lang.model.SourceVersion;
import com.google.errorprone.annotations.*; // violation '.* should be separated from previous import group by one line'

public class InputCustomImportOrderImportsContainingJava {}
