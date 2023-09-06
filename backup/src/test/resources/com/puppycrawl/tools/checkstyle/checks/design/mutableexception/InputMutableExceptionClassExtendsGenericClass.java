/*
MutableException
format = (default)^.*Exception$|^.*Error$|^.*Throwable$
extendedClassNameFormat = (default)^.*Exception$|^.*Error$|^.*Throwable$


*/

package com.puppycrawl.tools.checkstyle.checks.design.mutableexception;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Convenience class for holding an {@link Exception} in a thread-safe way  // NPE is not expected
 */
public class InputMutableExceptionClassExtendsGenericClass // ok
        extends AtomicReference<Exception> {

    private static final long serialVersionUID = 1L;

}
