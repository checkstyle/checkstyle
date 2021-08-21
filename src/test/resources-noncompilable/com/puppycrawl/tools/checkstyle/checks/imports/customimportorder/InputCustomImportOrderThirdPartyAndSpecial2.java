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


import static javax.swing.WindowConstants.*; // violation


import java.awt.Button; // violation
import java.awt.Frame;


import java.awt.Dialog; // violation
import java.io.File;

import com.puppycrawl.tools.*; // THIRD_PARTY_PACKAGE


import com.google.common.*; // violation

import org.apache.tools.*; // SPECIAL_IMPORTS
import org.apache.commons.beanutils.*;


import org.apache.commons.collections.*; // violation

import com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportControl; // SAME_PACKAGE(6)


import com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportRule; // violation

import antlr.*; // NONGROUP
import antlr.CommonASTWithHiddenTokens;


import antlr.Token; // violation

import antlr.collections.AST; // violation

public class InputCustomImportOrderThirdPartyAndSpecial2 {
}
