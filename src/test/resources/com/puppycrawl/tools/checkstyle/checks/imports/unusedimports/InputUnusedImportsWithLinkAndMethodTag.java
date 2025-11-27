/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = (default)false

*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.List;
import java.util.PriorityQueue; // violation 'Unused import - java.util.PriorityQueue.'
import java.time.Duration;
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Random;
import java.net.URI;
import java.util.Collection;
import java.time.LocalDate;
import java.util.concurrent.TimeUnit; // violation 'Unused import - java.util.concurrent.TimeUnit.'


/**
 * {@link TreeSet}
 * {@see TimeUnit#SECONDS}
 * {@link List#size}
 * {@link Random#ints},
 * {@link PriorityQueue#123invalid}
 *  {@link 123invalid}
 * {@linkplain HashSet}
 *  @see Duration#ZERO
 *  {@link #setUrl(URI)}
 *  {@link #Test(LocalDate, Collection)}
 */
class InputUnusedImportsWithLinkAndMethodTag extends
        InputUnusedImportsWithLinkAndMethodTagSuperClass {


}
