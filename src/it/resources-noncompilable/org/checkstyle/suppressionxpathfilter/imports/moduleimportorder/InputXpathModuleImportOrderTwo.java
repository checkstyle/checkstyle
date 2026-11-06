/*
ModuleImportOrder
option = (default)top
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

package org.checkstyle.suppressionxpathfilter.imports.moduleimportorder;

import module jdk.compiler;
// violation below 'Wrong lexicographical order for module import 'java.sql''
import module java.sql;

public class InputXpathModuleImportOrderTwo {
}
