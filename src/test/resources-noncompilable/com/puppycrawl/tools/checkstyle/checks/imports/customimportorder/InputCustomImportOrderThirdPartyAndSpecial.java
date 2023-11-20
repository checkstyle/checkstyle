/*
CustomImportOrder
customImportOrderRules = SAME_PACKAGE(3)###THIRD_PARTY_PACKAGE###STATIC###SPECIAL_IMPORTS
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = antlr.*
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

//non-compiled with javac: contains specially crafted set of imports for testing
package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;
import org.apache.commons.io.ByteOrderMark;

import static sun.tools.util.ModifierFilter.ALL_ACCESS;

import com.google.common.collect.HashMultimap;  // violation 'wrong order..* expecting group .* on this line'

import antlr.*;
import antlr.CommonASTWithHiddenTokens;
import antlr.Token;
import antlr.collections.AST;

public class InputCustomImportOrderThirdPartyAndSpecial
{
}
