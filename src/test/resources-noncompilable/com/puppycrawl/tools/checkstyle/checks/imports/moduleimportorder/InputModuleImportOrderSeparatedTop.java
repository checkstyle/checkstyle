/*
ModuleImportOrder
option = (default)top
separated = true


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

import module java.desktop;
import module java.sql;
import java.util.List; // violation ''java.util.List' should be separated from previous imports.'

public class InputModuleImportOrderSeparatedTop {
    List<String> list;
}
