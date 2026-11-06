/*
ModuleImportOrder
option = (default)top
separated = (default)false


*/

// non-compiled with javac: Compilable with Java25

package org.checkstyle.suppressionxpathfilter.imports.moduleimportorder;

import java.util.List;
// violation below 'Module import 'java.desktop' violates the configured relative order'
import module java.desktop;

public class InputXpathModuleImportOrderOne {
    List<String> list;
}
