/*
ModuleImportOrder
option = bottom
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

import java.util.List;
import static java.io.File.createTempFile;

import module java.desktop;
import module java.sql;

public class InputModuleImportOrderBottom {
    List<String> list;
}
