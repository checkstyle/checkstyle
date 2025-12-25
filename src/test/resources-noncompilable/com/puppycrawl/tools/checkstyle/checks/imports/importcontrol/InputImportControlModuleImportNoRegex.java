/*
ImportControl
file = (file)(resource)InputImportControlModuleImportNoRegex.xml
path = (default).*


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.importcontrol;

import module java.sql; // violation 'Disallowed import - java.sql.'
import module java.base;
import module java.logging; // violation 'Disallowed import - java.logging.'
import module java.rmi; // violation 'Disallowed import - java.rmi.'
import module javaXrmi;

class InputImportControlModuleImportNoRegex {}
