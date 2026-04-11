/*
com.puppycrawl.tools.checkstyle.checks.imports.ImportOrderCheck
option = (default)under
groups = (default)
ordered = (default)true
separated = (default)false
separatedStaticGroups = (default)false
caseSensitive = (default)true
staticGroups = (default)
sortStaticImportsAlphabetically = (default)false
useContainerOrderingForStatic = (default)false
tokens = (default)STATIC_IMPORT


*/

package com.puppycrawl.tools.checkstyle.api.fullident;
import java.util.LinkedList; // ok
import java.util.HashMap; // violation 'Wrong lexicographical order for .* import. Should be before .*.'

public class InputFullIdent {
}
