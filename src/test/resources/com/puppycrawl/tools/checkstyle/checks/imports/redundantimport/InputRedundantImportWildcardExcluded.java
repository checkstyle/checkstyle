/*
RedundantImport


*/

package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

import java.lang.*; // violation 'Redundant import from the java.lang package'
import java.lang.String; // violation 'Redundant import from the java.lang package'

import com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.*; // violation 'Redundant import from the same package'
import com.puppycrawl.tools.checkstyle.checks.imports.redundantimport.InputRedundantImportWildcard; // violation 'Redundant import from the same package'

class InputRedundantImportWildcardExcluded {
}
