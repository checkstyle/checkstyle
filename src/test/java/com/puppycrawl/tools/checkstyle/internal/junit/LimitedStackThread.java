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

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;

/**
 * Runs a JUnit {@code @Test} method in a worker thread with a requested stack size and timeout.
 * The JVM may round or ignore the stack size hint. Lifecycle methods run on the
 * JUnit thread, so tests must not rely on thread-local state from those methods.
 * Timeout or caller interruption interrupts the daemon worker. A test that ignores
 * interruption may continue running; it cannot prevent the test JVM from exiting.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@ExtendWith(LimitedStackThreadInterceptor.class)
public @interface LimitedStackThread {

    /**
     * Requested stack size in bytes; zero uses the JVM default.
     *
     * @return requested stack size
     */
    long stackSize() default 147_456;

    /**
     * Maximum test method execution time in milliseconds.
     *
     * @return positive timeout
     */
    long timeout() default 10_000;

    /**
     * Worker thread name.
     *
     * @return thread name
     */
    String name() default "LimitedStackSizeThread";

}
