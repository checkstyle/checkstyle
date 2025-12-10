/*
IllegalImport
illegalPkgs = (default)sun
illegalClasses = java.sql.Connection
illegalModules = java.base,java.logging
regexp = (default)false


*/

// non-compiled with javac: Compilable with Java25
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

import module java.base; // violation 'Illegal import - java.base'
import module java.xml;
import module java.sql;
import module java.logging; // violation 'Illegal import - java.logging'
import module java.naming;

import java.sql.Connection; // violation 'Illegal import - java.sql.Connection'
import java.sql.Driver;
import java.util.List;
import java.lang.*;

class InputIllegalImportModuleNoRegex {}
