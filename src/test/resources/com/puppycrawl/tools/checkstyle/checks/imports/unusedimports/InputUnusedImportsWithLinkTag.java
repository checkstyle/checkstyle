/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List; // violation 'Unused import - java.util.List.'
import java.util.Set;
import java.util.TreeSet; // violation 'Unused import - java.util.TreeSet.'

/**
 * @link List
 * {@link Set Set of items}
 * @link TreeSet tree conatins items
 *
 */
class InputUnusedImportsWithLinkTag {

}
