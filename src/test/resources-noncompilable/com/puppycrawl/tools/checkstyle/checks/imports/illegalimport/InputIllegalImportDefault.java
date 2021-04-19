//non-compiled with javac: contains sun package so IDEA marks it as build problem
package com.puppycrawl.tools.checkstyle.checks.imports.illegalimport;

import java.io.*; // ok
import sun.misc.*; // ok
import javax.swing.BorderFactory; // ok
import static java.io.File.listRoots; // ok
import sun.reflect.*; // violation

import java.awt.Component; // ok

/*
 * Config:
 * regexp = true
 * illegalPkgs = "sun.reflect"
 */
class InputIllegalImportDefault {}
