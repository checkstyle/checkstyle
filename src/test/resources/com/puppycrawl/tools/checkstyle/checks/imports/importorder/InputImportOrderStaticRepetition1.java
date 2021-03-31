package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = top
 * groups = {org, java}
 * ordered = true
 * separated = true
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = false
 */
import static java.lang.Math.*; // ok
import static org.antlr.v4.runtime.CommonToken.*; // ok
import static org.antlr.v4.runtime.CommonToken.*;  // ok

import java.util.Set; // ok

import org.junit.Test; // ok

public class InputImportOrderStaticRepetition1 {
}
