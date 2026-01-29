/*
com.puppycrawl.tools.checkstyle.checks.imports.UnusedImportsCheck
processJavadoc = (default)true

*/

package com.puppycrawl.tools.checkstyle.api.fullident;

import java.util. // violation 'Unused import - java.util.List.'
List;
import java.util. /* Block comment */
Map; // violation above 'Unused import - java.util.Map.'

public class InputFullIdentUnusedImport {
}
