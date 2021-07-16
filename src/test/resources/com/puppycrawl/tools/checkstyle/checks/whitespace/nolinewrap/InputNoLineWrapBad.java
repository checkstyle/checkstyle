/*
NoLineWrap
tokens = (default)PACKAGE_DEF, IMPORT, STATIC_IMPORT


*/

package com.puppycrawl.tools. // violation
    checkstyle.checks.whitespace.nolinewrap;

import com.puppycrawl.tools.checkstyle.TreeWalker;

import javax.accessibility. // violation
    AccessibleAttributeSequence;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;

import static java.math. // violation
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
