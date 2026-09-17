/*
ModuleImportOrder
option = (default)top
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

import module java.sql;
import module java.sql;
import java.util.List;

public class InputModuleImportOrderDuplicate {
    List<String> list;
}
