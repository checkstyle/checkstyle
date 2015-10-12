package com.puppycrawl.tools.checkstyle.checks.design;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Convenience class for holding an {@link Exception} in a thread-safe way
 */
public class InputMutableExceptionClassExtendsGenericClass extends AtomicReference<Exception> { // NPE is not expected

    private static final long serialVersionUID = 1L;

}