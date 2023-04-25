/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###\
                         SPECIAL_IMPORTS###SAME_PACKAGE(3)
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = ^com\\.google\\..+
specialImportsRegExp = ^org\\..+
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder; import static java.awt.Button.ABORT; import java.util.Map; import java.util.Map.Entry; 
// violation new line seperate group
import com.google.common.annotations.Beta; import com.google.common.collect.HashMultimap; // violation new line seperate group

import org.junit.rules.*;
import org.junit.runner.*;
import org.junit.validator.*;


import com.puppycrawl.tools.checkstyle.*; // violation should be before special imports"org."



import picocli.*; // violation should be before special imports"org."

class InputCustomImportOrderSingleLine {}
