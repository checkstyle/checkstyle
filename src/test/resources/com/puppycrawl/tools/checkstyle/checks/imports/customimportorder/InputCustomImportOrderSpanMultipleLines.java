/*
CustomImportOrder
customImportOrderRules = STATIC###STANDARD_JAVA_PACKAGE###SPECIAL_IMPORTS###SAME_PACKAGE(3)
standardPackageRegExp = (default)^(java|javax)\.
thirdPartyPackageRegExp = (default).*
specialImportsRegExp = ^org\\..+
separateLineBetweenGroups = (default)true
sortImportsInGroupAlphabetically = (default)false


*/

package com.puppycrawl.tools.checkstyle.checks.imports.customimportorder;

import static java.io.File
    // some comments

    .createTempFile

    // some comments
    ;

// comment between import groups
import java.util.
    Arrays
    ;

// comment within import group

import java.util. // violation 'Extra separation in import group before .*'

    BitSet
    ;
import java.util.

    // some comments
    Collection
    // some comments

    ;
import java.util.HashMap;


// comment within import group
import java.util.HashSet; // violation 'Extra separation in import group before .*'

// comment between import groups

import org.apache.tools.ant.*; // violation 'Extra separation in import group before .*'
import org.apache.commons.beanutils.*;
// comment between import groups


import com.puppycrawl.tools.checkstyle.*; 
// violation 'Wrong order for 'com.puppycrawl.tools.checkstyle.*' import.'


// comment between import groups
import picocli.*; // violation 'Wrong order for 'picocli.*' import.'

// comment within import group
import picocli.CommandLine; // violation 'Wrong order for 'picocli.CommandLine' import.'

class InputCustomImportOrderSpanMultipleLines {}
