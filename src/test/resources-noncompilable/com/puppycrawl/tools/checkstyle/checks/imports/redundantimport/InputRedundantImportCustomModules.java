/*
RedundantImport


*/

// non-compiled with javac: reference to non-existent modules moduleA and moduleB
package com.puppycrawl.tools.checkstyle.checks.imports.redundantimport;

import module moduleA;
import module moduleA; // violation 'Duplicate import to line 10'
import module moduleB;
import module moduleB; // violation 'Duplicate import to line 12'

class InputRedundantImportCustomModules {
}
