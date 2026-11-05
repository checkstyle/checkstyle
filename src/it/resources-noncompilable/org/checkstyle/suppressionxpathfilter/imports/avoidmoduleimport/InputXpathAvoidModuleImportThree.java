/*
AvoidModuleImport
excludes = java.sql
maxAllowedModuleImports = (default)0


*/

// non-compiled with javac: Compilable with Java25

package org.checkstyle.suppressionxpathfilter.imports.avoidmoduleimport;

import module java.base; // violation 'Using the 'import module' form of import should be avoided - java.base.'
import module java.sql;

public class InputXpathAvoidModuleImportThree {
}
