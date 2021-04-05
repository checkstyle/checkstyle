package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import org.antlr.v4.runtime.*; // ok

import java.util.Set; // violation

import static java.lang.Math.*; // violation
import static org.antlr.v4.runtime.CommonToken.*; // violation

/*
 * Config:
 * option = above
 * groups = {org, java}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = true
 * useContainerOrderingForStatic = false
 */
public class InputImportOrderStaticOnDemandGroupOrderBottom3
{

}
