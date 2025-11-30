/*
RedundantImport


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

import module java.base;
import module java.base; // violation 'Duplicate import to line 10'
import module java.logging;
import module java.logging; // violation 'Duplicate import to line 12'

class InputRedundantImportBuiltInModules {
}
