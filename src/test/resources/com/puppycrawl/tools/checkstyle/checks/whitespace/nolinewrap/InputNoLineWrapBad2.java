/*
NoLineWrap
tokens = IMPORT, STATIC_IMPORT, CLASS_DEF, METHOD_DEF, ENUM_DEF


*/

package com.puppycrawl.tools.
    checkstyle.checks.whitespace.nolinewrap;

import java.util.Calendar;

import javax.accessibility. // violation 'import statement should not be line-wrapped.'
    AccessibleAttributeSequence;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.math. // violation 'import statement should not be line-wrapped.'
		BigInteger.ZERO;

public class // violation 'CLASS_DEF statement should not be line-wrapped.'
    InputNoLineWrapBad2 {

	public void // violation 'METHOD_DEF statement should not be line-wrapped.'
	    fooMethod() {
		final int
		    foo = 0;
	}
}

enum // violation 'ENUM_DEF statement should not be line-wrapped.'
    FooFoo2 {
}

interface
    InterFoo2 {}
