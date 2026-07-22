/*
ModuleImportOrder
option = bottom
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

// violation below 'Module import 'java.desktop' violates the configured relative order'
import module java.desktop;
// violation below 'Module import 'java.sql' violates the configured relative order'
import module java.sql;
import java.util.List;

public class InputModuleImportOrderBottomAdjacentViolation {
    List<String> list;
}
