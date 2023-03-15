/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE###THIRD_PARTY_PACKAGE###SPECIAL_IMPORTS
standardPackageRegExp = (default)^(java|javax)\\.
thirdPartyPackageRegExp = ^com\\.google\\..+
specialImportsRegExp = ^org\\..+
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

//non-compiled with javac: contains no package declaration

import static java.awt.Button.ABORT;
import static java.io.File.createTempFile;
import java.util.Map; // violation
import java.util.Map.Entry;
import java.util.NoSuchElementException;

import com.google.common.annotations.Beta;
import com.google.common.collect.HashMultimap;


import org.apache.*; // violation



import antlr.*; // violation


class InputCustomImportOrderNoPackage3 {
}
