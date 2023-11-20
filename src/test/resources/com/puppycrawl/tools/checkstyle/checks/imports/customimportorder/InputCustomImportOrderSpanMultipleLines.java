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

import org.apache.tools.ant.*; // violation '.* should be separated from previous import group by one line'
import org.apache.commons.beanutils.*;
// comment between import groups


// comment between import groups
import picocli.*; // violation '.* should be separated from previous import group by one line'

// comment within import group
import picocli.CommandLine; // violation 'Extra separation in import group before .*'

class InputCustomImportOrderSpanMultipleLines {}
