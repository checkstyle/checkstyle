/*
ModuleImportOrder
option = (default)top
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

import module java.sql;
// violation below 'Wrong lexicographical order for module import 'java.desktop''
import module java.desktop;
import java.util.List;

public class InputModuleImportOrderLexOrder {
    List<String> list;
}
