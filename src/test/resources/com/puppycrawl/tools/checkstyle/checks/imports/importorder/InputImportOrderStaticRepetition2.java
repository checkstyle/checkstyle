package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.lang.Math.*; // ok
import static org.antlr.v4.runtime.CommonToken.*; // violation
import static org.antlr.v4.runtime.CommonToken.*;  // ok

import java.util.Set; // violation

import org.junit.Test; // ok

/*
 * Config:
 * option = top
 * groups = {org, java}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
public class InputImportOrderStaticRepetition2 {
}
