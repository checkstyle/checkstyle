////////////////////////////////////////////////////////////////////////////////
// Test case file for checkstyle.
// Created: 2001
////////////////////////////////////////////////////////////////////////////////
package com.puppycrawl.tools.checkstyle.whitespace;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.common.annotations.GwtCompatible;
import com.google.common.base.Function;
import com.google.common.base.Supplier;

class myFoo
{
	private static final String ALLOWS_NULL_KEYS = "";
	private static final String ALLOWS_NULL_VALUES = "";

	@MapFeature.Require({ALLOWS_NULL_KEYS, ALLOWS_NULL_VALUES})
	private void foo()
	{
		int i = 0;
		String[][] x = {{"foo"}};
		int len = 0;
		String sequence = null;
		for (int first = 0; first < len && matches(sequence.charAt(first)); first++) {}
		while (i == 1) {}
		do {} while (i == 1);
	}

	private boolean matches(char charAt)
	{
		return false;
	}
}

interface SupplierFunction<T> extends Function<Supplier<T>, T> {}

class EmptyFoo {}

enum EmptyFooEnum {}

class WithEmptyAnonymous
{
	private void foo()
	{
		MyClass c = new MyClass() {};
	}
}


@Retention(value = RetentionPolicy.CLASS)
@Target(
    ElementType.ANNOTATION_TYPE)
@Documented
@GwtCompatible
@interface Beta {}
@interface MapFeature {
	@interface Require {

		String[] value();
		
	}
}

class MyClass {
	
}