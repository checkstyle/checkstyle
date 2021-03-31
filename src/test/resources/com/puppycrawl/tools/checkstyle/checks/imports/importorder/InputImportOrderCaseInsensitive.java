package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

/*
 * Config:
 * option = under
 * groups = {}
 * ordered = true
 * separated = false
 * separatedStaticGroups = false
 * caseSensitive = false
 * staticGroups = {}
 * sortStaticImportsAlphabetically = false
 * useContainerOrderingForStatic = false
 */
import java.io.File; // ok
import java.io.InputStream; // ok
import java.io.IOException; // ok
import java.io.Reader; // ok
import static java.io.InputStream.*; // ok
import static java.io.IOException.*; // ok

public class InputImportOrderCaseInsensitive {
}
