/*
AvoidModuleImport
excludes = java.desktop
maxAllowedModuleImports = 1

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.base;
import module java.xml;
// violation above 'Only '1' module import is allowed per file.'
import module java.desktop;

public class InputAvoidModuleImportMaxAllowedAndExcluded {

    public void method() {
        int a = 1;
    }
}
