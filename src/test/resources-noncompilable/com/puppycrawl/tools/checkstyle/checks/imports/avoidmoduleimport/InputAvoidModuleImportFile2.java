/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = 2

*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.avoidmoduleimport;

import module java.net.http;
import module java.logging;

public class InputAvoidModuleImportFile2 {

    public String method() {
        return "hello";
    }
}
