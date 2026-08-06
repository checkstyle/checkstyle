/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = 2

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.base;
import module java.net.http;
import module java.desktop;
// violation above 'Only '2' module import is allowed per file.'

public class InputAvoidModuleImportMaxAllowed {

    public void doSomething() {
        int b = 1;
    }
}
