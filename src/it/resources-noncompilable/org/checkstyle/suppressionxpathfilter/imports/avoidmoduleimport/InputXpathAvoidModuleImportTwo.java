/*
AvoidModuleImport
excludes = (default)
maxAllowedModuleImports = 1


*/

// non-compiled with javac: Compilable with Java25

package org.checkstyle.suppressionxpathfilter.imports.avoidmoduleimport;

import module java.base;
import module java.logging; // violation 'Only '1' module import is allowed per file.'

public class InputXpathAvoidModuleImportTwo {
}
