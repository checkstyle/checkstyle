/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false
javadocTokens = (default)REFERENCE, PARAMETER_TYPE, THROWS_BLOCK_TAG

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List;
import java.util.Map;
import java.util.PriorityQueue; // violation 'Unused import - java.util.PriorityQueue.'
import java.time.Duration;
import java.util.HashSet;
import java.util.Set; // violation 'Unused import - java.util.Set.'
import java.util.TreeSet;
import java.util.Random;
import java.net.URI;
import java.util.Collection;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit; // violation 'Unused import - java.util.concurrent.TimeUnit.'


/**
 * {@linkplain ...}
 * {@code {@link Set}}
 * {@link TreeSet}
 * {@see TimeUnit#SECONDS}
 * {@link List#size}
 * {@link Random#ints},
 * {@link PriorityQueue#123invalid}
 *  {@link 123invalid}
 * {@linkplain HashSet}
 *  @see Duration#ZERO
 *  {@link #setUrl(URI)}
 *  {@link #Test0(Map.Entry)}
 *  {@link #Test(LocalDate, Collection)}
 */
class InputUnusedImportsWithLinkAndMethodTag extends
        InputUnusedImportsWithLinkAndMethodTagSuperClass {


}
