/*
ImportControl
file = (file)(resource)InputImportControlModuleImportNoRegex.xml
path = (default).*


*/

// non-compiled with javac: reference to non-existent module javaXlogging
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import module java.sql; // violation 'Disallowed import - java.sql.'
import module java.base;
import module java.logging; // violation 'Disallowed import - java.logging.'
import module javaXlogging;

class InputImportControlModuleImportNoRegex {}
