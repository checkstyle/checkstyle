/*
IllegalImport
illegalPkgs = sun.reflect
illegalClasses = (default)
regexp = true


*/

//non-compiled with javac: contains sun package so IDEA marks it as build problem
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

import java.io.*;
import sun.misc.*;
import javax.swing.BorderFactory;
import static java.io.File.listRoots;
import sun.reflect.*; // violation 'Illegal import - sun.reflect.*'

import java.awt.Component;

class InputIllegalImportDefault {}
