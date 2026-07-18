/*
IllegalImport
illegalPkgs = sun.reflect
illegalClasses = (default)
illegalModules = (default)
regexp = true


*/

// non-compiled with javac: contains specially crafted set of imports that requires classpath
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

import java.io.*;
import sun.misc.*;
import javax.swing.BorderFactory;
import static java.io.File.listRoots;
import sun.reflect.*; // violation 'Illegal import - sun.reflect.*'

import java.awt.Component;

class InputIllegalImportDefault {}
