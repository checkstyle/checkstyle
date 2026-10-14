///////////////////////////////////////////////////////////////////////////////////////////////
// checkstyle: Checks Java source code and other text files for adherence to a set of rules.
// Copyright (C) 2001-2026 the original author or authors.
//
// This library is free software; you can redistribute it and/or
// modify it under the terms of the GNU Lesser General Public
// License as published by the Free Software Foundation; either
// version 2.1 of the License, or (at your option) any later version.
//
// This library is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
// Lesser General Public License for more details.
//
// You should have received a copy of the GNU Lesser General Public
// License along with this library; if not, write to the Free Software
// Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
///////////////////////////////////////////////////////////////////////////////////////////////

package com.puppycrawl.tools.checkstyle.internal.junit;

import java.lang.reflect.Method;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.InvocationInterceptor;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

/**
 * Executes test methods using the resources declared in {@link LimitedStackThread}.
 * A timeout requests interruption; code that ignores it may continue on the daemon worker.
 */
public final class LimitedStackThreadInterceptor implements InvocationInterceptor {

    /**
     * Intercepts the test while preserving the original failure required by JUnit.
     *
     * @noinspection BadExceptionThrown
     * @noinspectionreason BadExceptionThrown - JUnit requires the original Throwable to be rethrown
     */
    @Override
    public void interceptTestMethod(Invocation<Void> invocation,
                                   ReflectiveInvocationContext<Method> invocationContext,
                                   ExtensionContext extensionContext)
            throws Throwable {
        final LimitedStackThread resources = invocationContext.getExecutable()
                .getAnnotation(LimitedStackThread.class);
        if (resources.stackSize() < 0 || resources.timeout() <= 0) {
            throw new IllegalArgumentException(
                    "Stack size must be nonnegative and timeout positive");
        }
        final AtomicReference<Throwable> failure = new AtomicReference<>();
        final CountDownLatch completed = new CountDownLatch(1);
        final Thread worker = new Thread(null,
                () -> invoke(invocation, failure, completed),
                resources.name(), resources.stackSize());
        // An uncooperative timed-out test must not keep the test JVM alive.
        worker.setDaemon(true);
        worker.start();
        try {
            if (!completed.await(resources.timeout(), TimeUnit.MILLISECONDS)) {
                throw new AssertionError("Test exceeded timeout of " + resources.timeout()
                        + " ms in " + resources.name());
            }
        }
        finally {
            if (worker.isAlive()) {
                worker.interrupt();
            }
        }
        if (failure.get() != null) {
            throw failure.get();
        }
    }

    /**
     * Invokes the test and hands its original failure back to the caller.
     *
     * @param invocation test invocation
     * @param failure original test failure
     * @param completed completion signal
     */
    private static void invoke(Invocation<Void> invocation, AtomicReference<Throwable> failure,
                               CountDownLatch completed) {
        try {
            invocation.proceed();
        }
        // -@cs[IllegalCatch] The test may fail with any exception or assertion error.
        catch (Throwable throwable) {
            failure.set(throwable);
        }
        finally {
            completed.countDown();
        }
    }

}
