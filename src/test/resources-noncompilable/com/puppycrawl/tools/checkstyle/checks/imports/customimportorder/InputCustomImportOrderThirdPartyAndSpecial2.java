//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.awt.Button.ABORT; // STATIC
import static java.io.File.createTempFile;


import static javax.swing.WindowConstants.*; // warn


import java.awt.Button; // STANDARD_JAVA_PACKAGE warn
import java.awt.Frame;


import java.awt.Dialog; // warn
import java.io.File;

import com.puppycrawl.tools.*; // THIRD_PARTY_PACKAGE


import com.google.common.*; // warn

import org.apache.tools.*; // SPECIAL_IMPORTS
import org.apache.commons.beanutils.*;


import org.apache.commons.collections.*; // warn

import com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportControl; // SAME_PACKAGE(6)


import com.puppycrawl.tools.checkstyle.checks.imports.AbstractImportRule; // warn

import antlr.*; // NONGROUP
import antlr.CommonASTWithHiddenTokens;


import antlr.Token;  // warn

import antlr.collections.AST; // warn

public class InputCustomImportOrderThirdPartyAndSpecial2 {
}
