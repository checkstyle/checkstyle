/*
UnusedImports
processJavadoc = (default)true
violateExecutionOnNonTightHtml = true


*/
package com.puppycrawl.tools.checkstyle.checks.imports.unusedimports;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.time.Duration;
import java.util.HashSet;
import java.util.Set; // violation 'Unused import - java.util.Set.'
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Random;
import java.net.URI;
import java.util.Collection;
import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.TimeUnit; // violation 'Unused import - java.util.concurrent.TimeUnit.'
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Queue;
import java.util.LinkedList;

/**
 * {@link Collections#emptyEnumeration}
 * {@link CallbackHandler#handle(Callback[])}
 * {@code {@link Set}}
 * {@link TreeSet}
 * {@see TimeUnit#SECONDS}
 * {@link List#size}
 * {@link Random#ints},
 * {@linkplain HashSet}
 * @see Duration#ZERO
 * {@link #setUrl(URI)}
 * {@link #test0(Map.Entry)}
 * {@link #test(LocalDate, Collection)}
 * {@link #extractParameters(double[], Month)}
 * {@link ArrayList#add(Object)}
 * {@link #test(LocalDateTime)}
 * {@link Queue#remove(Object) }
 * {@link LinkedList<Integer>#set(int, E)}
 */
class InputUnusedImportsWithLinkAndMethodTag extends
        InputUnusedImportsWithLinkAndMethodTagSuperClass {

    /**
     * @exception IOException
     */
    public int getD() throws IOException {
        return 1;
    }

    public void test(LocalDate date, Collection<?> collection) {
        // empty method for Javadoc link
    }

    public void test(LocalDateTime dateTime) {
        // empty method for Javadoc link
    }

    public void setUrl(URI uri) {
        // empty method for Javadoc link
    }

    public void test0(java.util.Map.Entry<?, ?> entry) {
        // empty method for Javadoc link
    }

    public void extractParameters(double[] values, Month month) {
        // empty method for Javadoc link
    }

}
