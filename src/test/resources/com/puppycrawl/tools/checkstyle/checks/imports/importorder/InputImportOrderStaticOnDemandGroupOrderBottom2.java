package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = bottom
 * groups = {org, java}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = false
 */
import org.antlr.v4.runtime.*; // ok

import java.util.Set; // violation

import static java.lang.Math.*; // violation
import static org.antlr.v4.runtime.CommonToken.*; // ok

public class InputImportOrderStaticOnDemandGroupOrderBottom2
{

}
