/*
ModuleImportOrder
option = (default)top
separated = true


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

import java.util.List;
// violation below 'Module import 'java.desktop' violates the configured relative order'
import module java.desktop;
import java.util.Set;

public class InputModuleImportOrderSeparatedPositionViolation {
    List<String> list;
    Set<String> set;
}
