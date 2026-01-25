/*
ImportControl
file = (file)(resource)InputImportControlModuleImportWithRegex.xml
path = (default).*


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import module java.sql; // violation 'Disallowed import - java.sql.'
import module java.base;

class InputImportControlModuleImportWithRegex {}
