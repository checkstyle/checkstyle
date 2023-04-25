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


import static javax.swing.WindowConstants.*; // violation should be in the same group as static


import java.awt.Button; // violation should be in the same group without new line
import java.awt.Frame;


import java.awt.Dialog; // violation should be in the same group as STANDARD_JAVA_PACKAGE
import java.io.File;

import com.puppycrawl.tools.*; // THIRD_PARTY_PACKAGE


import com.google.common.*; // violation should be in the same as THIRD_PARTY_PACKAGE

import org.apache.tools.*; // SPECIAL_IMPORTS
import org.apache.commons.beanutils.*;


import org.apache.commons.collections.*; // violation should be in the SPECIAL_IMPORTS

import com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportControl; // SAME_PACKAGE(6)


import com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportRule; 
// violation should be in SAME_PACKAGE(6) no line space

import antlr.*; // NONGROUP
import antlr.CommonASTWithHiddenTokens;


import antlr.Token; // violation should be in the same group as NONGROUP, no line space

import antlr.collections.AST; // violation should be in the same group as NONGROUP, no line space

public class InputCustomImportOrderThirdPartyAndSpecial2 {
}
