package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = top
 * groups = {org, java}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = false
 */
import static java.lang.Math.abs; // ok
import static org.antlr.v4.runtime.Recognizer.EOF; // ok

import org.antlr.v4.runtime.*; // violation

import java.util.Set; // violation

public class InputImportOrderStaticGroupOrder1
{

}
