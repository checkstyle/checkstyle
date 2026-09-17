/*
ModuleImportOrder
option = (default)top
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

import java.util.List;
// violation below 'Module import 'java.desktop' violates the configured relative order'
import module java.desktop;
// violation below 'Module import 'java.sql' violates the configured relative order'
import module java.sql;

public class InputModuleImportOrderTopViolation {
    List<String> list;
}
