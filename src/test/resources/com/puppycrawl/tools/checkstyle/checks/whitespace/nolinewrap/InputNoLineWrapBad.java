/*
NoLineWrap
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT


*/

package com.puppycrawl.tools. // violation 'package statement should not be line-wrapped.'
    checkstyle.checks.whitespace.nolinewrap;

import java.util.Calendar;

import javax.accessibility. // violation 'import statement should not be line-wrapped.'
    AccessibleAttributeSequence;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.math. // violation 'import statement should not be line-wrapped.'
		BigInteger.ZERO;

public class
    InputNoLineWrapBad {

	public void
	    fooMethod() {
		final int
		    foo = 0;
	}
}

enum
    FooFoo {
}

interface
    InterFoo {}
