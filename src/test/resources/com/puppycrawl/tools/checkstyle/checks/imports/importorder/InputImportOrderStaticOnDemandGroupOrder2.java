package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import static java.lang.Math.*; // ok
import static org.antlr.v4.runtime.CommonToken.*; // ok

import org.antlr.v4.runtime.*; // violation

import java.util.Set; // violation
import org.junit.Test; // violation

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
public class InputImportOrderStaticOnDemandGroupOrder2
{

}
