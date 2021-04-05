package com.puppycrawl.tools.checkstyle.checks.imports.importorder;
import java.util.LinkedList; // ok
import java.util.HashMap; // violation

/*
 * Config:
 * option = bottom
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = true
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
class InputImportOrderNonStaticWrongSequence {
}


