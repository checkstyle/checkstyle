/*
ModuleImportOrder
option = bottom
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

// violation below 'Module import 'java.desktop' violates the configured relative order'
import module java.desktop;
import java.util.List;
import module java.sql;

public class InputModuleImportOrderBottomViolation {
    List<String> list;
}
