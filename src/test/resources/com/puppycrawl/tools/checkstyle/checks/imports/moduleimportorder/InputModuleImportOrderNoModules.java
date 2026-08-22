/*
ModuleImportOrder
option = (default)top
separated = true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.moduleimportorder;

import java.util.List;

import static java.lang.Math.abs;

public class InputModuleImportOrderNoModules {
    List<String> list = List.of(String.valueOf(abs(1)));
}
