/*
ImportOrder
option = (default)under
groups = java, javax, org
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.checks.imports.importorder;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;

import javax.crypto.Cipher; // violation 'Extra separation in import group before .*'

public class InputImportOrder_WildcardUnspecified {
}
