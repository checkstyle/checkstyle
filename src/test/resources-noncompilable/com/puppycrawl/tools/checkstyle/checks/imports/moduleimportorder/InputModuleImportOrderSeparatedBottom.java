/*
ModuleImportOrder
option = bottom
separated = true


*/

// non-compiled with javac: Compilable with Java25

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

import java.util.List;
import module java.desktop; // violation ''java.desktop' should be separated from previous imports.'
import module java.sql;

public class InputModuleImportOrderSeparatedBottom {
    List<String> list;
}
