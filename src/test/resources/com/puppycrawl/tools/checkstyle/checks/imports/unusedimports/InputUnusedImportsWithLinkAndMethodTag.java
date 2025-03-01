/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collections; // violation 'Unused import - java.util.Collections.'
import java.util.List;
import java.util.Set;         // violation 'Unused import - java.util.Set.'

/**
 * @link {Collections::emptyEnumeration}
 * {@link List#size}
 * {@link Set::add}
 *
 */
class InputUnusedImportsWithLinkAndMethodTag {

}
