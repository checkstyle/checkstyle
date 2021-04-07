package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import org.antlr.v4.runtime.*; // ok

import static java.lang.Math.PI; // violation
import static org.antlr.v4.runtime.Recognizer.EOF; // ok

import java.util.Set; // ok

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
public class InputImportOrderStaticGroupOrderBottom_Negative2
{

}
