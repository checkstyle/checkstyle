/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = true
javadocTokens = REFERENCE, PARAMETER_TYPE, THROWS_BLOCK_TAG, EXCEPTION_BLOCK_TAG

*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collections; // violation 'Unused import - java.util.Collections.'
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue; // violation 'Unused import - java.util.PriorityQueue.'
import java.time.Duration;
import java.util.HashSet;
import java.util.Set; // violation 'Unused import - java.util.Set.'
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Random;
import java.net.URI;
import java.util.Collection;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.concurrent.TimeUnit; // violation 'Unused import - java.util.concurrent.TimeUnit.'
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Queue;
import java.util.LinkedList;

/**
 * {@linkplain ...}
 * @link {Collections::emptyEnumeration}
 * {@link CallbackHandler#handle(Callback[])}
 * {@code {@link Set}}
 * {@link TreeSet}
 * {@see TimeUnit#SECONDS}
 * {@link List#size}
 * {@link Random#ints},
 * {@link PriorityQueue#123invalid}
 * {@link 123invalid}
 * {@linkplain HashSet}
 * @see Duration#ZERO
 * {@link #setUrl(URI)}
 * {@link #Test0(Map.Entry)}
 * {@link #Test(LocalDate, Collection)}
 * {@link #extractParameters(double[], Month)}
 * {@link ArrayList<LocalTime>#add( LocalTime )},
 * {@link #Test(LocalDateTime)}
 * {@link Queue#remove(Object) }
 * {@link LinkedList<Integer>#set(int, E)}
 */
class InputUnusedImportsWithLinkAndMethodTag extends
        InputUnusedImportsWithLinkAndMethodTagSuperClass {

    /**
     * @exception IOException
     */
    public int getD(){
        return 1;
    }


}
