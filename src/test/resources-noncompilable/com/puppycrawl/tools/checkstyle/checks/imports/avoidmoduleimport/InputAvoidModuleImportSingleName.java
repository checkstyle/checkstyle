/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = (default)0

*/

// non-compiled with javac: contains specially crafted set of imports for testing

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module someModule;
// violation above 'Using the 'import module' form of import should be avoided'
import module otherModule;
// violation above 'Using the 'import module' form of import should be avoided'

public class InputAvoidModuleImportSingleName {
    void method() {
        String name = "bolt";
        int count = 42;
        System.out.println(name + ": " + count);
    }
}
