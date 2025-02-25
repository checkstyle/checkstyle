/*
UnusedImports
processJavadoc = (default)true


*/

package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collections; // violation 'Unused import - java.util.Collections.'
import java.util.List;
import java.util.Set;         // violation 'Unused import - java.util.Set.'
import java.util.ArrayList;
import java.util.PriorityQueue; // violation 'Unused import - java.util.PriorityQueue.'
import java.time.Duration;
import java.util.Queue;         // violation 'Unused import - java.util.Queue.'
import java.util.HashSet;
import java.util.TreeSet;
import java.util.Random;
import java.util.LinkedList;   // violation 'Unused import - java.util.LinkedList.'
import java.net.URI;
import java.util.Collection;
import java.time.LocalDate;
import java.time.LocalDateTime; // violation 'Unused import - java.time.LocalDateTime.'
import java.time.LocalTime;
import java.time.Month;


/**
 * @link {Collections::emptyEnumeration}
 * {@link TreeSet}
 * {@link List#size}
 * {@link Set::add}
 * {@link ArrayList<LocalTime>#add( LocalTime )},
 * {@link LinkedList<Integer>#set(int index(,int element)}
 * {@link Random#ints},
 * {@link PriorityQueue#123invalid}
 *  {@link 123invalid}
 * {@link Queue# }
 * {@linkplain HashSet}
 *  @see Duration#ZERO
 *  {@link #setUrl(URI)}
 *  {@link #Test(LocalDate, Collection)}
 *  {@link #Test(LocalDateTime}}
 *  {@link #extractParameters(double[], Month)}
 */
class InputUnusedImportsWithLinkAndMethodTag extends
        InputUnusedImportsWithLinkAndMethodTagSuperClass {


}
