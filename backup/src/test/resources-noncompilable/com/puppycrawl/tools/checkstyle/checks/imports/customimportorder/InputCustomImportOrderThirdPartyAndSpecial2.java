/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###\
                         SPECIAL_IMPORTS###SAME_PACKAGE(6)
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = ^com\\..+
specialImportsRegExp = ^org\\..+
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.awt.Button.ABORT; // STATIC
import static java.io.File.createTempFile;


import static javax.swing.WindowConstants.*; // violation 'Extra separation in import group before .*'


import java.awt.Button; // violation '.* should be separated from previous import group .*'
import java.awt.Frame;


import java.awt.Dialog; // violation 'Extra separation in import group before .*'
import java.io.File;

import com.puppycrawl.tools.*; // THIRD_PARTY_PACKAGE


import com.google.common.*; // violation 'Extra separation in import group before .*'

import org.apache.tools.*; // SPECIAL_IMPORTS
import org.apache.commons.beanutils.*;


import org.apache.commons.collections.*; // violation 'Extra separation in import group before .*'

import com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportControl; // SAME_PACKAGE(6)


import com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportRule; // violation 'Extra separation in import group before .*'

import antlr.*; // NONGROUP
import antlr.CommonASTWithHiddenTokens;


import antlr.Token; // violation 'Extra separation in import group before .*'

import antlr.collections.AST; // violation 'Extra separation in import group before .*'

public class InputCustomImportOrderThirdPartyAndSpecial2 {
}
