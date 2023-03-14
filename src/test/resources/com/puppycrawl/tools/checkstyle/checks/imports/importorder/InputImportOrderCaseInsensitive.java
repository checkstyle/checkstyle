/*
ImportOrder
option = (default)under
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = false
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.io.File; // ok
import java.io.InputStream; // ok
import java.io.IOException; // ok
import java.io.Reader; // ok
import static java.io.InputStream.*; // ok
import static java.io.IOException.*; // ok

public class InputImportOrderCaseInsensitive {
}
