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

import static com.google.common.truth.Truth.assertWithMessage;
import static com.puppycrawl.tools.checkstyle.internal.utils.TestUtil.getExpectedThrowable;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.InvocationInterceptor.Invocation;
import org.junit.jupiter.api.extension.ReflectiveInvocationContext;

public class LimitedStackThreadInterceptorTest {

    private Thread lifecycleThread;

    @BeforeEach
    public void recordLifecycleThread() {
        lifecycleThread = Thread.currentThread();
    }

    @Test
    @LimitedStackThread(stackSize = 1_048_576, name = "AnnotatedTestWorker")
    public void testAnnotationUsesWorkerThread() {
        assertWithMessage("Test method uses its configured worker")
                .that(Thread.currentThread().getName()).isEqualTo("AnnotatedTestWorker");
        assertWithMessage("The worker cannot keep the JVM alive")
                .that(Thread.currentThread().isDaemon()).isTrue();
        assertWithMessage("Lifecycle methods retain the JUnit thread")
                .that(Thread.currentThread()).isNotSameInstanceAs(lifecycleThread);
    }

    @Test
    public void testReturnsOnlyAfterInvocationCompletes() {
        final AtomicReference<Thread> executedThread = new AtomicReference<>();
        assertDoesNotThrow(() -> {
            intercept("configured", () -> {
                executedThread.set(Thread.currentThread());
                return null;
            });
        });
        assertWithMessage("Invocation completed in the configured thread")
                .that(executedThread.get().getName()).isEqualTo("ConfiguredWorker");
        assertWithMessage("Invocation does not use the caller's thread")
                .that(executedThread.get()).isNotSameInstanceAs(Thread.currentThread());
    }

    @Test
    public void testPreservesAssertionFailure() {
        final AssertionError expected = new AssertionError("Original assertion");
        final AssertionError actual = getExpectedThrowable(AssertionError.class,
                () -> {
                    intercept("configured", () -> {
                        throw expected;
                    });
                });
        assertWithMessage("Assertion identity is preserved")
                .that(actual).isSameInstanceAs(expected);
    }

    @Test
    public void testPreservesCheckedException() {
        final Exception expected = new Exception("Original exception");
        final Exception actual = getExpectedThrowable(Exception.class,
                () -> {
                    intercept("configured", () -> {
                        throw expected;
                    });
                });
        assertWithMessage("Exception identity is preserved")
                .that(actual).isSameInstanceAs(expected);
    }

    @Test
    public void testTimeoutInterruptsWorker() throws InterruptedException {
        final CountDownLatch interrupted = new CountDownLatch(1);
        final AssertionError failure = getExpectedThrowable(AssertionError.class,
                () -> {
                    intercept("shortTimeout", () -> {
                        return awaitInterruption(interrupted);
                    });
                });
        assertWithMessage("Timeout includes the configured limit")
                .that(failure).hasMessageThat().contains("100 ms");
        assertWithMessage("Timed-out worker is interrupted")
                .that(interrupted.await(5, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    public void testCallerInterruptionCancelsWorker() throws InterruptedException {
        final CountDownLatch interrupted = new CountDownLatch(1);
        Thread.currentThread().interrupt();
        try {
            getExpectedThrowable(InterruptedException.class, () -> {
                intercept("configured", () -> {
                    return awaitInterruption(interrupted);
                });
            });
        }
        finally {
            Thread.interrupted();
        }
        assertWithMessage("Caller cancellation interrupts its worker")
                .that(interrupted.await(5, TimeUnit.SECONDS)).isTrue();
    }

    @Test
    public void testInvalidStackSize() {
        final IllegalArgumentException failure = getExpectedThrowable(
                IllegalArgumentException.class,
                () -> {
                    intercept("invalidStack", () -> null);
                });
        assertWithMessage("Invalid stack hints have a configuration diagnostic")
                .that(failure).hasMessageThat()
                .isEqualTo("Stack size must be nonnegative and timeout positive");
    }

    @Test
    public void testInvalidTimeout() {
        final IllegalArgumentException failure = getExpectedThrowable(
                IllegalArgumentException.class,
                () -> {
                    intercept("invalidTimeout", () -> null);
                });
        assertWithMessage("Invalid timeouts have a configuration diagnostic")
                .that(failure).hasMessageThat()
                .isEqualTo("Stack size must be nonnegative and timeout positive");
    }

    // -@cs[IllegalThrows] Preserve the throwable declared by JUnit's interceptor API.
    private static void intercept(String methodName, Invocation<Void> invocation) throws Throwable {
        final Method method = ResourceDeclarations.class.getDeclaredMethod(methodName);
        new LimitedStackThreadInterceptor().interceptTestMethod(invocation,
                new MethodInvocationContext(method), null);
    }

    private static Void awaitInterruption(CountDownLatch interrupted) throws InterruptedException {
        try {
            new CountDownLatch(1).await();
        }
        finally {
            interrupted.countDown();
        }
        return null;
    }

    private static final class MethodInvocationContext
            implements ReflectiveInvocationContext<Method> {

        private final Method method;

        private MethodInvocationContext(Method method) {
            this.method = method;
        }

        @Override
        public Class<?> getTargetClass() {
            return ResourceDeclarations.class;
        }

        @Override
        public Method getExecutable() {
            return method;
        }

        @Override
        public List<Object> getArguments() {
            return List.of();
        }

        @Override
        public Optional<Object> getTarget() {
            return Optional.empty();
        }

    }

    // These methods supply annotation metadata through getDeclaredMethod.
    private static final class ResourceDeclarations {

        @LimitedStackThread(stackSize = 0, name = "ConfiguredWorker")
        private void configured() {
            // Only annotation metadata is used; tests supply their own invocation.
        }

        @LimitedStackThread(timeout = 100)
        private void shortTimeout() {
            // Only annotation metadata is used; tests supply their own invocation.
        }

        @LimitedStackThread(stackSize = -1)
        private void invalidStack() {
            // Only annotation metadata is used; tests supply their own invocation.
        }

        @LimitedStackThread(timeout = 0)
        private void invalidTimeout() {
            // Only annotation metadata is used; tests supply their own invocation.
        }

    }

}
