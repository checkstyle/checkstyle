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

public class InputModuleImportOrderSeparatedMisplaced {
    List<String> list;
}
