package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

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
import java.util.LinkedList; // ok
import java.util.HashMap; // violation

class InputImportOrderNonStaticWrongSequence {
}


