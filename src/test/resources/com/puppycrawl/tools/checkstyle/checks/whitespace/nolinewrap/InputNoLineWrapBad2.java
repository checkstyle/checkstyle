/*
NoLineWrap
tokens = IMPORT, STATIC_IMPORT, CLASS_DEF, METHOD_DEF, ENUM_DEF
skipAnnotations = (default)true


*/

package com.puppycrawl.tools.
    checkstyle.checks.whitespace.nolinewrap;

import java.util.Calendar;

import javax.accessibility. // violation
    AccessibleAttributeSequence;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.math. // violation
		BigInteger.ZERO;

public class // violation
    InputNoLineWrapBad2 {

	public void // violation
	    fooMethod() {
		final int
		    foo = 0;
	}
}

enum // violation
    FooFoo2 {
}

interface
    InterFoo2 {}
